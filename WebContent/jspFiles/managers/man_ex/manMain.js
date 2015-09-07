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

function manAccountStatusReply(jsobj) {
	var trd = $("#manAccountStatus tbody tr");
	var trh = $("#manAccountStatus thead tr");
	trh.empty();
	trd.empty();
	if (jsobj.hasOwnProperty("Account")) {
		var acc = jsobj.Account;
		for ( var key in acc) {
			// -----------header creation
			var th = document.createElement('th');
			var t = document.createTextNode(key);
			th.appendChild(t);
			trh.append(th);
			// -----------body creation
			var td = document.createElement('td');
			var t = document.createTextNode(acc[key]); // Create a text node
			td.appendChild(t);
			trd.append(td);
			console.log(acc[key])
		}
		console.log(acc);
	} else if (jsobj.hasOwnProperty("msg")) {
		alert(jsobj.msg);
	}
}

function manAccountCooReply(jsobj) {
	var writeDiv = $("#manAccCoo")
	writeDiv.empty();
	if (jsobj.hasOwnProperty("ownersList")) {
		var owners = jsobj.ownList;
		for (var i = 0; i < owners.length; i++) {
			var p = document.createElement('p');
			var t = document.createTextNode(owners[i]);
			p.appendChild(t);
			writeDiv.append(p)
			console.log(owners[i])
		}
	} else if (jsobj.hasOwnProperty("msg")) {
		alert(jsobj.msg);
		console.log(jsobj.msg)
	}
}

function manClientAccounts(jsobj) {
	var tb = $("#manClientAccounts tbody");
	var trh = $("#manClientAccounts thead tr");
	trh.empty();
	tb.empty();
	if (jsobj.hasOwnProperty("accountsList")) {
		var accs = jsobj.accList;
		for (var i = 0; i < accs.length; i++) {
			var acc = accs[i];
			var tr = document.createElement('tr');
			for ( var key in acc) {
				// -----------header creation
				if (i == 0) {
					var th = document.createElement('th');
					var t = document.createTextNode(key);
					th.appendChild(t);
					trh.append(th);
				}
				// -----------body creation
				var td = document.createElement('td');
				var t = document.createTextNode(acc[key]);
				td.appendChild(t);
				tr.appendChild(td);
				tb.append(tr);
			}
			console.log(acc)
		}
	} else {
		alert(jsobj.msg)
	}
}

function manAccountTrans(jsobj) {
	var tbody = $("#manAccTrans tbody");
	var trh = $("#manAccTrans thead tr");
	trh.empty();
	trd.empty();
	if (jsobj.hasOwnProperty('accountTransList')) {
		var transList = jsobj.get('accountTransList');
		for (var i = 0; i < transList.length; i++) {
			var tra = transList[i];
			var tr = document.createElement('tr');
			for ( var key in acc) {
				// ----head creation
				if (i == 0) {
					var th = document.createElement('th');
					var t = document.createTextNode(key);
					th.appendChild(t);
					trh.append(th);
				}
				// -----------body creation
				var td = document.createElement('td');
				var t = document.createTextNode(acc[key]);
				td.appendChild(t);
				tr.appendChild(td);
				tbody.append(tr);
			}
			console.log(acc)
		}
	} else if (jsobj.hasOwnProperty("msg")) {
		alert(jsobj.msg);
	}

}

function manClientTrans(jsobj) {
	var tbody = $("#manClientTrans tbody");
	var trh = $("#manClientTrans thead tr");
	trh.empty();
	trd.empty();
	if (jsobj.hasOwnProperty('clientTransList')) {
		var transList = jsobj.get('clientTransList');
		for (var i = 0; i < transList.length; i++) {
			var tra = transList[i];
			var tr = document.createElement('tr');
			for ( var key in acc) {
				// ----head creation
				if (i == 0) {
					var th = document.createElement('th');
					var t = document.createTextNode(key);
					th.appendChild(t);
					trh.append(th);
				}
				// -----------body creation
				var td = document.createElement('td');
				var t = document.createTextNode(acc[key]);
				td.appendChild(t);
				tr.appendChild(td);
				tbody.append(tr);
			}
			console.log(acc)
		}
	} else if (jsobj.hasOwnProperty("msg")) {
		alert(jsobj.msg);
	}

}

function manManyClientTrans(jsobj) {
}



