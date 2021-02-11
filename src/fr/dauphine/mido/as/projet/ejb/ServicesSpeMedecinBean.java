package fr.dauphine.mido.as.projet.ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import fr.dauphine.mido.as.projet.beans.Spemedecin;
import fr.dauphine.mido.as.projet.dao.DAOSpeMedecin;

/**
 * Session Bean implementation class ServicesSpeMedecinBean
 */
@Stateless
@LocalBean
public class ServicesSpeMedecinBean implements ServicesSpeMedecin {

	private DAOSpeMedecin daoSpeMedecin = new DAOSpeMedecin();
	
	@Override
	public boolean deleteSpeMedecin(int speMedecinId) {
		try {
			return daoSpeMedecin.deleteSpeMedecin(speMedecinId);
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Spemedecin getSpeMedecinByMedecinCentre(int medecinId, int centreId) {
		try {
			return daoSpeMedecin.getSpeMedecinByMedecinCentre(medecinId, centreId);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
