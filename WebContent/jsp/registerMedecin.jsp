<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="fr.dauphine.mido.as.projet.beans.Centremedical"%>
<%@page import="fr.dauphine.mido.as.projet.beans.Specialite"%>
<%@page import="fr.dauphine.mido.as.projet.beans.Medecin"%>
<%@page import="fr.dauphine.mido.as.projet.beans.Personne"%>
<%@page import="fr.dauphine.mido.as.projet.beans.Adresse"%>
<!DOCTYPE html>
<html>
    <%@include file="header.jsp" %>
    <script src="js/registerMedecin.js"></script>
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
				                            <h2 class="section-heading">Création d'un compte Medecin</h2>
				                            
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
	            Personne personne = (Personne) request.getAttribute("personne");
	            Adresse adresse = (Adresse) request.getAttribute("adresse");
            %>
            <form method="post" action="registerMedecin">
                <div class="form-group">
                    <div>
                        <label for="email">E-mail :</label>
                        <input type="text" value="<%= ((medecin == null) ? "" : medecin.getEmail()) %>" id="email" pattern="^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$" title="une adresse email valide" name="email" class="form-control" maxlength="50" required>
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
                        <input type="tel" value="<%= ((medecin == null) ? "" : medecin.getTelephone()) %>" pattern="((\+)33|0|0033)[1-9](\d{2}){4}$" title="un numéro de téléphone français" id="telephone" name="telephone" class="form-control" maxlength="10" required>
                        <br />
                    </div>
                    
                    <div>
                        <label for="adresse">Adresse d'habitation :</label>
                        <input type="text" value="<%= ((adresse == null) ? "" : adresse.getAdresseComplete()) %>" id="adresse" name="adresse" class="form-control" maxlength="50" required>
                        <br />
                    </div>
                    
                    <div id="listeCentre">
	                    <div class="ligne">
		                    <div class="row">
		                        <div class="col-md-5 " style="margin-top:3%;">
		                        	<div class="form-group">
									  <label for="centreMedical">Centre Médical :</label>
									  <select id="centreMedical" name="centreMedical">
									  	<%
									  	List<Centremedical> listeCentre = (List<Centremedical>) request.getAttribute("listeCentre");
									  	for(Centremedical c : listeCentre) {
									  		%> <option value="<%= c.getIdCentre() %>"> <%= c.getNom() %></option> <%
									  	}
									  	%>
									  </select>
									</div>
		                        </div>
		                        <div class="col-md-1">
		                        </div>
		                        <div class="col-md-5 listeDeroulante" style="margin-top:3%;">
		                        	<div class="form-group">
									  <label for="specialite">Spécialité :</label>
									  <select id="specialite" name="specialite">
									  	<%
									  	List<Specialite> listeSpecialite = (List<Specialite>) request.getAttribute("listeSpecialite");
									  	for(Specialite s : listeSpecialite) {
									  		%> <option value="<%= s.getIdSpecialite() %>"> <%= s.getLibelle() %></option> <%
									  	}
									  	%>
									  </select>
									</div>
		                        </div>
		                        <div class="col-md-1">
									<div class="delRow" style="margin-top:30%;">
									 	<button type="button" class="btn btn-secondary btn-lg" name="delRow">
		          							<i class="far fa-trash-alt fa-lg"></i>
		        						</button>
		        					</div>	
		        				</div>
		                    </div>
	                   	</div>
                   	</div>
                   	<br />
                   	
                   	<div class="row">
		               	<div class="col-md-3">
		                	<button type="button" name="addRow" id="addRow" value="Ajouter un centre médical" class="btn btn-primary btn-md" style="background-color: #F5F5F5; color: black;">
		                    	Ajouter un centre médical
		                    </button>
		                </div>
	               	</div>
	               	<br />
                    
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
