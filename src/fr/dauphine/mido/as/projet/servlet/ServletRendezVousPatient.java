package fr.dauphine.mido.as.projet.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.dauphine.mido.as.projet.beans.Patient;
import fr.dauphine.mido.as.projet.beans.Rendezvous;
import fr.dauphine.mido.as.projet.ejb.DateAgenda;
import fr.dauphine.mido.as.projet.ejb.ServicesPatient;
import fr.dauphine.mido.as.projet.ejb.ServicesRendezVous;
import fr.dauphine.mido.as.projet.ejb.TimeAgenda;
import fr.dauphine.mido.as.projet.mail.MailSender;

/**
 * Servlet implementation class ServletRendezVousPatient
 */
@WebServlet(name = "ServletRendezVousPatient", urlPatterns = {"/mesRendezVous"})
public class ServletRendezVousPatient extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    @EJB
    ServicesPatient servicesPatient;
    
    @EJB
    ServicesRendezVous servicesRendezVous;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletRendezVousPatient() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("type") == "patient") {
			String email = (String) request.getSession().getAttribute("login");
			Patient patient = this.servicesPatient.getPatientByEmail(email);
			List<Rendezvous> listeRendezVousPatient = this.servicesRendezVous.getRendezVousPatient(patient.getIdPatient());
			ArrayList<ArrayList<Object>> listeDetailsRendezVous = new ArrayList<ArrayList<Object>>();
			LocalDate date = LocalDate.now();
			LocalTime time = LocalTime.now();
			for(Rendezvous r : listeRendezVousPatient) {
				listeDetailsRendezVous.add(this.servicesRendezVous.getDetailsRendezVous(r.getIdRendezVous()));
			}
			
			request.setAttribute("patient", patient);
			request.setAttribute("listeDetailsRendezVous", listeDetailsRendezVous);
			request.setAttribute("date", date);
			request.setAttribute("time", time);
		    this.getServletContext().getRequestDispatcher("/jsp/rendezVousPatient.jsp").forward(request, response);
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
        String idRendezVous = request.getParameter("idRendezVous");
        String raisonAnnulation = request.getParameter("raisonAnnulation");
        System.out.println(raisonAnnulation);
        System.out.println(request.getParameter("raisonAnnulation"));
        if(raisonAnnulation.equals("")) {
        	request.setAttribute("warning", "Vous devez saisir une raison pour annuler un rendez-vous !");
			doGet(request, response);
        }
        else {
        	// TO DO
        	boolean lol = true;
        	if(lol) {
    			request.setAttribute("success", "Votre rendez-vous à bien été annulé !");
    			String email = (String) request.getSession().getAttribute("login");
    			String nom = (String) request.getSession().getAttribute("nom");
    			String prenom = (String) request.getSession().getAttribute("prenom");
    			doGet(request, response);
    			String mailContent = String.format("Bonjour %s %s,<br/><br/>Nous vous confirmons l'annulation de votre rendez-vous.<br/><br/>Cordialement, l'équipe", prenom, nom);
 		        MailSender sender = new MailSender();
 		        sender.sendMail("test@test.com", email, "Annulation d'un rendez-vous", mailContent);
    		}
    		else {
    			request.setAttribute("warning", "Une erreur est survenue lors de l'annulation du rendez-vous !");
    			doGet(request, response);
    		}
        }
	
	}

}
