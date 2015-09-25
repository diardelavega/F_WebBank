function manAccountStatus() {
	var accNr = $('#manAccountNr').val();
	var accStat = {
		head : "accountStatus",
		accountNr : accNr,
		empId : manEmpId
	}
	doSend(JSON.stringify(accStat));
}

function manAccountCoo() {
	var accNr = $('#manAccountNr').val();
	var cliAcc = {
		head : "accountCoowners",
		accountNr : accNr,
		empId : manEmpId
	}
	doSend(JSON.stringify(cliAcc));
}

function manClientAccounts() {
	var persId = $('#manPersonalId').val();
	var cliAcc = {
		head : "clientAccounts",
		personalId : persId,
		empId : manEmpId
	}
	doSend(JSON.stringify(cliAcc));
}

function manAccountTrans() {
	var accNr = $('#manAccountNr').val();
	var accTrans = {
		head : "accountTransactions",
		accountNr : accNr,
		empId : manEmpId
	}
	doSend(JSON.stringify(accTrans));
}

function manClientTrans() {
	var persId = $('#manPersonalId').val();
	var cliTrans = {
		head : "clientTransactions",
		personalId : persId,
		empId : manEmpId
	}
	doSend(JSON.stringify(cliTrans));
}

/* AFTER RESPONSE FUNCTIONS */

function manAccountStatusReply(jsobj) {
	console.log(acc);

	var trd = $("#manAccountStatus tbody tr");
	var trh = $("#manAccountStatus thead tr");
	trh.empty();
	trd.empty();
	if (jsobj.hasOwnProperty("account")) {
		var acc = jsobj.account;

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
		}
	} else if (jsobj.hasOwnProperty("msg")) {
		// alert(jsobj.msg);
		var d = $("#manMsgAlertMain");
		manMainAlertDisplay(jsobj.msg, "info", d);
	}
}

function manAccountCooReply(jsobj) {
	var writeDiv = $("#manAccCoo");
	writeDiv.empty();
	if (jsobj.hasOwnProperty("ownersList")) {
		var owners = jsobj.ownersList;

		for (var i = 0; i < owners.length; i++) {
			var p = document.createElement('p');

			for (key in owners[i]) {
				if (key === 'personalId') {
					var a = document.createElement("a");
					$(a).attr("href", "#");
					a.onclick = function(event) {
						onPersId(event);
					};
					a.innerHTML = owners[i][key];
					$(p).append(a);
					$(p).append(", ");
				} else if (key === 'bdata') {
					var bd = new Date(owners[i][key]);
					var t = document.createTextNode(bd.getDate() + "/"
							+ (bd.getMonth() + 1) + "/" + bd.getFullYear()
							+ ", ");
					p.appendChild(t);
				} else {
					$(p).append(owners[i][key]);
					$(p).append(", ");
				}
			}
			writeDiv.append(p);
		}
	} else if (jsobj.hasOwnProperty("msg")) {
		// alert(jsobj.msg);
		var d = $("#manMsgAlertMain");
		manMainAlertDisplay(jsobj.msg, "info", d);
	}
}

function onPersId(event) {
	var txt = $(event.target).text();
	$("#manPersonalId").val(txt);
}

function onAccId(event) {
	var txt = $(event.target).text();
	$("#manAccountNr").val(txt);
}

function manClientAccountsReply(jsobj) {
	console.log(jsobj);

	var tb = $("#manClientAccounts tbody");
	var trh = $("#manClientAccounts thead tr");
	trh.empty();
	tb.empty();

	if (jsobj.hasOwnProperty("accountsList")) {
		var accs = jsobj.accountsList;
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
				if (key === 'accountId') {
					var a = document.createElement('a');
					$(a).attr("href", "#");
					a.onclick = function(event) {
						onAccId(event);
					};
					a.innerHTML = acc[key];
					$(td).append(a);
				} else {
					var t = document.createTextNode(acc[key]);
					td.appendChild(t);
				}
				tr.appendChild(td);
				tb.append(tr);
			}
		}
	} else {
		// alert(jsobj.msg)
		var d = $("#manMsgAlertMain");
		manMainAlertDisplay(jsobj.msg, "info", d);
	}
}

function manAccountTransRply(jsobj) {
	var tbody = $("#manAccTrans tbody");
	var trh = $("#manAccTrans thead tr");
	trh.empty();
	tbody.empty();
	if (jsobj.hasOwnProperty('accountTransList')) {
		var transList = jsobj.accountTransList;
		for (var i = 0; i < transList.length; i++) {
			var tra = transList[i];
			var tr = document.createElement('tr');
			for ( var key in tra) {
				// ----head creation
				if (i == 0) {
					var th = document.createElement('th');
					var t = document.createTextNode(key);
					th.appendChild(t);
					trh.append(th);
				}
				// -----------body creation
				var td = document.createElement('td');

				var txt;
				if (tra[key] === null) {
					txt = '-';
				} else {
					txt = tra[key];
				}

				if (key === 'clientId') {
					var aa = document.createElement("a");
					$(aa).attr("href", "#");
					aa.onclick = function(event) {
						onPersId(event);
					};
					aa.innerHTML = txt;
					$(td).append(aa);
				} else if (key === 'account1') {
					var aa = document.createElement("a");
					$(aa).attr("href", "#");
					aa.onclick = function(event) {
						onAccId(event);
					};
					aa.innerHTML = txt;
					$(td).append(aa);
				} else if (key === 'account2') {
					var aa = document.createElement("a");
					$(aa).attr("href", "#");
					aa.onclick = function(event) {
						onAccId(event);
					};
					aa.innerHTML = txt;
					$(td).append(aa);
				} else {
					var t = document.createTextNode(tra[key]);
					td.appendChild(t);
				}
				tr.appendChild(td);
				tbody.append(tr);
			}
		}
	} else if (jsobj.hasOwnProperty("msg")) {
		// alert(jsobj.msg);
		var d = $("#manMsgAlertMain");
		manMainAlertDisplay(jsobj.msg, "info", d);
	}

}

function manClientTransReply(jsobj) {
	var tbody = $("#manClientTrans tbody");
	var trh = $("#manClientTrans thead tr");
	trh.empty();
	tbody.empty();
	if (jsobj.hasOwnProperty('clientTransList')) {
		var transList = jsobj.clientTransList;
		for (var i = 0; i < transList.length; i++) {
			var tra = transList[i];
			var tr = document.createElement('tr');
			for ( var key in tra) {
				// ----head creation
				if (i == 0) {
					var th = document.createElement('th');
					var t = document.createTextNode(key);
					th.appendChild(t);
					trh.append(th);
				}
				// -----------body creation
				var td = document.createElement('td');
				var txt;
				if (tra[key] === null) {
					txt = '-';
				} else {
					txt = tra[key];
				}

				if (key === 'clientId') {
					var aa = document.createElement("a");
					$(aa).attr("href", "#");
					aa.onclick = function(event) {
						onPersId(event);
					};
					aa.innerHTML = txt;
					$(td).append(aa);
				} else if (key === 'account1') {
					var aa = document.createElement("a");
					$(aa).attr("href", "#");
					aa.onclick = function(event) {
						onAccId(event);
					};
					aa.innerHTML = txt;
					$(td).append(aa);
				} else if (key === 'account2') {
					var aa = document.createElement("a");
					$(aa).attr("href", "#");
					aa.onclick = function(event) {
						onAccId(event);
					};
					aa.innerHTML = txt;
					$(td).append(aa);
				} else {
					var t = document.createTextNode(tra[key]);
					td.appendChild(t);
				}
				tr.appendChild(td);
				tbody.append(tr);
			}
		}
	} else if (jsobj.hasOwnProperty("msg")) {
		// alert(jsobj.msg);
		var d = $("#manMsgAlertMain");
		manMainAlertDisplay(jsobj.msg, "info", d);
	}

}

function mainManAlertDelegate() {
	var d = $("#manMsgAlertMain");
	manMainAlertDisplay(msg, "info", d);
}

// -------functional Help

