<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="fr.dauphine.mido.as.projet.beans.Patient"%>
<%@page import="fr.dauphine.mido.as.projet.beans.Personne"%>
<%@page import="fr.dauphine.mido.as.projet.beans.Adresse"%>
<!DOCTYPE html>
<html>
    <%@include file="header.jsp" %>
    <script src="js/registerPatient.js"></script>
    <body>
        <div class="container">
	        <div id="menu">	        		
	        		 <div id="includeMenu">
	        				<jsp:include page="menu.jsp" />
	        			</div>          
					<section class="dark-wrapper opaqued parallax" data-parallax="scroll" data-image-src="assets/img/MedecinOK.jpg" data-speed="0.7">	
						 						
				            <div class="section-inner ">				            	
				                <div class="container">
				                	
				                    <div class="row">				                    	
				                        <div class="col-lg-12 mt30 wow text-center">
				                            <h2 class="section-heading">Création d'un compte Patient</h2>
				                            
				                        </div>
				                    </div>
				                     
				                </div>
				               
				            </div>
				     </section>
				</div>
		
            <br/><br/>
            <%@include file="alert.jsp" %>
            <%            
                Patient patient = (Patient) request.getAttribute("patient");
	            Personne personne = (Personne) request.getAttribute("personne");
	            Adresse adresse = (Adresse) request.getAttribute("adresse");
            %>
            <form method="post" action="registerPatient">
                <div class="form-group">
                    <div>
                        <label for="email">E-mail :</label>
                        <input type="text" value="<%= ((patient == null) ? "" : patient.getEmail()) %>" id="email" pattern="^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$" title="une adresse email valide" name="email" class="form-control" maxlength="50" required>
                        <br />
                    </div>
                    
                    <div>
                        <label for="nom">Nom :</label>
                        <input type="text" value="<%= ((personne == null) ? "" : personne.getNom()) %>" id="nom" pattern="^[-'a-zA-ZÀ-ÖØ-öø-ÿ ]+$" title="un nom valide" name="nom" class="form-control" maxlength="15" required>
                        <br />
                    </div>
                    
                    <div>
                        <label for="prenom">Prenom :</label>
                        <input type="text" value="<%= ((personne == null) ? "" : personne.getPrenom()) %>" id="prenom" pattern="^[-'a-zA-ZÀ-ÖØ-öø-ÿ ]+$" title="un prenom valide" name="prenom" class="form-control" maxlength="15" required>
                        <br />
                    </div>
                    
                    <div>
                        <label for="telephone">Téléphone :</label>
                        <input type="tel" value="<%= ((patient == null) ? "" : patient.getTelephone()) %>" pattern="((\+)33|0|0033)[1-9](\d{2}){4}$" title="un numéro de téléphone français" id="telephone" name="telephone" class="form-control" maxlength="12" required>
                        <br />
                    </div>
                    
                    <div>
                        <label for="adresse">Adresse :</label>
                        <input type="text" value="<%= ((adresse == null) ? "" : adresse.getAdresseComplete()) %>" id="adresse" name="adresse" class="form-control" maxlength="50" required>
                        <br />
                    </div>
                    
                   <div>
                        <label for="ville">Ville :</label>
                        <input type="text" value="<%= ((adresse == null) ? "" : adresse.getVille()) %>" id="ville" name="ville" class="form-control" maxlength="30" required>
                        <br />
                    </div>
                    
                   <div>
                        <label for="cp">Code postal :</label>
                        <input type="text" value="<%= ((adresse == null) ? "" : adresse.getCodePostal()) %>" pattern="[0-9]{5}" title="5 chiffres" id="cp" name="cp" class="form-control" maxlength="5" required>
                        <br />
                        
                        <label for="pays">Pays :</label>
                        <input type="text" value="<%= ((adresse == null) ? "" : adresse.getPays()) %>" id="pays" pattern="^[-'a-zA-ZÀ-ÖØ-öø-ÿ ]+$" name="pays" class="form-control" maxlength="15" required>
                        <br />
                   </div>
                    
                   <div>
                        <label for="dateNaissance">Date de naissance :</label>
                        <input type="date" value="<%= ((personne == null) ? "" : request.getAttribute("date")) %>" id="dateNaissance" name="dateNaissance" class="form-control" maxlength="11" max="9999-12-31" required>
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
