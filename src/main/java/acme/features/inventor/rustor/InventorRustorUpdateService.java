package acme.features.inventor.rustor;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.configuration.Configuration;
import acme.entities.item.Item;
import acme.entities.rustor.Rustor;
import acme.features.inventor.item.InventorItemRepository;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.datatypes.Money;
import acme.framework.services.AbstractUpdateService;
import acme.roles.Inventor;
import acme.utils.AcceptedCurrencyLibrary;
import acme.utils.ChangeCurrencyLibrary;
import main.AntiSpam;

@Service
public class InventorRustorUpdateService implements AbstractUpdateService<Inventor, Rustor>{

	@Autowired
	protected InventorRustorRepository inventorRustorRepository;
	
	@Autowired
	protected InventorItemRepository inventorItemRepository;
	
	@Autowired
	protected ChangeCurrencyLibrary changeLibrary;
	
	@Override
	public boolean authorise(final Request<Rustor> request) {
		assert request != null;
		
		boolean result;
		
		final int rustorId;
		final Rustor rustor;
		final Inventor inventor;
		
		rustorId = request.getModel().getInteger("id");
		rustor = this.inventorRustorRepository.findRustorById(rustorId);
		
		inventor = rustor.getItem().getInventor();
		
		result = request.isPrincipal(inventor);
		
		return result;
	}

	@Override
	public void bind(final Request<Rustor> request, final Rustor entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		request.bind(entity,errors, "theme", "statement", "startDate", "finishDate", "share", "moreInfo");
		
		final Model model = request.getModel();
		
		if(model.hasAttribute("defaultCurrency")){
			final Money m = model.getAttribute("defaultCurrency", Money.class);
			entity.setShare(m);
		}
		
		
	}

	@Override
	public void unbind(final Request<Rustor> request, final Rustor entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		final Item i = this.inventorRustorRepository.findItemByRustorId(entity.getId());
		
		request.unbind(entity, model, "code", "theme", "statement", "creationMoment", "startDate", "finishDate", "share", "moreInfo");
		model.setAttribute("itemName", i.getName());
		final String defaultCurrency = this.inventorItemRepository.findDefaultCurrency();
		
		if(!(entity.getShare().getCurrency().equals(defaultCurrency))) {
			
			final Money m = this.changeLibrary.computeMoneyExchange(entity.getShare(), defaultCurrency).getTarget();
			
			model.setAttribute("showDefaultCurrency", true);
			model.setAttribute("budget",m);
			model.setAttribute("defaultCurrency", entity.getShare());
		}
		
	}

	@Override
	public Rustor findOne(final Request<Rustor> request) {
		assert request != null;
		
		Rustor result;
		int rustorId;
		
		rustorId = request.getModel().getInteger("id");
		result = this.inventorRustorRepository.findRustorById(rustorId);
		
		return result;
	}

	@Override
	public void validate(final Request<Rustor> request, final Rustor entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		final List<String> acceptedCurrencies = AcceptedCurrencyLibrary.getAcceptedCurrencies(this.inventorItemRepository.findAcceptedCurrencies());
		
		boolean spamWord;
		final boolean spamWordTitle;
		
		final Configuration configuration = this.inventorItemRepository.configuration();
		final AntiSpam antiSpam = new AntiSpam(configuration.getStrongSpamWords(), configuration.getStrongSpamThreshold(), configuration.getWeakSpamWords(), configuration.getWeakSpamThreshold(), entity.getStatement());
		spamWord = antiSpam.getAvoidSpam();
		errors.state(request, !spamWord, "statement", "inventor.rustor.form.error.spamWord");
		
		final AntiSpam antiSpamTitle = new AntiSpam(configuration.getStrongSpamWords(), configuration.getStrongSpamThreshold(), configuration.getWeakSpamWords(), configuration.getWeakSpamThreshold(), entity.getTheme());
		spamWordTitle = antiSpamTitle.getAvoidSpam();
		errors.state(request, !spamWordTitle, "theme", "inventor.rustor.form.error.spamWord");
		
		if(!(request.getModel().hasAttribute("defaultCurrency"))){
			
			if(!errors.hasErrors("share")) {
				boolean acceptedCurrency;
				
				acceptedCurrency = acceptedCurrencies.contains(entity.getShare().getCurrency());
				
				errors.state(request, acceptedCurrency, "share", "inventor.rustor.form.error.acceptedCurrency");
				
				boolean positiveValue;
				
				positiveValue = entity.getShare().getAmount()>0;
				
				errors.state(request, positiveValue, "share", "inventor.rustor.form.error.positiveValue");
			}
			
		}else {
			
			if(!errors.hasErrors("defaultCurrency")) {
				boolean acceptedCurrency;
				
				acceptedCurrency = acceptedCurrencies.contains(entity.getShare().getCurrency());
				
				errors.state(request, acceptedCurrency, "defaultCurrency", "inventor.rustor.form.error.acceptedCurrency");
				
				boolean positiveValue;
				
				positiveValue = entity.getShare().getAmount()>0;
				
				errors.state(request, positiveValue, "defaultCurrency", "inventor.rustor.form.error.positiveValue");
			}
			
		}
		
		if (!errors.hasErrors("startDate")) {
			
			final Calendar calendar = Calendar.getInstance();
			Date minimumPeriodStart;
			
			calendar.setTime(entity.getCreationMoment());
			calendar.add(Calendar.MONTH, 1);
			minimumPeriodStart = calendar.getTime();
			
			
			errors.state(request, entity.getStartDate().after(minimumPeriodStart), "startDate", "inventor.rustor.form.error.acceptedPeriodTime.start");
			
		}
		
		if (!errors.hasErrors("finishDate")) {
			
			final Calendar calendar = Calendar.getInstance();
			Date minimumPeriodFinish;
			
			calendar.setTime(entity.getStartDate());
			calendar.add(Calendar.WEEK_OF_YEAR, 1);
			minimumPeriodFinish = calendar.getTime();
			
			
			errors.state(request, entity.getFinishDate().after(minimumPeriodFinish), "finishDate", "inventor.rustor.form.error.acceptedPeriodTime.finish");
			
		}
		
	}

	@Override
	public void update(final Request<Rustor> request, final Rustor entity) {
		assert request != null;
		assert entity != null;
		
		this.inventorRustorRepository.save(entity);
		
	} 
	

}
