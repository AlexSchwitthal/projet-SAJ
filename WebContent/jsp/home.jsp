<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%@include file="header.jsp" %>
    <body>
        <div class="container">
        <%@include file="menu.jsp" %>
            <br>
            <h1>Bienvenue <%= request.getAttribute("login") %></h1>
            <hr>
        </div>
    </body>
</html>
