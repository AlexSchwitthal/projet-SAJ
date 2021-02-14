package fr.dauphine.mido.as.projet.ejb;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

import javax.ejb.Remote;

import fr.dauphine.mido.as.projet.beans.Medecin;
import fr.dauphine.mido.as.projet.beans.Planning;
import fr.dauphine.mido.as.projet.beans.Rendezvous;

@Remote
public interface ServicesRendezVous {
	public ArrayList<Medecin> rechercheMedecin(String nomMedecin);
	public ArrayList<Planning> rechercherCreneauxDisponibles(int idSpecialite, ArrayList<Integer> idCentres, ArrayList<Time> heuresDebut, ArrayList<Date> jours);
	public TreeSet<DateAgenda> getJoursDisponibles();
	public boolean hasRendezVousActif(String email);
	public boolean hasRendezVousActifCentre(String email, int idCentre);
	public ArrayList<Object> getDetailsRendezVous(int idRendezvous);
	public boolean cancelRendezVous(int idPlanning, String messageAnnulation);
	public List<Rendezvous> getRendezVousPatient(int idPatient);
	public List<Rendezvous> getRendezVousByDate(LocalDate localDate);
}
