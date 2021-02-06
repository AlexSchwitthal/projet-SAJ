package fr.dauphine.mido.as.projet.ejb;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import fr.dauphine.mido.as.projet.beans.Centremedical;
import fr.dauphine.mido.as.projet.dao.DAOCentre;

/**
 * Session Bean implementation class ServicesCentreBean
 */
@Stateless
@LocalBean
public class ServicesCentreBean implements ServicesCentre {

	private DAOCentre daoCentre = new DAOCentre();
	
	@Override
	public List<Centremedical> getAllCentre() {
		try {
			return daoCentre.getAllCentre();
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Centremedical getCentreById(int id) {
		try {
			return daoCentre.getCentreById(id);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
