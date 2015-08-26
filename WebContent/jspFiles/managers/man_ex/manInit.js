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
//	 alert(evt.data);
//	 console.log("received: " + evt.data);
	var jsobj = JSON.parse(evt.data);

	console.log(jsobj.head);
	var head = jsobj.head;

	switch (head) {
	case 'newEmployee':
		newEmpRes(jsobj);
		break;
	case 'empList':
		empInfoHandler(jsobj);
		break;
	case 'deleteEmployee':
		deleteEmpRes(jsobj);
		break;
	case 'alterEmployee':
		alterEmpRes(jsobj);
		break;
	case 'dirTransaction':
		transactionHandler(jsobj);
		break;
	case 'dirBalance':
//		alert("BALANCEEEEE"+jsobj.balist)
//		console.log(jsobj.balist);
		balanceHandler(jsobj);
		break;
	case 'error':
		errorRes(jsobj);
		break;
	}
}

function onError(evt) {
	alert(evt.data);
}