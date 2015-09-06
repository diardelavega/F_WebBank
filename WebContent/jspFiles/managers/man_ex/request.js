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
function requestArival(jsobj) {
	var request = jsobj.requestData;
	var reqType = request['reqType'];
	var clients = request['clientIdsList'];

	var accType = request['accType']
	var accFrom = request['accFromNr']
	var accTo = request['accToNr']
	var amount = request['amount']
	console.log(request);
	console.log(clients);
	console.log(accFrom);

}