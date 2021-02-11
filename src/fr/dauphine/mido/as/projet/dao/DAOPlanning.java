package fr.dauphine.mido.as.projet.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import fr.dauphine.mido.as.projet.beans.Medecin;
import fr.dauphine.mido.as.projet.beans.Patient;
import fr.dauphine.mido.as.projet.beans.Planning;
import fr.dauphine.mido.as.projet.beans.Rendezvous;

public class DAOPlanning {

	public Planning getPlanning(int idPlanning, Rendezvous rendezVous) {
		try {
			System.out.println("debut method dao");
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet-SAJ");
			EntityManager em = emf.createEntityManager();

			Planning planning = em.find(Planning.class, idPlanning);
			em.persist(rendezVous);
			planning.setRendezvous(rendezVous);
			
			//em.merge(planning);
			em.flush();
			em.close();
			emf.close(); 
			System.out.println("fin method dao");
			return planning;

		}
		catch(Exception e) {
			e.printStackTrace();	
			return null;
		}
	}
	
	public Planning getPlanningByRendezVous(int idRendezVous) {
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet-SAJ");
			EntityManager em = emf.createEntityManager();

			Query query = em.createQuery("select p from Planning p where p.rendezvous.idRendezVous = ?1");
			query.setParameter(1, idRendezVous);
			query.setMaxResults(1);
			
			List<Planning> results = query.getResultList();
		    if (results == null || results.isEmpty()) {
		        return null;
		    }
		    else {
		        em.close();
		        emf.close(); 
		    	return results.get(0);
		    }
		}
		catch(Exception e) {
			e.printStackTrace();	
			return null;
		}
	}
}
