/**
 * 
 */
package com.tidyslice.dipcrawler.aspects;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;


/**
 * @author erick
 * Spring AOP Aspect that opens a hibernate session before the invocation of a method.
 * This session is bound to the current Thread. 
 * After the execution of the method, the session is closed and unbounded. 
 * Used as a replacement of <code>OpenSessionInvViewInterceptor</code> for non-web 
 * applications
 */
@Aspect
public class OpenSessionInMethodAspect {

	private static final Logger logger = Logger.getLogger( OpenSessionInMethodAspect.class );
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Before("openSessionMethod()")
	public void openSession() {
		if( !TransactionSynchronizationManager.hasResource( sessionFactory ) ) {
			Session session = SessionFactoryUtils.getNewSession(sessionFactory);
			logger.debug("Opening single Hibernate Session in OpenSessionInMethodAspect");
			TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
		}
	}
	
	@After("closeSessionMethod()")
	public void closeSession() {
		
		SessionHolder sessionHolder =
			(SessionHolder) TransactionSynchronizationManager.unbindResource(sessionFactory);
		logger.debug("Closing single Hibernate Session in OpenSessionInMethodAspect");
		SessionFactoryUtils.closeSession(sessionHolder.getSession());
	}
	
	@Pointcut("execution(@com.tidyslice.dipcrawler.annotation.OpenSession * *(..))")
	public void openSessionMethod(){}
	
	@Pointcut("execution(@com.tidyslice.dipcrawler.annotation.CloseSession * *(..))")
	public void closeSessionMethod(){}
}
