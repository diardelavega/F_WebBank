function telAccountStatus() {
	var accNr = $("#telAccountNr").val();
	var req = {
		head : "accStats",
		accuntNr : accNr
	};
	doSend(JSON.Stringify(req));
}


function telAccountCoo() {
	var accNr = $("#telAccountNr").val();
	var req = {
		head : "accCoo",
		accuntNr : accNr
	};
	doSend(JSON.Stringify(req));
}

function telClientAccounts() {
	var persId = $("#telPersonalId").val();
	var req = {
		head : "cliAcc",
		personalId : persId
	};
	doSend(JSON.Stringify(req));
}