package fr.dauphine.mido.as.projet.ejb;

import java.time.LocalDate;
import java.util.Map;

import javax.ejb.Remote;

import fr.dauphine.mido.as.projet.beans.Centremedical;
import fr.dauphine.mido.as.projet.beans.Medecin;
import fr.dauphine.mido.as.projet.beans.Planning;
import fr.dauphine.mido.as.projet.beans.Rendezvous;

@Remote
public interface ServicesPlanning {
	public Map<String, Planning> getPlannings(LocalDate startDate, LocalDate endDate, Medecin medecin, Centremedical centre);
	public boolean initPlanning(LocalDate startDate, LocalDate endDate, Medecin medecin, Centremedical centre);
	public boolean desactivatePlanning(Centremedical centre, Medecin medecin);
	public boolean getPlanningIsActivated(Centremedical centre, Medecin medecin);
	public boolean updatePlanning(int idPlanning, boolean isDisponible);
	public Planning enregistrerPlanning(int idPlanning, Rendezvous rendezvous);
}