package fr.dauphine.mido.as.projet.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.dauphine.mido.as.projet.beans.Centremedical;
import fr.dauphine.mido.as.projet.beans.Planning;
import fr.dauphine.mido.as.projet.beans.Rendezvous;
import fr.dauphine.mido.as.projet.ejb.ServicesPatient;
import fr.dauphine.mido.as.projet.ejb.ServicesPlanning;

/**
 * Servlet implementation class ServletEnregistrerRendezVous
 */
@WebServlet("/enregistrerRendezVous")
public class ServletEnregistrerRendezVous extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB
	ServicesPatient servicesPatient;
	
	@EJB
	ServicesPlanning servicesPlanning;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletEnregistrerRendezVous() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.sendRedirect("rendezVous");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		Rendezvous rendezVous = new Rendezvous();
		Planning planning = null;
		String email = (String) request.getSession().getAttribute("login");
		int idPlanning = Integer.parseInt(request.getParameter("idRendezVous"));
		
		rendezVous.setEtat("actif");
		rendezVous.setPatient(servicesPatient.getPatientByEmail(email));
		
		servicesPlanning.updatePlanning(idPlanning, false);
		doGet(request, response);
	}

}
