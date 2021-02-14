package fr.dauphine.mido.as.projet.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.dauphine.mido.as.projet.beans.Adresse;
import fr.dauphine.mido.as.projet.beans.Medecin;
import fr.dauphine.mido.as.projet.beans.Personne;
import fr.dauphine.mido.as.projet.ejb.ServicesCentre;
import fr.dauphine.mido.as.projet.ejb.ServicesMedecin;
import fr.dauphine.mido.as.projet.ejb.ServicesSpecialite;
import fr.dauphine.mido.as.projet.mail.MailSender;
/**
 * Servlet implementation class ServletRegisterMedecin
 */
@WebServlet(name = "ServletRegisterMedecin", urlPatterns = {"/registerMedecin"})
public class ServletRegisterMedecin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String MAIL_SUBJECT = "Bienvenue sur notre plateforme";
	private String mailContent;
       
    @EJB
    ServicesCentre servicesCentre;
    
    @EJB
    ServicesSpecialite servicesSpecialite;
    
    @EJB
    ServicesMedecin servicesMedecin;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletRegisterMedecin() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("type") == "administrateur") {
			request.setAttribute("listeCentre", servicesCentre.getAllCentre());
			request.setAttribute("listeSpecialite", servicesSpecialite.getAllSpecialite());
	        this.getServletContext().getRequestDispatcher("/jsp/registerMedecin.jsp").forward(request, response);
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
		response.setContentType("text/html");
		
		Personne personne = new Personne();
		Adresse adresse = new Adresse();
		Medecin medecin = new Medecin();
		
        personne.setNom(request.getParameter("nom"));
        personne.setPrenom(request.getParameter("prenom"));
        
        adresse.setAdresseComplete(request.getParameter("adresse"));
        
        medecin.setEmail(request.getParameter("email"));
        medecin.setTelephone(request.getParameter("telephone"));
        medecin.setMotDePasse(request.getParameter("mdp"));
        
        String[] listeCentre = request.getParameterValues("centreMedical");
        String[] listeSpecialite = request.getParameterValues("specialite");

        String insert = servicesMedecin.ajoutMedecin(medecin, personne, adresse, listeCentre, listeSpecialite);
        if(insert.equals("ok")) {
            request.setAttribute("success", "Le medecin a bien été inscrit !");
            if(listeCentre.length == 1) {
            	this.mailContent = String.format("Bonjour %s %s,<br/><br/>Vous avez reçu ce courriel car vous avez êtes inscrit sur notre plateforme par un de nos administrateurs.<br/><br/>Votre centre affilié est %s.<br/><br/>Pour vous connecter, utilisez votre adresse mail \"%s\" et votre mot de passe \"%s\", que nous vous invitons à changer rapidement à des fins de sécurité. <br/><br/>Nous esperons que notre service vous portera satisfaction.<br/><br/>Cordialement, l'équipe", personne.getPrenom(), personne.getNom(), this.servicesCentre.getCentreById(Integer.parseInt(listeCentre[0])).getNom(), medecin.getEmail(), medecin.getMotDePasse());
     	       
            }
            else {
            	String centres = "";
            	for (int i =0; i < listeCentre.length; ++i) {
            		centres += this.servicesCentre.getCentreById(Integer.parseInt(listeCentre[i])).getNom();
            		if(i != listeCentre.length - 1) {
            			centres += ", ";
            		}
            	}
            	this.mailContent = String.format("Bonjour %s %s,<br/><br/>Vous avez reçu ce courriel car vous avez êtes inscrit sur notre plateforme par un de nos administrateurs.<br/><br/>Vos centres affiliées sont %s.<br/><br/>Pour vous connecter, utilisez votre adresse mail \"%s\" et votre mot de passe \"%s\", que nous vous invitons à changer rapidement à des fins de sécurité. <br/><br/>Nous esperons que notre service vous portera satisfaction.<br/><br/>Cordialement, l'équipe", personne.getPrenom(), personne.getNom(), centres, medecin.getEmail(), medecin.getMotDePasse());
      	       
            }
        }
        else {
        	request.setAttribute("medecin", medecin);
        	request.setAttribute("personne", personne);
        	request.setAttribute("adresse", adresse);
        	if(insert.equals("email")) {
            	request.setAttribute("warning", "L'adresse e-mail que vous avez saisie est déjà  prise !");
            }
        	else if(insert.equals("centre")) {
        		request.setAttribute("warning", "Vous ne pouvez pas inscrire plusieurs fois le même médecin dans un même centre !");
            }
        	else {
        		request.setAttribute("warning", "Une erreur est survenue lors de l'inscription du médecin !");
            }
        }
        this.doGet(request, response);
        
        if(insert.equals("ok")) {
        	MailSender sender = new MailSender();
	        sender.sendMail("test@test.com", medecin.getEmail(), MAIL_SUBJECT, mailContent);
        }
	}

}
