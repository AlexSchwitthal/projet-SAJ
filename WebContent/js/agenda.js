
(function() {

	var idCentre = document.getElementById("idCentre");
	var selectCentre = document.getElementById("centre-select");
	if (selectCentre != null) {
		selectCentre.addEventListener("change", function(e) {
			var id = e.target.value;
			
			document.location.href = `${location.protocol}//${location.host}${location.pathname}?centre=${id}`;
				
		});
	}
	
	var btnActivateAgenda = document.getElementById("activateAgenda");

	if (btnActivateAgenda != null) {
		btnActivateAgenda.addEventListener("click", function(e) {
			var a = confirm("Souhaitez-vous activer votre agenda ?");

			if (a) {
				document.location.href = "http://localhost:8080/projet-SAJ/activateAgenda";
			}
		});
	}
	
	var btnDesactivateAgenda = document.getElementById("desactivateAgenda");

	if (btnDesactivateAgenda != null) {
		btnDesactivateAgenda.addEventListener("click", function(e) {
			var a = confirm("Souhaitez-vous désactiver votre agenda ?");

			if (a) {
				document.location.href = "http://localhost:8080/projet-SAJ/desactivateAgenda";
			}
		});
	}
})();