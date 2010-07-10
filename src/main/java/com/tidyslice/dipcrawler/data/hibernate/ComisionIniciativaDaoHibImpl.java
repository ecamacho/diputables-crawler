/**
 * 
 */
package com.tidyslice.dipcrawler.data.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.tidyslice.dipcrawler.data.ComisionIniciativaDao;
import com.tidyslice.dipcrawler.domain.ComisionIniciativa;

/**
 * @author erick
 *
 */
@Repository("comisionIniciativaDao")
@Scope("prototype")
public class ComisionIniciativaDaoHibImpl extends GenericDaoHibImpl<ComisionIniciativa, Long>
		implements ComisionIniciativaDao {

	protected String domainClassName = ComisionIniciativa.class.getName();
	
	@Autowired
	public void setSessionFactory( SessionFactory sessionFactory )
	{
		this.sessionFactory = sessionFactory;
	}

}
