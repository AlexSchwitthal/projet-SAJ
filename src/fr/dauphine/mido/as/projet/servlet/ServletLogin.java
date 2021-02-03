package fr.dauphine.mido.as.projet.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.dauphine.mido.as.projet.ejb.ServicesAuth;

/**
 * Servlet implementation class ServletTest
 */
@WebServlet(name = "ServletLogin", urlPatterns = {"/login"})
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB
	ServicesAuth servicesAuth;
	  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletLogin() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("login") == null) {
	        this.getServletContext().getRequestDispatcher("/jsp/login.jsp").forward(request, response);
		}
		else {
			response.sendRedirect("home");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String result = this.servicesAuth.login(request.getParameter("login"), request.getParameter("mdp"));
		if(result.equals("erreur")) {
			 request.setAttribute("warning", "Identifiant incorrect !");
			 this.getServletContext().getRequestDispatcher("/jsp/login.jsp").forward(request, response);
		}
		else {
			HttpSession session = request.getSession(true);
			session.setAttribute("login", request.getParameter("login"));
			session.setAttribute("type", result);
            session.setMaxInactiveInterval(3600);
            response.sendRedirect("home");
		}
	}
}
