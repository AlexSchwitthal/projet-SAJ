package fr.dauphine.mido.as.projet.ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * Session Bean implementation class ServicesAuthBean
 */
@Stateless
@LocalBean
public class ServicesAuthBean implements ServicesAuth {

	@Override
	public String login(String email, String mdp) {
       
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet-SAJ");
		EntityManager em = emf.createEntityManager();
		
		Query queryPatient = em.createQuery("select p from Patient p where p.email = ?1 and p.motDePasse = ?2");
		queryPatient.setParameter(1, email);
		queryPatient.setParameter(2, mdp);
		if(queryPatient.getResultList().size() == 1) {
			em.close();
			emf.close();
			return "patient";
		}
		
		Query queryMedecin = em.createQuery("select m from Medecin m where m.email = ?1 and m.motDePasse = ?2");
		queryMedecin.setParameter(1, email);
		queryMedecin.setParameter(2, mdp);
		if(queryMedecin.getResultList().size() == 1) {
			em.close();
			emf.close();
			return "medecin";
		}
		
		Query queryAdmin = em.createQuery("select a from Administrateur a where a.email = ?1 and a.motDePasse = ?2");
		queryAdmin.setParameter(1, email);
		queryAdmin.setParameter(2, mdp);
		if(queryAdmin.getResultList().size() == 1) {
			em.close();
			emf.close();
			return "administrateur";
		}
		
		em.close();
		emf.close();
		return "erreur";
	}

	@Override
	public boolean isLogged() {
      return false;
	}
}
