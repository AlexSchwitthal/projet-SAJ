package fr.dauphine.mido.as.projet.beans;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the centremedical database table.
 * 
 */
@Entity
@Table (name="centremedical")
@NamedQuery(name="Centremedical.findAll", query="SELECT c FROM Centremedical c")
public class Centremedical implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idCentre;

	private String nom;

	private String telephone;

	//bi-directional many-to-one association to Adresse
	@ManyToOne
	@JoinColumn(name="idAdresse")
	private Adresse adresse;

	//bi-directional many-to-one association to Planning
	@OneToMany(mappedBy="centremedical")
	private List<Planning> plannings;

	//bi-directional many-to-one association to Spemedecin
	@OneToMany(mappedBy="centremedical")
	private List<Spemedecin> spemedecins;

	public Centremedical() {
	}

	public int getIdCentre() {
		return this.idCentre;
	}

	public void setIdCentre(int idCentre) {
		this.idCentre = idCentre;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Adresse getAdresse() {
		return this.adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	public List<Planning> getPlannings() {
		return this.plannings;
	}

	public void setPlannings(List<Planning> plannings) {
		this.plannings = plannings;
	}

	public Planning addPlanning(Planning planning) {
		getPlannings().add(planning);
		planning.setCentremedical(this);

		return planning;
	}

	public Planning removePlanning(Planning planning) {
		getPlannings().remove(planning);
		planning.setCentremedical(null);

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
		spemedecin.setCentremedical(this);

		return spemedecin;
	}

	public Spemedecin removeSpemedecin(Spemedecin spemedecin) {
		getSpemedecins().remove(spemedecin);
		spemedecin.setCentremedical(null);

		return spemedecin;
	}

}