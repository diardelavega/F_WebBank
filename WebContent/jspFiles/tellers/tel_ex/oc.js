function openAccount() {
	var persIdList = {
		empId : telId,
		head : "openAccountRequest",
		pId1 : $("[name=telOpenId1]").val(),
		pId2 : $("[name=telOpenId2]").val(),
		pId3 : $("[name=telOpenId3]").val(),
		pId4 : $("[name=telOpenId4]").val(),
		accType : $("[name=accType]").val()
	}
	doSend(JSON.stringify(persIdList));
}

function tellCloseAcc() {
	var persIdList = {
		empId : telId,
		head : "closeAccountRequest",
		pId1 : $("[name=telCloseId1]").val(),
		pId2 : $("[name=telCloseId2]").val(),
		pId3 : $("[name=telCloseId3]").val(),
		pId4 : $("[name=telCloseId4]").val(),
		accNr : $("[name=telCloseAcc]").val()
	}
	doSend(JSON.stringify(persIdList));
}

/* AFTER RESPONSE FUNCTIONS */

function reqReplyAlert(jsobj) {
	// get response with problems detected fon server side
	if (jsobj.hasOwnProperty("problematicId")) {
		var problemIds = jsobj.problematicId;

		var p = document.createElement("p");
		var t1 = document.createTextNode("Problems with some clients Ids: \t");
		p.appendChild(t1);

		for (var i = 0; i < problemIds.length; i++) {
			var br = document.createElement("br");
			p.appendChild(br);

			var a = document.createElement("a");
			$(a).attr("href", "#");
			$(a).attr("onclick", "idcheck(event)");
			var strong = document.createElement("strong");
			var t = document.createTextNode(problemIds[i]);

			strong.appendChild(t);
			a.appendChild(strong);
			p.appendChild(a);
			p.appendChild(br);
		}
		ret = ocAlertDisplay(p, "warn");
		fadeOut(ret);
	} else if (jsobj.hasOwnProperty("msg")) {

		ret = ocAlertDisplay(jsobj.msg, "info");
		fadeOut(ret);

	}

}
// ----------Extra Functionality
var telTimeOut;
function ocAlertDisplay(msg, alertType) {
	// display the interactive msg alert
	var div = $("#telOcMsgAlert");
	clearTimeout(telTimeOut);
	div.empty();
	// div.text(msg);
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

function fadeOut(div) {
	telTimeOut = setTimeout(function() {
		$(div).hide(1000);
	}, 5000);
}

function idcheck(event) {
	telHideShow("mainTel");
	var txt = $(event.target).text();
	$("#telPersonalId").val(txt);
}

function clearOpenAcc() {
	$("[name=telOpenId1]").val("");
	$("[name=telOpenId2]").val("");
	$("[name=telOpenId3]").val("");
	$("[name=telOpenId4]").val("");
}
function tellClearClose() {
	$("[name=telCloseId1]").val("");
	$("[name=telCloseId2]").val("");
	$("[name=telCloseId3]").val("");
	$("[name=telCloseId4]").val("");
	$("[name=telCloseAcc]").val("");
}

function ocRequestReply(jsobj) {
	console.log("ocRequestReply");
	if (jsobj.hasOwnProperty("replyData")) {
		var req = jsobj.replyData;
		console.log(req);
		// weite respone at replys
		telWriteAllMsgs(req);
		// write three arg @ head panel envelope
		telNotifyMsg(req.response, req.reqType, req.clientIdsList);
		// up arroc color notify
		telNotifyColor(req.response);

	} else {
		ret = ocAlertDisplay(jsobj.msg, "info");
		fadeOut(ret);
	}

}