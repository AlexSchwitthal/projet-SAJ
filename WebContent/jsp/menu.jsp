<nav class="navbar navbar-expand navbar-dark bg-dark">
	<div class="collapse navbar-collapse">
		<ul class="navbar-nav mr-auto">
			<li class="nav-item active"><a class="nav-link"
				href="registerPatient">Inscription (patient)</a>
			</li>
			<li class="nav-item active"><a class="nav-link"
				href="gestionPatient">Gestion (patient)</a>
			</li>
			<li class="nav-item active"><a class="nav-link"
				href="registerMedecin">Inscription (medecin)</a>
			</li>
			<li class="nav-item active">
			
				<%
                if (session.getAttribute("login") == null) {
                    %><a class="nav-link" href="login">Connexion</a> <%
                } 
                else {
                    %><a class="nav-link" href="affichage">Session</a> <%
                }
                %>
			</li>
		</ul>
	</div>
</nav>
<hr>
<br />