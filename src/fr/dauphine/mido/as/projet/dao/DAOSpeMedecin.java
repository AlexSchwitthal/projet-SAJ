package fr.dauphine.mido.as.projet.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

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
}
