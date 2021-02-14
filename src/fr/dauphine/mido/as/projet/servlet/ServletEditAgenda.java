package fr.dauphine.mido.as.projet.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ejb.EJB;
import javax.json.Json;
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
import fr.dauphine.mido.as.projet.ejb.ServicesRendezVous;

/**
 * Servlet implementation class ServletEditAgenda
 */
@WebServlet(name = "ServletEditAgenda", urlPatterns = { "/editAgenda", "/activateAgenda", "/desactivateAgenda",
		"/ajaxEditAgenda", "/cancelPlannings"})
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

	@EJB
	ServicesRendezVous servicesRendezVous;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletEditAgenda() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getSession().getAttribute("type") == "medecin") {
			HttpServletMapping httpServletMapping = request.getHttpServletMapping();

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
			case "/cancelPlannings":
				// cancel plannings
				doCancelPlannings(request, response, idCentre, medecin);
				break;
			default:
				// edit
				doEditAgenda(request, response, listCentre, idCentre, startDate, endDate, medecin);
			}
		} else if (request.getSession().getAttribute("login") != null) {
			response.sendRedirect("home");
		} else {
			response.sendRedirect("login");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void doAjaxEditAgenda(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String paramIdPlanning = request.getParameter("idPlanning");
		String paramDisponible = request.getParameter("disponible");

		int idPlanning = Integer.parseInt(paramIdPlanning);
		boolean disponible = paramDisponible != null && paramDisponible.equals("1");

		boolean status = servicesPlanning.updatePlanning(idPlanning, !disponible);
		String messageErreur = "";
		JsonObject resultJson = Json.createObjectBuilder().add("status", status).add("messageErreur", messageErreur)
				.add("disponible", !disponible).build();

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
		request.setAttribute("listDateAgenda", servicesAgenda.getDaysFromNow(ServletAgenda.NB_DAYS_ACTIVATE));
		request.setAttribute("listTimeAgenda", servicesAgenda.getWorkTime("08:00", "20:00"));
		request.setAttribute("listCentre", listCentre);
		request.setAttribute("mapPlanning", servicesPlanning.getPlannings(startDate, endDate, medecin, centre));

		this.getServletContext().getRequestDispatcher("/jsp/editAgenda.jsp").forward(request, response);
	}

	private void doCancelPlannings(HttpServletRequest request, HttpServletResponse response, int idCentre,
			Medecin medecin) throws ServletException, IOException {

		List<Centremedical> listCentre = servicesCentre.getCentresByMedecin(medecin.getIdMedecin());
		Centremedical centre = listCentre.get(0);

		String paramMotif = request.getParameter("motif")+" [Medecin]";

		String paramListIdPlanning = request.getParameter("listIdPlanning");
		if (idCentre > 0) {
			final int id = idCentre;
			centre = listCentre.stream().filter(c -> c.getIdCentre() == id).findFirst().orElse(centre);
		}

		List<Integer> listIdPlanning = Stream.of(paramListIdPlanning.split(",")).map(s -> {
			return Integer.parseInt(s);
		}).collect(Collectors.toList());

		listIdPlanning.forEach(idPlanning -> {
			servicesRendezVous.cancelRendezVous(idPlanning, paramMotif);
		});

		response.sendRedirect("editAgenda");
	}
}
