package fr.dauphine.mido.as.projet.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.dauphine.mido.as.projet.beans.Adresse;
import fr.dauphine.mido.as.projet.beans.Patient;
import fr.dauphine.mido.as.projet.beans.Personne;
import fr.dauphine.mido.as.projet.ejb.ServicesPatient;
import fr.dauphine.mido.as.projet.mail.MailSender;

/**
 * Servlet implementation class ServeltRegisterPatient
 */

@WebServlet(name = "ServletRegisterPatient", urlPatterns = {"/registerPatient"})
public class ServletRegisterPatient extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String MAIL_SUBJECT = "Bienvenue sur notre plateforme";
	private MailSender sender;
	private String mailContent;
	
    @EJB
    ServicesPatient servicesPatient;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletRegisterPatient() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("login") == null) {
			this.getServletContext().getRequestDispatcher("/jsp/registerPatient.jsp").forward(request, response);
		}
		else {
			response.sendRedirect("home");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
	        response.setContentType("text/html");
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	            
	        Personne personne = new Personne();
	        Patient patient = new Patient();
	        Adresse adresse = new Adresse();
	        
	        personne.setNom(request.getParameter("nom"));
	        personne.setPrenom(request.getParameter("prenom"));
	        personne.setDateNaissance(sdf.parse(request.getParameter("dateNaissance")));

	        adresse.setAdresseComplete(request.getParameter("adresse"));
	        adresse.setCodePostal(request.getParameter("cp"));
	        adresse.setPays(request.getParameter("pays"));
	        adresse.setVille(request.getParameter("ville"));
	        
	        patient.setEmail(request.getParameter("email"));
	        patient.setTelephone(request.getParameter("telephone"));
	        patient.setMotDePasse(request.getParameter("mdp"));

	        String insert = servicesPatient.ajoutPatient(patient, personne, adresse);
	        if(insert.equals("ok")) {
	            request.setAttribute("success", "Vous vous êtes bien inscrit !");
		        this.getServletContext().getRequestDispatcher("/jsp/login.jsp").forward(request, response);
		        this.mailContent = String.format("Bonjour %s %s,<br/><br/>Vous avez reçu ce courriel car vous vous êtes inscrit sur notre plateforme.<br/><br/> Vous pouvez désormais prendre des rendez-vous médicaux sur notre site. Nous esperons que notre service vous portera satisfaction.<br/><br/>Cordialement, l'équipe", personne.getPrenom(), personne.getNom());
		        this.sender = new MailSender();
		        sender.sendMail("test@test.com", patient.getEmail(), MAIL_SUBJECT, mailContent);
	        }
	        else {
	        	request.setAttribute("patient", patient);
	        	request.setAttribute("adresse", adresse);
	        	request.setAttribute("personne", personne);
	        	request.setAttribute("date", request.getParameter("dateNaissance"));
	        	if(insert.equals("email")) {
		            request.setAttribute("warning", "L'adresse e-mail que vous avez saisie est déjà prise !");
	        	}
	        	else { 
		            request.setAttribute("warning", "Une erreur est survenue lors de votre inscription !");
	        	}
	        	 this.getServletContext().getRequestDispatcher("/jsp/registerPatient.jsp").forward(request, response);
	        }
		}
		catch (IOException | ParseException e) {
            request.setAttribute("warning", "Une erreur est survenue lors de votre inscription !");
	        this.getServletContext().getRequestDispatcher("/jsp/registerPatient.jsp").forward(request, response);
            e.printStackTrace();
        };
	}

}
