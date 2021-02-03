package fr.dauphine.mido.as.projet.beans;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the adresse database table.
 * 
 */
@Entity
@Table (name="adresse")
@NamedQuery(name="Adresse.findAll", query="SELECT a FROM Adresse a")
public class Adresse implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idAdresse;

	private String adresseComplete;

	private String codePostal;

	private String pays;

	private String ville;

	//bi-directional many-to-one association to Centremedical
	@OneToMany(mappedBy="adresse")
	private List<Centremedical> centremedicals;

	//bi-directional many-to-one association to Personne
	@OneToMany(mappedBy="adresse")
	private List<Personne> personnes;

	public Adresse() {
	}

	public int getIdAdresse() {
		return this.idAdresse;
	}

	public void setIdAdresse(int idAdresse) {
		this.idAdresse = idAdresse;
	}

	public String getAdresseComplete() {
		return this.adresseComplete;
	}

	public void setAdresseComplete(String adresseComplete) {
		this.adresseComplete = adresseComplete;
	}

	public String getCodePostal() {
		return this.codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getPays() {
		return this.pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}

	public String getVille() {
		return this.ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public List<Centremedical> getCentremedicals() {
		return this.centremedicals;
	}

	public void setCentremedicals(List<Centremedical> centremedicals) {
		this.centremedicals = centremedicals;
	}

	public Centremedical addCentremedical(Centremedical centremedical) {
		getCentremedicals().add(centremedical);
		centremedical.setAdresse(this);

		return centremedical;
	}

	public Centremedical removeCentremedical(Centremedical centremedical) {
		getCentremedicals().remove(centremedical);
		centremedical.setAdresse(null);

		return centremedical;
	}

	public List<Personne> getPersonnes() {
		return this.personnes;
	}

	public void setPersonnes(List<Personne> personnes) {
		this.personnes = personnes;
	}

	public Personne addPersonne(Personne personne) {
		getPersonnes().add(personne);
		personne.setAdresse(this);

		return personne;
	}

	public Personne removePersonne(Personne personne) {
		getPersonnes().remove(personne);
		personne.setAdresse(null);

		return personne;
	}

}