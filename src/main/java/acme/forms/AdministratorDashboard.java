package acme.forms;

import java.io.Serializable;
import java.util.Map;

import org.springframework.data.util.Pair;

import acme.entities.patronage.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdministratorDashboard implements Serializable{
	
	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	int 							  numberOfComponents;
	Map<Pair<String, String>, Double> averageRetailPriceOfComponentsByTechnologyAndCurrency;
	Map<Pair<String, String>, Double> deviationRetailPriceOfComponentsByTechnologyAndCurrency;
	Map<Pair<String, String>, Double> minRetailPriceOfComponentsByTechnologyAndCurrency;
	Map<Pair<String, String>, Double> maxRetailPriceOfComponentsByTechnologyAndCurrency;
	int 							  numberOfTools;
	Map<String, Double>				  averageRetailPriceOfToolsByCurrency;
	Map<String, Double>				  deviationRetailPriceOfToolsByCurrency;
	Map<String, Double>				  minRetailPriceOfToolsByCurrency;
	Map<String, Double>			      maxRetailPriceOfToolsByCurrency;
	Map<Status, Integer>			  numberOfPatronagesByStatus;
	Map<Status, Double>				  averageBudgetOfPatronagesByStatus;
	Map<Status, Double>				  deviationBudgetOfPatronagesByStatus;
	Map<Status, Double>				  minBudgetOfPatronagesByStatus;
	Map<Status, Double>				  maxBudgetOfPatronagesByStatus;
	double							  ratioOfArtefactsWithRustor;
	Map<String, Double>				  averageRustorOfArtefactByCurrency;
	Map<String, Double>				  deviationRustorOfArtefactByCurrency;
	Map<String, Double>				  maxRustorOfArtefactByCurrency;
	Map<String, Double>				  minRustorOfArtefactByCurrency;
	

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
}
