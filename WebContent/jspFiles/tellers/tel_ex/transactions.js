function deposite() {
	var depData = {
		head : "deposite",
		accNr : $('[name=accNr]').val(),
		amount : $('[name=amount]').val(),
		note : $('[name=note]').val()
	}
	doSend(JSON.stringify(depData));
	// alert(depData);
	// console.log(depData);
}

function withdraw() {
	var withData = {
		head : "withdraw",
		accNr : $('[name=withAccNr]').val(),
		amount : $('[name=withAmount]').val(),
		persId : $('[name=withPersId]').val()
	}
	doSend(JSON.stringify(withData));
}

function tranfer() {
	var transfData = {
		head : "transfer",
		accFrom : $('[name=accNrFrom]').val(),
		accTo : $('[name=accNrTo]').val(),
		amount : $('[name=transfAmount]').val(),
		persId : $('[name=transfPersId]').val()
	}
	doSend(JSON.stringify(transfData));
}

/* AFTER RESPONSE */
function transRequestReply(jsobj) {
	/* response for transactions with +1K */
	if (jsobj.hasOwnProperty("replyData")) {
		var req = jsobj.replyData;

		var lastManagerToConsiderIt = req.lastManagerToConsiderIt;
		var note = req.note;
		var clientIdsList = req.clientIdsList;
		var reqType = req.reqType;
		var accType = req.accType;
		var accFromNr = req.accFromNr;
		var accToNr = req.accToNr;
		var amount = req.amount;
		var response = req.response;// accepted || denied

		var txt = reqType
		" - " + response + " " + " " + note + " ";

		if (response === "APROVED") {
			ret = transAlertDisplay(txt + " ", "info");
		} else {
			ret = transAlertDisplay(txt + " ", "warning");
		}
		fadeOut(ret);
	}
}

function depositeReply(jsobj) {
	// clonsole.log(jsobj.msg);
	transAlertDisplay(jsobj.msg, "info");
}
function withdrawReply(jsobj) {
	// clonsole.log(jsobj.msg);
	transAlertDisplay(jsobj.msg, "info");
}
function transferReply(jsobj) {
	// clonsole.log(jsobj.msg);
	transAlertDisplay(jsobj.msg, "info");
}

function errorRes(jsobj) {
	clonsole.log(jsobj.msg);
}

/* Functionality */
var telTimeOut;
function transAlertDisplay(msg, alertType) {
	var div = $("#telTransMsgAlert");
	clearTimeout(telTimeOut);
	div.empty();
	// div.text(msg);
	if (alertType === 'info') {
		$(div).addClass("alert alert-info");
	} else {
		$(div).addClass("alert alert-warning");
	}

	var span = document.createElement("span");
	$(span).attr("align", "left");
	span.style.padding = "230px";

	var a = document.createElement("a");
	a.innerHTML = "x";
	$(a).attr("href", "#");
	$(a).attr("onclick", "closeThis(event)");
	var br = document.createElement("br");

	span.appendChild(a)
	div.append(span);
	div.append(br);
	div.append(msg);

	$(div).show();
	fadeOut(div);
	return div;
}

function transFadeOut(div) {
	telTimeOut = setTimeout(function() {
		$(div).hide(1000);
	}, 5000);
}

function clearDep() {
	$('[name=accNr]').val("");
	$('[name=amount]').val("");
	$('[name=note]').val("");
}
function clearWithdraw() {
	$('[name=withAccNr]').val("");
	$('[name=withAmount]').val("");
	$('[name=withPersId]').val("");
}
function clearTransfer() {
	accFrom: $('[name=accNrFrom]').val("");
	accTo: $('[name=accNrTo]').val("");
	amount: $('[name=transfAmount]').val("");
	persId: $('[name=transfPersId]').val("");
}

// --------NEW ALERT TellPage NAV Panel

