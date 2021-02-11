package fr.dauphine.mido.as.projet.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		request.setAttribute("listeCreneauxHoraires", genererCreneauxHoraires());
		request.setAttribute("lesJours", servicesRendezVous.getJoursDisponibles());
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
		
		request.setAttribute("lesCreneaux", servicesRendezVous.rechercherCreneauxDisponibles(nomMedecin));
		this.getServletContext().getRequestDispatcher("/jsp/afficherCreneaux.jsp").forward(request, response);
	}

	protected void rechercheMulticriteres(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		/*if (request.getParameterValues("centre") == null) {
			System.out.println("all");
		}
		else {
			for (String s : request.getParameterValues("centre")) {
				System.out.println(s + "; ");
			}
		}*/
		int idSpecialite = Integer.parseInt(request.getParameter("specialite"));
		ArrayList<Integer> idCentres = convertStringArrayToIntArrayList(request.getParameterValues("centre"));
		List<String> heuresDebut = Arrays.asList(request.getParameterValues("heureDebut"));
		List<String> jours = Arrays.asList(request.getParameterValues("jours"));
		request.setAttribute("lesCreneaux", servicesRendezVous.rechercherCreneauxDisponibles(idSpecialite, idCentres, heuresDebut, jours));
		
		for (String s : heuresDebut) {
			System.out.println(s);
		}
		System.out.println("speMed = " + request.getParameter("specialite"));
		this.getServletContext().getRequestDispatcher("/jsp/afficherCreneaux2.jsp").forward(request, response);
	}
	
	
	public ArrayList<Integer> convertStringArrayToIntArrayList(String[] stringArray) {
		ArrayList<Integer> intArray = new ArrayList<Integer>();
		for (String s : stringArray) {
			intArray.add(Integer.parseInt(s));
		}
		
		return intArray;
	}
	
	public HashMap<String, String> genererCreneauxHoraires() {
		HashMap<String, String> creneauxHoraires = new HashMap<String, String>();
		int i = 8;
		
		while (i < 20) {
			creneauxHoraires.put(i + ":00:00", i + ":30:00");
			creneauxHoraires.put(i + ":30:00", i+1 + ":00:00");
			i++;
		}
		
		return creneauxHoraires;
	}
	
	//public ArrayList

}