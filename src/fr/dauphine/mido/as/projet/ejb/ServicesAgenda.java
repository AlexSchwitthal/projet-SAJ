package fr.dauphine.mido.as.projet.ejb;

import java.util.List;

import javax.ejb.*;

@Remote
public interface ServicesAgenda {
	public List<DateAgenda> getDaysFromNow(int nbDays);
	public List<TimeAgenda> getWorkTime(String timeStart, String timeEnd);
}