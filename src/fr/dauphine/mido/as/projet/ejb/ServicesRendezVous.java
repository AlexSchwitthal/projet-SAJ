package fr.dauphine.mido.as.projet.ejb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.dauphine.mido.as.projet.beans.Centremedical;
import fr.dauphine.mido.as.projet.beans.Medecin;
import fr.dauphine.mido.as.projet.beans.Planning;
import fr.dauphine.mido.as.projet.beans.Rendezvous;

//@Local or @Remote
public interface ServicesRendezVous {
	public HashMap<Medecin, HashMap<Centremedical, ArrayList<Planning>>> rechercherCreneauxDisponibles(String nomMedecin);
	public ArrayList<Planning> rechercherCreneauxDisponibles(int idSpecialite, ArrayList<Integer> idCentres, List<String> heuresDebut);
	public boolean hasRendezVousActif(String email);
	public boolean hasRendezVousActifCentre(String email, int idCentre);
}
