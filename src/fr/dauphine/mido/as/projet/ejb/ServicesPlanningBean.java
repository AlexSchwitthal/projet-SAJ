package fr.dauphine.mido.as.projet.ejb;

import java.util.Date;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import fr.dauphine.mido.as.projet.beans.Medecin;
import fr.dauphine.mido.as.projet.beans.Planning;
import fr.dauphine.mido.as.projet.beans.Rendezvous;
import fr.dauphine.mido.as.projet.dao.DAOPlanning;

/**
 * Session Bean implementation class ServicesCentreBean
 */
@Stateless
@LocalBean
public class ServicesPlanningBean implements ServicesPlanning {
	
	private DAOPlanning daoPlanning = new DAOPlanning();
	
	private static DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd/MM/yyyy_HH:mm");

	@Override
	public Map<String, Planning> getPlannings(LocalDate startDate, LocalDate endDate) {
		Map<String, Planning> mapPlanning = new HashMap<>();

		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet-SAJ");
			EntityManager em = emf.createEntityManager();

			TypedQuery<Planning> query = em.createQuery("select p from Planning p", Planning.class);
			List<Planning> listPlanning = query.getResultList();

			for (Planning p : listPlanning) {
				// LocalDate date = LocalDate.ofInstant(p.getDate().toInstant(),
				// ZoneId.systemDefault());
				//LocalDate date = LocalDate.ofEpochDay(p.getDate().getTime() / 86400000);
				LocalDate date = Instant.ofEpochMilli(p.getDate().getTime())
			      .atZone(ZoneId.systemDefault())
			      .toLocalDate();
				LocalTime time = p.getHeureDebut().toLocalTime();
				// System.out.println("=====!!!!! " + p.getDate().getTime());
				LocalDateTime dateTime = LocalDateTime.of(date, time);

				String strDateTime = dateTime.format(DTF);
				mapPlanning.put(strDateTime, p);
			}

			emf.close();
			em.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapPlanning;
	}


	public boolean initPlanning(LocalDate startDate, LocalDate endDate) {
		// Ajout de planning dans la table si il n'existe pas
		boolean allExec = false;
		/*
		 * entityManager.getTransaction().begin();
		 * entityManager.createNamedQuery("deleteIndividuByNom") .setParameter("nom",
		 * "David Gayerie") .executeUpdate();
		 * 
		 * entityManager.getTransaction().commit();
		 */
		System.out.println("----------start.0-----------");
		
		try {
			
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet-SAJ");
			EntityManager em = emf.createEntityManager();

			System.out.println("----------start1-----------");

			ZoneId defaultZoneId = ZoneId.systemDefault();

			// creating the instance of LocalDate using the day, month, year info
			LocalDate localDate = startDate;

			System.out.println("----------start2-----------");

			// local date + atStartOfDay() + default time zone + toInstant() = Date
			Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
			Time ts = Time.valueOf("8:00:00");
			Time te = Time.valueOf("8:30:00");
			
			Planning planning = new Planning();
			planning.setDate(date);
			planning.setHeureDebut(ts);
			planning.setHeureFin(te);
			boolean isAvail = localDate.getDayOfWeek() != DayOfWeek.SUNDAY; 
			planning.setDisponible(isAvail);
			
			System.out.println("----------start3-----------");
			
			em.persist(planning);
			
			System.out.println("----------end-----------");
			
			emf.close();
			em.close();
			
			allExec = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return allExec;
	}
	
	@Override
	public Planning getPlanning(int idPlanning, Rendezvous rendezVous) {
		try {
			System.out.println("services avant appel dao");
			Planning planning = daoPlanning.getPlanning(idPlanning, rendezVous);
			return planning;
		} 
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
