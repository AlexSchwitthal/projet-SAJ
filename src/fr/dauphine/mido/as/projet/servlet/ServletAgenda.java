package fr.dauphine.mido.as.projet.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.dauphine.mido.as.projet.ejb.ServicesAgenda;
import fr.dauphine.mido.as.projet.ejb.ServicesCentre;

import java.time.LocalDate;

/**
 * Servlet implementation class ServletTest
 */
@WebServlet(name = "ServletAgenda", urlPatterns = {"/agenda"})
public class ServletAgenda extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
    ServicesAgenda servicesAgenda;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletAgenda() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("listDateAgenda", servicesAgenda.getDaysFromNow(7));
		request.setAttribute("listTimeAgenda", servicesAgenda.getWorkTime("08:00", "20:00"));
        this.getServletContext().getRequestDispatcher("/jsp/agenda/showAgenda.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
