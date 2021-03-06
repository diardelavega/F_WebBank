function deposite() {
	var depData = {
		empId : telId,
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
		empId : telId,
		head : "withdraw",
		accNr : $('[name=withAccNr]').val(),
		amount : $('[name=withAmount]').val(),
		persId : $('[name=withPersId]').val()
	}
	doSend(JSON.stringify(withData));
}

function tranfer() {
	var transfData = {
		empId : telId,
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
		telWriteAllMsgs(req);
		ret = transAlertDisplay(req.reqType + " ", "info");
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
	console.log(jsobj.msg);
	ret = transAlertDisplay(jsobj.msg, "warning");
	transFadeOut(ret);
}

/* Functionality */
var telTimeOut;
function transAlertDisplay(msg, alertType) {
	var div = $("#telTransMsgAlert");
	clearTimeout(telTimeOut);
	div.empty();
	if (alertType === 'info') {
		$(div).addClass("alert alert-info");
	} else {
		$(div).addClass("alert alert-danger");
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

