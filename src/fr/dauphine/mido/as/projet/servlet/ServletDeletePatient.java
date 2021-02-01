package fr.dauphine.mido.as.projet.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.dauphine.mido.as.projet.beans.Patient;
import fr.dauphine.mido.as.projet.ejb.ServicesPersonne;

/**
 * Servlet implementation class ServletDeletePatient
 */
@WebServlet(name = "ServletDeletePatient", urlPatterns = {"/deletePatient"})
public class ServletDeletePatient extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    @EJB
    ServicesPersonne servicesPersonne;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletDeletePatient() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 this.getServletContext().getRequestDispatcher("/jsp/erreur.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Patient patient = this.servicesPersonne.getPatientByEmail("a.b@gmail.com");
		boolean isDeleted = this.servicesPersonne.deletePatient(patient.getIdPatient());
		
		if(isDeleted) {
			request.setAttribute("success", "Votre compte a bien été supprimé !");
	        this.getServletContext().getRequestDispatcher("/login").forward(request, response);
		}
		else {
			request.setAttribute("warning", "Une erreur est survenue lors de l'inscription du médecin !");
			this.getServletContext().getRequestDispatcher("/gestionPatient").forward(request, response);
		}
		
	}

}
