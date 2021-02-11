<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="fr.dauphine.mido.as.projet.beans.Medecin"%>
<%@page import="fr.dauphine.mido.as.projet.beans.Centremedical"%>
<%@page import="fr.dauphine.mido.as.projet.beans.Specialite"%>
<!DOCTYPE html>
<html>
    <%@include file="header.jsp" %>
    <body>
        <div class="container">
	        <div id="menu">	        		
	        		 <div id="includeMenu">
	        				<jsp:include page="menu.jsp" />
	        			</div>          
					<section class="dark-wrapper opaqued parallax" data-parallax="scroll" data-image-src="assets/img/equipe-medecins.jpg" data-speed="0.7">	
						 						
				            <div class="section-inner ">				            	
				                <div class="container">
				                	
				                    <div class="row">				                    	
				                        <div class="col-lg-12 mt30 wow text-center">
				                            <h2 class="section-heading">Gestion d'un compte Medecin</h2>
				                            
				                        </div>
				                    </div>
				                     
				                </div>
				               
				            </div>
				     </section>
				</div>
		
            <br/><br/>
            <%@include file="alert.jsp" %>
            <%            
                Medecin medecin = (Medecin) request.getAttribute("medecin");
            	Map<Centremedical, Specialite> m = (Map<Centremedical, Specialite>) request.getAttribute("map");
            %>
          
            <form method="post" action="gestionMedecin">
                <div class="form-group">
                    <div>
                        <label for="email">E-mail :</label>
                        <input value="<%= medecin.getEmail() %>" type="text" id="email" pattern="^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$" title="une adresse email valide" name="email" class="form-control" maxlength="50" required>
                        <br />
                    </div>
                    
                    <div>
                        <label for="nom">Nom :</label>
                        <input value="<%= medecin.getPersonne().getNom() %>" type="text" id="nom" name="nom" class="form-control" maxlength="15" required>
                        <br />
                    </div>
                    
                    <div>
                        <label for="prenom">Prenom :</label>
                        <input value="<%= medecin.getPersonne().getPrenom() %>" type="text" id="prenom" name="prenom" class="form-control" maxlength="15" required>
                        <br />
                    </div>
                    
                    <div>
                        <label for="telephone">Téléphone :</label>
                        <input value="<%= medecin.getTelephone() %>" type="tel" pattern="[0-9]{10}" title="10 chiffres" id="telephone" name="telephone" class="form-control" maxlength="10" required>
                        <br />
                    </div>
                    
                    <div>
                        <label for="adresse">Adresse d'habitation :</label>
                        <input value="<%= medecin.getPersonne().getAdresse().getAdresseComplete() %>" type="text" id="adresse" name="adresse" class="form-control" maxlength="50" required>
                        <br />
                    </div>

                    
                    <div>
                        <label for="mdp">Mot de passe :</label>
                        <input value="<%= medecin.getMotDePasse() %>" type="password" id="mdp" name="mdp" class="form-control" maxlength="15" required>
                        <br />
                    </div>
                    
                    <hr>
                    <div>
                        <input type="submit" value="Valider" class="btn btn-primary">
                    </div>
                </div>
            </form>
            <br>
            <br>

            <hr>	
            <h3>Suppression d'une affectation à un centre</h3>
			<div class="row">
            	<div class="col-md-5 ">
            		<div id="centre"><h4>Centre Médical :</h4></div>
            	 </div>
            	 <div class="col-md-1">
            	 </div>
            	<div class="col-md-5 ">
            		<div><h4>Specialité :</h4></div>
                </div>               	
            </div>
            <%     
            for (Map.Entry<Centremedical, Specialite> entry : m.entrySet()) {
            	%>
            	<form onSubmit="return confirm('Voulez-vous vraiment supprimer votre affection à se centre ?')" method="post" action="deleteMedecinCentre" id="deleteMedecinCentre">
            	<input id="idCentre" name="idCentre" type="hidden" value="<%= entry.getKey().getIdCentre() %>">
            	<input id="idSpecialite" name="idSpecialite" type="hidden" value="<%= entry.getValue().getIdSpecialite() %>">
            	<div class="row">
            	 	<div class="col-md-5" style="margin-top:3%;">
            	 		<div><%= entry.getKey().getNom() %></div>
            	 	</div>
            	 	<div class="col-md-1">
            	 	</div>
            		<div class="col-md-5" style="margin-top:3%;">
            			<div><%= entry.getValue().getLibelle() %></div>
                	</div>
  					<div class="col-md-1">
						<div class="delRow" style="margin-top:30%;">
							<div>
		                        <input type="submit" value="Supprimer" class="btn btn-danger">
		                    </div>
		        		</div>	
		        	</div>                	
                </div>
                </form>
            	<%
            }
            %>
            <hr>	

            <form onSubmit="return confirm('Voulez-vous vraiment supprimer votre compte ?')" method="post" action="deleteMedecin">
             	<input type="submit" value="Supprimer le compte" class="btn btn-danger">
			</form>
        </div>
    </body>
</html>
