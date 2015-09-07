function openAccount() {
	var persIdList = {
		head : "openAccount",
		pId1 : $("[name=telOpenId1]").val(),
		pId2 : $("[name=telOpenId2]").val(),
		pId3 : $("[name=telOpenId3]").val(),
		pId4 : $("[name=telOpenId4]").val(),
		accType : $("[name=accType]").val()
	}
	doSend(JSON.stringify(persIdList));
	alert("a request was sent to the Manager for confirmation");
}
function clearOpenAcc() {
	$("[name=telOpenId1]").val("");
	$("[name=telOpenId2]").val("");
	$("[name=telOpenId3]").val("");
	$("[name=telOpenId4]").val("");
}

function tellCloseAcc() {
	var persIdList = {
		head : "closeAccount",
		pId1 : $("[name=telCloseId1]").val(),
		pId2 : $("[name=telCloseId2]").val(),
		pId3 : $("[name=telCloseId3]").val(),
		pId4 : $("[name=telCloseId4]").val(),
		accNr : $("[name=telCloseAcc]").val()
	}
	doSend(JSON.stringify(persIdList));
	alert("a request was sent to the Manager for confirmation");
}

function tellClearClose() {
	$("[name=telCloseId1]").val("");
	$("[name=telCloseId2]").val("");
	$("[name=telCloseId3]").val("");
	$("[name=telCloseId4]").val("");
	$("[name=telCloseAcc]").val("");
}

/* AFTER RESPONSE FUNCTIONS */
function openAccReply(jsobj) {
	console.log(jsobj);
}

function closeAccReply(jsobj) {
	console.log(jsobj);
}
