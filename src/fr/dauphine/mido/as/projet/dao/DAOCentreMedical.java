package fr.dauphine.mido.as.projet.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import fr.dauphine.mido.as.projet.beans.Centremedical;

public class DAOCentreMedical {
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
	public List<Centremedical> getCentresByMedecin(int idMedecin) {
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet-SAJ");
			EntityManager em = emf.createEntityManager();
			TypedQuery<Centremedical> query = em.createQuery("select c from Spemedecin sm inner join sm.centremedical c where sm.medecin.idMedecin = ?1", Centremedical.class);
			query.setParameter(1, idMedecin);
			
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
}
