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
			
			emf.close();
			em.close();
			
			return results;
			
		} 
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Centremedical getCentreById(int id) {
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet-SAJ");
			EntityManager em = emf.createEntityManager();
			
			Query query = em.createQuery("select c from Centremedical c where c.idCentre = ?1");
			query.setParameter(1, id);
			Centremedical centre = (Centremedical) query.getSingleResult();
			return centre;
		} 
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
