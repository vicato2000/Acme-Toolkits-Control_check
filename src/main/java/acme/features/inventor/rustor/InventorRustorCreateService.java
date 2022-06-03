package acme.features.inventor.rustor;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import acme.framework.services.AbstractCreateService;
import acme.roles.Inventor;
import acme.utils.AcceptedCurrencyLibrary;
import main.AntiSpam;

@Service
public class InventorRustorCreateService implements AbstractCreateService<Inventor, Rustor>{
	
	@Autowired
	protected InventorRustorRepository repository;
	
	@Autowired
	protected InventorItemRepository inventorItemRepository;

	@Override
	public boolean authorise(final Request<Rustor> request) {
		assert request != null;
		
		boolean result;
		
		final int itemId;
		final Item item;
		final Inventor inventor;
		
		itemId = request.getModel().getInteger("itemId");
		item = this.inventorItemRepository.findItemByItemId(itemId);
		
		inventor = item.getInventor();
		
		result = request.isPrincipal(inventor) && this.repository.findRustorByItemId(itemId)==null;
		
		return result;
	}

	@Override
	public void bind(final Request<Rustor> request, final Rustor entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		request.bind(entity, errors,"code", "theme", "statement", "creationMoment", "startDate", "finishDate", "share", "moreInfo");
		
		
	}

	@Override
	public void unbind(final Request<Rustor> request, final Rustor entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model,"code", "theme", "statement", "creationMoment", "startDate", "finishDate", "share","moreInfo");
		
		model.setAttribute("itemName", this.inventorItemRepository.findItemByItemId(request.getModel().getInteger("itemId")).getName());
		model.setAttribute("itemId", request.getModel().getInteger("itemId"));
	}

	@Override
	public Rustor instantiate(final Request<Rustor> request) {
		assert request != null;
		
		int itemId;
		Item item;
		
		itemId = request.getModel().getInteger("itemId");
		
		item = this.repository.findItemByItemId(itemId);
		
		final Rustor result = new Rustor();
		
		result.setCreationMoment(new Date());
		
		result.setItem(item);
		
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
		
		if (!errors.hasErrors("code")) {
			
			final Calendar c = new GregorianCalendar();
			c.setTime(entity.getCreationMoment());
			
			final int yearInt = c.get(Calendar.YEAR);
			final int monthInt = c.get(Calendar.MONTH);
			final int dayInt = c.get(Calendar.DAY_OF_MONTH);
			
			final String yearString = String.valueOf(yearInt).substring(2);
			
			String monthString = "";
			if(c.get(Calendar.MONTH) < 9) {
				 monthString += "0"+ (c.get(Calendar.MONTH) + 1);
			}else {
				monthString += c.get(Calendar.MONTH);
			}
			
			String dayString = "";
			if(c.get(Calendar.DAY_OF_MONTH) < 9) {
				dayString += "0"+ (c.get(Calendar.DAY_OF_MONTH));
			}else {
				dayString += c.get(Calendar.DAY_OF_MONTH);
			}
			
			final String codeYear = entity.getCode().split("-")[1].substring(4,6); 
			final String codeMonth = entity.getCode().split("-")[1].substring(2,4); 
			final String codeDay = entity.getCode().split("-")[1].substring(0,2); 
			
			errors.state(request, codeYear.equals(yearString) && codeMonth.equals(monthString) && codeDay.equals(dayString) , "code", "inventor.rustor.form.error.invalidCode");
			
		}
		
	}

	@Override
	public void create(final Request<Rustor> request, final Rustor entity) {
		assert request != null;
		assert entity != null;
				
		this.repository.save(entity);
		
	}
	
	

}
