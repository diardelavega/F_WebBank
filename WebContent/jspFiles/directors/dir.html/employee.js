function alterEmpRes(jsobj) {
	// todoconfirm the action
	//console.log(jsobj.newId);
	alert("All DONE" + jsobj.newId);
}

function deleteEmpRes(jsobj) {
	// todoconfirm the action
	//console.log(jsobj.newId);
	alert("All DONE" + jsobj.newId);
}

function newEmpRes(jsobj) {
	// todoconfirm the action
	//console.log(jsobj.newId);
	alert("All DONE" + jsobj.newId);
}

function errorRes(jsobj) {
	// todoconfirm the action
	//console.log(jsobj.msg);
	alert("Error MSG  " + jsobj.msg);
}

function empInfoHandler(jsobj) {
	// initiate and create the table
	if (jsobj.hasOwnProperty("employees")) {
		deleteContent();
		var emps = jsobj.employees;
		// console.log("-----: ");
		for (var i = 0; i < emps.length; i++) {
			var line = emps[i];
			empTableTr(i);
			for ( var key in line) {
				if (i == 0) {
					empTableListHead(key);
					// empTableListBody(line[key]);
				}
				empTableListBody(line[key]);
//				console.log(i + " --- " + key + " --- " + line[key]);
			}
		}
	}
}

function empTableListHead(heads) {
	// create the table header based on the json that is received
	var trhead = $("#empTableList").find("thead tr");
	var th = document.createElement('th');
	var t = document.createTextNode(heads); // Create a text node
	th.appendChild(t);
	trhead.append(th);
}

function empTableListBody(vals) {
	// fill the last row <tr> of the table with cells <td>
	var tbody = $("#empTableList").find("tbody tr").last();

	var td = document.createElement('td');
	var t = document.createTextNode(vals); // Create a text node
	td.appendChild(t);
	tbody.append(td);
}

function empTableTr(i) {
	// append a new row <tr> at the end of the table
	var tbody = $("#empTableList").find("tbody");
	var tr = document.createElement('tr');
	tr.className = "toFillForm";
	tr.id = i;

	tr.onclick = function(event) {
		/* event.target get the element that started the event */
		// var tds = $(event.target).parent().find('td');
		retriveTrContent(event);
	}
	tbody.append(tr);
}

function retriveTrContent(event) {
	var tds = $(event.target).parent().find('td');
	$('[name=id2]').val(tds.eq(0).text());
	$('[name=fname2]').val(tds.eq(1).text());
	$('[name=lname2]').val(tds.eq(2).text());
	$('[name=address2]').val(tds.eq(3).text());
	$('[name=possition2]').val(tds.eq(4).text());
	$('[name=email2]').val(tds.eq(5).text());
	$('[name=psw2]').val(tds.eq(6).text());

	$('#delbut').attr("style", "visibility: visible");
	$('#altbut').attr("style", "visibility: visible");

}

function deleteContent() {
	// delete the content of the table body and head

	// var trhead = $("#empTableList").find("thead tr");
	$("#empTableList").find("thead tr").empty();
	// delete all header children "th"

	// var tbody = $("#empTableList").find("tbody");
	$("#empTableList").find("tbody").empty();
	// delete all header children "tr"
}

function clear2() {
	$('[name=id2]').val("");
	$('[name=fname2]').val("");
	$('[name=lname2]').val("");
	$('[name=address2]').val("");
	$('[name=possition2]').val("");
	$('[name=email2]').val("");
	$('[name=psw2]').val("");
}

function clear1() {
	// $('[name=id1]').prop('disabled', true);
	$('[name=fname1]').val("");
	$('[name=lname1]').val("");
	$('[name=address1]').val("");
	$('[name=possition1]').val("");
	$('[name=email1]').val("");
	$('[name=psw1]').val("");
}


function alterEmployee() {
	var emp = getEmpData("alter");
	doSend(JSON.stringify(emp));
	clear2();
}
function deleteEmployee() {
	var emp = getEmpData("delete");
	doSend(JSON.stringify(emp));
	clear2();
}



function newEmployee() {
	var emp = {
		head : "new",
		// dirId: '${sessionScope.empId}',
		id : $('[name=id1]').val(),
		fname : $('[name=fname1]').val(),
		lname : $('[name=lname1]').val(),
		address : $('[name=address1]').val(),
		possition : $('[name=possition1]').val(),
		eMail : $('[name=email1]').val(),
		password : $('[name=psw1]').val(),
	};
	doSend(JSON.stringify(emp));
	clear2();
}

function getEmpInfo() {
	// alert('emp infoing!!');
	var emp = getEmpData("info");
	doSend(JSON.stringify(emp));
}

function getEmpData(headVal) {
	var emp = {
		head : headVal,
		// dirId: '${sessionScope.empId}',
		id : $('[name=id2]').val(),
		fname : $('[name=fname2]').val(),
		lname : $('[name=lname2]').val(),
		address : $('[name=address2]').val(),
		possition : $('[name=possition2]').val(),
		eMail : $('[name=email2]').val(),
		password : $('[name=psw2]').val()
	};
	return emp;
}
