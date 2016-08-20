package com.ibm.persistence;

import javax.persistence.EntityManager;




public class RetriveUserDetails {
	
	private EntityManager entityManager = new PersistenceDetails().getEntitymanagerFromCurrent();
	
	
	/*public List<UserDetails> fetchUserData(String username){
		 return entityManager.createQuery("select un from UserDetails un where un.userName=:username")
		.setParameter("username", username).getResultList();
	}
*/
}
