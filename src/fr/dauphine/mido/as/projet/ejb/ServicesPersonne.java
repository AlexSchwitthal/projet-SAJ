package fr.dauphine.mido.as.projet.ejb;


import javax.ejb.*;

import fr.dauphine.mido.as.projet.beans.Personne;

@Remote
public interface ServicesPersonne {
	public Personne getPersonneByEmail(String type, String email);
	
	public boolean isEmailAlreadyExist(String email);
}