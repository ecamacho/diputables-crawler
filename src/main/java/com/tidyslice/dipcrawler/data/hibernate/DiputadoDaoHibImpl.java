/**
 * 
 */
package com.tidyslice.dipcrawler.data.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.tidyslice.dipcrawler.data.DiputadoDao;
import com.tidyslice.dipcrawler.domain.Diputado;

/**
 * @author erick
 *
 */
@Repository("diputadoDao")
@Scope("prototype")
public class DiputadoDaoHibImpl extends GenericDaoHibImpl<Diputado, String> implements DiputadoDao {

	
	
	protected String domainClassName = Diputado.class.getName();
	
	@Autowired
	public void setSessionFactory( SessionFactory sessionFactory )
	{
		this.sessionFactory = sessionFactory;
	}
}
