
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
				document.location.href = "activateAgenda";
			}
		});
	}

	var btnDesactivateAgenda = document.getElementById("desactivateAgenda");

	if (btnDesactivateAgenda != null) {
		btnDesactivateAgenda.addEventListener("click", function(e) {
			var a = confirm("Souhaitez-vous désactiver votre agenda ?");

			if (a) {
				document.location.href = "desactivateAgenda";
			}
		});
	}

	function switchDisponible($planning) {
		var idPlanning = $planning.attr("data-idPlanning");
		var disponible = $planning.attr("data-dispo");
		var url = `ajaxEditAgenda?idPlanning=${idPlanning}&disponible=${disponible}`;

		$planning.toggleClass("avail", disponible != "1");
		$planning.toggleClass("not-avail", disponible == "1");
		$planning.attr("data-dispo", disponible == "1" ? 0 : 1);

		fetch(url).then(function(response) {
			if (response.ok) {
				response.json().then(function(json) {
					//console.log(json);
					if (json.status) {
						var newDispo = json.disponible;
						$planning.toggleClass("avail", newDispo);
						$planning.toggleClass("not-avail", !newDispo);
						$planning.attr("data-dispo", newDispo ? 1 : 0);
					}
				});
			}

		});
	}

	var $btnAnnulerRdv = $("#btnAnnulerRdv");

	var mapCancelRdv = new Map();

	function annulerRdv($planning) {
		var idPlanning = $planning.attr("data-idPlanning");
		var toCancel = $planning.hasClass("to-cancel");

		if (!toCancel) {
			var data = { idPlanning };
			mapCancelRdv.set(idPlanning, data);
		} else {
			mapCancelRdv.delete(idPlanning);
		}

		$planning.toggleClass("to-cancel", !toCancel);
		$btnAnnulerRdv.prop("disabled", mapCancelRdv.size == 0);
	}

	var $tableAgenda = $("#table-agenda");

	if ($tableAgenda.length > 0) {
		$("tbody", $tableAgenda).on("mouseup", "td", function() {
			var $this = $(this);

			if ($this.hasClass("taken")) {
				annulerRdv($this);
			} else if (!$this.hasClass("not-init")) {
				switchDisponible($this);
			}
		});
	}

	$btnAnnulerRdv.on("click", function() {
		var messageAnnulation = prompt("Veuillez rentrez votre message d'annulation");
		if (messageAnnulation != null) {
			var listIdPlanning = [];
			mapCancelRdv.forEach(s => {
				listIdPlanning.push(s.idPlanning);
			});

			$("#inputMotif").val(messageAnnulation);
			$("#inputListIdPlanning").val(listIdPlanning);
			
			$("#formCancelPlannings").submit();
			
			
		}
	});
})();