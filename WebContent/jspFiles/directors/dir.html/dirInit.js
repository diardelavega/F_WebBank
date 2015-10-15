
var host=window.location.hostname;
var ws = new WebSocket("ws://"+host+":8080/F_WebBank/dir");

function newWs() {
	if (ws !== undefined && ws.readyState !== ws.CLOSED) {
		msgPost.innerHTML = "WebSocket is already opened.";
		return;
	}
	ws = new WebSocket("ws://"+host+":8080/F_WebBank/dir");
}

ws.onmessage = function(evt) {
	onMessage(evt);
};

ws.onerror = function(evt) {
	onError(evt);
};

ws.onopen = function(evt) {
	setTimeout(function() {
		dirSeverClientTupeling();
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

	// console.log(jsobj.head);
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
		balanceHandler(jsobj);
		break;
	case 'logoutReplay':
		logOutReplay(jsobj);
		break;
	case 'error':
		errorRes(jsobj);
		break;
	}
}

function onError(evt) {
	console.log(window.location.href);
	console.log("On Error");
//	alert(window.location.href);
//	alert(window.location.hostname);
//	alert(evt.data);
//	alert(evt.text);
}

function hideShow(chosen) {
	$('.hidable').each(function(index) {
		if ($(this).attr("id") === chosen) {
			$(this).show();
			// $(this).attr("style", "visibility: visible");
		} else {
			// $(this).attr("style", "visibility: collapse");
			$(this).hide();
		}
	});
}
function divhide() {
	hideShow('empDir');
	// $('.hidable').attr("style", "visibility: visible");
}
