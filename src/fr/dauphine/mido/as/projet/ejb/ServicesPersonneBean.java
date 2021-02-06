package fr.dauphine.mido.as.projet.ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import fr.dauphine.mido.as.projet.beans.Personne;
import fr.dauphine.mido.as.projet.dao.DAOPersonne;

/**
 * Session Bean implementation class ServicesPersonneBean
 */
@Stateless
@LocalBean
public class ServicesPersonneBean implements ServicesPersonne {
	
	private DAOPersonne daoPersonne = new DAOPersonne();
	
	@Override
	public Personne getPersonneByEmail(String type, String email) {
		return daoPersonne.getPersonneByEmail(type, email);
	}
	
	public boolean isEmailAlreadyExist(String email) {
		return daoPersonne.isEmailAlreadyExist(email);
	}

	@Override
	public String login(String email, String mdp) {
		return daoPersonne.login(email, mdp);
	}
}
