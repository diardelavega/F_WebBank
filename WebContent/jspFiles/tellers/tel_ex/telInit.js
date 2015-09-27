var ws = new WebSocket("ws://localhost:8080/F_WebBank/tel");
// var empId= $('#telEmpId').val();

function newWs() {
	if (ws !== undefined && ws.readyState !== ws.CLOSED) {
		msgPost.innerHTML = "WebSocket is already opened.";
		return;
	}
	ws = new WebSocket("ws://localhost:8080/F_WebBank/tel");
}

ws.onmessage = function(evt) {
	onMessage(evt);
};
ws.onerror = function(evt) {
	onError(evt);
};

ws.onopen = function(evt) {
	setTimeout(function() {
		telSeverClientTupeling();
	}, 500);

};

function doSend(msg) {
	// ws.send(msgSend.value);
	ws.send(msg);
}
function doClose() {
	ws.close();
}

function onMessage(evt) {
	// alert(evt.data);
	// console.log("received: " + evt.data);
	var jsobj = JSON.parse(evt.data);

	// console.log(jsobj);
	console.log(jsobj.head);

	var head = jsobj.head;

	switch (head) {
	case 'clientAccountsReply':
		clientAccounts(jsobj);
		break;
	case 'accountStatusReply':
		accountStatus(jsobj);
		break;
	case 'accountCoownersReply':
		accountCoowners(jsobj);
		break;
	case 'searchReply':
		searchReply(jsobj);
		break;
	// -------------------page 1
	case 'registerClientReply':
		registerClientReply(jsobj);
		break;
	case 'alterClientReply':
		alterClientReply(jsobj);
		break;
	case 'deleteClientReply':
		deleteClientReply(jsobj);
		break;
	// ------------------page 1.1
	 case 'depositeReply':
	 depositeReply(jsobj);
	 break;
	 case 'withdrawReply':
	 withdrawReply(jsobj);
	 break;
	 case 'transferReply':
	 transferReply(jsobj);
	 break;

	// ----------------page 2
	case 'requestReply':
		reqReplyAlert(jsobj);
		break;

	case 'ocRequestReply':
		ocRequestReply(jsobj);
		break;
	case 'transRequestReply':
		transRequestReply(jsobj);
		break;
	case 'logoutReplay':
		logOutReplay(jsobj);
		break;
	case 'error':
		errorRes(jsobj);
		break;
	case 'test':
		testOcrReciver(jsobj);
		break;
	/*
	 * case 'alterEmployee': alterEmpRes(jsobj); break; case 'dirTransaction':
	 * transactionHandler(jsobj); break; case 'dirBalance': //
	 * alert("BALANCEEEEE"+jsobj.balist) // console.log(jsobj.balist);
	 * balanceHandler(jsobj); break;
	 */
	case 'error':
		errorRes(jsobj);
		break;
	}
}

function onError(evt) {
	alert(evt.data);
}

function telHideShow(chosen) {
	$('.hidable').each(function(index) {
		if ($(this).attr("id") === chosen) {
			$(this).show();
		} else {
			$(this).hide();
		}
	});
}
function telDivhide() {
	telHideShow('mainTel');
	// $('.hidable').attr("style", "visibility: visible");
}

// function capitalize() {
// // setTimeout(function() {
// $("input[type=text]").focusout(function() {
// $(this).val($(this).val().toUpperCase());
// });
// }, 3000)
// }
