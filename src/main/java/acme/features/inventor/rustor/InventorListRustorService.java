package acme.features.inventor.rustor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.item.Item;
import acme.entities.rustor.Rustor;
import acme.features.inventor.item.InventorItemRepository;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractListService;
import acme.roles.Inventor;
import acme.utils.ChangeCurrencyLibrary;

@Service
public class InventorListRustorService implements AbstractListService<Inventor, Rustor>{

	// Internal state ---------------------------------------------------------

	@Autowired
	protected InventorRustorRepository inventorRustorRepository;
	
	@Autowired
	protected InventorItemRepository invetorItemRepository;
	
	@Autowired
	protected ChangeCurrencyLibrary changeLibrary;

	// AbstractListService<Inventor, Quantity> interface ---------------------------


	@Override
	public boolean authorise(final Request<Rustor> request) {
		assert request != null;

		boolean result;

		result = request.getPrincipal().hasRole(Inventor.class);

		return result;
	}

	@Override
	public void unbind(final Request<Rustor> request, final Rustor entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		final Item i = this.inventorRustorRepository.findItemByRustorId(entity.getId());
		
		request.unbind(entity, model, "code", "theme", "share");
		model.setAttribute("itemName", i.getName());
		
	}

	@Override
	public Collection<Rustor> findMany(final Request<Rustor> request) {
		assert request != null;

		final Collection<Rustor> result;

		result = this.inventorRustorRepository.findAllMine(request.getPrincipal().getAccountId());
		
		final String defaultCurrency = this.invetorItemRepository.findDefaultCurrency();
		
		result.stream()
			.filter(c->!(c.getShare().getCurrency().equals(defaultCurrency)))
			.forEach(c->c.setShare(this.changeLibrary.computeMoneyExchange(c.getShare(), defaultCurrency).getTarget()));
		
		return result;
	}
}
