<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="fr.dauphine.mido.as.projet.beans.Medecin"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%
	/*@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"*/
%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<%@include file="header.jsp"%>
<body>
	<div class="container">
		<div id="includeMenu">
			<%@include file="menu.jsp"%>
		</div>
		<section class="dark-wrapper opaqued parallax" data-parallax="scroll"
			data-image-src="assets/img/equipe-medecins.jpg" data-speed="0.7">
			<div class="section-inner ">
				<div class="container">
					<div class="row">
						<div class="col-lg-12 mt30 wow text-center">
							<h2 class="section-heading">Votre agenda</h2>
						</div>
					</div>
				</div>
			</div>
		</section>
		
		<p>NB=${fn:length(mapPlanning)}</p>
		<input type="hidden" id="idCentre" value="${ centre.idCentre }">
		<li><a href="editAgenda?centre=${ centre.idCentre }">Modifiez votre agenda</a></li>
		<label for="centre-select">Votre centre :</label>

		<c:choose>
			<c:when test="${ listCentre == null }">
	           Aucun
	        </c:when>

			<c:when test="${ fn:length(listCentre) == 1 }">
				<c:out value="${ listCentre[0].nom }" />
			</c:when>

			<c:otherwise>
				<select name="centres" id="centre-select">
					<c:forEach var="c" items="${listCentre}" varStatus="status">
						<option value="${ c.idCentre }" ${ c == centre ? 'selected' : ''}>${ c.nom }</option>
					</c:forEach>
				</select>
			</c:otherwise>
		</c:choose>



		<p></p>
		<table class="table-agenda">
			<thead>
				<tr>
					<td>Créneau horaire</td>
					<c:forEach var="dateAgenda" items="${listDateAgenda}">
						<td>${dateAgenda.localizedDate}<br>(${dateAgenda.localizedDayOfWeek})
						</td>
					</c:forEach>
				</tr>

			</thead>
			<tbody>
				<c:forEach var="timeAgenda" items="${listTimeAgenda}">
					<tr>
						<td><c:out value="${timeAgenda.formattedTime}" /></td>
						<c:forEach var="slot" items="${listDateAgenda}">
							<c:set var="dateTime"
								value="${slot.localizedDate}_${timeAgenda.formattedTime}" />
							<c:set var="planning"
								value="${mapPlanning[dateTime] != null ? mapPlanning[dateTime] : null}" />
							<c:set var="cssStyle"
								value="${planning != null ? planning.cellStyle : 'not-init' }" />
							<td class="${cssStyle}"></td>
						</c:forEach>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<script src="js/agenda.js"></script>
</body>
</html>