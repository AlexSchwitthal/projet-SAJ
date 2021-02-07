package fr.dauphine.mido.as.projet.ejb;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class ServicesCentreBean
 */
@Stateless
@LocalBean
public class ServicesAgendaBean implements ServicesAgenda {

	@Override
	public List<DateAgenda> getDaysFromNow(int nbDays) {
		List<DateAgenda> listDateAgenda = new ArrayList<>();
		LocalDate date = LocalDate.now();
		for (long i = 0; i<nbDays; i++) {
			listDateAgenda.add(new DateAgenda(date));
			date = date.plusDays(1);
		}
		return listDateAgenda;
	}
	
	@Override
	public List<TimeAgenda> getWorkTime(String strTimeStart, String strTimeEnd) {
		List<TimeAgenda> listTimeAgenda = new ArrayList<>();
		LocalTime time = LocalTime.parse(strTimeStart);
		LocalTime timeEnd = LocalTime.parse(strTimeEnd);
		while (time.isBefore(timeEnd)) {
			listTimeAgenda.add(new TimeAgenda(time));
			time = time.plusMinutes(30);
		}
		return listTimeAgenda;
	}	
}
