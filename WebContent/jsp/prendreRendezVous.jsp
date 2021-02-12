<%@page import="fr.dauphine.mido.as.projet.beans.Centremedical"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.TreeMap"%>
<%@page import="java.util.TreeSet"%>
<%@page import="fr.dauphine.mido.as.projet.beans.Specialite"%>
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
			
			<form method="post" action="/projet-SAJ/rendezVous">
				<fieldset>
					<legend>Recherche par nom</legend>
					<input type="hidden" name="formName" value="formRechercheParNom"/>
					<label for="nomMedecin">Nom du médecin : </label>
					<input type="text" id="nomMedecin" name="nomMedecin">
					<input type="submit" value="Rechercher un médecin">
				</fieldset>
			</form>

			<form method="post" action="/projet-SAJ/rendezVous">
				<fieldset>
					<legend>Recherche multicritères</legend>
					<input type="hidden" name="formName" value="formRechercheMulticriteres"/>
					<label for="specialite">Spécialité Médicale : </label>
					<select name="specialite" id="specialite">
					<%
						ArrayList<Specialite> listeSpecialites = (ArrayList<Specialite>) request.getAttribute("listeSpecialites");
						for (Specialite s : listeSpecialites) {
					%>
						<option value="<%= s.getIdSpecialite() %>"><%= s.getLibelle() %></option>
					<%
					}
					%>
					</select>
					<br>
					<fieldset>
						<legend>Choisir un centre</legend>
						<div class="container" style="border:2px solid #ccc; width:300px; height: 100px; overflow-y: scroll;">
						<%
							ArrayList<Centremedical> listeCentres = (ArrayList<Centremedical>) request.getAttribute("listeCentres");
							for (Centremedical c : listeCentres) {
						%>
							<div>
								<input type="checkbox" id="centre" name="centre" value="<%= c.getIdCentre()%>">
								<label for="centre"><%= c.getNom() %></label><br>
							</div>
						<%
							}
						%>
						</div>
					</fieldset>
					<fieldset>
						<legend>Jours</legend>
						<div class="container" style="border:2px solid #ccc; width:300px; height: 100px; overflow-y: scroll;">
						<%
							TreeSet<String> lesJours = (TreeSet<String>) request.getAttribute("lesJours");
							for (String s : lesJours) {
						%>
							<div>
								<input type="checkbox" id="jours" name="jours" value="<%= s %>">
								<label for="jours"><%= s %></label><br>
							</div>
						<%
							}
						%>
						</div>
					</fieldset>
					<fieldset>
						<legend>Creneaux horaires</legend>
						<div class="container" style="border:2px solid #ccc; width:300px; height: 100px; overflow-y: scroll;">
						<%
							TreeMap<String, String> creneauxHoraires = (TreeMap<String, String>) request.getAttribute("listeCreneauxHoraires");
							for (String s : creneauxHoraires.keySet()) {
						%>
							<div>
								<input type="checkbox" id="heureDebut" name="heureDebut" value="<%= s %>">
								<label for="heureDebut"><%= s + " - " + creneauxHoraires.get(s) %></label><br>
							</div>
						<%
							}
						%>
						</div>
					</fieldset>
					<input type="submit" value="Rechercher un médecin">
				</fieldset>
			</form>
		</div>
	</body>
</html>