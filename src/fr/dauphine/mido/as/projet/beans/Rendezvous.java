package fr.dauphine.mido.as.projet.beans;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the rendezvous database table.
 * 
 */
@Entity
@NamedQuery(name="Rendezvous.findAll", query="SELECT r FROM Rendezvous r")
public class Rendezvous implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idRendezVous;

	private String etat;

	//bi-directional many-to-one association to Planning
	@OneToMany(mappedBy="rendezvous")
	private List<Planning> plannings;

	//bi-directional many-to-one association to Patient
	@ManyToOne
	@JoinColumn(name="idPatient")
	private Patient patient;

	public Rendezvous() {
	}

	public int getIdRendezVous() {
		return this.idRendezVous;
	}

	public void setIdRendezVous(int idRendezVous) {
		this.idRendezVous = idRendezVous;
	}

	public String getEtat() {
		return this.etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public List<Planning> getPlannings() {
		return this.plannings;
	}

	public void setPlannings(List<Planning> plannings) {
		this.plannings = plannings;
	}

	public Planning addPlanning(Planning planning) {
		getPlannings().add(planning);
		planning.setRendezvous(this);

		return planning;
	}

	public Planning removePlanning(Planning planning) {
		getPlannings().remove(planning);
		planning.setRendezvous(null);

		return planning;
	}

	public Patient getPatient() {
		return this.patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

}