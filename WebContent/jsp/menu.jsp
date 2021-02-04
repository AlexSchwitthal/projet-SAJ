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
				                    <li>	
				                    	<a href="login">Connexion</a>
				                    </li> 
				                    <li><a class="nav-link"
										href="registerPatient">Inscription (patient)</a>
									</li>	
				                    <%
				                } 
				                else {
				                	%>
				                    <li>	
				                   		<a href="home">Accueil</a>
				                    </li> 
				                    <%
				                	if(session.getAttribute("type") == "patient") {
				                		%>
				                		<li><a href="gestionPatient">Gestion (patient)</a>
										</li>
				                		<%
				                	}
				                	else if(session.getAttribute("type") == "medecin") {
				                		%>
				                		<li><a href="gestionMedecin">Gestion (medecin)</a>
				                		</li>
				                		<%
				                	}
				                	else if(session.getAttribute("type") == "administrateur") {
				                		%>
				                		<li><a href="registerMedecin">Inscription (medecin)</a>
				                		</li>
				                		<%
				                	}
				                    %>
				                    <li>	
				                    	<a href="logout">Deconnexion</a> 
				                    </li> 
				                    <%
				                }
				                %>
						</ul>
					</div>
				</div>
			</nav>
		</div>
	
	</body>
</html>