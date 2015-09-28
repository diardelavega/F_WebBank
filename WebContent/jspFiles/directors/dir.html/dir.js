function dirSeverClientTupeling() {
	var reg = {
		head : "coordinate",
		empId : dirEmpId
	}
	doSend(JSON.stringify(reg));
}

function disableid() {
	$('#trid').attr("style", "visibility: collapse");
}

function enableId() {
	$('#trid').attr("style", "visibility: visible");
}

// ---------Dispaly MSG
var dirTimeOut;
function dispalyMsg(msg) {
	clearTimeout(dirTimeOut);

	console.log("Dispalying MSg");

	var div = $("#dirMsgAlert");
	$(div).empty();
	$(div).addClass("alert alert-info");

	var span = document.createElement("span");
	$(span).attr("align", "right");
	span.style.padding = "230px";

	var a = document.createElement("a");
	a.innerHTML = "&times;";
	$(a).attr("href", "#");
	$(a).attr("onclick", "closeThis(event)");

	var br = document.createTextNode(br);

	span.appendChild(a);
	$(div).append(span);
	div.append(br);
	div.append(msg);

	$(div).show();
	fadeOut(div);
}

function fadeOut(div) {
	dirTimeOut = setTimeout(function() {
		$(div).hide(1000);
	}, 5000);
}

function closeThis(event) {
	var div = $(event.target).parent().parent();
	$(div).hide(1000);
}
// ---------------------------

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

$(function() {
	/*
	 * initiating the different classes
	 * "amount,name,phone,address,email,password" to be ready to display user
	 * friendly functionalities
	 */
	var addresses = $(".address").focusout(function() {
		addressCtrl(this);
	});

	var paswords = $(".password").focusout(function() {
		pswCtrl(this);
	});

	var names = $(".name").focusout(function() {
		nameCtrl(this);
	});

	var phones = $(".phone").focusout(function() {
		phoneCtrl(this);
	});

	var notes = $(".note").focusout(function() {
		noteCtrl(this);
	});

	var emails = $(".mail").focusout(function() {
		emailCtrl(this);
	});

	var amts = $(".amount").focusout(function() {
		amountCtrl(this);
	});
	for (var i = 0; i < amts.length; i++) {
		$(amts[i]).attr("placeholder", "ex: 125.6787");
	}
});

function logOut() {
	var logout = {
		head : "logout",
		empId : dirEmpId,
	};
	doSend(JSON.stringify(logout));
}
