package acme.features.any.toolkit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.toolkit.Toolkit;
import acme.framework.controllers.AbstractController;
import acme.framework.roles.Any;

@Controller
public class AnyToolkitController extends AbstractController<Any, Toolkit>{

	// Internal state ---------------------------------------------------------
	
	@Autowired
	protected AnyToolkitListAllService anyToolkitListAllService;
	
	@Autowired
	protected AnyToolkitShowService anyToolkitShowService;
			
	// Constructors -----------------------------------------------------------

	@PostConstruct
	protected void initialise() {				
		super.addCommand("list", this.anyToolkitListAllService);
		super.addCommand("show", this.anyToolkitShowService);
	}
	
}
