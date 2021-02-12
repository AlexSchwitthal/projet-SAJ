package fr.dauphine.mido.as.projet.ejb;

import java.util.List;
import java.util.Map;

import javax.ejb.*;

import fr.dauphine.mido.as.projet.beans.Adresse;
import fr.dauphine.mido.as.projet.beans.Patient;
import fr.dauphine.mido.as.projet.beans.Personne;
import fr.dauphine.mido.as.projet.beans.Rendezvous;

@Remote
public interface ServicesPatient {
	public String ajoutPatient(Patient patient, Personne personne, Adresse adresse);
	public Patient getPatientByEmail(String email);
	public Patient updatePatient(int patientId, Map<String, String[]> parameters);
	public List<Rendezvous> deletePatient(int patientId);
}