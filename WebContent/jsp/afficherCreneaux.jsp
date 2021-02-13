<%@page import="fr.dauphine.mido.as.projet.beans.Spemedecin"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap" %>
<%@page import="java.util.TreeMap" %>
<%@page import="java.util.Date" %>
<%@page import="fr.dauphine.mido.as.projet.beans.Medecin"%>
<%@page import="fr.dauphine.mido.as.projet.beans.Planning"%>
<%@page import="fr.dauphine.mido.as.projet.beans.Centremedical"%>
<%@page import="fr.dauphine.mido.as.projet.ejb.DateAgenda"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page session="true"%>
<!DOCTYPE html>
<html>
	<%@include file="header.jsp" %>
	<script src="js/enregistrerRendezVous.js"></script>
	<body>
		<div class="container" style="min-height: 2000px">
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
			ArrayList<Medecin> lesMedecins = (ArrayList<Medecin>) request.getAttribute("lesMedecins");
			int i = 1;
			for (Medecin m : lesMedecins) {
			%>
			<h3>
			<%
			out.println(m.getPersonne().getNom());
			out.println(m.getPersonne().getPrenom());
			%>
			</h3>
			<div id="accordion">
				<% 
				for (Spemedecin sm : m.getSpemedecins()) {
				%>
  				<div class="card">
    				<div class="card-header" id="heading<%= i %>">
      					<h5 class="mb-0">
        					<button class="btn btn-link collapsed" data-toggle="collapse" data-target="#<%= i %>" aria-expanded="false" aria-controls="<%= i %>">
          						<%= sm.getSpecialite().getLibelle() + " - " + sm.getCentremedical().getNom() + " - " + sm.getCentremedical().getAdresse().getAdresseComplete() + " - " + sm.getCentremedical().getTelephone() %>
        					</button>
      					</h5>
    				</div>

    				<div id="<%= i %>" class="collapse" aria-labelledby="heading<%= i %>" data-parent="#accordion">
      					<div class="card-body">
        					
        					<div>
  								<div id="myCarousel" class="carousel slide" data-ride="carousel" data-interval="false">
    								<!-- Indicators -->
    								<ol class="carousel-indicators">
										<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
										<li data-target="#myCarousel" data-slide-to="1"></li>
    									<li data-target="#myCarousel" data-slide-to="2"></li>
									</ol>
		
									<!-- Wrapper for slides -->
									<div class="carousel-inner">
										<%
        								TreeMap<DateAgenda, ArrayList<Planning>> planningsQ = sm.getCentremedical().planningMed(m);
        								int j = 0;
										%>
										<div style="height: 300px; padding-left: 20px; padding-right: 20px" class="item active divScroll">
											<div class="row">
										<%
        								for (DateAgenda d : planningsQ.keySet()) {
										%>
											<div class="col text-center">
											<p>
												<%= d.getLocalizedDate() %><br>
												<%= d.getLocalizedDayOfWeek() %>
											</p>
											<%
											if (planningsQ.get(d).size() > 0) {
												for (Planning p : planningsQ.get(d)) {
												%>
													<p><a href="#" onclick="ajouterRendezVous('<%= p.getIdPlanning() %>')"><%= p.getHeureDebutString() %> - <%= p.getHeureFinString() %></a></p>
												<%
												}
											}
											else {
												%>
												<p>Aucun créneau disponible</p>
											<%
											}
												%>
											</div>
											<%
											j++;
											if (j % 7 == 0) {
											%>
												</div>
												</div>
												<div style="height: 300px; padding-left: 20px; padding-right: 20px" class="item">
													<div class="row">
											<%
											}
										}
        								%>
        								</div>
									</div>
								</div>
									<!-- Left and right controls -->
									<a class="left carousel-control" style="width:30px" href="#myCarousel" data-slide="prev">
										<span class="glyphicon glyphicon-chevron-left"></span>
										<span class="sr-only">Previous</span>
									</a>
									<a class="right carousel-control" style="width:30px" href="#myCarousel" data-slide="next">
										<span class="glyphicon glyphicon-chevron-right"></span>
										<span class="sr-only">Next</span>
									</a>
								</div>
							
        					
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
		</div>
	</body>
</html>