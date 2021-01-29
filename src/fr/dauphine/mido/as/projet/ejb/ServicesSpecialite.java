package fr.dauphine.mido.as.projet.ejb;

import java.util.List;

import javax.ejb.*;

import fr.dauphine.mido.as.projet.beans.Specialite;

@Remote
public interface ServicesSpecialite {
	public List<Specialite> getAllSpecialite();
}