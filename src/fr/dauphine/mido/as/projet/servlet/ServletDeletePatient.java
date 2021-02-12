package fr.dauphine.mido.as.projet.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.dauphine.mido.as.projet.beans.Centremedical;
import fr.dauphine.mido.as.projet.beans.Medecin;
import fr.dauphine.mido.as.projet.beans.Patient;
import fr.dauphine.mido.as.projet.beans.Planning;
import fr.dauphine.mido.as.projet.beans.Rendezvous;
import fr.dauphine.mido.as.projet.beans.Spemedecin;
import fr.dauphine.mido.as.projet.ejb.ServicesPatient;
import fr.dauphine.mido.as.projet.ejb.ServicesRendezVous;
import fr.dauphine.mido.as.projet.mail.MailSender;

/**
 * Servlet implementation class ServletDeletePatient
 */
@WebServlet(name = "ServletDeletePatient", urlPatterns = {"/deletePatient"})
public class ServletDeletePatient extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String MAIL_SUBJECT = "Suppression de votre compte";
	private MailSender sender;
	private String mailContent;
       
    @EJB
    ServicesPatient servicesPatient;
    
    @EJB
    ServicesRendezVous servicesRendezVous;
    
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
		List<Rendezvous> listeRDVAnnules = this.servicesPatient.deletePatient(patient.getIdPatient());
		//boolean isDeleted = this.servicesPatient.deletePatient(patient.getIdPatient());
		if(listeRDVAnnules != null) {			
			if (listeRDVAnnules.size()>0) {
				String contenuTableau ="";
				for (Rendezvous rdv : listeRDVAnnules) {
					ArrayList<Object> elements = servicesRendezVous.getDetailsRendezVous(rdv.getIdRendezVous());
					Medecin medecin = (Medecin) elements.get(0);
					Centremedical centre = (Centremedical) elements.get(1);
					Spemedecin speMedecin = (Spemedecin) elements.get(2);
					Planning planning = (Planning) elements.get(3);					
					contenuTableau+="<tr><td>" + planning.getDate().toString() + "</td>";
					contenuTableau+="<td>" + planning.getHeureDebut().toString()+ "</td>";
					contenuTableau+="<td>" + medecin.getPersonne().getNom() + "</td>";
					contenuTableau+="<td>" + medecin.getPersonne().getPrenom() + "</td>";
					contenuTableau+= "<td>" + speMedecin.getSpecialite().getLibelle() + "</td>";
					contenuTableau+="<td>" + centre.getNom() + "</td>";
					contenuTableau+="<td>" + centre.getAdresse().getAdresseComplete() + "</td>";
					contenuTableau+="<td>" + centre.getTelephone() + "</td></tr>";
				}
				this.mailContent = String.format("Bonjour %s %s,<br/><br/>Vous avez re�u ce courriel car vous avez supprim� votre compte de notre plateforme.<br/><br/>Voici la liste des rendez-vous annul�s suite � la suppression de votre compte :<br/><table><tr><th>Date</th><th>Heure</th><th>Nom du medecin</th><th>Prenom du medecin</th><th>Specialite du medecin</th><th>Nom du centre</th><th>Adresse du centre</th><th>Tel. du centre</th></tr>%s</table><br/><br/>Nous esperons vous revoir sous peu et nous vous souhaitons bonne continuation.<br/><br/>Cordialement, l'�quipe", patient.getPersonne().getPrenom(), patient.getPersonne().getNom(), contenuTableau);
		   	}else {
		   		this.mailContent = String.format("Bonjour %s %s,<br/><br/>Vous avez re�u ce courriel car vous avez supprim� votre compte de notre plateforme.<br/><br/> Nous esperons vous revoir sous peu et nous vous souhaitons bonne continuation.<br/><br/>Cordialement, l'�quipe", patient.getPersonne().getPrenom(), patient.getPersonne().getNom());
		       
			}
			this.sender = new MailSender();
	        sender.sendMail("test@test.com", patient.getEmail(), MAIL_SUBJECT, mailContent);
        
			session.invalidate();
			request.setAttribute("success", "Votre compte a bien �t� supprim� !");
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
