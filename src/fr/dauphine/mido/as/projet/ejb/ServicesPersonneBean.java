package fr.dauphine.mido.as.projet.ejb;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import fr.dauphine.mido.as.projet.beans.Administrateur;
import fr.dauphine.mido.as.projet.beans.Adresse;
import fr.dauphine.mido.as.projet.beans.Medecin;
import fr.dauphine.mido.as.projet.beans.Patient;
import fr.dauphine.mido.as.projet.beans.Personne;
import fr.dauphine.mido.as.projet.beans.Rendezvous;
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
	        
	        if(this.isEmailAlreadyExist(patient.getEmail())) {
	        	return false;
	        }
	        
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
			
	        if(this.isEmailAlreadyExist(medecin.getEmail())) {
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
	public Patient getPatientByEmail(String email) {
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet-SAJ");
			EntityManager em = emf.createEntityManager();
			
			Query query = em.createQuery("select p from Patient p where p.email = ?1");
			query.setParameter(1, email);
			query.setMaxResults(1);
			
			List<Patient> results = query.getResultList();
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
	public Patient updatePatient(int patientId, Map<String, String[]> parameters) {
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet-SAJ");
			EntityManager em = emf.createEntityManager();
			Patient patient = em.find(Patient.class, patientId);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	          
			patient.getPersonne().setNom(parameters.get("nom")[0]);
			patient.getPersonne().setPrenom(parameters.get("prenom")[0]);
			patient.getPersonne().setDateNaissance(sdf.parse(parameters.get("dateNaissance")[0]));
	
			patient.getPersonne().getAdresse().setAdresseComplete(parameters.get("adresse")[0]);
			patient.getPersonne().getAdresse().setCodePostal(parameters.get("cp")[0]);
			patient.getPersonne().getAdresse().setPays(parameters.get("pays")[0]);
			patient.getPersonne().getAdresse().setVille(parameters.get("ville")[0]);
		        
			patient.setEmail(parameters.get("email")[0]);
			patient.setTelephone(parameters.get("telephone")[0]);
			patient.setMotDePasse(parameters.get("mdp")[0]);
		    		    
		    em.merge(patient);
		    em.flush();
		    emf.close();
		    em.close();
		    
		    return patient;
		} 
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean deletePatient(int patientId) {
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet-SAJ");
			EntityManager em = emf.createEntityManager();
			
			Patient patient = em.find(Patient.class, patientId);
			
			// A METTRE DANS UNE FUTUR CLASSE servicesRDV (maybe ?)
			Query query = em.createQuery("select r from Rendezvous r where r.patient.idPatient = ?1");
			query.setParameter(1, patientId);
			List<Rendezvous> results = query.getResultList();
			for(Rendezvous r : results) {
				r.setEtat("Annulé");
				em.merge(r);
			}
			
			patient.setEtat("Supprimé");
			em.merge(patient);
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
	
	public boolean isEmailAlreadyExist(String email) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet-SAJ");
		EntityManager em = emf.createEntityManager();
		
		Query queryPatient = em.createQuery("select p from Patient p where p.email = ?1");
		queryPatient.setParameter(1, email);
		if(queryPatient.getResultList().size() != 0) {
			return true;
		}
		
		Query queryMedecin = em.createQuery("select m from Medecin m where m.email = ?1");
		queryMedecin.setParameter(1, email);
		if(queryMedecin.getResultList().size() != 0) {
			return true;
		}
		
		Query queryAdmin = em.createQuery("select a from Administrateur a where a.email = ?1");
		queryAdmin.setParameter(1, email);
		if(queryAdmin.getResultList().size() != 0) {
			return true;
		}
		
		return false;
	}

	@Override
	public Personne getPersonneByEmail(String type, String email) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet-SAJ");
		EntityManager em = emf.createEntityManager();
		switch(type) {
		case "patient":
			Query queryPatient = em.createQuery("select p from Patient p where p.email = ?1");
			queryPatient.setParameter(1, email);
			Patient patient = (Patient) queryPatient.getResultList().get(0);
			emf.close();
			em.close();
			return patient.getPersonne();
		
		case "medecin":
			Query queryMedecin = em.createQuery("select m from Medecin m where m.email = ?1");
			queryMedecin.setParameter(1, email);
			Medecin medecin = (Medecin) queryMedecin.getResultList().get(0);
			emf.close();
			em.close();
			return medecin.getPersonne();
		
		case "administrateur":
			Query queryAdmin = em.createQuery("select a from Administrateur a where a.email = ?1");
			queryAdmin.setParameter(1, email);
			Administrateur admin = (Administrateur) queryAdmin.getResultList().get(0);
			emf.close();
			em.close();
			return admin.getPersonne();
		default:
			emf.close();
			em.close();
			return null;
		}
	}
}
