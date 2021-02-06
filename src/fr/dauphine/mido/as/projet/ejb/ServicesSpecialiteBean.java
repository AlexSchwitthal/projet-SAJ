package fr.dauphine.mido.as.projet.ejb;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import fr.dauphine.mido.as.projet.beans.Specialite;
import fr.dauphine.mido.as.projet.dao.DAOSpecialite;

/**
 * Session Bean implementation class ServicesSpecialiteBean
 */
@Stateless
@LocalBean
public class ServicesSpecialiteBean implements ServicesSpecialite {

	DAOSpecialite daoSpecialite = new DAOSpecialite();
	
	@Override
	public List<Specialite> getAllSpecialite() {
		try {
			return daoSpecialite.getAllSpecialite();
		} 
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Specialite getSpecialiteById(int id) {
		try {
			return daoSpecialite.getSpecialiteById(id);
		} 
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
