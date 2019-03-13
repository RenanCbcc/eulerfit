package br.com.gym.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("MySQLDatabase");
	
	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
	
}