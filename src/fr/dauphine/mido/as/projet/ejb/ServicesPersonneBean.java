package fr.dauphine.mido.as.projet.ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import fr.dauphine.mido.as.projet.beans.Administrateur;
import fr.dauphine.mido.as.projet.beans.Medecin;
import fr.dauphine.mido.as.projet.beans.Patient;
import fr.dauphine.mido.as.projet.beans.Personne;

/**
 * Session Bean implementation class ServicesPersonneBean
 */
@Stateless
@LocalBean
public class ServicesPersonneBean implements ServicesPersonne {
	
	@Override
	public Personne getPersonneByEmail(String type, String email) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet-SAJ");
		EntityManager em = emf.createEntityManager();
		switch(type) {
		case "patient":
			Query queryPatient = em.createQuery("select p from Patient p where p.email = ?1");
			queryPatient.setParameter(1, email);
			Patient patient = (Patient) queryPatient.getResultList().get(0);
			emf.close();
			em.close();
			return patient.getPersonne();
		
		case "medecin":
			Query queryMedecin = em.createQuery("select m from Medecin m where m.email = ?1");
			queryMedecin.setParameter(1, email);
			Medecin medecin = (Medecin) queryMedecin.getResultList().get(0);
			emf.close();
			em.close();
			return medecin.getPersonne();
		
		case "administrateur":
			Query queryAdmin = em.createQuery("select a from Administrateur a where a.email = ?1");
			queryAdmin.setParameter(1, email);
			Administrateur admin = (Administrateur) queryAdmin.getResultList().get(0);
			emf.close();
			em.close();
			return admin.getPersonne();
		default:
			emf.close();
			em.close();
			return null;
		}
	}
	
	public boolean isEmailAlreadyExist(String email) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet-SAJ");
		EntityManager em = emf.createEntityManager();
		
		Query queryPatient = em.createQuery("select p from Patient p where p.email = ?1");
		queryPatient.setParameter(1, email);
		if(queryPatient.getResultList().size() != 0) {
			return true;
		}
		
		Query queryMedecin = em.createQuery("select m from Medecin m where m.email = ?1");
		queryMedecin.setParameter(1, email);
		if(queryMedecin.getResultList().size() != 0) {
			return true;
		}
		
		Query queryAdmin = em.createQuery("select a from Administrateur a where a.email = ?1");
		queryAdmin.setParameter(1, email);
		if(queryAdmin.getResultList().size() != 0) {
			return true;
		}
		return false;
	}
}
