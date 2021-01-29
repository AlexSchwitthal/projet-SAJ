package fr.dauphine.mido.as.projet.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import fr.dauphine.mido.as.projet.beans.Centremedical;

/**
 * Session Bean implementation class ServicesCentreBean
 */
@Stateless
@LocalBean
public class ServicesCentreBean implements ServicesCentre {

	@Override
	public List<Centremedical> getAllCentre() {
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet-SAJ");
			EntityManager em = emf.createEntityManager();

			Query query = em.createNamedQuery("Centremedical.findAll");
			List<Centremedical> results = query.getResultList();
			return results;
			
		} 
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
