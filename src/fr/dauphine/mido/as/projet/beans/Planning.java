package fr.dauphine.mido.as.projet.beans;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the planning database table.
 * 
 */
@Entity
@Table (name="planning")
@NamedQuery(name="Planning.findAll", query="SELECT p FROM Planning p")
public class Planning implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idPlanning;

	@Temporal(TemporalType.DATE)
	private Date date;

	private Time heureDebut;

	private Time heureFin;
	
	private boolean disponible;

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

	public boolean getDisponible() {
		return disponible;
	}

	public void setDisponible(boolean b) {
		this.disponible = b;
	}
}