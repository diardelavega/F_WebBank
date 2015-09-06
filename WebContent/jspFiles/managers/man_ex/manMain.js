function manAccountStatus() {
	var accNr = $('#manAccountNr').val();
	var accStat = {
		head : "accountStatus",
		accountNr : accNr,
		empId : $('#manEmpId').val()
	}
	doSend(JSON.stringify(accStat));
}

function manAccountCoo() {
	var accNr = $('#manAccountNr').val();
	var cliAcc = {
		head : "accountCoowners",
		accountNr : accNr,
		empId : $('#manEmpId').val()
	}
	doSend(JSON.stringify(cliAcc));
}

function manClientAccounts() {
	var persId = $('#manPersonalId').val();
	var cliAcc = {
		head : "clientAccounts",
		personalId : persId,
		empId : $('#manEmpId').val()
	}
	doSend(JSON.stringify(cliAcc));
}

function manAccountTrans() {
	var accNr = $('#manAccountNr').val();
	var accTrans = {
		head : "accountTransactions",
		accountNr : accNr,
		empId : $('#manEmpId').val()
	}
	doSend(JSON.stringify(accTrans));
}

function manClientTrans() {
	var persId = $('#manPersonalId').val();
	var cliTrans = {
		head : "clientTransactions",
		personalId : persId,
		empId : $('#manEmpId').val()
	}
	doSend(JSON.stringify(cliTrans));
}

/* AFTER RESPONSE FUNCTIONS */

function manAccountStatusReply() {
}

function manAccountCooReply() {
}

function manClientAccounts() {
}

function manAccountTrans() {
}

function manClientTrans() {
}
