package fr.dauphine.mido.as.projet.ejb;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.transaction.Transactional;
import fr.dauphine.mido.as.projet.beans.Centremedical;
import fr.dauphine.mido.as.projet.beans.Medecin;
import fr.dauphine.mido.as.projet.beans.Planning;
import fr.dauphine.mido.as.projet.beans.Rendezvous;
import fr.dauphine.mido.as.projet.beans.Spemedecin;
import fr.dauphine.mido.as.projet.dao.DAOMedecin;
import fr.dauphine.mido.as.projet.dao.DAOPlanning;
import fr.dauphine.mido.as.projet.dao.DAORendezVous;
import fr.dauphine.mido.as.projet.dao.DAOSpeMedecin;

@Stateless
@Local
public class ServicesRendezVousBean implements ServicesRendezVous {

	private DAORendezVous daoRendezVous = new DAORendezVous();
	private DAOMedecin daoMedecin = new DAOMedecin();
	private DAOSpeMedecin daoSpeMedecin = new DAOSpeMedecin();
	private DAOPlanning daoPlanning = new DAOPlanning();

	@PersistenceUnit
	private EntityManagerFactory emf;

	@Transactional
	public ArrayList<Medecin> rechercheMedecin(String nomMedecin) {
		try {
			System.out.println("In rechercheMedecin()");
			EntityManager em = emf.createEntityManager();
			Query query = em.createQuery("SELECT m FROM Medecin m inner join m.personne p where p.nom = :nomMedecin")
					.setParameter("nomMedecin", nomMedecin);
			List<Medecin> listeMedecins = query.getResultList();
			ArrayList<Medecin> resultats = new ArrayList<Medecin>();

			for (Medecin m : listeMedecins) {
				resultats.add(m);
			}
			return resultats;
		}
		catch (Exception e) {
			return null;
		}
	}

	public HashMap<Centremedical, ArrayList<Planning>> rechercherCreneauxParCentre(Medecin m) {
		HashMap<Centremedical, ArrayList<Planning>> planningCentre = new HashMap<Centremedical, ArrayList<Planning>>();
		Centremedical cm = null;

		for (Spemedecin sm : m.getSpemedecins()) {
			cm = sm.getCentremedical();
			planningCentre.put(cm, rechercherCreneauxDisponibles(m.getIdMedecin(), cm.getIdCentre()));
		}

		return planningCentre;
	}

	public HashMap<Medecin, HashMap<Centremedical, ArrayList<Planning>>> rechercherCreneauxDisponibles(String nomMedecin) {
		ArrayList<Medecin> listeMedecins = rechercheMedecin(nomMedecin);
		HashMap<Medecin, HashMap<Centremedical, ArrayList<Planning>>> lesCreneaux = new HashMap<Medecin, HashMap<Centremedical,ArrayList<Planning>>>();

		for (Medecin m : listeMedecins) {
			lesCreneaux.put(m, rechercherCreneauxParCentre(m));
		}

		return lesCreneaux;
	}

	public ArrayList<Planning> rechercherCreneauxDisponibles(int idMedecin, int idCentre) {
		try {
			EntityManager em = emf.createEntityManager();
			LocalDate t1 = LocalDate.now();

			Query query = em.createQuery(
					"SELECT planning FROM Planning planning "
							+ "inner join planning.medecin medecin "
							+ "inner join planning.centremedical centre "
							+ "where medecin.idMedecin = :idMedecin "
							+ "and centre.idCentre = :idCentre "
							+ "and planning.date between :t1 and :t2")
					.setParameter("idMedecin", idMedecin)
					.setParameter("idCentre", idCentre)
					.setParameter("t1", asDate(t1), TemporalType.DATE)
					.setParameter("t2", asDate(t1.plusDays(20)), TemporalType.DATE);
			List<Planning> listePlannings = query.getResultList();
			ArrayList<Planning> resultats = new ArrayList<Planning>();

			for (Planning p : listePlannings) {
				resultats.add(p);
			}

			return resultats;
		}
		catch (Exception  e) {
			return null;
		}
	}

	public ArrayList<Planning> rechercherCreneauxDisponibles(int idSpecialite, ArrayList<Integer> idCentres, ArrayList<Time> heuresDebut, ArrayList<Date> jours) {

		try {
			ArrayList<Planning> resultats = new ArrayList<Planning>();
			EntityManager em = emf.createEntityManager();
			Query query = null;
			System.out.println("in rechercherCreneauxDispos");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date d = sdf.parse("2021-02-15");
			
			String queryString = "select planning from Planning planning,"
					+ " Spemedecin spemedecin"
					+ " where spemedecin.medecin.idMedecin = planning.medecin.idMedecin"
					+ " and spemedecin.centremedical.idCentre = planning.centremedical.idCentre"
					+ " and spemedecin.specialite.idSpecialite = :idSpecialite";
			queryString += (idCentres != null) ? " and planning.centremedical.idCentre in :idCentre" : " and :idCentre IS NULL";
			queryString += (jours != null) ? " and planning.date in :date" : " and :date IS NULL";
			queryString += (heuresDebut != null) ? " and planning.heureDebut in :heuresDeb" : " and :heuresDeb IS NULL";
			
			query = em.createQuery(queryString)
					.setParameter("idSpecialite", idSpecialite)
					.setParameter("idCentre", idCentres)
					.setParameter("date", jours)
					.setParameter("heuresDeb", heuresDebut);

			List<Planning> listePlannings = query.getResultList();
			System.out.println("size list = " + listePlannings.size());
			for (Planning p : listePlannings) {
				resultats.add(p);
				System.out.println(p.getIdPlanning());
			}

			return resultats;
		}
		catch (Exception  e) {
			return null;
		}
	}

	public TreeSet<DateAgenda> getJoursDisponibles() {
		EntityManager em = emf.createEntityManager();
		TreeSet<DateAgenda> lesJours = new TreeSet<DateAgenda>(new Comparator<DateAgenda>() {
			@Override
			public int compare(DateAgenda d1, DateAgenda d2) {
				return d1.getDate().compareTo(d2.getDate());
			}
		});
		Query query = null;
		LocalDate t1 = LocalDate.now();
		DateAgenda date = null;
		//DateTimeFormatter formatUS = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		query = em.createQuery("select distinct planning from Planning planning "
				+ "where planning.date between :t1 and :t2")
				.setParameter("t1", asDate(t1), TemporalType.DATE)
				.setParameter("t2", asDate(t1.plusDays(20)), TemporalType.DATE);

		List<Planning> listePlannings = query.getResultList();
		System.out.println("size list = " + listePlannings.size());
		for (Planning p : listePlannings) {
			date = new DateAgenda(LocalDate.parse(p.getDate().toString()));
			lesJours.add(date);
		}

		return lesJours;
	}

	public Date asDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	@Override
	public boolean hasRendezVousActif(String email) {
		try {
			Medecin medecin = daoMedecin.getMedecinByEmail(email);
			if(medecin == null) {
				return false;
			}
			List<Rendezvous> listRDV = daoRendezVous.getListeRendezVousMedecin(medecin.getIdMedecin());
			if(listRDV.size() == 0) {
				return false;
			}
			else {
				return true;
			}
		}
		catch(Exception e) {
			e.printStackTrace();	
			return false;
		}
	}

	@Override
	public boolean hasRendezVousActifCentre(String email, int idCentre) {
		try {
			Medecin medecin = daoMedecin.getMedecinByEmail(email);
			if(medecin == null) {
				return false;
			}
			List<Rendezvous> listRDV = daoRendezVous.getListeRendezVousMedecinCentre(medecin.getIdMedecin(), idCentre);
			if(listRDV.size() == 0) {
				return false;
			}
			else {
				return true;
			}
		}
		catch(Exception e) {
			e.printStackTrace();	
			return false;
		}
	}

	@Override
	public ArrayList<Object> getDetailsRendezVous(int idRendezVous) {
		Medecin medecin = daoRendezVous.getMedecinRendezVous(idRendezVous);
		Centremedical centre = daoRendezVous.getCentreRendezVous(idRendezVous);
		Spemedecin speMedecin = daoSpeMedecin.getSpeMedecinByMedecinCentre(medecin.getIdMedecin(), centre.getIdCentre());
		Planning planning = daoPlanning.getPlanningByRendezVous(idRendezVous);
		ArrayList<Object> elements = new ArrayList<Object>();
		
		elements.add(medecin);
		elements.add(centre);
		elements.add(speMedecin);
		elements.add(planning);
		
		return elements;
	}


}