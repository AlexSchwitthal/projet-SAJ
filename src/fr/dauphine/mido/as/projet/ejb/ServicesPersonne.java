package fr.dauphine.mido.as.projet.ejb;

import java.util.Map;

import javax.ejb.*;

import fr.dauphine.mido.as.projet.beans.Adresse;
import fr.dauphine.mido.as.projet.beans.Medecin;
import fr.dauphine.mido.as.projet.beans.Patient;
import fr.dauphine.mido.as.projet.beans.Personne;

@Remote
public interface ServicesPersonne {
	public boolean ajoutPatient(Patient patient, Personne personne, Adresse adresse);
	
	public boolean ajoutMedecin(Medecin medecin, Personne personne, Adresse adresse, String[] listeCentre, String[] listeSpecialite);
	
	public Patient getPatientByEmail(String email);
	
	public Personne getPersonneByEmail(String type, String email);
	
	public Patient updatePatient(int patientId, Map<String, String[]> parameters);
	
	public boolean deletePatient(int patientId);
}