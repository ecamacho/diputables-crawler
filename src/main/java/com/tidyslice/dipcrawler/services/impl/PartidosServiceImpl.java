/**
 * 
 */
package com.tidyslice.dipcrawler.services.impl;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.tidyslice.dipcrawler.data.PartidoDao;
import com.tidyslice.dipcrawler.domain.Partido;
import com.tidyslice.dipcrawler.services.PartidosService;

/**
 * @author erick
 *
 */
@Service("partidosService")
public class PartidosServiceImpl implements PartidosService {

	@Autowired
	private PartidoDao partidoDao;

	@Autowired
	private SessionFactory sessionFactory;
	/* (non-Javadoc)
	 * @see com.tidyslice.dipcrawler.services.PartidosService#persistPartidos(java.util.concurrent.ConcurrentHashMap)
	 */
	@Override
	@Transactional
	public void persistPartidos(ConcurrentHashMap<String, Partido> partidos) {
		
		TransactionSynchronizationManager.unbindResource(sessionFactory);
		Session session = SessionFactoryUtils.getNewSession(sessionFactory);
		
		TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
		Iterator<String> iterator = partidos.keySet().iterator();
		while( iterator.hasNext() ) {
			partidoDao.persist( partidos.get( iterator.next() ) );
		}
		
	}
	
	

}
