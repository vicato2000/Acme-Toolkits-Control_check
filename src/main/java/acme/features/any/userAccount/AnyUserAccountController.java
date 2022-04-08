package acme.features.any.userAccount;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.framework.controllers.AbstractController;
import acme.framework.entities.UserAccount;
import acme.framework.roles.Any;

@Controller
public class AnyUserAccountController extends AbstractController<Any,UserAccount> {

	// Internal state ---------------------------------------------------------

		@Autowired
		protected AnyUserAccountListAllService anyUserAccountListAllService;
		
		@Autowired
		protected AnyUserAccountShowService anyUserAccountShowService;


		// Constructors -----------------------------------------------------------


		@PostConstruct
		protected void initialise() {
			super.addCommand("list", this.anyUserAccountListAllService);
			super.addCommand("show", this.anyUserAccountShowService);
		}
}