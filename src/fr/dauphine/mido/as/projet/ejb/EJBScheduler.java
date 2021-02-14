package fr.dauphine.mido.as.projet.ejb;

import javax.ejb.*;

@Remote
public interface EJBScheduler {
	public void EnvoiRappelEmail();
}