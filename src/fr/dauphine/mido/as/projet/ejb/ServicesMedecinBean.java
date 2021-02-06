package fr.dauphine.mido.as.projet.ejb;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import fr.dauphine.mido.as.projet.beans.Adresse;
import fr.dauphine.mido.as.projet.beans.Medecin;
import fr.dauphine.mido.as.projet.beans.Personne;
import fr.dauphine.mido.as.projet.beans.Spemedecin;

/**
 * Session Bean implementation class ServicesMedecinBean
 */
@Stateless
@LocalBean
public class ServicesMedecinBean implements ServicesMedecin {
	
	@EJB
	ServicesPersonne servicesPersonne;
	
	@EJB
	ServicesCentre servicesCentre;
	
	@EJB
	ServicesSpecialite servicesSpecialite;
		
	@Override
	public boolean ajoutMedecin(Medecin medecin, Personne personne, Adresse adresse, String[] listeCentre, String[] listeSpecialite) {
		try {
			
	        if(servicesPersonne.isEmailAlreadyExist(medecin.getEmail())) {
	        	return false;
	        }
	        
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
	
	@Override
	public Medecin getMedecinByEmail(String email) {
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet-SAJ");
			EntityManager em = emf.createEntityManager();
			
			Query query = em.createQuery("select m from Medecin m where m.email = ?1");
			query.setParameter(1, email);
			query.setMaxResults(1);
			
			List<Medecin> results = query.getResultList();
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
	
	@Override
	public Medecin updateMedecin(int medecinId, Map<String, String[]> parameters) {
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet-SAJ");
			EntityManager em = emf.createEntityManager();
			Medecin medecin = em.find(Medecin.class, medecinId);
	          
			medecin.getPersonne().setNom(parameters.get("nom")[0]);
			medecin.getPersonne().setPrenom(parameters.get("prenom")[0]);
	
			medecin.getPersonne().getAdresse().setAdresseComplete(parameters.get("adresse")[0]);
		        
			medecin.setEmail(parameters.get("email")[0]);
			medecin.setTelephone(parameters.get("telephone")[0]);
			medecin.setMotDePasse(parameters.get("mdp")[0]);
		    		    
		    em.merge(medecin);
		    em.flush();
		    emf.close();
		    em.close();
		    
		    return medecin;
		} 
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean deleteMedecin(int medecinId) {
		return false;
	}
}
