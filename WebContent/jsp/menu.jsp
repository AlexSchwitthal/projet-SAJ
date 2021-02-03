<nav class="navbar navbar-expand navbar-dark bg-dark">
	<div class="collapse navbar-collapse">
		<ul class="navbar-nav mr-auto">
				<%
                if (session.getAttribute("login") == null) {
                    %>
                    <li class="nav-item active">	
                    	<a class="nav-link" href="login">Connexion</a>
                    </li> 
                    <li class="nav-item active"><a class="nav-link"
						href="registerPatient">Inscription (patient)</a>
					</li>	
                    <%
                } 
                else {
                	%>
                    <li class="nav-item active">	
                   		<a class="nav-link" href="home">Accueil</a>
                    </li> 
                    <%
                	if(session.getAttribute("type") == "patient") {
                		%>
                		<li class="nav-item active"><a class="nav-link"
							href="gestionPatient">Gestion (patient)</a>
						</li>
                		<%
                	}
                	else if(session.getAttribute("type") == "medecin") {
                		%>
                		<li class="nav-item active"><a class="nav-link"
                			href="gestionMedecin">Gestion (medecin)</a>
                		</li>
                		<%
                	}
                	else if(session.getAttribute("type") == "administrateur") {
                		%>
                		<li class="nav-item active"><a class="nav-link"
                			href="registerMedecin">Inscription (medecin)</a>
                		</li>
                		<%
                	}
                    %>
                    <li class="nav-item active">	
                    	<a class="nav-link" href="logout">Deconnexion</a> 
                    </li> 
                    <%
                }
                %>
		</ul>
	</div>
</nav>
<hr>
<br />