package fr.dauphine.mido.as.projet.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.dauphine.mido.as.projet.beans.Centremedical;
import fr.dauphine.mido.as.projet.beans.Medecin;
import fr.dauphine.mido.as.projet.beans.Patient;
import fr.dauphine.mido.as.projet.beans.Planning;
import fr.dauphine.mido.as.projet.beans.Rendezvous;
import fr.dauphine.mido.as.projet.beans.Spemedecin;
import fr.dauphine.mido.as.projet.dao.DAORendezVous;
import fr.dauphine.mido.as.projet.ejb.ServicesCentre;
import fr.dauphine.mido.as.projet.ejb.ServicesPatient;
import fr.dauphine.mido.as.projet.ejb.ServicesPlanning;
import fr.dauphine.mido.as.projet.ejb.ServicesRendezVous;
import fr.dauphine.mido.as.projet.ejb.ServicesSpecialite;
import fr.dauphine.mido.as.projet.mail.MailSender;

/**
 * Servlet implementation class ServletEnregistrerRendezVous
 */
@WebServlet("/enregistrerRendezVous")
public class ServletEnregistrerRendezVous extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ServletRendezVous servletRendezVous = new ServletRendezVous();   
	@EJB
	ServicesPatient servicesPatient;
	
	@EJB
	ServicesPlanning servicesPlanning;
	
	@EJB
	ServicesRendezVous servicesRendezVous;
	
	@EJB
	ServicesSpecialite servicesSpecialite;
	
	@EJB
	ServicesCentre servicesCentre;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletEnregistrerRendezVous() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("rendezVous");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		Rendezvous rendezVous = new Rendezvous();
		String email = (String) request.getSession().getAttribute("login");
		int idPlanning = Integer.parseInt(request.getParameter("idRendezVous"));
		
		rendezVous.setEtat(DAORendezVous.ETAT_ACTIF);
		rendezVous.setPatient(servicesPatient.getPatientByEmail(email));
		
		Planning planningInserted = servicesPlanning.enregistrerPlanning(idPlanning, rendezVous);
		
		if(planningInserted != null) {
			request.setAttribute("success", "Votre rendez-vous a bien été pris !");
    	}
    	else { 
            request.setAttribute("warning", "Une erreur est survenue lors de la prise du rendez-vous !");
    	}
		
		//servletRendezVous
		Patient patient = this.servicesPatient.getPatientByEmail(email);
		request.setAttribute("patient", patient);
		request.setAttribute("listeSpecialites", servicesSpecialite.getAllSpecialite());
		request.setAttribute("listeCentres", servicesCentre.getAllCentre());
		request.setAttribute("listeCreneauxHoraires", this.servletRendezVous.genererCreneauxHoraires());
		request.setAttribute("lesJours", servicesRendezVous.getJoursDisponibles());
		
		this.getServletContext().getRequestDispatcher("/jsp/prendreRendezVous.jsp").forward(request, response);
		
		
		
		if(planningInserted != null) {
			ArrayList<Object> elements = servicesRendezVous.getDetailsRendezVous(planningInserted.getRendezvous().getIdRendezVous());
			Medecin medecin = (Medecin) elements.get(0);
			Centremedical centre = (Centremedical) elements.get(1);
			Spemedecin speMedecin = (Spemedecin) elements.get(2);
			Planning planning = (Planning) elements.get(3);		
			String mailContent = String.format("Bonjour,<br/><br/>Vous vous confirmons la prise de votre rendez-vous de " 
					+ planning.getHeureDebutString() + " à " + planning.getHeureFinString() + " le " + planning.getDate().toString() 
					+ " avec le " + speMedecin.getSpecialite().getLibelle() + " " + medecin.getPersonne().getPrenom() + " " + 
					medecin.getPersonne().getNom() + " au centre " + centre.getNom() + " situé à l'adresse " +
					centre.getAdresse().getAdresseComplete() + ". Vous pouvez joindre le centre au numéro suivant : " + centre.getTelephone() + "<br/><br/>Cordialement, l'équipe");
			MailSender sender = new MailSender();
			sender.sendMail("test@test.com", email, "Prise d'un rendez-vous", mailContent);
		}
	}

}
