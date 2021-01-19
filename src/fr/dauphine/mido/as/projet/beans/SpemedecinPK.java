package fr.dauphine.mido.as.projet.beans;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the spemedecin database table.
 * 
 */
@Embeddable
public class SpemedecinPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private int idMedecin;

	@Column(insertable=false, updatable=false)
	private int idCentre;

	@Column(insertable=false, updatable=false)
	private int idSpecialite;

	public SpemedecinPK() {
	}
	public int getIdMedecin() {
		return this.idMedecin;
	}
	public void setIdMedecin(int idMedecin) {
		this.idMedecin = idMedecin;
	}
	public int getIdCentre() {
		return this.idCentre;
	}
	public void setIdCentre(int idCentre) {
		this.idCentre = idCentre;
	}
	public int getIdSpecialite() {
		return this.idSpecialite;
	}
	public void setIdSpecialite(int idSpecialite) {
		this.idSpecialite = idSpecialite;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SpemedecinPK)) {
			return false;
		}
		SpemedecinPK castOther = (SpemedecinPK)other;
		return 
			(this.idMedecin == castOther.idMedecin)
			&& (this.idCentre == castOther.idCentre)
			&& (this.idSpecialite == castOther.idSpecialite);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idMedecin;
		hash = hash * prime + this.idCentre;
		hash = hash * prime + this.idSpecialite;
		
		return hash;
	}
}