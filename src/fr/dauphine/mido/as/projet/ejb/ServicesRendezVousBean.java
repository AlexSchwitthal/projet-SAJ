package fr.dauphine.mido.as.projet.ejb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import fr.dauphine.mido.as.projet.beans.Centremedical;
import fr.dauphine.mido.as.projet.beans.Medecin;
import fr.dauphine.mido.as.projet.beans.Planning;
import fr.dauphine.mido.as.projet.beans.Rendezvous;

@Stateless
@Local
public class ServicesRendezVousBean implements ServicesRendezVous{
	@PersistenceUnit
	private EntityManagerFactory emf;
	
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
	
	public ArrayList<Planning> rechercherCreneauxDisponibles(int idMedecin) {
		try {
			EntityManager em = emf.createEntityManager();
			Query query = em.createQuery(
					"SELECT planning FROM Planning planning "
					+ "inner join planning.medecin med "
					+ "where med.idMedecin = 15");
				//	.setParameter("idMedecin", idMedecin);
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
	
	public ArrayList<Planning> rechercherCreneauxDisponible(int idMedecin, int idCentre) {
		try {
			EntityManager em = emf.createEntityManager();
			Query query = em.createQuery(
					"SELECT planning FROM Planning planning "
					+ "inner join planning.medecin med "
					+ "inner join planning.centremedical centre"
					+ "where med.idMedecin = 15 "
					+ "and centre.idCentre = 1");
				//	.setParameter("idMedecin", idMedecin);
			List<Planning> listePlannings = query.getResultList();
			ArrayList<Planning> resultats = new ArrayList<Planning>();
			
			for (Planning p : listePlannings) {
				resultats.add(p);
				System.out.println(p.getHeureDebut());
			}
			return resultats;
		}
		catch (Exception  e) {
			return null;
		}
	}
	
}