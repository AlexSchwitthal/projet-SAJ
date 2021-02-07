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
import fr.dauphine.mido.as.projet.dao.DAORendezVous;
import fr.dauphine.mido.as.projet.ejb.ServicesMedecin;
import fr.dauphine.mido.as.projet.ejb.ServicesRendezVous;
import fr.dauphine.mido.as.projet.mail.MailSender;

/**
 * Servlet implementation class ServletDeleteMedecin
 */
@WebServlet(name = "ServletDeleteMedecin", urlPatterns = {"/deleteMedecin"})
public class ServletDeleteMedecin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String MAIL_SUBJECT = "Suppression de votre compte";
	private MailSender sender;
	private String mailContent;
	
	@EJB
	ServicesRendezVous servicesRendezVous;
	
	@EJB 
	ServicesMedecin servicesMedecin;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletDeleteMedecin() {
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
		if(session.getAttribute("login") != null) {
			String email = (String) session.getAttribute("login");
			Medecin medecin = servicesMedecin.getMedecinByEmail(email);
			if(!servicesRendezVous.hasRendezVousActif(medecin.getEmail())) {
				boolean isDeleted = servicesMedecin.deleteMedecin(medecin.getIdMedecin());
				if(isDeleted) {
					session.invalidate();
					request.setAttribute("success", "Votre compte a bien été supprimé !");
			        this.getServletContext().getRequestDispatcher("/jsp/login.jsp").forward(request, response);
			        this.mailContent = String.format("Bonjour %s %s,<br/><br/>Vous avez reçu ce courriel car vous avez supprimé votre compte de notre plateforme.<br/><br/> Nous esperons vous revoir sous peu et nous vous souhaitons bonne continuation.<br/><br/>Cordialement, l'équipe", medecin.getPersonne().getPrenom(), medecin.getPersonne().getNom());
			        this.sender = new MailSender();
			        sender.sendMail("test@test.com", medecin.getEmail(), MAIL_SUBJECT, mailContent);
		        
				}
				else {
					request.setAttribute("medecin", medecin);
					request.setAttribute("warning", "Une erreur est survenue lors de la suppression du compte !");
					this.getServletContext().getRequestDispatcher("/jsp/gestionMedecin.jsp").forward(request, response);
				}
				
			}
			else {
				request.setAttribute("medecin", medecin);
				request.setAttribute("warning", "Votre compte ne peut pas être supprimé, vous avez encore des rendez-vous actifs !");
				this.getServletContext().getRequestDispatcher("/jsp/gestionMedecin.jsp").forward(request, response);
			}
		}
	}

}
