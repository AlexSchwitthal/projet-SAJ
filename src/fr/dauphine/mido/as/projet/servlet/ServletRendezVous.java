package fr.dauphine.mido.as.projet.servlet;

import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

		int idSpecialite = Integer.parseInt(request.getParameter("specialite"));
		String[] paramsCentres = request.getParameterValues("centre");
		String[] paramsHeuresDebut = request.getParameterValues("heureDebut");
		String[] paramsJours = request.getParameterValues("jours");

		ArrayList<Integer> idCentres = convertStringArrayToIntArrayList(paramsCentres);
		ArrayList<Date> jours = convertListStringDateToListDate(paramsJours);
		ArrayList<Time> heuresDebut = convertListStringTimeToListTime(paramsHeuresDebut);

		request.setAttribute("lesCreneaux", servicesRendezVous.rechercherCreneauxDisponibles(idSpecialite, idCentres, heuresDebut, jours));

		System.out.println("speMed = " + request.getParameter("specialite"));
		this.getServletContext().getRequestDispatcher("/jsp/afficherCreneaux2.jsp").forward(request, response);
	}


	public ArrayList<Integer> convertStringArrayToIntArrayList(String[] stringArray) {
		ArrayList<Integer> intArray = new ArrayList<Integer>();
		if (stringArray != null && stringArray.length > 0) {
			for (String s : stringArray) {
				intArray.add(Integer.parseInt(s));
			}

			return intArray;
		}
		else {
			return null;
		}
	}
	
	public ArrayList<Date> convertListStringDateToListDate(String[] stringDate) {
		try {
			if (stringDate != null) {
				ArrayList<Date> listeDates = new ArrayList<Date>();
				DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

				for (String s : Arrays.asList(stringDate)) {
					listeDates.add(formatter.parse(s));
				}
				return listeDates;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<Time> convertListStringTimeToListTime(String[] stringTime) {
		try {
			if (stringTime != null) {
				
				ArrayList<Time> listeHeures = new ArrayList<Time>();
				DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
				for (String s : Arrays.asList(stringTime)) {
					listeHeures.add(new Time(formatter.parse(s).getTime()));
				} 
				return listeHeures;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public TreeMap<String, String> genererCreneauxHoraires() {
		TreeMap<String, String> creneauxHoraires = new TreeMap<String, String>();
		
		for (int i = 8; i < 10; i++) {
			creneauxHoraires.put("0" + i + ":00:00", i + ":30:00");
			creneauxHoraires.put("0" + i + ":30:00", i+1 + ":00:00");
		}

		for (int i = 10; i < 20; i++) {
			creneauxHoraires.put(i + ":00:00", i + ":30:00");
			creneauxHoraires.put(i + ":30:00", i+1 + ":00:00");
		}

		return creneauxHoraires;
	}

	//public ArrayList

}