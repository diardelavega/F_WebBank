var ws = new WebSocket("ws://localhost:8080/F_WebBank/man");

function newWs() {
	if (ws !== undefined && ws.readyState !== ws.CLOSED) {
		msgPost.innerHTML = "WebSocket is already opened.";
		return;
	}
	ws = new WebSocket("ws://localhost:8080/F_WebBank/man");
}

ws.onmessage = function(evt) {
	onMessage(evt);
};
ws.onerror = function(evt) {
	onError(evt);
};

ws.onopen = function(evt) {
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

	console.log(jsobj.head);
	var head = jsobj.head;

	switch (head) {
//	case 'balanceReply':
//		balanceReply(jsobj)
//		break;
//	case 'transactionReply':
//		transactionReply(jsobj)
//		break;
	case 'clientAccountsReply':
		manClientAccounts(jsobj);
		break;
	case 'accountStatusReply':
		manAccountStatusReply(jsobj);
		break;
	case 'accountOwnersReply':
		manAccountCooReply(jsobj);
		break;
	case 'clientTransactionReply':
		manClientTrans(jsobj);
		break;
	case 'manyClientTransactionReply':
		manManyClientTrans(jsobj);
		break;
	case 'accountTransactionsReply':
		manAccountTrans(jsobj);
		break;
	case 'leaveRequestReply':
		leaveRequest(jsobj);
		break;
	case 'requestRequestReply':
		requestRequestReply(jsobj);
		break;
	case 'error':
		errorRes(jsobj);
		break;
	}

}

function onError(evt) {
	alert(evt.data);
}

function ManHideShow(chosen) {
	$('.hidable').each(function(index) {
		if ($(this).attr("id") === chosen) {
			$(this).show();
//			$(this).attr("style", "visibility: visible");
		} else {
//			$(this).attr("style", "visibility: collapse");
			$(this).hide();
		}
	});
}
function divhide() {
	ManHideShow('mainMan');
	// $('.hidable').attr("style", "visibility: visible");
}




/*function capitalize() {
	// setTimeout(function() {
	$("input[type=text]").onblure(function() {
		$(this).val($(this).val().toUpperCase());
	});
	// }, 3000)
}*/
