package fr.dauphine.mido.as.projet.servlet;

import java.io.IOException;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.dauphine.mido.as.projet.beans.Patient;
import fr.dauphine.mido.as.projet.ejb.ServicesPatient;

/**
 * Servlet implementation class ServletGestionPatient
 */
@WebServlet(name = "ServletGestionPatient", urlPatterns = {"/gestionPatient"})
public class ServletGestionPatient extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    @EJB
    ServicesPatient servicesPatient;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletGestionPatient() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("type") == "patient") {
			String email = (String) request.getSession().getAttribute("login");
			Patient patient = this.servicesPatient.getPatientByEmail(email);
			request.setAttribute("patient", patient);
		    this.getServletContext().getRequestDispatcher("/jsp/gestionPatient.jsp").forward(request, response);
		}
		else if(request.getSession().getAttribute("login") != null) {
			response.sendRedirect("home");
		}
		else {
			response.sendRedirect("login");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = (String) request.getSession().getAttribute("login");
		Patient patient = this.servicesPatient.getPatientByEmail(email);
		Map<String, String[]> parameters = request.getParameterMap();
		Patient updatedPatient = this.servicesPatient.updatePatient(patient.getIdPatient(), parameters);
		if(updatedPatient != null) {
			request.setAttribute("patient", updatedPatient);
			request.setAttribute("success", "Vos données ont bien été mis à jour !");
		    this.getServletContext().getRequestDispatcher("/jsp/gestionPatient.jsp").forward(request, response);
		}
	}

}
