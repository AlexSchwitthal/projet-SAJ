package fr.dauphine.mido.as.projet.beans;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the medecin database table.
 * 
 */
@Entity
@NamedQuery(name="Medecin.findAll", query="SELECT m FROM Medecin m")
public class Medecin implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idMedecin;

	private String email;

	private String motDePasse;

	private String telephone;

	//bi-directional many-to-one association to Personne
	@ManyToOne
	@JoinColumn(name="idPersonne")
	private Personne personne;

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

}