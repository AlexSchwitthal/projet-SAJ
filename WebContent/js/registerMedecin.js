$(document).ready(function() {
	$('#addRow').on('click', function() {
		var copy = $('.ligne').last().clone();
		$('#listeCentre').append(copy);
		$('.delRow button').addClass('btn-danger').removeClass('btn-secondary');
	});
	
	$('body').on('click', '.delRow', function() {
		//console.log($('.ligne').size());
		
		if($('.ligne').length > 1) {
			$(this).closest('.ligne').remove();
		}

		var nbLignes = $('.ligne').length;
		if(nbLignes <= 1) {
			$('.delRow button').addClass('btn-secondary').removeClass('btn-danger');
		}
	});
});