$(function() {
	$(".datePicker").datepicker({
		changeMonth : true,
		changeYear : true,
		dateFormat : 'dd/mm/yy',
	});
});

function severClientTupeling() {
	alert("TUPELING");
	var reg = {
		head : "coordinate",
		empId : $("#telEmpId").val()
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




function deleteClient() {
	clientData = {
		head : 'deleteClient',
		id : $('[name=id]').val()
	}
	doSend(JSON.stringify(clientData));
}
function newClientReg() {
	clientData = {
		head : 'newClientReg',
		// dirId : ${sessionScope.empId},
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
	clientData = {
		head : 'alterClient',
		// dirId : ${sessionScope.empId},
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

//-----------TRANSACTIONS
function deposite(){}

function withdraw(){}

function transfer(){}

//-----------END OFTRANSACTIONS



function clientIdDataSearch() {
	var persId = $("#telPersonalId").val();
	if (persId != '') {
		clientDataSearch = {
			head : 'search',
			// dirId : ${sessionScope.empId},
			id : persId
		}
		doSend(JSON.stringify(clientDataSearch));
	}
}

function clientFormDataSearch() {
	clientDataSearch = {
		head : 'search',
		// dirId : ${sessionScope.empId},
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
	if (jsobj.hasOwnProperty("response")) {
		alertDisplay(jobj.response);
	} else {
		alertDisplay(jobj.msg);
	}
}

function alterClientReply(jobj) {
	if (jsobj.hasOwnProperty("response")) {
		alertDisplay(jobj.response);
	} else {
		alertDisplay(jobj.msg);
	}
}

function deleteClientReply(jobj) {
	if (jsobj.hasOwnProperty("response")) {
		alertDisplay(jobj.response);
	} else {
		alertDisplay(jobj.msg);
	}
}
// ---------------

function searchReply(jsobj) {
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
					th.setAttribute("align", "center");
					var t = document.createTextNode(key);
					th.appendChild(t);
					tableHead.append(th);
				}
				// -----------body creation
				var td = document.createElement('td');
				// var t = document.createTextNode(cust[key]); // Create a tex

				if (key === 'bdata') {
					var bd = new Date(cust[key]);
					var t = document.createTextNode(bd.getDate() + "/"
							+ (bd.getMonth() + 1) + "/" + bd.getFullYear());
					// .toLocaleDateString('en-GB'));
				} else {
					var t = document.createTextNode(cust[key]);
				}

				if (key === 'personalId') {
					var a = document.createElement('a');
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
		alert(jsobj.msg);
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
}

function clientAccounts(jsobj) {
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

function accountStatus(jsobj) {
	var trd = $("#telAccountStatus tbody tr");
	var trh = $("#telAccountStatus thead tr");
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

function accountCoowners(jsobj) {
	var writeDiv = $("#telCoowner")
	writeDiv.empty();
	if (jsobj.hasOwnProperty("ownList")) {
		var owners = jsobj.ownList;
		;
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

function accCoownHeader() {
	trh.empty();
	var trh = $("#telAccCoowner thead tr");
	var th = document.createElement('th');
	var t = document.createTextNode(key);
	th.appendChild(t);
	trh.append(th);
}

function errorRes(jsobj) {
	// var alerter = $('#mainMsgAlert');
	// alerter.addClass("alerter");
	// $(alerter).addClass("alert alert-warning");
	// alerter.innerHTML = jsobj.msg;
	alertDisplay(jsobj.msg);
}

/* END AFTER RESPONSE FUNCTION */

function onPersId(event) {
	var txt = $(event.target).text();
	$("#telPersonalId").val(txt);
}

function alertDisplay(msg) {
	var alerter = $('#mainMsgAlert');
	alerter.classList.add("alert alert-info");
	alerter.innerHTML = msg;
}
