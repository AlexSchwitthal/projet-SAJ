package fr.dauphine.mido.as.projet.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import fr.dauphine.mido.as.projet.beans.Centremedical;
import fr.dauphine.mido.as.projet.beans.Medecin;
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
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean getIsActivatedByCentre(Centremedical centre, Medecin medecin) {
		boolean isActivated = false;

		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet-SAJ");
			EntityManager em = emf.createEntityManager();

			TypedQuery<Spemedecin> query = em
					.createQuery("select s from Spemedecin s where s.centremedical.idCentre = ?1 AND s.medecin.idMedecin = ?2", Spemedecin.class);
			query.setParameter(1, centre.getIdCentre());
			query.setParameter(2, medecin.getIdMedecin());
			isActivated = query.getSingleResult().isActivated();
			emf.close();
			em.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return isActivated;
	}

	public boolean updateIsActivatedByCentre(Centremedical centre, Medecin medecin, boolean isActivated) {
		boolean updated = false;

		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet-SAJ");
			EntityManager em = emf.createEntityManager();

			TypedQuery<Spemedecin> query = em.createQuery("select s from Spemedecin s where s.centremedical.idCentre = ?1 AND s.medecin.idMedecin = ?2",
					Spemedecin.class);
			query.setParameter(1, centre.getIdCentre());
			query.setParameter(2, medecin.getIdMedecin());
			Spemedecin sm = query.getSingleResult();

			sm.setActivated(isActivated);
			em.merge(sm);
			em.flush();
			emf.close();
			em.close();

			updated = true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return updated;
	}
}
