<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%/*@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"*/%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<%@include file="../header.jsp"%>
<body>
	<div class="container">
		<%@include file="../menu.jsp"%>
		<br>
		<h1>Test</h1>
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
							<td class="not-init">test</td>
						</c:forEach>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>
