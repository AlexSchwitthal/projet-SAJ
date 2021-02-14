<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap" %>
<%@page import="java.time.LocalDate" %>
<%@page import="java.time.LocalTime" %>
<%@page import="java.time.ZoneId" %>
<%@page import="java.sql.Time" %>
<%@page import="java.util.Date" %>
<%@page import="fr.dauphine.mido.as.projet.beans.Patient"%>
<%@page import="fr.dauphine.mido.as.projet.beans.Medecin"%>
<%@page import="fr.dauphine.mido.as.projet.beans.Spemedecin"%>
<%@page import="fr.dauphine.mido.as.projet.beans.Planning"%>
<%@page import="fr.dauphine.mido.as.projet.beans.Centremedical"%>
<%@page import="fr.dauphine.mido.as.projet.beans.Rendezvous"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page session="true"%>
<!DOCTYPE html>
<html>
	<%@include file="header.jsp" %>
	<script src="js/rendezVousPatient.js"></script>
	<body>
		<div class="container">
			<div id="menu">
				<div id="includeMenu">
					<jsp:include page="menu.jsp"/>
				</div>          
				<section class="dark-wrapper opaqued parallax" data-parallax="scroll" data-image-src="assets/img/modifInfosPersos.jpg" data-speed="0.7">						
					<div class="section-inner ">				            	
						<div class="container">
							<div class="row">				                    	
								<div class="col-lg-12 mt30 wow text-center">
									<h2 class="section-heading">Mes rendez-vous</h2>  
								</div>
							</div>		                     
						</div>
					</div>
				</section>
			</div>
			<br>
			<%@include file="alert.jsp" %>
			<br>
			<table class="table">
				<thead>
					<tr>
						<th scope="col">Date</th>
						<th scope="col">Creneau</th>
						<th scope="col">Médecin</th>
						<th scope="col">Specialité</th>
						<th scope="col">Centre</th>
						<th scope="col">Téléphone</th>
						<th scope='col'>Etat</th>
						<th scope='col'>Annuler RDV/raison annulation</th>
					</tr>
				</thead>
				<tbody>
				<%
				Patient patient = (Patient) request.getAttribute("patient"); 
				ArrayList<ArrayList<Object>>  listePlanningPatient = (ArrayList<ArrayList<Object>>) request.getAttribute("listePlanningPatient");
				LocalDate localDate = (LocalDate) request.getAttribute("date");
				LocalTime localTime = (LocalTime) request.getAttribute("time");
				Time currentTime = Time.valueOf(localTime);
				Date currentDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

				for (ArrayList<Object> elements : listePlanningPatient) {
					Medecin medecin = (Medecin) elements.get(0);
					Centremedical centreMedical = (Centremedical) elements.get(1);
					Spemedecin speMedecin = (Spemedecin) elements.get(2);
					Planning planning = (Planning) elements.get(3);
					
				
					String etat = planning.getRendezvous().getEtat();
					
					// vérifie si la date du rendez-vous est déjà passé ou non
					if(currentDate.after(planning.getDate()) || (currentDate.equals(planning.getDate()) && currentTime.after(planning.getHeureDebut()))) {
						if(etat.equals("Actif") || etat.equals("actif")) {
							etat = "Obsolète";
						}
					}

					if(etat.equals("Annulé"))  {
						%>
						<tr style="background-color:#ff6666;">
						<%
					} 
					else if(etat.equals("Obsolète"))  {
						%>
						<tr style="background-color:#D3D3D3;">
						<%
					} 
					else { 
						%>
						<tr style="background-color:#99ff99;">
						<%
					} 
					%>
						<th scope="row"><%= planning.dateStringFormatFR() %></th>	
						<td><%= planning.getHeureDebut() + " - " + planning.getHeureFin() %></td>
						<td><%= medecin.getPersonne().getNom() + " " + medecin.getPersonne().getPrenom() %></td>
						<td><%= speMedecin.getSpecialite().getLibelle() %></td>
						<td><%= centreMedical.getNom() %><br><%= centreMedical.getAdresse().getAdresseComplete() %></td>
						<td><%= centreMedical.getTelephone() %></td>
						<td><%= etat %></td>
						<% 
						if(etat.equals("Actif") || etat.equals("actif")) {
							%>
							<td>
								<form style="padding:0;margin:0;" onSubmit="return validationSuppression()" method="post" action="mesRendezVous">
									<input id="idPlanning" name="idPlanning" type="hidden" value="<%= planning.getIdPlanning() %>">
									<input name="raisonAnnulation" type="hidden" value="">
									<center><input type="submit" value="Annuler RDV" class="btn btn-danger"></center>
								</form>
							</td>
							<%		
						}
						else if(etat.equals("Annulé")) {
							%> <td><%= planning.getRendezvous().getMessageAnnulation() %></td> <%
						}
						else {
							%> <td></td> <%
						}
						%>
					</tr>
					<%
					
				}
				%>
				</tbody>
			</table>
		</div>
	</body>
</html>