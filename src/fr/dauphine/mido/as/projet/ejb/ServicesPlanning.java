package fr.dauphine.mido.as.projet.ejb;

import java.time.LocalDate;
import java.util.Map;

import javax.ejb.Remote;

import fr.dauphine.mido.as.projet.beans.Planning;

@Remote
public interface ServicesPlanning {
	public Map<String, Planning> getPlannings(LocalDate startDate, LocalDate endDate);
	public boolean initPlanning(LocalDate startDate, LocalDate endDate);
}