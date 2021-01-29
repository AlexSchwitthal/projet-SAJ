package fr.dauphine.mido.as.projet.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import fr.dauphine.mido.as.projet.beans.Specialite;

/**
 * Session Bean implementation class ServicesCentreBean
 */
@Stateless
@LocalBean
public class ServicesSpecialiteBean implements ServicesSpecialite {

	@Override
	public List<Specialite> getAllSpecialite() {
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet-SAJ");
			EntityManager em = emf.createEntityManager();

			Query query = em.createNamedQuery("Specialite.findAll");
			List<Specialite> results = query.getResultList();
			return results;
			
		} 
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
