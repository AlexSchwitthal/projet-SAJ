package fr.dauphine.mido.as.projet.ejb;

import javax.ejb.*;

import fr.dauphine.mido.as.projet.beans.Adresse;
import fr.dauphine.mido.as.projet.beans.Patient;
import fr.dauphine.mido.as.projet.beans.Personne;
@Remote
//@Local
public interface ServicesPersonne {
	public boolean ajoutPatient(Patient patient, Personne personne, Adresse adresse);
}