package fr.dauphine.mido.as.projet.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import fr.dauphine.mido.as.projet.beans.Centremedical;
import fr.dauphine.mido.as.projet.beans.Medecin;
import fr.dauphine.mido.as.projet.beans.Planning;
import fr.dauphine.mido.as.projet.beans.Rendezvous;

public class DAORendezVous {
	public List<Rendezvous> getListeRendezVousMedecin(int idMedecin) {
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet-SAJ");
			EntityManager em = emf.createEntityManager();
			Query query = em.createQuery(
					"select r from Planning p inner join p.rendezvous r where p.medecin.idMedecin = ?1 and r.etat = ?2");
			query.setParameter(1, idMedecin);
			query.setParameter(2, "actif");
			List<Rendezvous> results = query.getResultList();
			emf.close();
			em.close();
			return results;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public List<Rendezvous> getListeRendezVousMedecinCentre(int idMedecin, int idCentre) {
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet-SAJ");
			EntityManager em = emf.createEntityManager();
			Query query = em.createQuery("select r from Planning p " + "inner join p.rendezvous r "
					+ "where p.medecin.idMedecin = ?1 " + "and p.centremedical.idCentre = ?2 " + "and r.etat = ?3");
			query.setParameter(1, idMedecin);
			query.setParameter(2, idCentre);
			query.setParameter(3, "Actif");
			List<Rendezvous> results = query.getResultList();
			emf.close();
			em.close();
			return results;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Centremedical getCentreRendezVous(int idRendezVous) {
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet-SAJ");
			EntityManager em = emf.createEntityManager();
			Query query = em.createQuery(
					"select p from Planning p " + "inner join p.rendezvous r " + "where r.idRendezVous = ?1");
			query.setParameter(1, idRendezVous);
			query.setMaxResults(1);
			List<Planning> results = query.getResultList();

			emf.close();
			em.close();
			return results.get(0).getCentremedical();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Medecin getMedecinRendezVous(int idRendezVous) {
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet-SAJ");
			EntityManager em = emf.createEntityManager();
			Query query = em.createQuery(
					"select p from Planning p " + "inner join p.rendezvous r " + "where r.idRendezVous = ?1");
			query.setParameter(1, idRendezVous);
			query.setMaxResults(1);
			List<Planning> results = query.getResultList();

			emf.close();
			em.close();
			return results.get(0).getMedecin();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean cancelRendezVous(int idPlanning, String messageAnnulation) {
		boolean updated = false;
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet-SAJ");
			EntityManager em = emf.createEntityManager();
			TypedQuery<Rendezvous> query = em.createQuery(
					"select r from Planning p inner join p.rendezvous r where p.idPlanning = ?1",
					Rendezvous.class);
			query.setParameter(1, idPlanning);
			query.setMaxResults(1);
			Rendezvous rendezvous = query.getSingleResult();
			rendezvous.setEtat("annulé");
			rendezvous.setMessageAnnulation(messageAnnulation);
			em.merge(rendezvous);
			em.flush();
			emf.close();
			em.close();
			updated = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return updated;
	}
	
	public List<Rendezvous> getRendezVousPatient(int idPatient) {
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet-SAJ");
			EntityManager em = emf.createEntityManager();
			//Query query = em.createQuery("select r from Rendezvous r where r.patient.idPatient = ?1");
			Query query = em.createQuery("select r from Planning p inner join p.rendezvous r where r.patient.idPatient = ?1 order by p.date desc, p.heureDebut desc");
			query.setParameter(1, idPatient);
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
