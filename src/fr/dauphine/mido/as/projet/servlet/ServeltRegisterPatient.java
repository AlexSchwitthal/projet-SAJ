package fr.dauphine.mido.as.projet.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.dauphine.mido.as.projet.beans.Adresse;
import fr.dauphine.mido.as.projet.beans.Patient;
import fr.dauphine.mido.as.projet.beans.Personne;

/**
 * Servlet implementation class ServeltRegisterPatient
 */
@WebServlet(name = "ServeltRegisterPatient", urlPatterns = {"/registerPatient"})
public class ServeltRegisterPatient extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServeltRegisterPatient() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        this.getServletContext().getRequestDispatcher("/jsp/registerPatient.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
	        response.setContentType("text/html");
	        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	        // NOT WORKING
	        EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet-SAJ");
	        //EntityManager em = emf.createEntityManager();
	            
	        Personne personne = new Personne();
	        Patient patient = new Patient();
	        Adresse adresse = new Adresse();
	        response.setContentType("text/html");

	        personne.setNom(request.getParameter("nom"));
	        personne.setPrenom(request.getParameter("prenom"));
	        personne.setDateNaissance(sdf.parse(request.getParameter("dateNaissance")));

	        adresse.setAdresseComplete(request.getParameter("adresse"));
	        adresse.setCodePostal(request.getParameter("cp"));
	        adresse.setPays(request.getParameter("pays"));
	        
	        patient.setEmail(request.getParameter("email"));
	        patient.setTelephone(request.getParameter("tel"));
	        patient.setMotDePasse(request.getParameter("mdp"));
	        
	        patient.setPersonne(personne);
	        personne.setAdresse(adresse);

	     //   em.getTransaction().begin();
	    //    em.persist(adresse);
	    //    em.persist(personne);
	     //   em.persist(patient);
	    //   em.getTransaction().commit();
	      
	       //em.close();
	        emf.close();
		}
		catch (Exception e) {
            e.printStackTrace();
        };
	}

}
