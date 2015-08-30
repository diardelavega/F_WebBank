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
	case 'balanceReply':
		balanceReply(jsobj)
		break;
	case 'transactionReply':
		transactionReply(jsobj)
		break;
	case 'clientAccountsReply':
		clientAccounts(jsobj);
		break;
	case 'accountStatusReply':
		accountStatus(jsobj);
		break;
	case 'open':
		openAcc(jsobj);
		break;
	case 'close':
		closeAcc(jsobj);
		break;
	case 'p_1k_dep':
		p1kDep(jsobj);
		break;
	case 'p_1k_with':
		p1kWith(jsobj);
		break;
	case 'p_1k_trans':
		p1kTrans(jsobj);
		break;
	case 'p_6_acc':
		p6Acc(jsobj);
		break;
	case 'newRequest':
		newReq(jsobj);
		break;
	case 'error':
		errorRes(jsobj);
		break;
	}

}

function onError(evt) {
	alert(evt.data);
}