package fr.dauphine.mido.as.projet.ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import fr.dauphine.mido.as.projet.beans.Specialite;

/**
 * Session Bean implementation class ServicesAuthBean
 */
@Stateless
@LocalBean
public class ServicesAuthBean implements ServicesAuth {

	@Override
	public boolean login(HttpServletRequest request, String email, String mdp) {
        
		HttpSession session = request.getSession(true);
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet-SAJ");
		EntityManager em = emf.createEntityManager();
		
		em.close();
		emf.close();
		return false;
	}

	@Override
	public boolean logout(HttpServletRequest request) {
        try {
    		HttpSession session = request.getSession();
            session.invalidate();
            return true;
        }
        catch(Exception e) {
			e.printStackTrace();	
			return false;
		}
	}

	@Override
	public boolean isLogged(HttpServletRequest request) {
		 
		HttpSession session = request.getSession(true);
        if (session.getAttribute("login") != null) {
        	return true;
        }
        else {
        	return false;
        }
	}
}
