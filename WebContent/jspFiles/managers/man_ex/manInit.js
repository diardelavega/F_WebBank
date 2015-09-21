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
	manSeverClientTupeling();
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
		break;
	case 'error':
		errorRes(jsobj);
		break;
	case 'requestAlert':
		manRequestAlert(jsobj);
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

/*
 * function capitalize() { // setTimeout(function() {
 * $("input[type=text]").onblure(function() {
 * $(this).val($(this).val().toUpperCase()); }); // }, 3000) }
 */
