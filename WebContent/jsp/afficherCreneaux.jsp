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
				Rendez-vous disponibles avec 
			</p>
			<%
			HashMap<Medecin, HashMap<Centremedical, ArrayList<Planning>>> lesCreneaux = (HashMap<Medecin, HashMap<Centremedical, ArrayList<Planning>>>) request.getAttribute("lesCreneaux");
			int i = 1;
			for (Medecin m : lesCreneaux.keySet()) {
			%>
			<h3>
			<%
			out.println(m.getPersonne().getNom());
			out.println(m.getPersonne().getPrenom());
			%>
			</h3>
			<div id="accordion">
				<% 
				for (Centremedical cm : lesCreneaux.get(m).keySet()) {
				%>
  				<div class="card">
    				<div class="card-header" id="heading<%= i %>">
      					<h5 class="mb-0">
        					<button class="btn btn-link collapsed" data-toggle="collapse" data-target="#<%= i %>" aria-expanded="false" aria-controls="<%= i %>">
          						<%= cm.getMedecinSpecialite(m.getIdMedecin()).getLibelle() + " - " + cm.getNom() %>
        					</button>
      					</h5>
    				</div>

    				<div id="<%= i %>" class="collapse" aria-labelledby="heading<%= i %>" data-parent="#accordion">
      					<div><%= cm.getAdresse().getAdresseComplete() + " - " + cm.getTelephone()%></div>
      					<div class="card-body">
        					<%
        					for (Planning p : lesCreneaux.get(m).get(cm)) {
							%>
							<p>
								<%= p.dateStringFormatFR() %> / <%= p.getHeureDebut() %> - <%= p.getHeureFin() %>
								<input type="button" value="click" onclick="ajouterRendezVous('<%= p.getIdPlanning() %>')">
							</p>
							<%
							}
        					%>
      					</div>
    				</div>
  				</div>
  				<%
  					i++;
  				}
  				%>
			</div>
			<%
			}
			%>
		</div>
	</body>
</html>