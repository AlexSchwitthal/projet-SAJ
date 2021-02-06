$(document).ready(function() {
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth() + 1;
	var yyyy = today.getFullYear() - 1;
	if(dd < 10) {
		dd= '0' + dd
	} 
	if(mm < 10) {
		mm= '0' + mm
	} 
	
	var min = (yyyy-119) + '-' + mm + '-' + dd;
	var max = yyyy + '-01-01';
	document.getElementById("dateNaissance").setAttribute("min", min);
	document.getElementById("dateNaissance").setAttribute("max", max);

});