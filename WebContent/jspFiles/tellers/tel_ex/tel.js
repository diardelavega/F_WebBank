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

function registerClient() {

}

/* AFTER RESPONSE FUNCTION */
function clientAccounts(jsobj) {
	if (jsobj.hasOwnProperty("accList")) {
		var accs = jsobj.accList;
		for (var i = 0; i < accs.length; i++) {
			console.log(accs[i])
		}
	}
}

function accountStatus(jsobj) {
	if (jsobj.hasOwnProperty("Account")) {
		var acc = jsobj.Account;
		var tr = $("#telAccountStatus  tr");
		tr.empty();
		for ( var key in acc) {
			var td = document.createElement('td');
			var t = document.createTextNode(acc[key]); // Create a text node
			td.appendChild(t);
			tr.append(td);
			console.log(acc[key])
		}
		console.log(acc);
	}
}
function clearAccountStatus(){
	var tr = $("#telAccountStatus  tr");
	tr.empty();
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