function telAccountStatus() {
	var accNr = $("#telAccountNr").val();
	var req = {
		head : "accountStatus",
		accuntNr : accNr
	};
	doSend(JSON.stringify(req));
}

function telAccountCoo() {
	var accNr = $("#telAccountNr").val();
	var req = {
		head : "accountCoowners",
		accuntNr : accNr
	};
	doSend(JSON.stringify(req));
}

function telClientAccounts() {
	var persId = $("#telPersonalId").val();
	var req = {
		head : "clientAccounts",
		personalId : persId
	};
	doSend(JSON.stringify(req));
}

/* AFTER RESPONSE FUNCTION */
function clientAccounts(jsobj) {
	if (jsobj.hasOwnProperty("accList")) {
		var accs = jsobj.accList;
		for (var i = 0; i < accs.length; i++) {
			console.log(acc[i])
		}
	}
}

function accountStatus(jsobj) {
//	if (jsobj.hasOwnProperty("Account")) {
		var acc = jsobj.Account;
		console.log(acc);
		console.log(jsobj.acc1);
		console.log(jsobj.acc2);
		console.log(jsobj.acc3);
		console.log(jsobj.acc4);
		console.log(jsobj.acc5);
//	} else if (jsobj.hasOwnProperty("msg")) {
//		console.log(jsobj.msg)
//	}
}

function accountCoowners(jsobj) {
	if (jsobj.hasOwnProperty("ownList")) {
		var owners = jsobj.ownList;
		for (var i = 0; i < owners.length; i++) {
			console.log(owners[i])
		}
	} else if (jsobj.hasOwnProperty("msg")) {
		console.log(jsobj.msg)
	}
}

function errorRes(jsobj) {
	console.log(jsobj.msg)
}

/* AFTER RESPONSE FUNCTION */