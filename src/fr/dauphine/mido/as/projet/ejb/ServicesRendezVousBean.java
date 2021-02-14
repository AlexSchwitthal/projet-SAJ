package fr.dauphine.mido.as.projet.ejb;

import java.sql.Time;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

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


	public ArrayList<Medecin> rechercheMedecin(String nomMedecin) {
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet-SAJ");
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


	public ArrayList<Planning> rechercherCreneauxDisponibles(int idSpecialite, ArrayList<Integer> idCentres, ArrayList<Time> heuresDebut, ArrayList<Date> jours) {
		return daoPlanning.rechercherCreneauxDisponibles(idSpecialite, idCentres, heuresDebut, jours);
	}

	public TreeSet<DateAgenda> getJoursDisponibles() {
		LocalDate t1 = LocalDate.now();
		return daoPlanning.getJoursDisponibles(asDate(t1), asDate(t1.plusDays(20)));
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

	@Override
	public boolean cancelRendezVous(int idPlanning, String messageAnnulation) {
		return daoRendezVous.cancelRendezVous(idPlanning, messageAnnulation);
	}

	@Override
	public List<Rendezvous> getRendezVousPatient(int idPatient) {
		try {
			return daoRendezVous.getRendezVousPatient(idPatient);
		}
		catch(Exception e) {
			e.printStackTrace();	
			return null;
		}
	}
	
	@Override
	public List<Rendezvous> getRendezVousByDate(LocalDate localDate){
		return daoRendezVous.getRendezVousByDate(localDate);
	}
}