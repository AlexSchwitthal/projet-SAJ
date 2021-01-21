package fr.dauphine.mido.as.projet.beans;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the medecin database table.
 * 
 */
@Entity
@NamedQuery(name="Medecin.findAll", query="SELECT m FROM Medecin m")
public class Medecin implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idMedecin;

	private String email;

	private String motDePasse;

	private String telephone;

	//bi-directional many-to-one association to Personne
	@ManyToOne
	@JoinColumn(name="idPersonne")
	private Personne personne;

	//bi-directional many-to-one association to Planning
	@OneToMany(mappedBy="medecin")
	private List<Planning> plannings;

	//bi-directional many-to-one association to Spemedecin
	@OneToMany(mappedBy="medecin")
	private List<Spemedecin> spemedecins;

	public Medecin() {
	}

	public int getIdMedecin() {
		return this.idMedecin;
	}

	public void setIdMedecin(int idMedecin) {
		this.idMedecin = idMedecin;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMotDePasse() {
		return this.motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Personne getPersonne() {
		return this.personne;
	}

	public void setPersonne(Personne personne) {
		this.personne = personne;
	}

	public List<Planning> getPlannings() {
		return this.plannings;
	}

	public void setPlannings(List<Planning> plannings) {
		this.plannings = plannings;
	}

	public Planning addPlanning(Planning planning) {
		getPlannings().add(planning);
		planning.setMedecin(this);

		return planning;
	}

	public Planning removePlanning(Planning planning) {
		getPlannings().remove(planning);
		planning.setMedecin(null);

		return planning;
	}

	public List<Spemedecin> getSpemedecins() {
		return this.spemedecins;
	}

	public void setSpemedecins(List<Spemedecin> spemedecins) {
		this.spemedecins = spemedecins;
	}

	public Spemedecin addSpemedecin(Spemedecin spemedecin) {
		getSpemedecins().add(spemedecin);
		spemedecin.setMedecin(this);

		return spemedecin;
	}

	public Spemedecin removeSpemedecin(Spemedecin spemedecin) {
		getSpemedecins().remove(spemedecin);
		spemedecin.setMedecin(null);

		return spemedecin;
	}

}