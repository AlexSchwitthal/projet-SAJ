<%@page import="fr.dauphine.mido.as.projet.beans.Centremedical"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="fr.dauphine.mido.as.projet.beans.Specialite"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Prendre un rendez-vous</title>
	</head>
	<body>
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
				<select name=specialite" id="specialite">
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
				<input type="submit" value="Rechercher un médecin">
			</fieldset>
		</form>
	</body>
</html>