package fr.dauphine.mido.as.projet.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.dauphine.mido.as.projet.beans.Centremedical;
import fr.dauphine.mido.as.projet.beans.Medecin;
import fr.dauphine.mido.as.projet.ejb.ServicesAgenda;
import fr.dauphine.mido.as.projet.ejb.ServicesCentre;
import fr.dauphine.mido.as.projet.ejb.ServicesMedecin;
import fr.dauphine.mido.as.projet.ejb.ServicesPlanning;

/**
 * Servlet implementation class ServletTest
 */
@WebServlet(name = "ServletEditAgenda", urlPatterns = { "/editAgenda", "/activateAgenda", "/desactivateAgenda",
		"/ajaxEditAgenda" })
public class ServletEditAgenda extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	ServicesMedecin servicesMedecin;

	@EJB
	ServicesAgenda servicesAgenda;

	@EJB
	ServicesPlanning servicesPlanning;

	@EJB
	ServicesCentre servicesCentre;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletEditAgenda() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpServletMapping httpServletMapping = request.getHttpServletMapping();
		// MappingMatch mappingMatch = httpServletMapping.getMappingMatch();

		String pattern = httpServletMapping.getPattern();
		Medecin medecin = (Medecin) request.getSession().getAttribute("medecin");
		List<Centremedical> listCentre = servicesCentre.getCentresByMedecin(medecin.getIdMedecin());
		String paramCentre = request.getParameter("centre");
		int idCentre = 0;

		try {
			idCentre = Integer.parseInt(paramCentre);
		} catch (Exception e) {

		}
		LocalDate startDate = LocalDate.now();
		LocalDate endDate = startDate.plusDays(ServletAgenda.NB_DAYS_ACTIVATE);

		switch (pattern) {
		case "/ajaxEditAgenda":
			doAjaxEditAgenda(request, response);
			break;
		case "/activateAgenda":
			// activate
			doActivateAgenda(request, response, startDate, endDate, medecin);
			break;
		case "/desactivateAgenda":
			// desactivate
			doDesactivateAgenda(request, response, medecin);
			break;
		default:
			// edit
			doEditAgenda(request, response, listCentre, idCentre, startDate, endDate, medecin);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private void doAjaxEditAgenda(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// String str = "1986-04-08 12:30";
		/*
		 * String paramDateTime = request.getParameter("dateTime"); DateTimeFormatter
		 * formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm");
		 * DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		 * DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
		 * LocalDateTime dateTime = LocalDateTime.parse(paramDateTime, formatter);
		 * String strDate = dateTime.toLocalDate().format(formatterDate); String strTime
		 * = dateTime.toLocalTime().format(formatterTime);
		 */

		// Récupérer date, temps, medecin, centre
		// Requête sql pour récupérer planning
		// Change valeur isActivated

		String paramIdPlanning = request.getParameter("idPlanning");
		String paramDisponible = request.getParameter("disponible");

		int idPlanning = Integer.parseInt(paramIdPlanning);
		boolean disponible = paramDisponible != null && paramDisponible.equals("1");

		boolean status = servicesPlanning.updatePlanning(idPlanning, !disponible);
		String messageErreur = "";
		JsonObject resultJson = Json.createObjectBuilder().add("status", status).add("messageErreur", messageErreur)
				.build();

		response.getWriter().print(resultJson.toString());
	}

	private void doActivateAgenda(HttpServletRequest request, HttpServletResponse response, LocalDate startDate,
			LocalDate endDate, Medecin medecin) throws ServletException, IOException {
		Centremedical centre = (Centremedical) request.getSession().getAttribute("centre");
		servicesPlanning.initPlanning(startDate, endDate, medecin, centre);
		response.sendRedirect("agenda");
	}

	private void doDesactivateAgenda(HttpServletRequest request, HttpServletResponse response, Medecin medecin)
			throws ServletException, IOException {
		Centremedical centre = (Centremedical) request.getSession().getAttribute("centre");
		servicesPlanning.desactivatePlanning(centre, medecin);
		response.sendRedirect("agenda");
	}

	private void doEditAgenda(HttpServletRequest request, HttpServletResponse response, List<Centremedical> listCentre,
			int idCentre, LocalDate startDate, LocalDate endDate, Medecin medecin)
			throws ServletException, IOException {
		Centremedical centre = listCentre.get(0);

		if (idCentre > 0) {
			final int id = idCentre;
			centre = listCentre.stream().filter(c -> c.getIdCentre() == id).findFirst().orElse(centre);
		}

		boolean isActivated = servicesPlanning.getPlanningIsActivated(centre, medecin);

		request.getSession().setAttribute("centre", centre);
		request.setAttribute("isActivated", isActivated);
		request.setAttribute("centre", centre);
		request.setAttribute("listDateAgenda", servicesAgenda.getDaysFromNow(7));
		request.setAttribute("listTimeAgenda", servicesAgenda.getWorkTime("08:00", "20:00"));
		request.setAttribute("listCentre", listCentre);
		request.setAttribute("mapPlanning", servicesPlanning.getPlannings(startDate, endDate, medecin, centre));

		this.getServletContext().getRequestDispatcher("/jsp/editAgenda.jsp").forward(request, response);
	}

}