package fr.dauphine.mido.as.projet.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import fr.dauphine.mido.as.projet.beans.Adresse;
import fr.dauphine.mido.as.projet.beans.Patient;
import fr.dauphine.mido.as.projet.beans.Personne;
import fr.dauphine.mido.as.projet.beans.Rendezvous;

public class DAOPatient {
	public String ajoutPatient(Patient patient, Personne personne, Adresse adresse) { 
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
	        
	        return "ok";
		}
		catch (Exception e) {
			e.printStackTrace();
			return "null";
		}
	}
	
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
	
	public List<Rendezvous> deletePatient(int patientId) {
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet-SAJ");
			EntityManager em = emf.createEntityManager();
			
			Patient patient = em.find(Patient.class, patientId);
			
			Query query = em.createQuery("select r from Rendezvous r where r.patient.idPatient = ?1");
			query.setParameter(1, patientId);
			List<Rendezvous> results = query.getResultList();
			
			List<Rendezvous> listeRDVAnnulées = new ArrayList<Rendezvous>();
			
			for(Rendezvous r : results) {
				if(r.getEtat().equals("Actif")) {
					listeRDVAnnulées.add(r);
				}
				//System.out.println(r.getPlannings().get(0).getCentremedical().getMedecinSpecialite(r.getPlannings().get(0).getMedecin().getIdMedecin()).getIdSpecialite());
				r.setEtat("Annulé");
				em.merge(r);
			}
			
			patient.setEtat("Supprimé");
			em.merge(patient);
		    em.flush();
			emf.close();
			em.close();
			
			return listeRDVAnnulées;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
