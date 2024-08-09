$(document).ready(function () {
	$(".email-validation").on("input", validateEmail);
	$(".email-validation").trigger('input');
});

var validEmailRegex = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

function validateEmail(e) {
	let btnId = $(e.target).data("btnId");
	if(validEmailRegex.test($(e.target).val()))
		$('#'+btnId).prop('disabled', false)
	else
		$('#'+btnId).prop('disabled', true)
}