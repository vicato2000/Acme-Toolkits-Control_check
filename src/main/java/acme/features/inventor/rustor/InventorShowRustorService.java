package acme.features.inventor.rustor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.item.Item;
import acme.entities.rustor.Rustor;
import acme.features.inventor.item.InventorItemRepository;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.roles.Inventor;
import acme.utils.ChangeCurrencyLibrary;

@Service
public class InventorShowRustorService implements AbstractShowService<Inventor,Rustor> {
	
	// Internal state ---------------------------------------------------------

	@Autowired
	protected InventorRustorRepository inventorRustorRepository;
	
	@Autowired
	protected InventorItemRepository inventorItemRepository;
	
	@Autowired
	protected ChangeCurrencyLibrary changeLibrary;

	// AbstractShowService<Authenticated, Item> interface ---------------------------


	@Override
	public boolean authorise(final Request<Rustor> request) {
		assert request != null;
		
		boolean result;
		
		final int id = request.getModel().getInteger("id");
		
		final Rustor r = this.inventorRustorRepository.findRustorById(id);
		
		result = request.getPrincipal().getAccountId() == r.getItem().getInventor().getUserAccount().getId();
		
		return result;
	}

	@Override
	public Rustor findOne(final Request<Rustor> request) {
		assert request != null;

		Rustor result;
		final int rustorId;

		rustorId = request.getModel().getInteger("id");
		
		//itemId = request.getModel().getInteger("itemId");
		result = this.inventorRustorRepository.findRustorById(rustorId);
		
		final String defaultCurrency = this.inventorItemRepository.findDefaultCurrency();
		
		if(!(result.getShare().getCurrency().equals(defaultCurrency))){
			result.setShare(this.changeLibrary.computeMoneyExchange(result.getShare(), defaultCurrency).getTarget());
		}

		return result;
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
		
		final Rustor rustor = this.inventorRustorRepository.findRustorById(entity.getId());
		
		if(!(rustor.getShare().getCurrency().equals(defaultCurrency))) {
			model.setAttribute("showDefaultCurrency", true);
			model.setAttribute("defaultCurrency",rustor.getShare());
		}
		
	}
}
