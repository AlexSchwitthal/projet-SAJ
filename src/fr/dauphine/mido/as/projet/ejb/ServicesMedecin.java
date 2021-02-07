package fr.dauphine.mido.as.projet.ejb;

import java.util.Map;

import javax.ejb.*;

import fr.dauphine.mido.as.projet.beans.Adresse;
import fr.dauphine.mido.as.projet.beans.Centremedical;
import fr.dauphine.mido.as.projet.beans.Medecin;
import fr.dauphine.mido.as.projet.beans.Personne;
import fr.dauphine.mido.as.projet.beans.Specialite;

@Remote
public interface ServicesMedecin {
	public String ajoutMedecin(Medecin medecin, Personne personne, Adresse adresse, String[] listeCentre, String[] listeSpecialite);
	public Medecin getMedecinByEmail(String email);
	public Medecin updateMedecin(int medecinId, Map<String, String[]> parameters);
	public boolean deleteMedecin(int medecinId);
	public Map<Centremedical, Specialite> getCentreSpeById(int medecinId);
}