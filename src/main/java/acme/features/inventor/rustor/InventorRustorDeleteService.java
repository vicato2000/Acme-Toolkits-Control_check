package acme.features.inventor.rustor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.rustor.Rustor;
import acme.features.inventor.item.InventorItemRepository;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractDeleteService;
import acme.roles.Inventor;

@Service
public class InventorRustorDeleteService implements AbstractDeleteService<Inventor, Rustor>{
	
	@Autowired
	protected InventorRustorRepository inventorRustorRepository;
	
	@Autowired
	protected InventorItemRepository inventorItemRepository;

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
		
		request.bind(entity,errors,"code", "theme", "statement", "creationMoment", "startDate", "finishDate", "share", "moreInfo");
	}

	@Override
	public void unbind(final Request<Rustor> request, final Rustor entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "code", "theme", "statement", "creationMoment", "startDate", "finishDate", "share", "moreInfo");
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
		
		
	}

	@Override
	public void delete(final Request<Rustor> request, final Rustor entity) {
		assert request != null;
		assert entity != null;
		
		this.inventorRustorRepository.delete(entity);
		
	}
	
	
	

}
