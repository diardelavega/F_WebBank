var host=window.location.hostname;

var ws = new WebSocket("ws://"+host+":8080/F_WebBank/cli");

function newWs() {
	if (ws !== undefined && ws.readyState !== ws.CLOSED) {
		msgPost.innerHTML = "WebSocket is already opened.";
		return;
	}
	ws = new WebSocket("ws://"+host+":8080/F_WebBank/cli");
}

ws.onmessage = function(evt) {
	onMessage(evt);
};
ws.onerror = function(evt) {
	onError(evt);
};

ws.onopen = function(evt) {
	setTimeout(function() {
		cliSeverClientTupeling();
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

	case 'transactionReply':
		cliTransactionReply(jsobj);
		break;
	case 'balanceReply':
		cliBalanceReply(jsobj);
		break;
	case 'transferReply':
		cliTransferReply(jsobj);
		break;
	case 'accountsReply':
		cliAccountsReply(jsobj);
		break;
	case 'accountsPushed':
		cliAccountsPushed(jsobj);
		break;
	case 'logoutReplay':
		logOutReplay(jsobj);
		break;

	case 'default':
		console.log("NoHeader");
	}

}

function onError(evt) {
	console.log(window.location.href);
	console.log("On Error");
//	alert(window.location.hostname);
//	alert(evt.data);
//	alert(evt.text);
}

function cliHideShow(chosen) {
	$('.hidable').each(function(index) {
		if ($(this).attr("id") === chosen) {
			$(this).show();
		} else {
			$(this).hide();
		}
	});
}
function divhide() {
	cliHideShow('accCli');
}
