package fr.dauphine.mido.as.projet.beans;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the patient database table.
 * 
 */
@Entity
@Table (name="patient")
@NamedQuery(name="Patient.findAll", query="SELECT p FROM Patient p")
public class Patient implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idPatient;

	private String email;

	private String motDePasse;

	private String telephone;

	private String etat = "Actif";
	
	//bi-directional many-to-one association to Personne
	@ManyToOne
	@JoinColumn(name="idPersonne")
	private Personne personne;

	//bi-directional many-to-one association to Rendezvous
	@OneToMany(mappedBy="patient")
	private List<Rendezvous> rendezvouses;

	public Patient() {
	}

	public int getIdPatient() {
		return this.idPatient;
	}

	public void setIdPatient(int idPatient) {
		this.idPatient = idPatient;
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

	public String getEtat() {
		return this.etat;
	}
	
	public void setEtat(String etat) {
		this.etat = etat;
	}
	
	public Personne getPersonne() {
		return this.personne;
	}

	public void setPersonne(Personne personne) {
		this.personne = personne;
	}

	public List<Rendezvous> getRendezvouses() {
		return this.rendezvouses;
	}

	public void setRendezvouses(List<Rendezvous> rendezvouses) {
		this.rendezvouses = rendezvouses;
	}

	public Rendezvous addRendezvous(Rendezvous rendezvous) {
		getRendezvouses().add(rendezvous);
		rendezvous.setPatient(this);

		return rendezvous;
	}

	public Rendezvous removeRendezvous(Rendezvous rendezvous) {
		getRendezvouses().remove(rendezvous);
		rendezvous.setPatient(null);

		return rendezvous;
	}

}