<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%@include file="header.jsp" %>
    <body>
        <div class="container">
        <%@include file="menu.jsp" %>
            <br>
            <h1>Bonjour, veuillez rentrer vos identifiants</h1>
            <hr>
            <% 
            if(request.getAttribute("success") != null) {
                %>
                 <div class="alert alert-success" role="alert">
                	<%= request.getAttribute("success") %>
            	</div>
                <%
            }
            %>
            <form method="post" action="ServetLogin">
                <div class="form-group">
                    <div>
                        <label for="login">Nom :</label>
                        <input type="text" id="login" name="login" class="form-control" maxlength="15" required>
                        <br />
                    </div>
                    <div>
                        <label for="mdp">Mot de passe :</label>
                        <input type="password" id="mdp" name="mdp" class="form-control" maxlength="15" required>
                        <br />
                    </div>
                    <hr>
                    <div>
                        <input type="submit" value="Valider" class="btn btn-primary">

                        <input type="reset" value="Effacer" class="btn btn-danger">
                    </div>
                </div>
            </form>
        </div>
    </body>
</html>
