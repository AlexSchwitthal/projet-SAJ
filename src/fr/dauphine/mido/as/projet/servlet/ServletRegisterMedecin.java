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
import fr.dauphine.mido.as.projet.ejb.ServicesSpecialite;
/**
 * Servlet implementation class ServletRegisterMedecin
 */
@WebServlet(name = "ServletRegisterMedecin", urlPatterns = {"/registerMedecin"})
public class ServletRegisterMedecin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    @EJB
    ServicesCentre servicesCentre;
    
    @EJB
    ServicesSpecialite servicesSpecialite;
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
		request.setAttribute("listeCentre", servicesCentre.getAllCentre());
		request.setAttribute("listeSpecialite", servicesSpecialite.getAllSpecialite());
        this.getServletContext().getRequestDispatcher("/jsp/registerMedecin.jsp").forward(request, response);
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
        
        String[] lines = request.getParameterValues("specialite");
        System.out.println(lines);
   //     for(String s : lines) {
    //    	System.out.println(s);
    //    }
       // System.out.println(request.getParameter("specialite[1]"));
		//System.out.println("test");
	}

}
