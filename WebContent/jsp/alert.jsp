<% 
if(request.getAttribute("warning") != null) {
	%>
	<div class="alert alert-danger" role="alert">
    	<%= request.getAttribute("warning") %>
    </div>
    <%
}
else if(request.getAttribute("success") != null) {
%>
	<div class="alert alert-success" role="alert">
    	<%= request.getAttribute("success") %>
    </div>
    <%
}
%>