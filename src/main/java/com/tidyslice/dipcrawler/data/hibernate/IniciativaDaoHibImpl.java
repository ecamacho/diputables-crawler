/**
 * 
 */
package com.tidyslice.dipcrawler.data.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.tidyslice.dipcrawler.data.IniciativaDao;
import com.tidyslice.dipcrawler.domain.Iniciativa;

/**
 * @author erick
 *
 */
@Repository("iniciativaDao")
@Scope("prototype")
public class IniciativaDaoHibImpl extends GenericDaoHibImpl<Iniciativa, String> implements
		IniciativaDao {

	
	@Autowired
	public void setSessionFactory( SessionFactory sessionFactory )
	{
		this.sessionFactory = sessionFactory;
	}

	@Override
	public String getDomainClassName() {		
		return Iniciativa.class.getName();
	}

	@Override
	public String getPK(Iniciativa entity) {
		
		return entity.getId();
	}
}
