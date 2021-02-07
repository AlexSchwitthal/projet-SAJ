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
		for (Medecin m : lesCreneaux.keySet()) {
			out.println(m.getPersonne().getNom());
			out.println(m.getPersonne().getPrenom());
			for (Centremedical cm : lesCreneaux.get(m).keySet()) {
				out.println(cm.getMedecinSpecialite(m.getIdMedecin()).getLibelle());
				out.println(cm.getNom());
				out.println(cm.getAdresse().getAdresseComplete());
				out.println(cm.getTelephone());
				for (Planning p : lesCreneaux.get(m).get(cm)) {
					out.println(p.getHeureDebut() + " - " + p.getHeureFin());
				}
			}
		}
		%>
		</div>
	</body>
</html>