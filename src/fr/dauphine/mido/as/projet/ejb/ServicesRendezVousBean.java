package fr.dauphine.mido.as.projet.ejb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.transaction.Transactional;

import fr.dauphine.mido.as.projet.beans.Centremedical;
import fr.dauphine.mido.as.projet.beans.Medecin;
import fr.dauphine.mido.as.projet.beans.Planning;
import fr.dauphine.mido.as.projet.beans.Rendezvous;
import fr.dauphine.mido.as.projet.beans.Spemedecin;
import fr.dauphine.mido.as.projet.dao.DAOMedecin;
import fr.dauphine.mido.as.projet.dao.DAORendezVous;

@Stateless
@Local
public class ServicesRendezVousBean implements ServicesRendezVous {
	
	private DAORendezVous daoRendezVous = new DAORendezVous();
	private DAOMedecin daoMedecin = new DAOMedecin();
	
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
			Query query = em.createQuery(
					"SELECT planning FROM Planning planning "
							+ "inner join planning.medecin medecin "
							+ "inner join planning.centremedical centre "
							+ "where medecin.idMedecin = :idMedecin "
							+ "and centre.idCentre = :idCentre")
					.setParameter("idMedecin", idMedecin)
					.setParameter("idCentre", idCentre);
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
	
public ArrayList<Planning> rechercherCreneauxDisponibles(int idSpecialite, ArrayList<Integer> idCentres, List<String> heuresDebut) {
		
		
		try {
			ArrayList<Planning> resultats = new ArrayList<Planning>();
			EntityManager em = emf.createEntityManager();
			Query query = null;
			System.out.println("in rechercherCreneauxDispos");
				/*for (Date date : dates) {
					for (String creneau : creneaux) {*/
				        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				        java.util.Date d = sdf.parse("2021-02-15");
						query = em.createQuery("select planning from Planning planning, "
								+ "Spemedecin spemedecin "
								+ "where spemedecin.medecin.idMedecin = planning.medecin.idMedecin "
								+ "and spemedecin.centremedical.idCentre = planning.centremedical.idCentre "
								+ "and planning.centremedical.idCentre in :idCentre "
								+ "and spemedecin.specialite.idSpecialite = :idSpecialite "
								+ "and planning.date = :date")
								//+ "and planning.heureDebut in ('20:00:00', '15:00:00')")
								.setParameter("idSpecialite", idSpecialite)
								.setParameter("idCentre", idCentres)
								.setParameter("date", d);
							
						List<Planning> listePlannings = query.getResultList();
						System.out.println("size list = " + listePlannings.size());
						for (Planning p : listePlannings) {
							resultats.add(p);
							System.out.println(p.getIdPlanning());
						}
			/*	}
			}*/

			return resultats;
		}
		catch (Exception  e) {
			return null;
		}
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
	
	
}