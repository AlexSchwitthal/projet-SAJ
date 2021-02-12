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

import fr.dauphine.mido.as.projet.beans.Centremedical;
import fr.dauphine.mido.as.projet.beans.Medecin;
import fr.dauphine.mido.as.projet.beans.Specialite;
import fr.dauphine.mido.as.projet.beans.Spemedecin;
import fr.dauphine.mido.as.projet.ejb.ServicesMedecin;
import fr.dauphine.mido.as.projet.ejb.ServicesRendezVous;
import fr.dauphine.mido.as.projet.ejb.ServicesSpeMedecin;
import fr.dauphine.mido.as.projet.mail.MailSender;

/**
 * Servlet implementation class ServletDeleteMedecinCentre
 */
@WebServlet(name = "ServletDeleteMedecinCentre", urlPatterns = {"/deleteMedecinCentre"})
public class ServletDeleteMedecinCentre extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB
	ServicesRendezVous servicesRendezVous;
	
	@EJB 
	ServicesMedecin servicesMedecin;
	
	@EJB
	ServicesSpeMedecin servicesSpeMedecin;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletDeleteMedecinCentre() {
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
		
		int idCentre = Integer.parseInt(request.getParameter("idCentre"));
		int idSpecialite = Integer.parseInt(request.getParameter("idSpecialite"));
		
		HttpSession session = request.getSession(true);
		if(session.getAttribute("login") != null) {
			String email = (String) session.getAttribute("login");
			Medecin medecin = servicesMedecin.getMedecinByEmail(email);
			
			
			if(!servicesRendezVous.hasRendezVousActifCentre(medecin.getEmail(), idCentre)) {
				Spemedecin speMedecin = servicesSpeMedecin.getSpeMedecinByMedecinCentre(medecin.getIdMedecin(), idCentre);
				boolean isDeleted = servicesSpeMedecin.deleteSpeMedecin(speMedecin.getIdSpeMedecin());
				if(isDeleted) {
					request.setAttribute("success", "l'attribution au centre a bien été supprimé !");
				}
				else {
					request.setAttribute("warning", "Une erreur est survenue lors de la suppression du centre !");
				}
			}
			else {
				request.setAttribute("warning", "l'attribution au centre ne peux pas être retiré, vous y avez encore des rendez-vous actifs !");
			}
			Map<Centremedical, Specialite> map = this.servicesMedecin.getCentreSpeById(medecin.getIdMedecin());
			request.setAttribute("map", map);
			request.setAttribute("medecin", medecin);
			this.getServletContext().getRequestDispatcher("/jsp/gestionMedecin.jsp").forward(request, response);
		}

	}
}
