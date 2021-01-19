package fr.dauphine.mido.as.projet.beans;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the personne database table.
 * 
 */
@Entity
@NamedQuery(name="Personne.findAll", query="SELECT p FROM Personne p")
public class Personne implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idPersonne;

	@Temporal(TemporalType.DATE)
	@Column(name="date_naissance")
	private Date dateNaissance;

	private int idAdresse;

	private String nom;

	private String prenom;

	//bi-directional many-to-one association to Administrateur
	@OneToMany(mappedBy="personne")
	private List<Administrateur> administrateurs;

	//bi-directional many-to-one association to Medecin
	@OneToMany(mappedBy="personne")
	private List<Medecin> medecins;

	//bi-directional many-to-one association to Patient
	@OneToMany(mappedBy="personne")
	private List<Patient> patients;

	public Personne() {
	}

	public int getIdPersonne() {
		return this.idPersonne;
	}

	public void setIdPersonne(int idPersonne) {
		this.idPersonne = idPersonne;
	}

	public Date getDateNaissance() {
		return this.dateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public int getIdAdresse() {
		return this.idAdresse;
	}

	public void setIdAdresse(int idAdresse) {
		this.idAdresse = idAdresse;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return this.prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public List<Administrateur> getAdministrateurs() {
		return this.administrateurs;
	}

	public void setAdministrateurs(List<Administrateur> administrateurs) {
		this.administrateurs = administrateurs;
	}

	public Administrateur addAdministrateur(Administrateur administrateur) {
		getAdministrateurs().add(administrateur);
		administrateur.setPersonne(this);

		return administrateur;
	}

	public Administrateur removeAdministrateur(Administrateur administrateur) {
		getAdministrateurs().remove(administrateur);
		administrateur.setPersonne(null);

		return administrateur;
	}

	public List<Medecin> getMedecins() {
		return this.medecins;
	}

	public void setMedecins(List<Medecin> medecins) {
		this.medecins = medecins;
	}

	public Medecin addMedecin(Medecin medecin) {
		getMedecins().add(medecin);
		medecin.setPersonne(this);

		return medecin;
	}

	public Medecin removeMedecin(Medecin medecin) {
		getMedecins().remove(medecin);
		medecin.setPersonne(null);

		return medecin;
	}

	public List<Patient> getPatients() {
		return this.patients;
	}

	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}

	public Patient addPatient(Patient patient) {
		getPatients().add(patient);
		patient.setPersonne(this);

		return patient;
	}

	public Patient removePatient(Patient patient) {
		getPatients().remove(patient);
		patient.setPersonne(null);

		return patient;
	}

}