function dirSeverClientTupeling() {
	var reg = {
		head : "coordinate",
		empId : dirEmpId
	}
	doSend(JSON.stringify(reg));
}

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
});

function logOut() {
	var logout = {
		head : "logout",
		empId : dirEmpId,
	};
	doSend(JSON.stringify(logout));
}

//function logOutReplay(jsobj) {
//	if (jsobj.hasOwnProperty('response')) {
//		if (jsobj.response === 'OK!') {
//			window.location.href = "../../logout.jsp";
//		}
//	} else if (jsobj.hasOwnProperty('msg')) {
//		console.log("on log out  " + jsobj.msg);
//	}
//}
