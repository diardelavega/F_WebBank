function deposite() {
	var depData = {
		head : "deposite",
		accNr : $('[name=accNr]').val(),
		amount : $('[name=amount]').val(),
		note : $('[name=note]').val()
	}
	doSend(JSON.stringify(depData));
	// alert(depData);
	// console.log(depData);
}

function withdraw() {
	var withData = {
		head : "withdraw",
		accNr : $('[name=withAccNr]').val(),
		amount : $('[name=withAmount]').val(),
		persId : $('[name=withPersId]').val()
	}
	doSend(JSON.stringify(withData));
}

function tranfer() {
	var transfData = {
		head : "transfer",
		accFrom : $('[name=accNrFrom]').val(),
		accTo : $('[name=accNrTo]').val(),
		amount : $('[name=transfAmount]').val(),
		persId : $('[name=transfPersId]').val()
	}
	doSend(JSON.stringify(transfData));
}

function clearDep() {
	$('[name=accNr]').val("");
	$('[name=amount]').val("");
	$('[name=note]').val("");
}
function clearWithdraw() {
	$('[name=withAccNr]').val("");
	$('[name=withAmount]').val("");
	$('[name=withPersId]').val("");
}
function clearTransfer() {
	accFrom: $('[name=accNrFrom]').val("");
	accTo: $('[name=accNrTo]').val("");
	amount: $('[name=transfAmount]').val("");
	persId: $('[name=transfPersId]').val("");
}
/* AFTER RESPONSE */

function depositeRes(jsobj) {
	clonsole.log(jsobj.msg);
}
function withdrawRes(jsobj) {
	clonsole.log(jsobj.msg);
}
function transferRes(jsobj) {
	clonsole.log(jsobj.msg);
}

function errorRes(jsobj) {
	clonsole.log(jsobj.msg);
}