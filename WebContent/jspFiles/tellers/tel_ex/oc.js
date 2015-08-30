function openAccount() {
	var persIdList = {
		head : "openAccount",
		pId1 : $("#telOpen1 input").val(),
		pId2 : $("#telOpen2 input").val(),
		pId3 : $("#telOpen3 input").val(),
		pId4 : $("#telOpen4 input").val()
	}
	doSend(JSON.stringify(persIdList));

}
function openAccCheck() {
	var inputs, index, counter = 0;
	inputs = $("#telOpen input");
	for (index = 0; index < inputs.length; ++index) {
		if (inputs[index].val === "") {
			counter++;
		}
	}// for
	if (counter >= 4) {
		alert("NO PID PROVIDED");
	} else {
		openAccount();
	}
}


function closeAccount() {
}

function registerClient() {
}