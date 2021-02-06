package fr.dauphine.mido.as.projet.ejb;

import java.util.ArrayList;

import fr.dauphine.mido.as.projet.beans.Medecin;
import fr.dauphine.mido.as.projet.beans.Planning;

//@Local or @Remote
public interface ServicesRendezVous {
	public ArrayList<Medecin> rechercheMedecin(String nomMedecin);
	public ArrayList<Planning> rechercherCreneauxDisponibles(int idMedecin);
	public ArrayList<Planning> rechercherCreneauxDisponible(int idMedecin, int idCentre);
}
