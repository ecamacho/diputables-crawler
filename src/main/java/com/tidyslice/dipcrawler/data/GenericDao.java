/**
 * 
 */
package com.tidyslice.dipcrawler.data;

import java.util.List;

/**
 * @author erick
 *
 */
public interface GenericDao<E,K> {
	
	E persist(E entity);
	
	E merge(E entity);
	
	void remove(E entity);
	
	E findById(K id);
	
	List<E> findAll( );
	
	List<E> findSublist( int firstResult, int maxResults);
	
	long countAll();
}
