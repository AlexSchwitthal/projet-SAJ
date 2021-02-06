package fr.dauphine.mido.as.projet.servlet;

import java.io.IOException;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.dauphine.mido.as.projet.beans.Medecin;
import fr.dauphine.mido.as.projet.ejb.ServicesMedecin;

/**
 * Servlet implementation class ServletGestionMedecin
 */
@WebServlet(name = "ServletGestionMedecin", urlPatterns = {"/gestionMedecin"})
public class ServletGestionMedecin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB
 	ServicesMedecin servicesMedecin;
 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletGestionMedecin() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("type") == "medecin") {
			String email = (String) request.getSession().getAttribute("login");
			Medecin medecin = this.servicesMedecin.getMedecinByEmail(email);
			request.setAttribute("medecin", medecin);
			this.getServletContext().getRequestDispatcher("/jsp/gestionMedecin.jsp").forward(request, response);
		}
		else if(request.getSession().getAttribute("login") != null) {
			response.sendRedirect("home");
		}
		else {
			response.sendRedirect("login");
		}
		 
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = (String) request.getSession().getAttribute("login");
		Medecin medecin = this.servicesMedecin.getMedecinByEmail(email);
		Map<String, String[]> parameters = request.getParameterMap();
		Medecin updatedMedecin = this.servicesMedecin.updateMedecin(medecin.getIdMedecin(), parameters);
		if(updatedMedecin != null) {
			HttpSession session = request.getSession(true);
			session.setAttribute("login", updatedMedecin.getEmail());
			session.setAttribute("nom", updatedMedecin.getPersonne().getNom());
			session.setAttribute("prenom", updatedMedecin.getPersonne().getPrenom());
			request.setAttribute("medecin", updatedMedecin);
			request.setAttribute("success", "Vos données ont bien été mis à jour !");
		    this.getServletContext().getRequestDispatcher("/jsp/gestionMedecin.jsp").forward(request, response);
		}
	}
}
