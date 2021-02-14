package fr.dauphine.mido.as.projet.ejb;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.Timer;
import javax.ejb.TimerService;

import fr.dauphine.mido.as.projet.beans.Centremedical;
import fr.dauphine.mido.as.projet.beans.Medecin;
import fr.dauphine.mido.as.projet.beans.Patient;
import fr.dauphine.mido.as.projet.beans.Planning;
import fr.dauphine.mido.as.projet.beans.Rendezvous;
import fr.dauphine.mido.as.projet.beans.Spemedecin;
import fr.dauphine.mido.as.projet.mail.MailSender;

@Stateless
public class EJBSchedulerBean implements EJBScheduler {

	@Resource
	TimerService timerService;

	/**
	 * Default constructor.
	 */
	public EJBSchedulerBean() {
		// TODO Auto-generated constructor stub
	}

	public void stopTimer(Serializable info) {
		Timer timer;
		Collection timers = timerService.getTimers();
		for (Object object : timers) {
			timer = ((Timer) object);
			if (timer.getInfo().equals(info)) {
				timer.cancel();
				break;
			}
		}
	}

	@Schedule(hour = "3", minute = "0", second = "0", dayOfWeek = "Mon-Sun", dayOfMonth = "*", month = "*", year = "*", info = "MyTimer")
	public void logMessagePlanifie() {
		ServicesPlanning servicesPlanning = new ServicesPlanningBean();
		System.out.println("@Schedule called at: " + new Date());
		LocalDate localDate = LocalDate.now().plusDays(1);
		List<Planning> listPlanning = servicesPlanning.getPlanningByDateWithRendezvous(localDate);
		listPlanning.forEach(planning -> {
			Medecin medecin = planning.getMedecin();
			Centremedical centre = planning.getCentremedical();
			Rendezvous rendezvous = planning.getRendezvous();
			Patient patient = rendezvous.getPatient();
			String email = patient.getEmail();
			String mailContent = String.format("Bonjour,<br/><br/> Nous vous rappelons que vous avez un rendez-vous le %s à %s le %s avec le médecin %s %s au centre %s"
					+ " situé à l'adresse %s. Vous pouvez joindre le centre au numéro suivant : %s <br/><br/>Cordialement, l'équipe",
					planning.getHeureDebutString(),  planning.getHeureFinString(), planning.getDate().toString(), medecin.getPersonne().getPrenom(),
					medecin.getPersonne().getNom(), centre.getNom(), centre.getAdresse().getAdresseComplete(), centre.getTelephone());
					MailSender sender = new MailSender();
					sender.sendMail("test@test.com", email, "Prise d'un rendez-vous", mailContent);
		});
		
	}
}