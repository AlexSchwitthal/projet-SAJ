package fr.dauphine.mido.as.projet.beans;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the spemedecin database table.
 * 
 */
@Entity
@Table (name="spemedecin")
@NamedQuery(name="Spemedecin.findAll", query="SELECT s FROM Spemedecin s")
public class Spemedecin implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idSpeMedecin;

	//bi-directional many-to-one association to Centremedical
	@ManyToOne
	@JoinColumn(name="idCentre")
	private Centremedical centremedical;

	//bi-directional many-to-one association to Medecin
	@ManyToOne
	@JoinColumn(name="idMedecin")
	private Medecin medecin;

	//bi-directional many-to-one association to Specialite
	@ManyToOne
	@JoinColumn(name="idSpecialite")
	private Specialite specialite;
	
	private boolean isActivated;

	public Spemedecin() {
	}

	public int getIdSpeMedecin() {
		return this.idSpeMedecin;
	}

	public void setIdSpeMedecin(int idSpeMedecin) {
		this.idSpeMedecin = idSpeMedecin;
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

	public Specialite getSpecialite() {
		return this.specialite;
	}

	public void setSpecialite(Specialite specialite) {
		this.specialite = specialite;
	}

	public boolean isActivated() {
		return isActivated;
	}

	public void setActivated(boolean isActivated) {
		this.isActivated = isActivated;
	}

}