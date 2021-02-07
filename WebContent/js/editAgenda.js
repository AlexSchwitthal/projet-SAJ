
(function() {

	var btnActivateAgenda = document.getElementById("activateAgenda");

	btnActivateAgenda.addEventListener("click", function(e) {
		var a = confirm("Souhaitez-vous activer votre agenda ?");

		if (a) {
			document.location.href = "http://localhost:8080/projet-SAJ/activateAgenda";
		}
	});

})();