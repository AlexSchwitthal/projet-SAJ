package fr.dauphine.mido.as.projet.servlet;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.dauphine.mido.as.projet.beans.Patient;
import fr.dauphine.mido.as.projet.beans.Rendezvous;
import fr.dauphine.mido.as.projet.ejb.ServicesPatient;
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
		List<Rendezvous> listeRDVAnnulés = this.servicesPatient.deletePatient(patient.getIdPatient());
		//boolean isDeleted = this.servicesPatient.deletePatient(patient.getIdPatient());
		System.out.println(listeRDVAnnulés + "mdr");
		if(listeRDVAnnulés != null) {
//			if (listeRDVAnnulés.size()>0) {
//				String contenuTableau ="";
//				for (Rendezvous rdv : listeRDVAnnulés) {
//					contenuTableau+="<td>" + rdv.getPlannings().get(0).getDate() + "</td>";
//					contenuTableau+="<td>" + rdv.getPlannings().get(0).getHeureDebut() + "</td>";
//					contenuTableau+="<td>" + rdv.getPlannings().get(0).getMedecin().getPersonne().getNom() + "</td>";
//					contenuTableau+="<td>" + rdv.getPlannings().get(0).getMedecin().getPersonne().getPrenom() + "</td>";
//					contenuTableau+= "<td>" + rdv.getPlannings().get(0).getCentremedical().getMedecinSpecialite(rdv.getPlannings().get(0).getMedecin().getIdMedecin()) + "</td>";
//					contenuTableau+="<td>" + rdv.getPlannings().get(0).getCentremedical().getNom() + "</td>";
//					contenuTableau+="<td>" + rdv.getPlannings().get(0).getCentremedical().getAdresse() + "</td>";
//					
//				}
//				this.mailContent = String.format("Bonjour %s %s,<br/><br/>Vous avez reçu ce courriel car vous avez supprimé votre compte de notre plateforme.<br/><br/>Voici la liste des rendez-vous annulés suite à la suppression de votre compte :<br/><table><tr>Date</tr><tr>Heure</tr><tr>Nom du medecin</tr><tr>Prenom du medecin<tr></tr><tr>Specialite du medecin</tr><tr>Adresse du centre</tr><tr>Tel. du centre</tr>%s</table><br/><br/>Nous esperons vous revoir sous peu et nous vous souhaitons bonne continuation.<br/><br/>Cordialement, l'équipe", patient.getPersonne().getPrenom(), patient.getPersonne().getNom(), contenuTableau);
//		   	}else {
//		   		this.mailContent = String.format("Bonjour %s %s,<br/><br/>Vous avez reçu ce courriel car vous avez supprimé votre compte de notre plateforme.<br/><br/> Nous esperons vous revoir sous peu et nous vous souhaitons bonne continuation.<br/><br/>Cordialement, l'équipe", patient.getPersonne().getPrenom(), patient.getPersonne().getNom());
//		       
//			}
//			this.sender = new MailSender();
//	        sender.sendMail("test@test.com", patient.getEmail(), MAIL_SUBJECT, mailContent);
        
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
