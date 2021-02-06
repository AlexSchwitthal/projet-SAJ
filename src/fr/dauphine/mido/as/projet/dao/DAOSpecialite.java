package fr.dauphine.mido.as.projet.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import fr.dauphine.mido.as.projet.beans.Specialite;

public class DAOSpecialite {
	public List<Specialite> getAllSpecialite() {
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet-SAJ");
			EntityManager em = emf.createEntityManager();

			Query query = em.createNamedQuery("Specialite.findAll");
			List<Specialite> results = query.getResultList();
			emf.close();
			em.close();
			return results;
			
		} 
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public Specialite getSpecialiteById(int id) {
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet-SAJ");
			EntityManager em = emf.createEntityManager();
			
			Query query = em.createQuery("select s from Specialite s where s.idSpecialite = ?1");
			query.setParameter(1, id);
			Specialite specialite = (Specialite) query.getSingleResult();
			emf.close();
			em.close();
			return specialite;
		} 
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
