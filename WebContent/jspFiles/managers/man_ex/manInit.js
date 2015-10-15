var host=window.location.hostname;
var ws = new WebSocket("ws://"+host+":8080/F_WebBank/man");

function newWs() {
	if (ws !== undefined && ws.readyState !== ws.CLOSED) {
		msgPost.innerHTML = "WebSocket is already opened.";
		return;
	}
	ws = new WebSocket("ws://"+host+":8080/F_WebBank/man");
}

ws.onmessage = function(evt) {
	onMessage(evt);
};
ws.onerror = function(evt) {
	onError(evt);
};

ws.onopen = function(evt) {
	setTimeout(function() {
		manSeverClientTupeling();
	}, 500);
};

function doSend(msg) {
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
	case 'requestNumber':
		requestNumber(jsobj);
		break;
	case 'clientAccountsReply':
		manClientAccountsReply(jsobj);
		break;
	case 'accountStatusReply':
		manAccountStatusReply(jsobj);
		break;
	case 'accountOwnersReply':
		manAccountCooReply(jsobj);
		break;
	case 'clientTransactionReply':
		manClientTransReply(jsobj);
		break;
	case 'manyClientTransactionReply':
		manManyClientTransReply(jsobj);
		break;
	case 'accountTransactionsReply':
		manAccountTransRply(jsobj);
		break;
	case 'leaveRequestReply':
		leaveRequest(jsobj);
		break;
	case 'requestRequestReply':
		requestRequestReply(jsobj);
	case 'requestInit':
		requestInit(jsobj);
		break;
	case 'error':
		errorRes(jsobj);
		break;
	case 'requestAlert':
		manRequestAlert(jsobj);
		break;
	case 'approveRequestReply':
		approveRequestReply(jsobj);
		break;
	case 'dennieRequestReply':
		dennieRequestReply(jsobj);
		break;
	case 'logoutReplay':
		logOutReplay(jsobj);
		break;

	case 'default':
		console.log("NoHeader");
	}

}

function onError(evt) {
	alert(evt.data);
}

function ManHideShow(chosen) {
	$('.hidable').each(function(index) {
		if ($(this).attr("id") === chosen) {
			$(this).show();
		} else {
			$(this).hide();
		}
	});
}
function divhide() {
	ManHideShow('mainMan');
}
