package fr.dauphine.mido.as.projet.ejb;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.dauphine.mido.as.projet.beans.Centremedical;
import fr.dauphine.mido.as.projet.beans.Medecin;
import fr.dauphine.mido.as.projet.beans.Planning;

//@Local or @Remote
public interface ServicesRendezVous {
	public HashMap<Medecin, HashMap<Centremedical, ArrayList<Planning>>> rechercherCreneauxDisponibles(String nomMedecin);
	//public ArrayList<Planning> rechercherCreneauxDisponibles(int idSpecialite, ArrayList<Integer> idCentres, ArrayList<Date> dates, ArrayList<String> creneaux);
	public ArrayList<Planning> rechercherCreneauxDisponibles(int idSpecialite, ArrayList<Integer> idCentres, List<String> heuresDebut);
}
