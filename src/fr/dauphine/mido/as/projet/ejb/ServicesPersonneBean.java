package fr.dauphine.mido.as.projet.ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import fr.dauphine.mido.as.projet.beans.Adresse;
import fr.dauphine.mido.as.projet.beans.Patient;
import fr.dauphine.mido.as.projet.beans.Personne;

/**
 * Session Bean implementation class ServicesPersonneBean
 */
@Stateless
@LocalBean
public class ServicesPersonneBean implements ServicesPersonne {
	@Override
	public boolean ajoutPatient(Patient patient, Personne personne, Adresse adresse) {
		try {
	        EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet-SAJ");
	        EntityManager em = emf.createEntityManager();

	        
	        em.persist(adresse);
	        personne.setAdresse(adresse);
	        em.persist(personne);
	        patient.setPersonne(personne);
	        em.persist(patient);
	        
	        em.close();
	        emf.close(); 
	        
	        return true;
		}
		catch(Exception e) {
			e.printStackTrace();	
			return false;
		}
	}
}
