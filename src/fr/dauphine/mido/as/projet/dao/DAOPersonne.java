package fr.dauphine.mido.as.projet.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import fr.dauphine.mido.as.projet.beans.Administrateur;
import fr.dauphine.mido.as.projet.beans.Medecin;
import fr.dauphine.mido.as.projet.beans.Patient;
import fr.dauphine.mido.as.projet.beans.Personne;

public class DAOPersonne {
	public Personne getPersonneByEmail(String type, String email) {
		try {
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
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public boolean isEmailAlreadyExist(String email) {
		try {
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
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public String login(String email, String mdp) {
	    try {
	    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet-SAJ");
			EntityManager em = emf.createEntityManager();
			
			Query queryPatient = em.createQuery("select p from Patient p where p.email = ?1 and p.motDePasse = ?2 and p.etat != ?3");
			queryPatient.setParameter(1, email);
			queryPatient.setParameter(2, mdp);
			queryPatient.setParameter(3, "Supprimé");
			if(queryPatient.getResultList().size() == 1) {
				em.close();
				emf.close();
				return "patient";
			}
			
			Query queryMedecin = em.createQuery("select m from Medecin m where m.email = ?1 and m.motDePasse = ?2");
			queryMedecin.setParameter(1, email);
			queryMedecin.setParameter(2, mdp);
			if(queryMedecin.getResultList().size() == 1) {
				em.close();
				emf.close();
				return "medecin";
			}
			
			Query queryAdmin = em.createQuery("select a from Administrateur a where a.email = ?1 and a.motDePasse = ?2");
			queryAdmin.setParameter(1, email);
			queryAdmin.setParameter(2, mdp);
			if(queryAdmin.getResultList().size() == 1) {
				em.close();
				emf.close();
				return "administrateur";
			}
			
			em.close();
			emf.close();
			return "erreur";
	    }
	    catch(Exception e) {
	    	e.printStackTrace();
			return null;
	    }
	}
}
