package fr.dauphine.mido.as.projet.ejb;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import fr.dauphine.mido.as.projet.beans.Centremedical;
import fr.dauphine.mido.as.projet.dao.DAOCentreMedical;

/**
 * Session Bean implementation class ServicesCentreBean
 */
@Stateless
@LocalBean
public class ServicesCentreBean implements ServicesCentre {

	private DAOCentreMedical daoCentreMedical = new DAOCentreMedical();
	
	@Override
	public List<Centremedical> getAllCentre() {
		try {
			return daoCentreMedical.getAllCentre();
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Centremedical getCentreById(int id) {
		try {
			return daoCentreMedical.getCentreById(id);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
