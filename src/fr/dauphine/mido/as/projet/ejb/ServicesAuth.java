package fr.dauphine.mido.as.projet.ejb;

import javax.ejb.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Remote
public interface ServicesAuth {
	public String login(String email, String mdp);
	public boolean isLogged();
}