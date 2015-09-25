function manGetRequest() {
	var getter = {
		head : "getRequest",
		empId : window.manEmpId
	};
	doSend(JSON.stringify(getter));
}

function manLeaveRequest() {
	var leaver = {
		head : "leaveRequest",
		empId : window.manEmpId
	};
	doSend(JSON.stringify(leaver));

}

function approve() {
	var res = {
		head : "approve",
		note : $("#manReqNote").val(),
		empId : window.manEmpId
	}
	doSend(JSON.stringify(res));
}

function denie() {
	var res = {
		head : "dennie",
		note : $("#manReqNote").val(),
		empId : window.manEmpId
	}
	doSend(JSON.stringify(res));
}

/* AFTER RESPONSE FUNCTIONS */

function requestRequestReply(jsobj) {
	console.log(jsobj);
	if (jsobj.hasOwnProperty('requestDetails')) {
		var request = jsobj.requestDetails;

		var reqType = request['reqType'];
		manWriteAllMsgs(request);
	} else {
		reqAlertDelegate(jsobj.msg);
	}
}

function reqAlertDelegate(msg) {
	// call msg alert for request page alerts
	var d = $("#manMsgAlertReq");
	manMainAlertDisplay(msg, "info", d);
}

function leaveRequest(jsobj) {
	// alert(jsobj.get('msg'));
	reqAlertDelegate(jsobj.msg);
}

function approveRequestReply(jsobj) {
	reqAlertDelegate(jsobj.msg);
}
function dennieRequestReply(jspbj) {
	reqAlertDelegate(jsobj.msg);
}

function manWriteAllMsgs(req) {

	var date = new Date();
	req['time'] = date.getHours() + ":" + date.getMinutes() + ":"
			+ date.getSeconds();

	var table = $("#allRequests");
	var tr = document.createElement("tr");
	// tr.setAttribute("class", "border_bottom");

	var tr1 = document.createElement("tr");
	var tr2 = document.createElement("tr");
	tr.appendChild(tr1);
	tr.appendChild(tr2);

	for ( var key in req) {
		if (key === 'amount')
			if (req[key] <= 0.5)
				continue;
		if (key === 'pin' || key === 'status')
			continue;

		var td1 = document.createElement("td");
		// td1.setAttribute("align", "center");
		td1.innerHTML = key;
		tr1.appendChild(td1);

		if (key === 'clientIdsList') {
			var sel = document.createElement("select");
			for (k in req[key]) {
				var opt = document.createElement("option");
				opt.innerHTML = req[key][k];
				sel.appendChild(opt);
			}

			var td2 = document.createElement("td");
			// td2.setAttribute("align", "center");
			td2.appendChild(sel);
			tr2.appendChild(td2);
			continue;
		}

		var td2 = document.createElement("td");
		// td2.setAttribute("align", "center");
		td2.innerHTML = req[key];
		tr2.appendChild(td2);
	}
	table.prepend(tr);
	// ----Append current request----
	var curTab = $("#curentRequest");
	$(curTab).empty();
	curTab.append(tr);
	// ------------------------------
}

function clearReviewedRequesrs(){
	$("#allRequests tbody").empty();
}