function telSeverClientTupeling() {
	// alert("AAAAA");
	var reg = {
		head : "coordinate",
		empId : telId
	}
	doSend(JSON.stringify(reg));
}

function clear1() {
	// $('[name=id1]').prop('disabled', true);
	$('[name=fname1]').val("");
	$('[name=lname1]').val("");
	$('[name=bdate]').val("");
	$('[name=address1]').val("");
	$('[name=phone]').val("");
	$('[name=email1]').val("");
	$('[name=psw1]').val("");
	$('[name=id]').val("");
}

function telAccountStatus() {
	var accNr = $("#telAccountNr").val();
	var req = {
		head : "accountStatus",
		empId : telId,
		accuntNr : accNr
	};
	doSend(JSON.stringify(req));
}

function telAccountCoo() {
	var accNr = $("#telAccountNr").val();
	var req = {
		head : "accountCoowners",
		empId : telId,
		accuntNr : accNr
	};
	doSend(JSON.stringify(req));
}

function telClientAccounts() {
	var persId = $("#telPersonalId").val();
	var req = {
		head : "clientAccounts",
		empId : telId,
		personalId : persId
	};
	doSend(JSON.stringify(req));
}

function deleteClient() {
	clientData = {
		head : 'deleteClient',
		empId : telId,
		id : $('[name=id]').val()
	}
	doSend(JSON.stringify(clientData));
}

function newClientReg() {
	clientData = {
		head : 'newClientReg',
		empId : telId,
		id : $('[name=id]').val(),
		fname : $('[name=fname1]').val(),
		lname : $('[name=lname1]').val(),
		bdate : $('[name=bdate]').val(),
		address : $('[name=address1]').val(),
		phone : $('[name=phone]').val(),
		eMail : $('[name=email1]').val(),
		password : $('[name=psw1]').val()
	}
	doSend(JSON.stringify(clientData));
}

function alterClient() {
	if ($('[name=id]').val() != window.initialPersId) {
		ret = mainAlertDisplay("ID Can Not Be Altere", "warning");
		fadeOut(ret);
	} else {
		clientData = {
			head : 'alterClient',
			empId : telId,
			id : $('[name=id]').val(),
			fname : $('[name=fname1]').val(),
			lname : $('[name=lname1]').val(),
			bdate : $('[name=bdate]').val(),
			address : $('[name=address1]').val(),
			phone : $('[name=phone]').val(),
			eMail : $('[name=email1]').val(),
			password : $('[name=psw1]').val()
		}
		doSend(JSON.stringify(clientData));
	}
}

// -----------TRANSACTIONS

// -----------END OFTRANSACTIONS

function clientIdDataSearch() {
	var persId = $("#telPersonalId").val();
	if (persId != '') {
		clientDataSearch = {
			head : 'search',
			empId : telId,
			id : persId
		}
		doSend(JSON.stringify(clientDataSearch));
	}
}

function clientFormDataSearch() {
	clientDataSearch = {
		head : 'search',
		empId : telId,
		id : $('[name=id]').val(),
		fname : $('[name=fname1]').val(),
		lname : $('[name=lname1]').val(),
		address : $('[name=address1]').val(),
		phone : $('[name=phone]').val(),
		eMail : $('[name=email1]').val(),
		password : $('[name=psw1]').val()
	}
	doSend(JSON.stringify(clientDataSearch));
}

/* AFTER RESPONSE FUNCTION */

function registerClientReply(jobj) {
	var div = $("#telMainMsgAlert");
	if (jobj.hasOwnProperty("response")) {
		ret = mainAlertDisplay(jobj.response, "info");
		fadeOut(ret);
	} else {
		ret = mainAlertDisplay(jobj.msg, "warning");
		fadeOut(ret);
	}
}

function alterClientReply(jsobj) {
	console.log(jsobj);
	if (jsobj.hasOwnProperty("response")) {
		ret = mainAlertDisplay(jsobj.response, "info");
		fadeOut(ret);
	} else {
		ret = mainAlertDisplay(jsobj.msg, "warning");
		fadeOut(ret);
	}
}

function deleteClientReply(jsobj) {
	if (jsobj.hasOwnProperty("response")) {
		ret = mainAlertDisplay(jsobj.response, "info");
		fadeOut(ret);
	} else {
		ret = mainAlertDisplay(jsobj.msg, "warning");
		fadeOut(ret);
	}
}
// ---------------

function searchReply(jsobj) {
	// display the client search results in a table
	console.log(jsobj);
	if (jsobj.hasOwnProperty("customerList")) {
		var custList = jsobj.customerList;

		var tableHead = $('#telClientTableList thead');
		var tablebody = $('#telClientTableList tbody');
		tableHead.empty();
		tablebody.empty();

		for (var i = 0; i < custList.length; i++) {
			var cust = custList[i];
			var tr = document.createElement('tr');
			tr.onclick = function(event) {
				retriveTrContent(event);
			}
			for ( var key in cust) {
				// -----------header creation
				if (i == 0) {
					var th = document.createElement('th');
					// th.setAttribute("align", "center");
					if (key === 'customerStatus') {
						var t = document.createTextNode("cStastus");
					} else {
						var t = document.createTextNode(key);
					}
					th.appendChild(t);
					tableHead.append(th);
				}
				// -----------body creation
				var td = document.createElement('td');
				if (key === 'bdata') {
					var bd = new Date(cust[key]);
					var t = document.createTextNode(bd.getDate() + "/"
							+ (bd.getMonth() + 1) + "/" + bd.getFullYear());
				} else {
					var t = document.createTextNode(cust[key]);
				}

				if (key === 'personalId') {
					var a = document.createElement('a');
					$(a).attr("href", "#");
					a.onclick = function(event) {
						onPersId(event);
					};
					a.appendChild(t);
					td.appendChild(a);
					tr.appendChild(td);
					tablebody.append(tr);
				} else {
					td.appendChild(t);
					tr.appendChild(td);
					tablebody.append(tr);
				}
			}// for key
		}// for i
	} else {
		ret = mainAlertDisplay(jsobj.msg, "warning");
		fadeOut(ret);
	}
}

function retriveTrContent(event) {

	var tds = $(event.target).parent().find('td');
	$('[name=fname1]').val(tds.eq(3).text());
	$('[name=lname1]').val(tds.eq(4).text());
	$('[name=bdate]').val(tds.eq(5).text());
	$('[name=address1]').val(tds.eq(6).text());
	$('[name=phone]').val(tds.eq(7).text());
	$('[name=email1]').val(tds.eq(2).text());
	$('[name=psw1]').val(tds.eq(8).text());
	$('[name=id]').val(tds.eq(0).text());
	window.initialPersId = tds.eq(0).text();
}

function clientAccounts(jsobj) {
	console.log(jsobj);
	var tb = $("#telClientAccounts tbody");
	var trh = $("#telClientAccounts thead tr");
	trh.empty();
	tb.empty();
	if (jsobj.hasOwnProperty("accList")) {
		var accs = jsobj.accList;
		for (var i = 0; i < accs.length; i++) {
			var acc = accs[i];
			var tr = document.createElement('tr');
			for ( var key in acc) {
				if (key === 'customers')
					continue;
				// }
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
				if (key === 'accountId') {
					var a = document.createElement('a');
					$(a).attr("href", "#");
					a.onclick = function(event) {
						onAccId(event);
					};
					a.appendChild(t);
					td.appendChild(a);
					tr.appendChild(td);
					tb.append(tr);
				} else {

					td.appendChild(t);
					tr.appendChild(td);
					tb.append(tr);
				}
			}
			console.log(acc)
		}
	} else {
		ret = mainAlertDisplay(jsobj.msg, "warning");
		fadeOut(ret);
	}
}

function accountStatus(jsobj) {
	var trd = $("#telAccountStatus tbody tr");
	var trh = $("#telAccountStatus thead tr");
	trh.empty();
	trd.empty();
	if (jsobj.hasOwnProperty("Account")) {
		var acc = jsobj.Account;
		for ( var key in acc) {
			if (key === 'customers')
				continue;
			// } else {
			// -----------header creation
			var th = document.createElement('th');
			var t = document.createTextNode(key);
			th.appendChild(t);
			trh.append(th);
			// -----------body creation
			var td = document.createElement('td');
			var t = document.createTextNode(acc[key]);
			td.appendChild(t);
			trd.append(td);
			// }
			console.log(acc[key])
		}
		console.log(acc);
	} else if (jsobj.hasOwnProperty("msg")) {
		mainAlertDisplay(jsobj.msg);
	}
}

function accountCoowners(jsobj) {
	var writeDiv = $("#telCoowner")
	writeDiv.empty();
	if (jsobj.hasOwnProperty("ownList")) {
		var owners = jsobj.ownList;
		for (var i = 0; i < owners.length; i++) {
			var p = document.createElement('p');
			for (var j = 0; j < owners[i].length; j++) {
				console.log(owners[i][j]);
				if (j == 0) {
					var a = document.createElement("a");
					$(a).attr("href", "#");
					a.onclick = function(event) {
						onPersId(event);
					};
					a.innerHTML = owners[i][j];
					$(p).append(a);
					$(p).append(", ");
				} else if (j == 3) {
					var bd = new Date(owners[i][j]);
					var t = document.createTextNode(bd.getDate() + "/"
							+ (bd.getMonth() + 1) + "/" + bd.getFullYear()
							+ ", ");
					p.appendChild(t);
				} else {
					$(p).append(owners[i][j] + ", ");
				}
				writeDiv.append(p)
			}
		}
	} else if (jsobj.hasOwnProperty("msg")) {
		mainAlertDisplay(jsobj.msg);
	}
}

function accCoownHeader() {
	trh.empty();
	var trh = $("#telAccCoowner thead tr");
	var th = document.createElement('th');
	var t = document.createTextNode(key);
	th.appendChild(t);
	trh.append(th);
}

function errorRes(jsobj) {
	ret = mainAlertDisplay(jsobj.msg, "warning");
	fadeOut(ret);
}

/* END AFTER RESPONSE FUNCTION */

// --------------FUNCTIONALITY
function onPersId(event) {
	var txt = $(event.target).text();
	$("#telPersonalId").val(txt);
}

function onAccId(event) {
	var txt = $(event.target).text();
	$("#telAccountNr").val(txt);
}

var telTimeOut;
function mainAlertDisplay(msg, alertType) {
	var div = $("#telMainMsgAlert");
	clearTimeout(telTimeOut);
	div.empty();
	if (alertType === 'info') {
		$(div).addClass("alert alert-info");
	} else {
		$(div).addClass("alert alert-danger");
	}

	var span = document.createElement("span");
	$(span).attr("align", "left");
	span.style.padding = "230px";

	var a = document.createElement("a");
	a.innerHTML = "&times;";
	$(a).attr("href", "#");
	$(a).attr("onclick", "closeThis(event)");
	var br = document.createElement("br");

	span.appendChild(a)
	div.append(span);
	div.append(br);
	div.append(msg);

	$(div).show();
	 fadeOut(div);
	return div;
}

function fadeOut(div) {
	telTimeOut = setTimeout(function() {
		$(div).hide(1000);
	}, 5000);
}

function closeThis(event) {
	var div = $(event.target).parent().parent();
	$(div).hide(1000);
}
