<%@page import="fr.dauphine.mido.as.projet.beans.Spemedecin"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap" %>
<%@page import="fr.dauphine.mido.as.projet.beans.Medecin"%>
<%@page import="fr.dauphine.mido.as.projet.beans.Planning"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page session="true"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Créneaux disponible</title>
	</head>
	<body>
		<p>
			Rendez-vous disponibles avec <%= request.getAttribute("medecinRecherche").toString() %>
		</p>
		<%
		HashMap<Medecin, ArrayList<Planning>> medecinsPlannings = (HashMap<Medecin, ArrayList<Planning>>) session.getAttribute("medecinsPlannings");
		for (Medecin m : medecinsPlannings.keySet()) {
			out.println(m.getPersonne().getNom());
			out.println(m.getPersonne().getPrenom());
			for (Spemedecin s : m.getSpemedecins()) {
				out.println(s.getSpecialite().getLibelle());
				out.println(s.getCentremedical().getNom());
				out.println(s.getCentremedical().getAdresse().getAdresseComplete());
				out.println(s.getCentremedical().getTelephone());
			}
		}
		%>
	</body>
</html>