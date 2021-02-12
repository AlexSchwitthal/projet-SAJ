package fr.dauphine.mido.as.projet.ejb;

import java.util.List;

import javax.ejb.*;

import fr.dauphine.mido.as.projet.beans.Centremedical;

@Remote
public interface ServicesCentre {
	public List<Centremedical> getAllCentre();
	public Centremedical getCentreById(int id);
	public List<Centremedical> getCentresByMedecin(int idMedecin);
}