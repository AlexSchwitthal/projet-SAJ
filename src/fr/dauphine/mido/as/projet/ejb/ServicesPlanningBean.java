package fr.dauphine.mido.as.projet.ejb;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import fr.dauphine.mido.as.projet.beans.Centremedical;
import fr.dauphine.mido.as.projet.beans.Medecin;
import fr.dauphine.mido.as.projet.beans.Planning;
import fr.dauphine.mido.as.projet.beans.Rendezvous;
import fr.dauphine.mido.as.projet.dao.DAOPlanning;
import fr.dauphine.mido.as.projet.dao.DAOSpeMedecin;

/**
 * Session Bean implementation class ServicesCentreBean
 */
@Stateless
@LocalBean
public class ServicesPlanningBean implements ServicesPlanning {
	private DAOPlanning daoPlanning = new DAOPlanning();
	private DAOSpeMedecin daoSpemedecin = new DAOSpeMedecin();

	@Override
	public Map<String, Planning> getPlannings(LocalDate startDate, LocalDate endDate, Medecin medecin, Centremedical centre) {
		return daoPlanning.getPlannings(startDate, endDate, medecin, centre);
	}

	public boolean initPlanning(LocalDate startDate, LocalDate endDate, Medecin medecin, Centremedical centre) {
		boolean updated = daoSpemedecin.updateIsActivatedByCentre(centre, medecin, true);
		return updated ? daoPlanning.initPlanning(startDate, endDate, medecin, centre) : false;
	}
	
	@Override
	public boolean getPlanningIsActivated(Centremedical centre, Medecin medecin) {
		return daoSpemedecin.getIsActivatedByCentre(centre, medecin);
	}

	@Override
	public boolean desactivatePlanning(Centremedical centre, Medecin medecin) {
		return daoSpemedecin.updateIsActivatedByCentre(centre, medecin, false);
	}
	
	@Override
	public boolean updatePlanning(int idPlanning, boolean isDisponible) {
		return daoPlanning.updatePlanning(idPlanning, isDisponible);
	}
	
	@Override
	public Planning enregistrerPlanning(int idPlanning, Rendezvous rendezVous) {
		try {
			return daoPlanning.enregistrerPlanning(idPlanning, rendezVous);
		} 
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/*@Override
	public boolean setRendezVousNull(int idPlanning) {
		return daoPlanning.setRendezVousNull(idPlanning);
	}*/

	@Override
	public Planning getPlanning(int idPlanning, Rendezvous rendezvous) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Planning getPlanningByRendezVous(int idRendezVous) {
		try {
			return daoPlanning.getPlanningByRendezVous(idRendezVous);

		} 
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ArrayList<ArrayList<Object>> getPlanningPatient(int idPatient) {
		try {
			return daoPlanning.getPlanningPatient(idPatient);

		} 
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<Planning> getPlanningByDateWithRendezvous(LocalDate localDate){
		return daoPlanning.getPlanningByDateWithRendezvous(localDate);
	}
}
