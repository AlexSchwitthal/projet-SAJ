package fr.dauphine.mido.as.projet.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import fr.dauphine.mido.as.projet.beans.Medecin;
import fr.dauphine.mido.as.projet.beans.Planning;
import fr.dauphine.mido.as.projet.beans.Spemedecin;

public class DAOSpeMedecin {
	public List<Spemedecin> getCentresByIdMedecin(int medecinId) {
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet-SAJ");
			EntityManager em = emf.createEntityManager();

			Query query = em.createQuery("select s from Spemedecin s where s.medecin.idMedecin = ?1");
			query.setParameter(1, medecinId);
			List<Spemedecin> results = query.getResultList();
			emf.close();
			em.close();
			return results;
		} 
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Spemedecin getSpeMedecinByMedecinCentre(int medecinId, int centreId, int specialiteId) {
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet-SAJ");
			EntityManager em = emf.createEntityManager();

			Query query = em.createQuery("select s from Spemedecin s "
					+ "where s.medecin.idMedecin = ?1 "
					+ "and s.centremedical.idCentre = ?2 "
					+ "and s.specialite.idSpecialite = ?3");
			query.setParameter(1, medecinId);
			query.setParameter(2, centreId);
			query.setParameter(3, specialiteId);
			query.setMaxResults(1);
			
			List<Spemedecin> results = query.getResultList();
		    if (results == null || results.isEmpty()) {
		        return null;
		    }
		    else {
		        em.close();
		        emf.close(); 
		    	return results.get(0);
		    }
		} 
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public boolean deleteSpeMedecin(int speMedecinId) {
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet-SAJ");
			EntityManager em = emf.createEntityManager();

			Spemedecin speMedecin = em.find(Spemedecin.class, speMedecinId);
			
			em.remove(speMedecin);
			
			for(Planning p : speMedecin.getCentremedical().getPlannings()) {
				System.out.println("test");
				em.remove(p);
				em.remove(p.getRendezvous());
			}
			
			em.flush();
			emf.close();
			em.close();
			return true;
		} 
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
