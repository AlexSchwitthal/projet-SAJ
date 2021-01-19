package fr.dauphine.mido.as.projet.ejb;

import javax.ejb.Remote;
import fr.dauphine.mido.as.projet.beans.Personne;

@Remote
public interface ServicesPersonne {
	public Personne recherchePersonnes(Personne personne);
}
