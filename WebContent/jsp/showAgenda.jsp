<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="fr.dauphine.mido.as.projet.beans.Medecin"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
		<li><a href="editAgenda">Modifiez votre agenda</a></li>
		<p> NB=${mapPlanning.size()}</p>
		<p>
		<c:forEach var="i" items="${mapPlanning}">
		[${i.key}=${i.value}]
		</c:forEach>
		</p>
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
							<c:set var="planning" value="${mapPlanning[dateTime] != null ? mapPlanning[dateTime].idPlanning : 'tst'}" />

							<td class="not-init"><c:out value="${planning}" /></td>
						</c:forEach>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>
