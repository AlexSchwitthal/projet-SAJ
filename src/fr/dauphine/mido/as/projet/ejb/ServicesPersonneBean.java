package fr.dauphine.mido.as.projet.ejb;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import fr.dauphine.mido.as.projet.beans.Adresse;
import fr.dauphine.mido.as.projet.beans.Medecin;
import fr.dauphine.mido.as.projet.beans.Patient;
import fr.dauphine.mido.as.projet.beans.Personne;
import fr.dauphine.mido.as.projet.beans.Spemedecin;

/**
 * Session Bean implementation class ServicesPersonneBean
 */
@Stateless
@LocalBean
public class ServicesPersonneBean implements ServicesPersonne {
	
	@EJB
	ServicesCentre servicesCentre;
	
	@EJB
	ServicesSpecialite servicesSpecialite;
	
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
	
	@Override
	public boolean ajoutMedecin(Medecin medecin, Personne personne, Adresse adresse, String[] listeCentre, String[] listeSpecialite) {
		try {
			if(listeCentre.length != listeSpecialite.length) {
				return false;
			}
			// vérification que tout les centres sont différents
			for(int i = 0; i < listeCentre.length; i++) {
				for(int j = 0; j < listeCentre.length; j++) {
					if(i != j) {
						if(listeCentre[i].equals(listeCentre[j])) {
							return false;
						}
					}
				}
			}
			
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet-SAJ");
	        EntityManager em = emf.createEntityManager();
	        
	        
	        em.persist(adresse);
	        personne.setAdresse(adresse);
	        em.persist(personne);
	        medecin.setPersonne(personne);
	        em.persist(medecin);
	        
	        for(int i = 0; i < listeCentre.length; i++) {
	        	Spemedecin speMedecin = new Spemedecin();
	        	speMedecin.setCentremedical(servicesCentre.getCentreById(Integer.parseInt(listeCentre[i])));
	        	speMedecin.setSpecialite(servicesSpecialite.getSpecialiteById(Integer.parseInt(listeSpecialite[i])));
	        	speMedecin.setMedecin(medecin);
	        	em.persist(speMedecin);
	        }
	        
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
