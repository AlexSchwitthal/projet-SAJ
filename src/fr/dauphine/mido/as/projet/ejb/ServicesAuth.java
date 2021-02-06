package fr.dauphine.mido.as.projet.ejb;

import javax.ejb.*;

@Remote
public interface ServicesAuth {
	public String login(String email, String mdp);
	public boolean isLogged();
}