package fr.dauphine.mido.as.projet.ejb;

import java.util.Map;

import javax.ejb.*;

import fr.dauphine.mido.as.projet.beans.Adresse;
import fr.dauphine.mido.as.projet.beans.Medecin;
import fr.dauphine.mido.as.projet.beans.Personne;

@Remote
public interface ServicesMedecin {
	public boolean ajoutMedecin(Medecin medecin, Personne personne, Adresse adresse, String[] listeCentre, String[] listeSpecialite);

	public Medecin getMedecinByEmail(String email);
	
	public Medecin updateMedecin(int medecinId, Map<String, String[]> parameters);
	
	public boolean deleteMedecin(int medecinId);
}