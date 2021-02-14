package fr.dauphine.mido.as.projet.ejb;

import java.io.Serializable;
import javax.ejb.*;

@Remote
public interface EJBScheduler {
	public void stopTimer(Serializable info);

	public void logMessagePlanifie();
}