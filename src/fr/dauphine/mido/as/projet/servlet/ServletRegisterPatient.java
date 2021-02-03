package fr.dauphine.mido.as.projet.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.dauphine.mido.as.projet.beans.Adresse;
import fr.dauphine.mido.as.projet.beans.Patient;
import fr.dauphine.mido.as.projet.beans.Personne;
import fr.dauphine.mido.as.projet.ejb.ServicesPersonne;

/**
 * Servlet implementation class ServeltRegisterPatient
 */

@WebServlet(name = "ServletRegisterPatient", urlPatterns = {"/registerPatient"})
public class ServletRegisterPatient extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    @EJB
    ServicesPersonne servicesPersonne;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletRegisterPatient() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("login") == null) {
			this.getServletContext().getRequestDispatcher("/jsp/registerPatient.jsp").forward(request, response);
		}
		else {
			response.sendRedirect("home");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
	        response.setContentType("text/html");
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	            
	        Personne personne = new Personne();
	        Patient patient = new Patient();
	        Adresse adresse = new Adresse();
	        //response.setContentType("text/html");

	        personne.setNom(request.getParameter("nom"));
	        personne.setPrenom(request.getParameter("prenom"));
	        personne.setDateNaissance(sdf.parse(request.getParameter("dateNaissance")));

	        adresse.setAdresseComplete(request.getParameter("adresse"));
	        adresse.setCodePostal(request.getParameter("cp"));
	        adresse.setPays(request.getParameter("pays"));
	        adresse.setVille(request.getParameter("ville"));
	        
	        patient.setEmail(request.getParameter("email"));
	        patient.setTelephone(request.getParameter("telephone"));
	        patient.setMotDePasse(request.getParameter("mdp"));

	        boolean insert = servicesPersonne.ajoutPatient(patient, personne, adresse);
	        if(insert) {
	            request.setAttribute("success", "Vous vous êtes bien inscrit !");
		        this.getServletContext().getRequestDispatcher("/login").forward(request, response);
	        }
	        else {
	            request.setAttribute("warning", "Une erreur est survenue lors de votre inscription !");
		        this.getServletContext().getRequestDispatcher("/jsp/registerPatient.jsp").forward(request, response);
	        }

		}
		catch (Exception e) {
            request.setAttribute("warning", "Une erreur est survenue lors de votre inscription !");
	        this.getServletContext().getRequestDispatcher("/jsp/registerPatient.jsp").forward(request, response);
            e.printStackTrace();
        };
	}

}
