<%@page import="java.util.Date"%>
<%@page import="fr.dauphine.mido.as.projet.beans.Centremedical"%>
<%@page import="fr.dauphine.mido.as.projet.ejb.DateAgenda"%>
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
				<section class="dark-wrapper opaqued parallax" data-parallax="scroll" data-image-src="assets/img/prendreRDV.jpg" data-speed="0.7">						
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
			<br/><br/>
            <%@include file="alert.jsp" %>
			<div class="formRendezVous">
			<form method="post" action="/projet-SAJ/rendezVous">
				<h3>Recherche par nom</h3>
					<input type="hidden" name="formName" value="formRechercheParNom"/>
					<label for="nomMedecin">Nom du médecin : </label>
					<input type="text" id="nomMedecin" name="nomMedecin">
					<input type="submit" class="btn btn-info" value="Rechercher un médecin">
			</form>
			</div>
			<div class="formRendezVousMulticriteres">
			<form method="post" action="/projet-SAJ/rendezVous">
				<h3>Recherche multicritères</h3>
					<input type="hidden" name="formName" value="formRechercheMulticriteres"/>
					<fieldset>
						<legend>Critère obligatoire</legend>
					<label for="specialite">Spécialité Médicale : </label>
					<select name="specialite" id="specialite" required>
					 	<option value="" selected disabled hidden>Choisir une spécialité</option>
					<%
						ArrayList<Specialite> listeSpecialites = (ArrayList<Specialite>) request.getAttribute("listeSpecialites");
						for (Specialite s : listeSpecialites) {
					%>
						<option value="<%= s.getIdSpecialite() %>"><%= s.getLibelle() %></option>
					<%
					}
					%>
					</select>
					</fieldset>
					<br>
					<fieldset>
						<legend>Critères facultatifs</legend>
					<div class="row">
					<div class="col">
						<label class="critere">Centres médicaux :</label>
						<div class="container-checkBox">
						<%
							ArrayList<Centremedical> listeCentres = (ArrayList<Centremedical>) request.getAttribute("listeCentres");
							for (Centremedical c : listeCentres) {
						%>
							<div class="checkForm">
								<input type="checkbox" id="centre" name="centre" value="<%= c.getIdCentre()%>">
								<label for="centre"><%= c.getNom() %></label><br>
							</div>
						<%
							}
						%>
						</div>
					</div>
					<div class="col">
						<label class="critere">Dates :</label>
						<div class="container-checkBox">
						<%
							TreeSet<DateAgenda> lesJours = (TreeSet<DateAgenda>) request.getAttribute("lesJours");
							for (DateAgenda d : lesJours) {
						%>
							<div class="checkForm">
								<input type="checkbox" id="jours" name="jours" value="<%= d.getLocalizedDate() %>">
								<label for="jours"><%= d.getLocalizedDate() %></label><br>
							</div>
						<%
							}
						%>
						</div>
					</div>
					<div class="col">
						<label class="critere">Horaires :</label>
						<div class="container-checkBox">
						<%
							TreeMap<String, String> creneauxHoraires = (TreeMap<String, String>) request.getAttribute("listeCreneauxHoraires");
							for (String s : creneauxHoraires.keySet()) {
						%>
							<div class="checkForm">
								<input type="checkbox" id="heureDebut" name="heureDebut" value="<%= s %>">
								<label for="heureDebut"><%= s + " - " + creneauxHoraires.get(s) %></label><br>
							</div>
						<%
							}
						%>
						</div>
					</div>
					</div>
					</fieldset>
					<input type="submit" class="btn btn-info" value="Rechercher les créneaux" style="float: right; margin-top: 20px">
			</form>
			</div>
		</div>
	</body>
</html>