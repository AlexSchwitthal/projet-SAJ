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
import fr.dauphine.mido.as.projet.ejb.ServicesPersonne;

/**
 * Servlet implementation class ServletGestionPatient
 */
@WebServlet(name = "ServletGestionPatient", urlPatterns = {"/gestionPatient"})
public class ServletGestionPatient extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    @EJB
    ServicesPersonne servicesPersonne;
    
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
		Patient patient = this.servicesPersonne.getPatientByEmail("a.b@gmail.com");
		if(patient != null) {
            request.setAttribute("patient", patient);
	        this.getServletContext().getRequestDispatcher("/jsp/gestionPatient.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Patient patient = this.servicesPersonne.getPatientByEmail("a.b@gmail.com");
		Map<String, String[]> parameters = request.getParameterMap();
		Patient updatedPatient = this.servicesPersonne.updatePatient(patient.getIdPatient(), parameters);
		if(updatedPatient != null) {
			request.setAttribute("patient", updatedPatient);
			request.setAttribute("success", "Vos donn�es ont bien �t� mis � jour !");
		    this.getServletContext().getRequestDispatcher("/jsp/gestionPatient.jsp").forward(request, response);
		}
	}

}