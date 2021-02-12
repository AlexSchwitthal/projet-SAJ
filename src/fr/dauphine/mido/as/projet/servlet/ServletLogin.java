package fr.dauphine.mido.as.projet.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.dauphine.mido.as.projet.beans.Medecin;
import fr.dauphine.mido.as.projet.beans.Personne;
import fr.dauphine.mido.as.projet.ejb.ServicesMedecin;
import fr.dauphine.mido.as.projet.ejb.ServicesPatient;
import fr.dauphine.mido.as.projet.ejb.ServicesPersonne;

/**
 * Servlet implementation class ServletTest
 */
@WebServlet(name = "ServletLogin", urlPatterns = {"/login"})
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    @EJB
    ServicesPersonne servicesPersonne;
    
    @EJB
    ServicesMedecin servicesMedecin;
    
    @EJB
    ServicesPatient servicesPatient;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletLogin() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("login") == null) {
	        this.getServletContext().getRequestDispatcher("/jsp/login.jsp").forward(request, response);
		}
		else {
			response.sendRedirect("home");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = this.servicesPersonne.login(request.getParameter("login"), request.getParameter("mdp"));
		if(type.equals("erreur")) {
			 request.setAttribute("warning", "Identifiant incorrect !");
			 this.getServletContext().getRequestDispatcher("/jsp/login.jsp").forward(request, response);
		}
		else {
			Personne personneLogged = this.servicesPersonne.getPersonneByEmail(type, request.getParameter("login"));
			HttpSession session = request.getSession(true);
			String email = request.getParameter("login");
			session.setAttribute("login", email);
			session.setAttribute("type", type);
			session.setAttribute("nom", personneLogged.getNom());
			session.setAttribute("prenom", personneLogged.getPrenom());
			session.setAttribute("personne", personneLogged);
			Medecin medecin = servicesMedecin.getMedecinByEmail(email);
			session.setAttribute("medecin", medecin);
			session.setAttribute("patient", servicesPatient.getPatientByEmail(email));
            session.setMaxInactiveInterval(3600);
            response.sendRedirect("home");
		}
	}
}
