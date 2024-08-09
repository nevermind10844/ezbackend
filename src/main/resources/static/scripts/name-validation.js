$(document).ready(function() {
	$(".new-element-name-validation").on('input', validateNewElementName);
	$(".new-element-name-validation").trigger('input');
});

function validateNewElementName() {
	let name = $(this).val();
	let targetId = $(this).data('target');
	$('#' + targetId).prop('disabled', !(name && name.length > 2));
}