package fr.dauphine.mido.as.projet.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.dauphine.mido.as.projet.beans.Centremedical;
import fr.dauphine.mido.as.projet.beans.Medecin;
import fr.dauphine.mido.as.projet.ejb.ServicesAgenda;
import fr.dauphine.mido.as.projet.ejb.ServicesCentre;
import fr.dauphine.mido.as.projet.ejb.ServicesPlanning;

import java.time.LocalDate;
import java.util.List;

/**
 * Servlet implementation class ServletTest
 */
@WebServlet(name = "ServletAgenda", urlPatterns = { "/agenda" })
public class ServletAgenda extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final int NB_DAYS_ACTIVATE = 7;

	@EJB
	ServicesAgenda servicesAgenda;

	@EJB
	ServicesPlanning servicesPlanning;

	@EJB
	ServicesCentre servicesCentre;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletAgenda() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Medecin medecin = (Medecin) request.getSession().getAttribute("medecin");
		List<Centremedical> listCentre = servicesCentre.getCentresByMedecin(medecin.getIdMedecin());
		String paramCentre = request.getParameter("centre");
		int idCentre = 0;
		
		try {
			idCentre = Integer.parseInt(paramCentre);
		} catch(Exception e) {
			
		}
		
		LocalDate startDate = LocalDate.now();
		LocalDate endDate = startDate.plusDays(NB_DAYS_ACTIVATE);	
		
		Centremedical centre = listCentre.get(0);
		
		if (idCentre > 0) {
			final int id = idCentre;
			centre = listCentre.stream().filter(c -> c.getIdCentre() == id).findFirst().orElse(centre);
		}
		
		request.getSession().setAttribute("centre", centre);
		request.setAttribute("centre", centre);
		request.setAttribute("listCentre", listCentre);
		request.setAttribute("listDateAgenda", servicesAgenda.getDaysFromNow(NB_DAYS_ACTIVATE));
		request.setAttribute("listTimeAgenda", servicesAgenda.getWorkTime("08:00", "20:00"));
		
		

		request.setAttribute("mapPlanning", servicesPlanning.getPlannings(startDate, endDate, medecin, centre));

		this.getServletContext().getRequestDispatcher("/jsp/showAgenda.jsp").forward(request, response);
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

}
