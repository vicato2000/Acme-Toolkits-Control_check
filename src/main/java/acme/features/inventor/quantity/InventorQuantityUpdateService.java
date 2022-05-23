package acme.features.inventor.quantity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.quantity.Quantity;
import acme.features.inventor.item.InventorItemRepository;
import acme.features.inventor.toolkit.InventorToolkitRepository;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractUpdateService;
import acme.roles.Inventor;;


@Service
public class InventorQuantityUpdateService implements AbstractUpdateService<Inventor, Quantity>{
	
	@Autowired
	protected InventorQuantityRepository inventorQuantityRepository;
	
	@Autowired
	protected InventorItemRepository inventorItemRepository;
	
	@Autowired
	protected InventorToolkitRepository inventorToolkitRepository;

	@Override
	public boolean authorise(final Request<Quantity> request) {
		assert request != null;
		
		boolean result = true;
		
		int quantityId;
		Quantity quantity;
		Inventor inventor;
		
		quantityId = request.getModel().getInteger("id");
		quantity = this.inventorQuantityRepository.findQuantityById(quantityId);
		inventor = quantity.getToolkit().getInventor();
		
		result = !quantity.getToolkit().isPublished() && request.isPrincipal(inventor);
		
		return result;
	}

	@Override
	public void bind(final Request<Quantity> request, final Quantity entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		request.bind(entity,errors, "amount");
		
	}

	@Override
	public void unbind(final Request<Quantity> request, final Quantity entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "item.name", "toolkit.id","amount");
		model.setAttribute("toolkit.inventor.fullName", entity.getToolkit().getInventor().getIdentity().getFullName());
	}

	@Override
	public Quantity findOne(final Request<Quantity> request) {
		
		assert request != null;
		
		int id;
		Quantity result;
		
		id = request.getModel().getInteger("id");
		
		result = this.inventorQuantityRepository.findQuantityById(id);
		
		return result;
	}

	@Override
	public void validate(final Request<Quantity> request, final Quantity entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		if(!errors.hasErrors("amount")) {
			boolean amountPositive;
			
			amountPositive = entity.getAmount() > 0;
			
			errors.state(request, amountPositive, "amount", "inventor.quantity.form.error.amount");
		}
		
	}

	@Override
	public void update(final Request<Quantity> request, final Quantity entity) {
		assert request != null;
		assert entity != null;
		
		this.inventorQuantityRepository.save(entity);
		
	}
	
	

	
	


}
