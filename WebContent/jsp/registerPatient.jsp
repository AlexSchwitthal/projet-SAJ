<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%@include file="header.jsp" %>
    <body>
        <div class="container">
        <%@include file="menu.jsp" %>
            <br>
            <h1>Inscription patient</h1>
            <hr>
            <% 
            if(request.getAttribute("warning") != null) {
                %>
                 <div class="alert alert-danger" role="alert">
                	<%= request.getAttribute("warning") %>
            	</div>
                <%
            }
            %>
            <form method="post" action="registerPatient">
                <div class="form-group">
                    <div>
                        <label for="email">E-mail :</label>
                        <input type="text" id="email" pattern="^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$" title="une adresse email valide" name="email" class="form-control" maxlength="50" required>
                        <br />
                    </div>
                    
                    <div>
                        <label for="nom">Nom :</label>
                        <input type="text" id="nom" name="nom" class="form-control" maxlength="15" required>
                        <br />
                    </div>
                    
                    <div>
                        <label for="prenom">Prenom :</label>
                        <input type="text" id="prenom" name="prenom" class="form-control" maxlength="15" required>
                        <br />
                    </div>
                    
                    <div>
                        <label for="telephone">Téléphone :</label>
                        <input type="tel" pattern="[0-9]{10}" title="10 chiffres" id="telephone" name="telephone" class="form-control" maxlength="10" required>
                        <br />
                    </div>
                    
                    <div>
                        <label for="adresse">Adresse :</label>
                        <input type="text" id="adresse" name="adresse" class="form-control" maxlength="50" required>
                        <br />
                    </div>
                    
                   <div>
                        <label for="ville">Ville :</label>
                        <input type="text" id="ville" name="ville" class="form-control" maxlength="30" required>
                        <br />
                    </div>
                    
                   <div>
                        <label for="cp">Code postal :</label>
                        <input type="text" pattern="[0-9]{5}" title="5 chiffres" id="cp" name="cp" class="form-control" maxlength="5" required>
                        <br />
                        
                        <label for="pays">Pays :</label>
                        <input type="text" id="pays" name="pays" class="form-control" maxlength="15" required>
                        <br />
                   </div>
                    
                   <div>
                        <label for="dateNaissance">Date de naissance :</label>
                        <input type="date" id="dateNaissance" name="dateNaissance" class="form-control" maxlength="11" max="9999-12-31" required>
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
