package fr.dauphine.mido.as.projet.beans;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

import javax.persistence.*;

import fr.dauphine.mido.as.projet.ejb.DateAgenda;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;


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

	public Specialite getMedecinSpecialite(int idMedecin) {
		for (Spemedecin sm : this.spemedecins) {
			if (sm.getMedecin().getIdMedecin() == idMedecin) {
				return sm.getSpecialite();
			}
		}
		return null;
	}

	public TreeMap<DateAgenda, ArrayList<Planning>> planningMed(Medecin m) {
		TreeMap<DateAgenda, ArrayList<Planning>> planningsQuotidien = new TreeMap<DateAgenda, ArrayList<Planning>>(new Comparator<DateAgenda>() {
			@Override
			public int compare(DateAgenda d1, DateAgenda d2) {
				return d1.getDate().compareTo(d2.getDate());
			}
		});
		int nbJours = 20;

		LocalDate localDate = LocalDate.now();
		for (int i = 0; i < nbJours; i++) {
			planningsQuotidien.put(new DateAgenda(localDate), new ArrayList<Planning>());
			localDate = localDate.plusDays(1);
		}
		
		LocalDate dateNow = LocalDate.now();
		LocalTime localTime = LocalTime.now();
		Time currentTime = Time.valueOf(localTime);
		Date currentDate = Date.from(dateNow.atStartOfDay(ZoneId.systemDefault()).toInstant());
		
		for (Planning p : this.plannings) {
			if (p.getDisponible() && p.getMedecin().getIdMedecin() == m.getIdMedecin()) {

				for (DateAgenda d :planningsQuotidien.keySet()) {
					if (d.getDate().getDayOfMonth() == p.getDate().getDate() && d.getDate().getMonthValue() == p.getDate().getMonth()+1 && d.getDate().getYear() == p.getDate().getYear()+1900) {
						Date date = Date.from(dateNow.atStartOfDay(ZoneId.systemDefault()).toInstant());
						if(currentDate.before(p.getDate()) || (currentDate.equals(p.getDate()) && currentTime.before(p.getHeureDebut()))) {
							planningsQuotidien.get(d).add(p);
						}
					}
				}
			}
		}
		return planningsQuotidien;
	}

}