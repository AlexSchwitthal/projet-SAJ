package fr.dauphine.mido.as.projet.ejb;

import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import fr.dauphine.mido.as.projet.beans.Adresse;
import fr.dauphine.mido.as.projet.beans.Medecin;
import fr.dauphine.mido.as.projet.beans.Personne;
import fr.dauphine.mido.as.projet.dao.DAOMedecin;
import fr.dauphine.mido.as.projet.dao.DAOPersonne;

/**
 * Session Bean implementation class ServicesMedecinBean
 */
@Stateless
@LocalBean
public class ServicesMedecinBean implements ServicesMedecin {
	
	private DAOMedecin daoMedecin = new DAOMedecin();
	private DAOPersonne daoPersonne = new DAOPersonne();

	@Override
	public String ajoutMedecin(Medecin medecin, Personne personne, Adresse adresse, String[] listeCentre, String[] listeSpecialite) {
		try {
			
	        if(daoPersonne.isEmailAlreadyExist(medecin.getEmail())) {
	        	return "email";
	        }
	        
			if(listeCentre.length != listeSpecialite.length) {
				return "erreur";
			}
			// vérification que tout les centres sont différents
			for(int i = 0; i < listeCentre.length; i++) {
				for(int j = 0; j < listeCentre.length; j++) {
					if(i != j) {
						if(listeCentre[i].equals(listeCentre[j])) {
							return "centre";
						}
					}
				}
			}
			
			boolean result = daoMedecin.ajoutMedecin(medecin, personne, adresse, listeCentre, listeSpecialite);
			if(result) {
				return "ok";
			}
			else {
				return "ko";
			}
		}
		catch(Exception e) {
			e.printStackTrace();	
			return "ko";
		}
	}
	
	@Override
	public Medecin getMedecinByEmail(String email) {
		try {
			Medecin medecin = daoMedecin.getMedecinByEmail(email);
			return medecin;
		} 
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Medecin updateMedecin(int medecinId, Map<String, String[]> parameters) {
		try {
			Medecin medecin = daoMedecin.updateMedecin(medecinId, parameters);  
		    return medecin;
		} 
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean deleteMedecin(int medecinId) {
		try {
			return daoMedecin.deleteMedecin(medecinId);
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
