<%@page import="fr.dauphine.mido.as.projet.beans.Spemedecin"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap" %>
<%@page import="fr.dauphine.mido.as.projet.beans.Medecin"%>
<%@page import="fr.dauphine.mido.as.projet.beans.Planning"%>
<%@page import="fr.dauphine.mido.as.projet.beans.Centremedical"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page session="true"%>
<!DOCTYPE html>
<html>
	<%@include file="header.jsp" %>
	<script src="js/enregistrerRendezVous.js"></script>
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
									<h2 class="section-heading">Prendre un rendez-vous</h2>  
								</div>
							</div>		                     
						</div>
					</div>
				</section>
			</div>
		<p>
			Rendez-vous disponibles avec multi
		</p>
		<table class="table">
			<thead>
				<tr>
					<th scope="col">Date</th>
					<th scope="col">Creneau</th>
					<th scope="col">Médecin</th>
					<th scope="col">Centre</th>
					<th scope="col">Téléphone</th>
					<th scope='col'></th>
				</tr>
			</thead>
			<tbody>
			<%
			ArrayList<Planning> lesCreneaux = (ArrayList<Planning>) request.getAttribute("lesCreneaux");
			Medecin m = null;
			Centremedical c = null;
			
			for (Planning p : lesCreneaux) {
				m = p.getMedecin();
				c = p.getCentremedical();
			%>
			<tr>
				<th scope="row"><%= p.dateStringFormatFR() %></th>	
				<td><%= p.getHeureDebut() + " - " + p.getHeureFin() %></td>
				<td><%= p.getMedecin().getPersonne().getNom() + " " + p.getMedecin().getPersonne().getPrenom() %></td>
				<td><%= c.getNom() %><br><%= c.getAdresse().getAdresseComplete() %></td>
				<td><%= c.getTelephone() %></td>
				<td><input type="button" value="click" onclick="ajouterRendezVous('<%= p.getIdPlanning() %>')"></td>
			</tr>
			<%
			}
			%>
			</tbody>
		</table>
		</div>
	</body>
</html>