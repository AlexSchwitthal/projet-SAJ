function validationSuppression() {
	var raisonAnnulation = prompt("Veuillez saisir la raison de l'annulation du rendez vous :'");
	var elements = document.getElementsByName("raisonAnnulation");
    for(var i = elements.length - 1; i >= 0; --i) {
        elements[i].value = raisonAnnulation;
    }   
	return raisonAnnulation;
}