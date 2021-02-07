package fr.dauphine.mido.as.projet.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.MappingMatch;

import fr.dauphine.mido.as.projet.ejb.ServicesAgenda;
import fr.dauphine.mido.as.projet.ejb.ServicesCentre;
import fr.dauphine.mido.as.projet.ejb.ServicesPlanning;

import java.time.LocalDate;

/**
 * Servlet implementation class ServletTest
 */
@WebServlet(name = "ServletEditAgenda", urlPatterns = {"/editAgenda", "/activateAgenda"})
public class ServletEditAgenda extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
    ServicesAgenda servicesAgenda;
	
	@EJB
    ServicesPlanning servicesPlanning;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletEditAgenda() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpServletMapping httpServletMapping = request.getHttpServletMapping();
		MappingMatch mappingMatch = httpServletMapping.getMappingMatch();
		
		String pattern = httpServletMapping.getPattern();
		System.out.println("----------starta-----------");

		if (pattern.equals("/activateAgenda")) {
			// activate
			System.out.println("----------startb-----------");
			servicesPlanning.initPlanning(LocalDate.now(), null);
			System.out.println("----------startc-----------");
			response.sendRedirect("agenda");
			
		} else {
			// edit
			System.out.println("----------startd-----------");
			request.setAttribute("listDateAgenda", servicesAgenda.getDaysFromNow(7));
			request.setAttribute("listTimeAgenda", servicesAgenda.getWorkTime("08:00", "20:00"));
	        this.getServletContext().getRequestDispatcher("/jsp/editAgenda.jsp").forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
