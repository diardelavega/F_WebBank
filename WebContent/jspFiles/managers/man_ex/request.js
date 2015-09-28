function manGetRequest() {
	var getter = {
		head : "getRequest",
		empId : window.manEmpId
	};
	doSend(JSON.stringify(getter));
}

function manLeaveRequest() {
	if (relRequest == null || relRequest === 'undefined') {
		console.log("WTF is He Talking About, There Is No Request Here");
		reqAlertDelegate("There Is No Request Under Consideration");
	} else {
		var curTab = $("#curentRequest");
		$(curTab).empty();
		relRequest = null;

		var leaver = {
			head : "leaveRequest",
			empId : window.manEmpId
		};
		doSend(JSON.stringify(leaver));
	}
}

function approve() {
	if (relRequest == null || relRequest === 'undefined') {
		console.log("WTF is He Talking About, There Is No Request Here");
		reqAlertDelegate("There Is No Request Under Consideration");
	} else {
		writeReviewedRequests("app");
		relRequest = null;

		var res = {
			head : "approve",
			note : $("#manReqNote").val(),
			empId : window.manEmpId
		}
		doSend(JSON.stringify(res));
	}
}

function denie() {
	if (relRequest == null || relRequest === 'undefined') {
		console.log("WTF is He Talking About, There Is No Request Here");
		reqAlertDelegate("There Is No Request Under Consideration");
	} else {
		writeReviewedRequests("den");
		relRequest = null;

		var res = {
			head : "dennie",
			note : $("#manReqNote").val(),
			empId : window.manEmpId
		}
		doSend(JSON.stringify(res));
	}
}

/* AFTER RESPONSE FUNCTIONS */

function requestRequestReply(jsobj) {
	console.log(jsobj);
	if (jsobj.hasOwnProperty('requestDetails')) {
		var request = jsobj.requestDetails;
		manWriteAllMsgs(request);
		// keep requests that come from server side update
		relRequest = request;
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

function dennieRequestReply(jsobj) {
	reqAlertDelegate(jsobj.msg);
}

function manWriteAllMsgs(req) {
	// write the acquired request to an table
	console.log("TRYING TO WRITE THE CURENT REQ");

	var date = new Date();
	req['time'] = date.getHours() + ":" + date.getMinutes() + ":"
			+ date.getSeconds();

	/* var table = $("#allRequests tbody"); */
	var tr = document.createElement("tr");

	var tr1 = document.createElement("tr");
	var tr2 = document.createElement("tr");
	tr.appendChild(tr1);
	tr.appendChild(tr2);

	for ( var key in req) {
		if (key === null || key === "") {
			continue;
		}
		if (key === 'amount')
			if (req[key] <= 0.5)
				continue;
		if (key === 'pin' || key === 'status')
			continue;

		var td1 = document.createElement("td");
		td1.innerHTML = key;
		tr1.appendChild(td1);

		if (key === 'clientIdsList') {
			var div = document.createElement("div");
			$(div).attr("class", "dropdown");
			var but = document.createElement("button");
			$(but).attr("data-toggle", "dropdown");

			var spa = document.createElement("span");
			$(spa).attr("class", "caret");
			$(but).text("clientIds");
			$(but).append(spa);
			$(div).append(but);

			var ul = document.createElement("ul");
			$(ul).attr("class", "dropdown-menu");

			for (k in req[key]) {
				var li = document.createElement("li");
				li.onclick = function(event) {
					onPersId(event);
				};
				var a = document.createElement("a");
				$(a).attr("href", "#");
				$(a).text(req[key][k]);

				$(li).append(a);
				$(ul).append(li);
			}
			$(div).append(ul);

			var td2 = document.createElement("td");
			// td2.setAttribute("align", "center");
			$(td2).append(div);
			tr2.appendChild(td2);
			continue;
		}

		var td2 = document.createElement("td");
		// td2.setAttribute("align", "center");
		td2.innerHTML = req[key];
		tr2.appendChild(td2);
	}
	// table.prepend(tr);
	// ----Append current request----
	var curTab = $("#curentRequest");
	$(curTab).empty();
	curTab.append(tr);
	// ------------------------------
}

function onPersId(event) {
	var txt = $(event.target).text();
	$("#manPersonalId").val(txt);
}

function onAccId(event) {
	var txt = $(event.target).text();
	$("#manAccountNr").val(txt);
}

function clearReviewedRequesrs() {
	 $("#allRequests tbody").empty();
//	writeReviewedRequests("");
}

function writeReviewedRequests(way) {
	var rr = $("#reviewedRequests tbody");
	var curTabLine = $("#curentRequest tbody tr");
	if (way === 'den') {
		$(curTabLine).css("background-color", "rgb(240, 200, 200);");
	} else {
		$(curTabLine).css("background-color", "rgb(200, 240, 200);");
	}
	$(rr).prepend(curTabLine);
	// $(curTabLine).empty();
}