package org.libermundi.theorcs.services.base;

import java.io.Serializable;

import org.libermundi.theorcs.domain.base.Identifiable;
import org.springframework.data.repository.CrudRepository;


/**
 * Interface of any Manager Service
 *
 */
public interface Service<T extends Identifiable<I>,I extends Serializable> {

	/**
	 * Get Object by Id
	 * @param id
	 * @return Object
	 */
	T findOne(I id);
	
	/**
	 * Duplicate the Object
	 * @param original Object to duplicate
	 * @return duplicated Object
	 */
	T duplicate(T original);

	/**
	 * Duplicate the Object with the ability to reset the Primary Key
	 * @param original Object to duplicate
	 * @param resetPrimaryKey boolean to reset or not the Primary  Key in duplicating the object
	 * @return duplicated Object
	 */
	T duplicate(T original, boolean resetPrimaryKey);
	
	/**
	 * Delete the object defined by id
	 * @param id int
	 */
	void delete(I id);
	
	/**
	 * Delete the object defined by id
	 * @param object object
	 */
	void delete(T entity);
	
	/**
	 * Get all Object and return them into a List
	 * @return List of Object 
	 */
	Iterable<T> findAll();
	
	/**
	 * Make a simple "select count(*)"
	 * @return the total numbers of managed items in the database
	 */
	long count();
	
	/**
	 * Getter of the DAO of the Service Manager
	 * @return the instance of the Dao used by the Manager
	 */
	CrudRepository<T,I> getRepository();

	/**
	 * Setter of the DAO of the Service Manager
	 * @param repository
	 */
	void setRepository(CrudRepository<T,I> repository);
	
	/**
	 * return a newly created T Object
	 */
	T createNew();
	
	/**
	 * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
	 * entity instance completely.
	 * 
	 * @param entity
	 * @return the saved entity
	 */
	<S extends T> S save(S entity);

}
