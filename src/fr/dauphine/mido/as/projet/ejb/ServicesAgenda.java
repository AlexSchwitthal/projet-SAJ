package fr.dauphine.mido.as.projet.ejb;

import java.util.List;

import javax.ejb.*;

import fr.dauphine.mido.as.projet.beans.Centremedical;

@Remote
public interface ServicesAgenda {
	public List<DateAgenda> getDaysFromNow(int nbDays);
	public List<TimeAgenda> getWorkTime(String timeStart, String timeEnd);
}