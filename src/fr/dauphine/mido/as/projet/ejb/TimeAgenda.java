package fr.dauphine.mido.as.projet.ejb;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TimeAgenda implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Locale LOCALE_FR = new Locale("fr", "FR");
	private static final DateTimeFormatter LOCALIZED_TIME_FMT = DateTimeFormatter.ofPattern("HH:mm", LOCALE_FR);
	
	private LocalTime time;
	private String formattedTime;
	
	public TimeAgenda(LocalTime time) {
		this.time = time;
		this.formattedTime = this.time.format(LOCALIZED_TIME_FMT);
	}
	
	public String getFormattedTime() {
		return this.formattedTime;
	}
}
