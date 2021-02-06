package fr.dauphine.mido.as.projet.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import fr.dauphine.mido.as.projet.beans.Medecin;
import fr.dauphine.mido.as.projet.beans.Planning;
import fr.dauphine.mido.as.projet.ejb.ServicesCentre;
import fr.dauphine.mido.as.projet.ejb.ServicesRendezVous;
import fr.dauphine.mido.as.projet.ejb.ServicesSpecialite;

/**
 * Servlet implementation class ServletRendezVous
 */
@WebServlet("/rendezVous")
public class ServletRendezVous extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	ServicesRendezVous servicesRendezVous;
	
	@EJB
	ServicesSpecialite servicesSpecialite;
	
	@EJB
	ServicesCentre servicesCentre;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletRendezVous() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("listeSpecialites", servicesSpecialite.getAllSpecialite());
		request.setAttribute("listeCentres", servicesCentre.getAllCentre());
		this.getServletContext().getRequestDispatcher("/jsp/prendreRendezVous.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getParameter("formName"));
		System.out.println(request.getParameterValues("formName").equals("formRechercheParNom"));
		if (request.getParameterValues("formName").length == 1) {
			String formName = request.getParameterValues("formName")[0];
			if (formName.equals("formRechercheParNom")) {
				rechercheParNom(request, response);
			}
			else if (formName.equals("formRechercheMulticriteres")) {
				rechercheMulticriteres(request, response);
			}
		}
	}
	
	protected void rechercheParNom(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String nomMedecin = request.getParameter("nomMedecin");
		servicesRendezVous.rechercherCreneauxDisponible(15, 1);
		ArrayList<Medecin> resultatsMedecins = servicesRendezVous.rechercheMedecin(nomMedecin);
		ArrayList<Planning> resultatsPlannings = null;
		HashMap<Medecin, ArrayList<Planning>> medecinsPlannings = new HashMap<Medecin, ArrayList<Planning>>();
		
		for (Medecin m : resultatsMedecins) {
			resultatsPlannings = servicesRendezVous.rechercherCreneauxDisponibles(m.getIdMedecin());
			//System.out.println(m.getIdMedecin() + " : " + resultatsPlannings);
			medecinsPlannings.put(m, resultatsPlannings);
		}
		
		
		/*request.setAttribute("medecinRecherche", nomMedecin);
		request.setAttribute("medecinsPlannings", medecinsPlannings);
		for (Medecin m : medecinsPlannings.keySet()) {
			System.out.println(m.getPersonne().getNom());
			for (Planning p : medecinsPlannings.get(m)) {
				System.out.println(" = " + p.getHeureDebut());
			}
		}
		System.out.println("done");*/
		//this.getServletContext().getRequestDispatcher("/jsp/afficherCreneaux.jsp").forward(request, response);
	}

	/*protected void rechercheParNom(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String nomMedecin = request.getParameter("nomMedecin");
		ArrayList<Medecin> resultats = servicesRendezVous.rechercheMedecin(nomMedecin);
		HttpSession session = request.getSession();
		session.setAttribute("userMedecinInput", nomMedecin);
		session.setAttribute("lesMedecins", resultats);
		this.getServletContext().getRequestDispatcher("/jsp/afficherCreneaux.jsp").forward(request, response);
	}*/

	protected void rechercheMulticriteres(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		if (request.getParameterValues("centre") == null) {
			System.out.println("all");
		}
		else {
			for (String s : request.getParameterValues("centre")) {
				System.out.println(s + "; ");
			}
		}
	}

}