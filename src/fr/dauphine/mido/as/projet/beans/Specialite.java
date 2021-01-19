package fr.dauphine.mido.as.projet.beans;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the specialite database table.
 * 
 */
@Entity
@NamedQuery(name="Specialite.findAll", query="SELECT s FROM Specialite s")
public class Specialite implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idSpecialite;

	private String libelle;

	//bi-directional many-to-one association to Spemedecin
	@OneToMany(mappedBy="specialite")
	private List<Spemedecin> spemedecins;

	public Specialite() {
	}

	public int getIdSpecialite() {
		return this.idSpecialite;
	}

	public void setIdSpecialite(int idSpecialite) {
		this.idSpecialite = idSpecialite;
	}

	public String getLibelle() {
		return this.libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public List<Spemedecin> getSpemedecins() {
		return this.spemedecins;
	}

	public void setSpemedecins(List<Spemedecin> spemedecins) {
		this.spemedecins = spemedecins;
	}

	public Spemedecin addSpemedecin(Spemedecin spemedecin) {
		getSpemedecins().add(spemedecin);
		spemedecin.setSpecialite(this);

		return spemedecin;
	}

	public Spemedecin removeSpemedecin(Spemedecin spemedecin) {
		getSpemedecins().remove(spemedecin);
		spemedecin.setSpecialite(null);

		return spemedecin;
	}

}