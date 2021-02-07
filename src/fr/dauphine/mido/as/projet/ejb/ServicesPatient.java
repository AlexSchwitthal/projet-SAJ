package fr.dauphine.mido.as.projet.ejb;

import java.util.Map;

import javax.ejb.*;

import fr.dauphine.mido.as.projet.beans.Adresse;
import fr.dauphine.mido.as.projet.beans.Patient;
import fr.dauphine.mido.as.projet.beans.Personne;

@Remote
public interface ServicesPatient {
	public String ajoutPatient(Patient patient, Personne personne, Adresse adresse);
	public Patient getPatientByEmail(String email);
	public Patient updatePatient(int patientId, Map<String, String[]> parameters);
	public boolean deletePatient(int patientId);
}