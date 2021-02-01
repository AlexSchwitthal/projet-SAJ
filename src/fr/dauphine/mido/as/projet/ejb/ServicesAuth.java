package fr.dauphine.mido.as.projet.ejb;

import javax.ejb.*;
import javax.servlet.http.HttpServletRequest;

@Remote
//@Local
public interface ServicesAuth {
	public boolean login(HttpServletRequest request, String email, String mdp);
	public boolean logout(HttpServletRequest request);
	public boolean isLogged(HttpServletRequest request);
}