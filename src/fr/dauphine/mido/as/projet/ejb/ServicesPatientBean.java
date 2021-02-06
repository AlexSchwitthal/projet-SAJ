package fr.dauphine.mido.as.projet.ejb;

import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import fr.dauphine.mido.as.projet.beans.Adresse;
import fr.dauphine.mido.as.projet.beans.Patient;
import fr.dauphine.mido.as.projet.beans.Personne;
import fr.dauphine.mido.as.projet.dao.DAOPatient;
import fr.dauphine.mido.as.projet.dao.DAOPersonne;
/**
 * Session Bean implementation class ServicesPatientBean
 */
@Stateless
@LocalBean
public class ServicesPatientBean implements ServicesPatient {
	
	private DAOPatient daoPatient = new DAOPatient();
	private DAOPersonne DAOPersonne = new DAOPersonne();
	
	@Override
	public boolean ajoutPatient(Patient patient, Personne personne, Adresse adresse) {
		try {
	        if(DAOPersonne.isEmailAlreadyExist(patient.getEmail())) {
	        	return false;
	        }
	        else {
	        	 return daoPatient.ajoutPatient(patient, personne, adresse);
	        }
		}
		catch(Exception e) {
			e.printStackTrace();	
			return false;
		}
	}
	

	@Override
	public Patient getPatientByEmail(String email) {
		try {
			return daoPatient.getPatientByEmail(email);
		} 
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Patient updatePatient(int patientId, Map<String, String[]> parameters) {
		try {
			return daoPatient.updatePatient(patientId, parameters);
		} 
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean deletePatient(int patientId) {
		try {
			return daoPatient.deletePatient(patientId);
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
