package fr.dauphine.mido.as.projet.ejb;

import javax.ejb.*;

import fr.dauphine.mido.as.projet.beans.Spemedecin;

@Remote
public interface ServicesSpeMedecin {
	public boolean deleteSpeMedecin(int speMedecinId);
	public Spemedecin getSpeMedecinByMedecinCentre(int medecinId, int centreId, int specialiteId);
}