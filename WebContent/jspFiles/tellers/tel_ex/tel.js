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
	var reg = {
		head : "coordinate",
		empId : $("#telEmpId").val()
	}

	doSend(reg);
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

function searchReply(jsobj) {
	console.log(jsobj);
	if (jsobj.hasOwnProperty("customerList")) {
		var custList = jsobj.customerList;
		var tableHead = $('#telClientTableList thead');
		var tablebody = $('#telClientTableList tbody');

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
					var t = document.createTextNode(key);
					th.appendChild(t);
					tableHead.append(th);
				}
				// -----------body creation
				var td = document.createElement('td');
				var t = document.createTextNode(cust[key]); // Create a tex
				td.appendChild(t);
				tr.appendChild(td);
				tablebody.append(tr);
			}
		}
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

	// $('#delbut').attr("style", "visibility: visible");
	// $('#altbut').attr("style", "visibility: visible");

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
				var t = document.createTextNode(acc[key]); // Create a tex
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

/* END AFTER RESPONSE FUNCTION */