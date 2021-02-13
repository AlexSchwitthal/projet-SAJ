package fr.dauphine.mido.as.projet.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.dauphine.mido.as.projet.beans.Patient;
import fr.dauphine.mido.as.projet.beans.Rendezvous;
import fr.dauphine.mido.as.projet.ejb.DateAgenda;
import fr.dauphine.mido.as.projet.ejb.ServicesPatient;
import fr.dauphine.mido.as.projet.ejb.ServicesRendezVous;
import fr.dauphine.mido.as.projet.ejb.TimeAgenda;

/**
 * Servlet implementation class ServletRendezVousPatient
 */
@WebServlet(name = "ServletRendezVousPatient", urlPatterns = {"/mesRendezVous"})
public class ServletRendezVousPatient extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    @EJB
    ServicesPatient servicesPatient;
    
    @EJB
    ServicesRendezVous servicesRendezVous;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletRendezVousPatient() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//if(request.getSession().getAttribute("type") == "patient") {
		//	String email = (String) request.getSession().getAttribute("login");
			Patient patient = this.servicesPatient.getPatientByEmail("schwitthal.alexandre@gmail.com");
			List<Rendezvous> listeRendezVousPatient = this.servicesRendezVous.getRendezVousPatient(patient.getIdPatient());
			ArrayList<ArrayList<Object>> listeDetailsRendezVous = new ArrayList<ArrayList<Object>>();
			LocalDate date = LocalDate.now();
			LocalTime time = LocalTime.now();
			//TimeAgenda time = new TimeAgenda(LocalTime.now());
			//System.out.println(date.getLocalizedDate());
			//System.out.println(time.getFormattedTime());
			for(Rendezvous r : listeRendezVousPatient) {
				//System.out.println(r.getIdRendezVous());
				listeDetailsRendezVous.add(this.servicesRendezVous.getDetailsRendezVous(r.getIdRendezVous()));
			}
			
			request.setAttribute("patient", patient);
			request.setAttribute("listeDetailsRendezVous", listeDetailsRendezVous);
			request.setAttribute("date", date);
			request.setAttribute("time", time);
		    this.getServletContext().getRequestDispatcher("/jsp/rendezVousPatient.jsp").forward(request, response);
		//}
		//else if(request.getSession().getAttribute("login") != null) {
		//	response.sendRedirect("home");
		//}
		//else {
		//	response.sendRedirect("login");
		//}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idRendezVous = request.getParameter("idRendezVous");
        
		doGet(request, response);
	}

}
