package fr.dauphine.mido.as.projet.ejb;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateAgenda implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Locale LOCALE_FR = new Locale("fr", "FR");
	private static final DateTimeFormatter LOCALIZED_DATE_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy", LOCALE_FR);
	private static final DateTimeFormatter LOCALIZED_DAY_OF_WEEK_FMT = DateTimeFormatter.ofPattern("EEEE", LOCALE_FR);
	
	private LocalDate date;
	private String localizedDate;
	private String localizedDayOfWeek;
	
	public DateAgenda(LocalDate date) {
		this.date = date;
		this.localizedDate = this.date.format(LOCALIZED_DATE_FMT);
		this.localizedDayOfWeek = this.date.format(LOCALIZED_DAY_OF_WEEK_FMT);
	}
	
	public LocalDate getDate() {
		return this.date;
	}
	
	public String getLocalizedDate() {
		return this.localizedDate;
	}

	public String getLocalizedDayOfWeek() {
		return this.localizedDayOfWeek;
	}
}
