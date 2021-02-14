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
			data-image-src="assets/img/agenda.jpg" data-speed="0.7">
			<div class="section-inner ">
				<div class="container">
					<div class="row">
						<div class="col-lg-12 mt30 wow text-center">
							<h2 class="	section-heading">Modifier votre agenda</h2>
						</div>
					</div>
				</div>
			</div>
		</section>

		<input type="hidden" id="idCentre" value="${ centre.idCentre }">
		<a href="agenda?centre=${ centre.idCentre }"><button type="button"
				class="btn btn-secondary">Affichez votre agenda</button></a><br /> <label
			for="centre-select">Votre centre :</label>

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
		<div>
			<c:if test="${ !isActivated }">
				<button class="btn btn-success" type="button" id="activateAgenda">Activer
					l'agenda</button>
			</c:if>
			<c:if test="${ isActivated }">
				<button class="btn btn-success" type="button" id="desactivateAgenda">Désactiver
					l'agenda</button>
			</c:if>
			<button class="btn btn-danger" type="button" id="btnAnnulerRdv"
				disabled>Annuler RDV</button>
		</div>

		<div style="display: flex">
			<table class="table-agenda">
				<thead>
					<tr>
						<td>Créneau horaire</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="timeAgenda" items="${listTimeAgenda}">
						<tr>
							<td><c:out value="${timeAgenda.formattedTime}" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div style="overflow: scroll hidden">
				<table class="table-agenda" id="table-agenda">
					<thead>
						<tr>
							<c:forEach var="dateAgenda" items="${listDateAgenda}">
								<td>${dateAgenda.localizedDate}<br>(${dateAgenda.localizedDayOfWeek})
								</td>
							</c:forEach>
						</tr>

					</thead>
					<tbody>
						<c:forEach var="timeAgenda" items="${listTimeAgenda}">
							<tr>
								<c:forEach var="slot" items="${listDateAgenda}">
									<c:set var="dateTime"
										value="${slot.localizedDate}_${timeAgenda.formattedTime}" />
									<c:set var="planning"
										value="${mapPlanning[dateTime] != null ? mapPlanning[dateTime] : null}" />
									<c:set var="cssStyle"
										value="${planning != null ? planning.cellStyle : 'not-init' }" />
									<c:set var="patientInfo"
										value="${planning != null ? planning.patientInfoFromRendezvous : null }" />
									<td class="${cssStyle}"
										data-idPlanning="${planning.idPlanning}"
										data-dispo="${ planning.disponible ? 1 : 0 }"><c:if
											test="${patientInfo != null}">
											<span class="tooltipText">${patientInfo}</span>
										</c:if></td>
									</td>
								</c:forEach>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<form action="cancelPlannings" method="post" id="formCancelPlannings"
		style="display: none">
		<input type="hidden" name="motif" value="" id="inputMotif" /> <input
			type="hidden" name="listIdPlanning" value="" id="inputListIdPlanning" />
	</form>
	<script src="js/agenda.js"></script>
</body>
</html>

