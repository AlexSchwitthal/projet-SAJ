<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%@include file="header.jsp" %>
    <body>
        <div class="container">        	
            <div id="menu">	 
            	<div id="includeMenu">
       				<jsp:include page="menu.jsp" />
       			</div>          
				<section class="dark-wrapper opaqued parallax" data-parallax="scroll" data-image-src="assets/img/dna.jpg" data-speed="0.7">
			            <div class="section-inner ">
			                <div class="container">
			                    <div class="row">
			                        <div class="col-lg-12 mt30 wow text-center">
			                            <h2 class="section-heading">Connexion à l'application</h2>
			                        </div>
			                    </div>
			                </div>			                
			            </div>
			     </section>
			</div>
	
            <br/><br/>
            <%@include file="alert.jsp" %>
            <form method="post" action="login">
                <div class="form-group">
                    <div>
                        <label for="login">Adresse E-mail :</label>
                        <input type="text" id="login" name="login" class="form-control" maxlength="50" required>
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
