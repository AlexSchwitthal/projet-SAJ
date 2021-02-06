package fr.dauphine.mido.as.projet.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.dauphine.mido.as.projet.beans.Patient;
import fr.dauphine.mido.as.projet.ejb.ServicesPatient;

/**
 * Servlet implementation class ServletDeletePatient
 */
@WebServlet(name = "ServletDeletePatient", urlPatterns = {"/deletePatient"})
public class ServletDeletePatient extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    @EJB
    ServicesPatient servicesPatient;
    
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
		HttpSession session = request.getSession(true);
		String email = (String) session.getAttribute("login");
		Patient patient = this.servicesPatient.getPatientByEmail(email);
		boolean isDeleted = this.servicesPatient.deletePatient(patient.getIdPatient());
		if(isDeleted) {
			session.invalidate();
			request.setAttribute("success", "Votre compte a bien été supprimé !");
			this.getServletContext().getRequestDispatcher("/jsp/login.jsp").forward(request, response);
		}
		else {
			if(patient != null) {
				request.setAttribute("patient", patient);
				request.setAttribute("warning", "Une erreur est survenue lors de la suppression du compte !");
				this.getServletContext().getRequestDispatcher("/gestionPatient").forward(request, response);
			}

		}
		
	}

}
