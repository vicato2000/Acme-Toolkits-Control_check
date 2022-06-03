package acme.features.inventor.rustor;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.rustor.Rustor;
import acme.framework.controllers.AbstractController;
import acme.roles.Inventor;

@Controller
public class InventorRustorController extends AbstractController<Inventor, Rustor> {

	// Internal state ---------------------------------------------------------
	
	@Autowired
	protected InventorListRustorService	listChimpumService;
	
	@Autowired
	protected InventorShowRustorService showChimpumService;
	
	@Autowired
	protected InventorRustorCreateService createChimpumService;
	
	@Autowired
	protected InventorRustorUpdateService updateChimpumService;
	
	@Autowired
	protected InventorRustorDeleteService deleteChimpumService;
	
	// Constructors -----------------------------------------------------------

	@PostConstruct
	protected void initialise() {
		super.addCommand("list", this.listChimpumService);
		super.addCommand("show", this.showChimpumService);
		super.addCommand("create", this.createChimpumService);
		super.addCommand("update", this.updateChimpumService);
		super.addCommand("delete", this.deleteChimpumService);
	}
}
