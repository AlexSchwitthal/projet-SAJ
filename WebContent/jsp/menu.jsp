<!DOCTYPE html>
<html>
	<body id="page-top" class="index">	
		<div class="master-wrapper">
			<nav class="navbar navbar-default navbar-fixed-top fadeInDown" data-wow-delay="0.5s">
				<div class="container">		
					<div class="collapse navbar-collapse" id="main-navigation">
						<ul id="itemsMenu" class="nav navbar-nav navbar-right">
								<%
				                if (session.getAttribute("login") == null) {
				                    %>
				                    <li class="dropdown">	
				                    	<a href="login">Connexion</a>
				                    </li> 
				                    <li class="dropdown"><a class="nav-link"
										href="registerPatient">Inscription (patient)</a>
									</li>	
				                    <%
				                } 
				                else {
				                	%>
				                    <li class="dropdown">	
				                   		<a href="home">Accueil</a>
				                    </li> 
				                    <%
				                	if(session.getAttribute("type") == "patient") {
				                		%>
				                		<li class="dropdown"><a href="gestionPatient">Gestion (patient)</a>
										</li>
				                		<%
				                	}
				                	else if(session.getAttribute("type") == "medecin") {
				                		%>
				                		<li class="dropdown"><a href="gestionMedecin">Gestion (medecin)</a>
				                		</li>
				                		<%
				                	}
				                	else if(session.getAttribute("type") == "administrateur") {
				                		%>
				                		<li class="dropdown"><a href="registerMedecin">Inscription (medecin)</a>
				                		</li>
				                		<%
				                	}
				                    %>
				                    <li class="dropdown">	
				                    	<a href="logout">Deconnexion</a> 
				                    </li> 
				                    <%
				                }
				                %>
						</ul>
							<%
							if(session.getAttribute("prenom") != null && session.getAttribute("nom") != null) {
								%>
								<div style="color:white">Connecté en tant que : <%= session.getAttribute("prenom") %> <%= session.getAttribute("nom") %></div>
								<%
							}
							%>
					</div>
				</div>
			</nav>
		</div>
	</body>
</html>
