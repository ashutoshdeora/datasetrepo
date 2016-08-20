/**
 * 
 */
package com.ibm.managedBean;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author ibm
 *
 */
public abstract class CommonFacesBean {
	
	private static final String PERSISTENCE_UNIT_NAME = "tmorcl";
	
	/**
	 * 
	 * @return entityManager
	 */
	protected EntityManager getEntitymanagerFromCurrent() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		return em;
	}

}
