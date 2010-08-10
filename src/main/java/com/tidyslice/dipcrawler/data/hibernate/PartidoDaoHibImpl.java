/**
 * 
 */
package com.tidyslice.dipcrawler.data.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tidyslice.dipcrawler.data.PartidoDao;
import com.tidyslice.dipcrawler.domain.Partido;

/**
 * @author erick
 *
 */
@Repository("partidoDao")
//@Scope("prototype")
public class PartidoDaoHibImpl extends GenericDaoHibImpl<Partido, String> implements
		PartidoDao {

	
	
	@Autowired
	public void setSessionFactory( SessionFactory sessionFactory )
	{
		this.sessionFactory = sessionFactory;
	}
	
	

	public String getDomainClassName() {
		return Partido.class.getName();
	}

	@Override
	public String getPK(Partido entity) {		
		return entity.getNombre();
	}

}
