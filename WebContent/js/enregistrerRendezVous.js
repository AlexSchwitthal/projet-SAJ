function ajouterRendezVous(idPlanning) {
	var form = document.createElement("form");
	form.setAttribute("action", '/projet-SAJ/enregistrerRendezVous');
	form.setAttribute("method", "POST");
	
	var input = document.createElement("input");
	input.setAttribute("type", "text");
	input.setAttribute("name", "idRendezVous");
	input.setAttribute("value", idPlanning);
	
	console.log(idPlanning);
	form.appendChild(input);
	document.body.appendChild(form);
	form.submit();
}

/*function ajouterRendezVous(date, heureDebut, heureFin, idCentre, mailMedecin) {
	const params = {
		date : date,
		heureDebut: heureDebut,
		heureFin: heureFin,
		idCentre: idCentre,
		mailMedecin: mailMedecin
	};
	
	var form = document.createElement("form");
	form.setAttribute("action", '/projet-SAJ/enregistrerRendezVous');
	form.setAttribute("method", "POST");
	
	var input = null;
	Object.entries(params).forEach(([key, value]) => {
		input = document.createElement("input");
		input.setAttribute("type", "text");
		input.setAttribute("name", key);
		input.setAttribute("value", value);
		console.log(key, value);
		form.appendChild(input);
	});
	
	document.body.appendChild(form);
	form.submit();
}*/