/**
 * 
 */
package com.tidyslice.dipcrawler.data.hibernate;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;

import com.tidyslice.dipcrawler.data.GenericDao;

/**
 * @author erick
 *
 */
public abstract class GenericDaoHibImpl<E,K> implements GenericDao<E, K> {

	
	
	protected SessionFactory sessionFactory;
	
	protected String domainClassName;
	
	@Override
	public long countAll() {
		
		return (Long) sessionFactory.getCurrentSession().
			createQuery("SELECT count(e) from " + getDomainClassName( ) + " e").uniqueResult(); 
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<E> findAll() {
		
		return sessionFactory.getCurrentSession().
		createQuery("SELECT e from " + getDomainClassName( ) + " e").list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public E findById(K id) {
		if( id != null ) {
			return (E) sessionFactory.getCurrentSession().get( getDomainClassName( ), (Serializable) id);
		} else {
			return null;
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<E> findSublist(int firstResult, int maxResults) {
		
		return sessionFactory.getCurrentSession().
			createQuery("SELECT e from " + getDomainClassName( ) + " e").
			setFirstResult(firstResult).setMaxResults(maxResults).list();
	}

	@Override
	public E merge(E entity) {
		sessionFactory.getCurrentSession().merge( entity );
		sessionFactory.getCurrentSession().flush();
		return entity;
	}

	@Override
	public E persist(E entity) {
		
		sessionFactory.getCurrentSession().persist( entity);
		
		return entity;
	}

	@Override
	public void remove(E entity) {
		sessionFactory.getCurrentSession().delete( entity );
		
	}
	
	public abstract String getDomainClassName( );
	

	public abstract K getPK( E entity );
}
