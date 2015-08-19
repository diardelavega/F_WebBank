function disableid() {
	$('#trid').attr("style", "visibility: collapse");
	// $('#butid').attr('value', 'SAVEss');
	// $('#butid').attr('onclick', '');
}

function enableId() {
	$('#trid').attr("style", "visibility: visible");
	// $('#butid').attr('value', 'SEARCHzzz');
	// $('#butid').val('snitcheszzz');
	// document.getElementById("butid").onclick = function() { getEmpInfo(); }
}

function capitalize() {
	// setTimeout(function() {
	$("input[type=text]").keyup(function() {
		$(this).val($(this).val().toUpperCase());
	});
	// }, 3000)
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
	// $("#datepicker2").datepicker({
	// changeMonth : true,
	// changeYear : true,
	// dateFormat : 'dd/mm/yy',
	// // onSelect : function(dateText, inst) {
	// // window.tDate= dateText;
	// // window.tDate = $(this).datepicker('getDate');
	// // alert(dateAsString);
	// // alert(tDate);
	// // }
	// });
});
