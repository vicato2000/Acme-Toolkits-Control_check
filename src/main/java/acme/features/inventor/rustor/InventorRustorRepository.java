package acme.features.inventor.rustor;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.item.Item;
import acme.entities.rustor.Rustor;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Inventor;

@Repository
public interface InventorRustorRepository extends AbstractRepository{
	
	
	@Query("SELECT c FROM Rustor c WHERE c.item.inventor.userAccount.id = :inventorId")
	Collection<acme.entities.rustor.Rustor> findAllMine(int inventorId);
	
	
	@Query("SELECT c FROM Rustor c WHERE c.id = :rustorId")
	Rustor findRustorById(int rustorId);
	
	
	@Query("SELECT c.item FROM Rustor c WHERE c.id = :rustorId")
	Item findItemByRustorId(int rustorId);
	
	@Query("SELECT c.code FROM Rustor c")
	List<String> findAllCodes();
	
	@Query("SELECT i FROM Item i WHERE i.id = :itemId")
	Item findItemByItemId(int itemId);
	
	@Query("SELECT c FROM Rustor c WHERE c.item.id = :itemId")
	Rustor findRustorByItemId(int itemId);
	
	@Query("SELECT i FROM Inventor i WHERE i.userAccount.id = :inventorId")
	Inventor findInventorByInventorId(int inventorId);
}