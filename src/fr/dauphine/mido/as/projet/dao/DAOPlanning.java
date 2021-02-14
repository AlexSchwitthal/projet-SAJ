package fr.dauphine.mido.as.projet.dao;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeSet;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import fr.dauphine.mido.as.projet.beans.Centremedical;
import fr.dauphine.mido.as.projet.beans.Medecin;
import fr.dauphine.mido.as.projet.beans.Planning;
import fr.dauphine.mido.as.projet.beans.Rendezvous;
import fr.dauphine.mido.as.projet.beans.Spemedecin;
import fr.dauphine.mido.as.projet.ejb.DateAgenda;

public class DAOPlanning {
	private static DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd/MM/yyyy_HH:mm");
	private static ZoneId DEFAULT_ZONE_ID = ZoneId.systemDefault();

	public Map<String, Planning> getPlannings(LocalDate startDate, LocalDate endDate, Medecin medecin,
			Centremedical centre) {
		Map<String, Planning> mapPlanning = new HashMap<>();

		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet-SAJ");
			EntityManager em = emf.createEntityManager();
			EntityManager em2 = emf.createEntityManager();

			Date sDate = Date.from(startDate.atStartOfDay(DEFAULT_ZONE_ID).toInstant());
			Date eDate = Date.from(endDate.atStartOfDay(DEFAULT_ZONE_ID).toInstant());

			TypedQuery<Spemedecin> query = em.createQuery(
					"select s from Spemedecin s WHERE s.medecin.idMedecin = ?1 AND s.centremedical.idCentre = ?2",
					Spemedecin.class);
			query.setParameter(1, medecin.getIdMedecin());
			query.setParameter(2, centre.getIdCentre());

			Spemedecin spemedecin = query.getSingleResult();
			boolean isActivated = spemedecin.isActivated();

			// "select p from Planning p WHERE p.medecin.idMedecin = ?1 AND
			// p.centremedical.idCentre = ?2 AND p.date BETWEEN ?3 AND ?4",
			TypedQuery<Planning> query2 = em2.createQuery(
					"select p from Planning p left join p.rendezvous r WHERE p.medecin.idMedecin = ?1 AND p.centremedical.idCentre = ?2 AND (p.date BETWEEN ?3 AND ?4) AND (?5=true OR r is not NULL)",
					Planning.class);

			query2.setParameter(1, medecin.getIdMedecin());
			query2.setParameter(2, centre.getIdCentre());
			query2.setParameter(3, sDate, TemporalType.DATE);
			query2.setParameter(4, eDate, TemporalType.DATE);
			query2.setParameter(5, isActivated);

			List<Planning> listPlanning = query2.getResultList();

			for (Planning p : listPlanning) {
				LocalDate date = Instant.ofEpochMilli(p.getDate().getTime()).atZone(ZoneId.systemDefault())
						.toLocalDate();
				LocalTime time = p.getHeureDebut().toLocalTime();
				LocalDateTime dateTime = LocalDateTime.of(date, time);

				String strDateTime = dateTime.format(DTF);
				mapPlanning.put(strDateTime, p);
			}

			emf.close();
			em.close();
			em2.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapPlanning;
	}

	public Planning getPlanning(int idPlanning, Rendezvous rendezVous) {
		try {
			System.out.println("debut method dao");
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet-SAJ");
			EntityManager em = emf.createEntityManager();

			Planning planning = em.find(Planning.class, idPlanning);
			em.persist(rendezVous);
			planning.setRendezvous(rendezVous);

			// em.merge(planning);
			em.flush();
			em.close();
			emf.close();
			System.out.println("fin method dao");
			return planning;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Planning enregistrerPlanning(int idPlanning, Rendezvous rendezVous) {
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet-SAJ");
			EntityManager em = emf.createEntityManager();

			Planning planning = em.find(Planning.class, idPlanning);
			em.persist(rendezVous);
			planning.setRendezvous(rendezVous);
			planning.setDisponible(false);

			em.flush();
			em.close();
			emf.close();

			return planning;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean initPlanning(LocalDate startDate, LocalDate endDate, Medecin medecin, Centremedical centre) {
		// Ajout de planning dans la table si il n'existe pas
		boolean allExec = false;

		try {

			EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet-SAJ");
			EntityManager em = emf.createEntityManager();

			// creating the instance of LocalDate using the day, month, year info

			LocalDate localDate = startDate.plusDays(0);

			while (localDate.isBefore(endDate)) {
				Date date = Date.from(localDate.atStartOfDay(DEFAULT_ZONE_ID).toInstant());
				boolean isDayOff = localDate.getDayOfWeek() == DayOfWeek.SUNDAY;

				TypedQuery<Planning> query = em.createQuery(
						"select p from Planning p where p.date = ?1 AND p.medecin.idMedecin = ?2 AND p.centremedical.idCentre = ?3",
						Planning.class);

				query.setParameter(1, date);
				query.setParameter(2, medecin.getIdMedecin());
				query.setParameter(3, centre.getIdCentre());

				List<Planning> results = query.getResultList();
				if (results.isEmpty()) {

					for (int i = 0; i < 24; i++) {
						long hour = 8 + i / 2;
						long min = i % 2 == 0 ? 0 : 30;

						Planning planning = createPlanning(date, hour, min, isDayOff, medecin, centre);
						em.persist(planning);
					}
				}
				localDate = localDate.plusDays(1);
			}

			emf.close();
			em.close();

			allExec = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return allExec;
	}

	public Planning createPlanning(Date date, long hour, long min, boolean isDayOff, Medecin medecin,
			Centremedical centre) {
		Planning p = new Planning();

		p.setDate(date);
		TimeZone tz = TimeZone.getDefault();
		long offset = tz.getRawOffset();
		long s = (hour * 3600 + min * 60) * 1000;
		long e = (hour * 3600 + (min + 30) * 60) * 1000;
		Time ts = new Time(s - offset);
		Time te = new Time(e - offset);

		p.setHeureDebut(ts);
		p.setHeureFin(te);
		p.setMedecin(medecin);
		p.setCentremedical(centre);
		p.setDisponible(!isDayOff);

		return p;
	}

	public boolean updatePlanning(int idPlanning, boolean isDisponible) {
		boolean updated = false;

		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet-SAJ");
			EntityManager em = emf.createEntityManager();
			Planning planning = em.find(Planning.class, idPlanning);

			planning.setDisponible(isDisponible);
			em.merge(planning);
			em.flush();
			emf.close();
			em.close();

			updated = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return updated;
	}

	public Planning getPlanningByRendezVous(int idRendezVous) {
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet-SAJ");
			EntityManager em = emf.createEntityManager();

			Query query = em.createQuery("select p from Planning p where p.rendezvous.idRendezVous = ?1");
			query.setParameter(1, idRendezVous);
			query.setMaxResults(1);

			List<Planning> results = query.getResultList();
			if (results == null || results.isEmpty()) {
				return null;
			} else {
				em.close();
				emf.close();
				return results.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public TreeSet<DateAgenda> getJoursDisponibles(Date d1, Date d2) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet-SAJ");
		EntityManager em = emf.createEntityManager();
		TreeSet<DateAgenda> lesJours = new TreeSet<DateAgenda>(new Comparator<DateAgenda>() {
			@Override
			public int compare(DateAgenda d1, DateAgenda d2) {
				return d1.getDate().compareTo(d2.getDate());
			}
		});
		DateAgenda date = null;

		Query query = em
				.createQuery(
						"select distinct planning from Planning planning " + "where planning.date between :t1 and :t2")
				.setParameter("t1", d1, TemporalType.DATE).setParameter("t2", d2, TemporalType.DATE);

		List<Planning> listePlannings = query.getResultList();

		for (Planning p : listePlannings) {
			date = new DateAgenda(LocalDate.parse(p.getDate().toString()));
			lesJours.add(date);
		}

		em.close();
		emf.close();

		return lesJours;
	}

	public ArrayList<Planning> rechercherCreneauxDisponibles(int idSpecialite, ArrayList<Integer> idCentres,
			ArrayList<Time> heuresDebut, ArrayList<Date> jours) {
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet-SAJ");
			ArrayList<Planning> resultats = new ArrayList<Planning>();
			EntityManager em = emf.createEntityManager();
			Query query = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date d = sdf.parse("2021-02-15");

			String queryString = "select planning from Planning planning," + " Spemedecin spemedecin"
					+ " where spemedecin.medecin.idMedecin = planning.medecin.idMedecin"
					+ " and spemedecin.centremedical.idCentre = planning.centremedical.idCentre"
					+ " and spemedecin.specialite.idSpecialite = :idSpecialite";
			queryString += (idCentres != null) ? " and planning.centremedical.idCentre in :idCentre"
					: " and :idCentre IS NULL";
			queryString += (jours != null) ? " and planning.date in :date" : " and :date IS NULL";
			queryString += (heuresDebut != null) ? " and planning.heureDebut in :heuresDeb" : " and :heuresDeb IS NULL";

			query = em.createQuery(queryString).setParameter("idSpecialite", idSpecialite)
					.setParameter("idCentre", idCentres).setParameter("date", jours)
					.setParameter("heuresDeb", heuresDebut);

			List<Planning> listePlannings = query.getResultList();

			for (Planning p : listePlannings) {
				resultats.add(p);
				System.out.println(p.getIdPlanning());
			}

			em.close();
			emf.close();

			return resultats;
		} catch (Exception e) {
			return null;
		}
	}

	public boolean setRendezVousNull(int idPlanning) {
		boolean updated = false;
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet-SAJ");
			EntityManager em = emf.createEntityManager();
			Planning planning = em.find(Planning.class, idPlanning);
			planning.setRendezvous(null);
			em.merge(planning);
			em.flush();
			emf.close();
			em.close();
			updated = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return updated;
	}
}
