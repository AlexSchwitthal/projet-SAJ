package fr.dauphine.mido.as.projet.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import fr.dauphine.mido.as.projet.beans.Rendezvous;

public class DAORendezVous {
	public List<Rendezvous> getListeRendezVousMedecin(int idMedecin) {
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet-SAJ");
			EntityManager em = emf.createEntityManager();
			Query query = em.createQuery("select r from Planning p inner join p.rendezvous r where p.medecin.idMedecin = ?1 and r.etat = ?2");
			query.setParameter(1, idMedecin);
			query.setParameter(2, "actif");
			List<Rendezvous> results = query.getResultList();
			emf.close();
			em.close();
			return results;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public List<Rendezvous> getListeRendezVousMedecinCentre(int idMedecin, int idCentre) {
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet-SAJ");
			EntityManager em = emf.createEntityManager();
			Query query = em.createQuery("select r from Planning p "
					+ "inner join p.rendezvous r "
					+ "where p.medecin.idMedecin = ?1 "
					+ "and p.centremedical.idCentre = ?2 "
					+ "and r.etat = ?3");
			query.setParameter(1, idMedecin);
			query.setParameter(2, idCentre);
			query.setParameter(3, "actif");
			List<Rendezvous> results = query.getResultList();
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
