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

// ------ALERTING
function popoverAlert(inp, txt) {
	$(inp).popover({
		title : 'Incorect Value',
		content : txt,
		placement : 'right'
	}).focus(function() {
		$(this).popover('hide');
	}).on('shown.bs.popover', function() {
		setTimeout(function() {
			$(inp).popover('hide');
		}, 3500);
	});

	$(inp).popover('show');
}

function amountCtrl(inp) {
	if (inp.value === "") {
		return;
	}
	if (inp.value.indexOf(".") > -1) {
		var reg = /^\d{1,5}\.{0,}\d{0,7}$/;
	} else {
		var reg = /^\d{1,5}$/;
	}

	if (!reg.test(inp.value)) {
		var txt = "format: 1235,567 ";
		popoverAlert(inp, txt);
	} else {
		popUp(inp);
	}
}

function emailCtrl(inp) {
	if (inp.value === "") {
		return;
	}
	var re = /^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
	if (!re.test(inp.value)) {
		var txt = "format: abc@xyz.com ";
		popoverAlert(inp, txt);
	} else {
		popUp(inp);
	}

}

function phoneCtrl(inp) {
	if (inp.value === "") {
		return;
	}
	var re = /^\d{10}$/;
	if (!re.test(inp.value)) {
		var txt = "10 digits only";
		popoverAlert(inp, txt);
	} else {
		popUp(inp);
	}

}

function nameCtrl(inp) {
	if (inp.value === "") {
		return;
	}
	if (inp.value.length > 30 || inp.value.length < 5) {
		var txt = "between 5 & 30 chars";
		popoverAlert(inp, txt);
	} else {
		var re = /^[a-zA-Z]+$/;
		if (!re.test(inp.value)) {
			popUp(inp);
			var txt = "not alowed chars, letters only ";
			popoverAlert(inp, txt);
		} else {
			popUp(inp);
		}
	}

}

function pswCtrl(inp) {
	if (inp.value === "") {
		return;
	}
	if (inp.value.length > 24 || inp.value.length < 6) {
		var txt = "between 6 & 24 chars";
		popoverAlert(inp, txt);
	} else {
		popUp(inp);
	}
}

function addressCtrl(inp) {
	if (inp.value === "") {
		return;
	}
	if (inp.value.length > 50 || inp.value.length < 5) {
		var txt = "between 5 & 50 chars";
		popoverAlert(inp, txt);
	} else {
		var re = /^[a-zA-Z0-9\s,'-\(\)&]*$/;
		if (!re.test(inp.value)) {
			popUp(inp);
			var txt = "not alowed chars, alphanumeric and -()'& ";
			popoverAlert(inp, txt);
		} else {
			popUp(inp);
		}
	}
}

function noteCtrl(inp) {
	if (inp.value === "") {
		return;
	}
	if (inp.value.length > 20) {
		var txt = "less than 20 chars";
		popoverAlert(inp, txt);
	} else {
		popUp(inp);
	}
}

function popUp(inp) {
	if ($(inp).data('bs.popover')) {
		$(inp).popover('destroy');
	}

}