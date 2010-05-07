/**
 * 
 */
package com.tidyslice.dipcrawler.data.hibernate;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;

import com.tidyslice.dipcrawler.data.GenericDao;

/**
 * @author erick
 *
 */
public abstract class GenericDaoHibImpl<E,K> implements GenericDao<E, K> {

	private static final Logger logger = Logger.getLogger( GenericDaoHibImpl.class );
	
	protected SessionFactory sessionFactory;
	
	protected String domainClassName;
	
	@Override
	public long countAll() {
		
		return (Long) sessionFactory.getCurrentSession().
			createQuery("SELECT count(e) from " + domainClassName + " e").uniqueResult(); 
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<E> findAll() {
		
		return sessionFactory.getCurrentSession().
		createQuery("SELECT e from " + domainClassName + " e").list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public E findById(K id) {
		
		return (E) sessionFactory.getCurrentSession().get( domainClassName, (Serializable) id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<E> findSublist(int firstResult, int maxResults) {
		
		return sessionFactory.getCurrentSession().
			createQuery("SELECT e from " + domainClassName + " e").
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
	
	protected String getClassName( E entity )
	{
		return entity.getClass().getName();
		
	}

}
