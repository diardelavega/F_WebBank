function manGetRequest() {
	var getter = {
		head : "getRequest",
		empId : $("#manEmpId").val()
	};
	doSend(JSON.stringify(getter));
}

function manLeaveRequest() {
	var leaver = {
		head : "leaveRequest",
		empId : $("#manEmpId").val()
	};
	doSend(JSON.stringify(getter));

}

/* AFTER RESPONSE FUNCTIONS */
function requestRequestReply(jsobj) {
	console.log(jsobj);
	var request = jsobj.requestDetails;

	var reqType = request['reqType'];
	manWriteAllMsgs(request);
	console.log(reqType);
	console.log(request);

	// var clients = request['clientIdsList'];
	// var accType = request['accType']
	// var accFrom = request['accFromNr']
	// var accTo = request['accToNr']
	// var amount = request['amount']
	// console.log(request);
	// console.log(clients);
	// console.log(accFrom);
}

function leaveRequest(jsobj) {
	alert(jsobj.get('msg'));
}

function manWriteAllMsgs(req) {
	// go to the page where all the last responses of the requests that require
	// confirmation are
	// if (jsobj.hasOwnProperty("ocr")) {
	// var req = jsobj.ocr;
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
}