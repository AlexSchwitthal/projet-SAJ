package fr.dauphine.mido.as.projet.beans;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;


/**
 * The persistent class for the planning database table.
 * 
 */
@Entity
@NamedQuery(name="Planning.findAll", query="SELECT p FROM Planning p")
public class Planning implements Serializable {
	private static final long serialVersionUID = 1L;
	/*private static final Locale LOCALE_FR = new Locale("fr", "FR");
	private static final DateTimeFormatter LOCALIZED_DATE_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy", LOCALE_FR);
	private static final DateTimeFormatter LOCALIZED_DAY_OF_WEEK_FMT = DateTimeFormatter.ofPattern("EEEE", LOCALE_FR);*/

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idPlanning;

	@Temporal(TemporalType.DATE)
	private Date date;

	private Time heureDebut;

	private Time heureFin;

	//bi-directional many-to-one association to Centremedical
	@ManyToOne
	@JoinColumn(name="idCentre")
	private Centremedical centremedical;

	//bi-directional many-to-one association to Medecin
	@ManyToOne
	@JoinColumn(name="idMedecin")
	private Medecin medecin;

	//bi-directional many-to-one association to Rendezvous
	@ManyToOne
	@JoinColumn(name="idRendezVous")
	private Rendezvous rendezvous;

	public Planning() {
	}

	public int getIdPlanning() {
		return this.idPlanning;
	}

	public void setIdPlanning(int idPlanning) {
		this.idPlanning = idPlanning;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Time getHeureDebut() {
		return this.heureDebut;
	}

	public void setHeureDebut(Time heureDebut) {
		this.heureDebut = heureDebut;
	}

	public Time getHeureFin() {
		return this.heureFin;
	}

	public void setHeureFin(Time heureFin) {
		this.heureFin = heureFin;
	}

	public Centremedical getCentremedical() {
		return this.centremedical;
	}

	public void setCentremedical(Centremedical centremedical) {
		this.centremedical = centremedical;
	}

	public Medecin getMedecin() {
		return this.medecin;
	}

	public void setMedecin(Medecin medecin) {
		this.medecin = medecin;
	}

	public Rendezvous getRendezvous() {
		return this.rendezvous;
	}

	public void setRendezvous(Rendezvous rendezvous) {
		this.rendezvous = rendezvous;
	}
	/*
	public String getLocalizedDate() {
		return this.date.toLocalDate().format(LOCALIZED_DATE_FMT);
	}

	public String getLocalizedDayOfWeek() {
		return this.date.toLocalDate().format(LOCALIZED_DAY_OF_WEEK_FMT);
	}*/
}