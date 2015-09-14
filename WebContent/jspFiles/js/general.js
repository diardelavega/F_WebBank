function capitalize() {
	// setTimeout(function() {
	$("input[type=text]").focusout(function() {
		$(this).val($(this).val().toUpperCase());
	});
	// }, 3000)
}

$(function() {
	$("input[type=text]").focusout(function() {
		$(this).val($(this).val().toUpperCase());
	});
});

function foo() {
	// alert("FOOOOOOO!!");
	console.log("FOOOOOOO!!");
}

$(function() {
	/*
	 * append the datepicker functionality to the "From date" and "To date"
	 * input fields of balance and transaction
	 */
	$(".datePicker").datepicker({
		changeMonth : true,
		changeYear : true,
		dateFormat : 'dd/mm/yy',
	});
});