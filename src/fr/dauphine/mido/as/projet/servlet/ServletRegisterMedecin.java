package fr.dauphine.mido.as.projet.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.dauphine.mido.as.projet.ejb.ServicesCentre;
import fr.dauphine.mido.as.projet.ejb.ServicesSpecialite;
/**
 * Servlet implementation class ServletRegisterMedecin
 */
@WebServlet(name = "ServeltRegisterMedecin", urlPatterns = {"/registerMedecin"})
public class ServletRegisterMedecin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    @EJB
    ServicesCentre servicesCentre;
    
    @EJB
    ServicesSpecialite servicesSpecialite;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletRegisterMedecin() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("listeCentre", servicesCentre.getAllCentre());
		request.setAttribute("listeSpecialite", servicesSpecialite.getAllSpecialite());
        this.getServletContext().getRequestDispatcher("/jsp/registerMedecin.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
