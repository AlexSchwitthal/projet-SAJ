package fr.dauphine.mido.as.projet.ejb;

import java.time.LocalDate;
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
	public Planning getPlanning(int idPlanning, Rendezvous rendezVous) {
		try {
			System.out.println("services avant appel dao");
			Planning planning = daoPlanning.getPlanning(idPlanning, rendezVous);
			return planning;
		} 
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}