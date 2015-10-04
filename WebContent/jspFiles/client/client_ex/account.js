function cliTransfer() {
	var tra = {
		persId : persId,
		head : "transfer",
		accFrom : $('[name=accNrFrom]').val(),
		accTo : $('[name=accNrTo]').val(),
		amount : $('[name=transfAmount]').val(),
	// persId :persId
	// $('[name=transfPersId]').val()
	}
	doSend(JSON.stringify(tra));
}

function cliTransferReply(jsobj) {
	if (jsobj.hasOwnProperty("tra")) {
		dispalyMsg(jsobj.tra);
	} else {
		dispalyMsg(jsobj.msg);
	}
}

var accArr = [];
function fillAccArr(accounts) {
	for (var i = 0; i < accounts.length; i++) {
		accArr[i] = accounts[i];
	}
	console.log("accArr");
	console.log(accArr);
}

function cliAccountsPushed(jsobj) {
	// receives the updated accounts
	console.log("cliAccountsPushed");
	if (jsobj.hasOwnProperty("accs")) {
		accounts = jsobj.accs;
		fillAccArr(accounts);
		console.log(accounts);
		showAccounts(accounts);
	}
}

function cliAccountsReply(jsobj) {
	// receive accounts that were asked for
	console.log("cliAccountsReply");
	if (jsobj.hasOwnProperty("accounts")) {
		accounts = jsobj.accounts;
		fillAccArr(accounts);
		console.log(accounts);
		showAccounts(accounts);
	} else {
		console.log(jsobj.msg);
		dispalyMsg(jsobj.msg);
	}
}

// -------------Construct & Display
function cliClearTransfer() {
	accFrom: $('[name=accNrFrom]').val("");
	accTo: $('[name=accNrTo]').val("");
	amount: $('[name=transfAmount]').val("");
	persId: $('[name=transfPersId]').val("");
}

function showAccounts(accs) {
	console.log("UFFFF!!");
	var ulPill = $("#accountPills");
	$(ulPill).empty();
	var divPillCont = $("#accountPillContenet");

	for (var i = 0; i < accs.length; i++) {
		console.log("---------:" + i);
		/* dynamically create the pills, the header with the accounts */
		var li = document.createElement("li");
		var a = document.createElement("a");
		a.setAttribute("href", "#acc" + i);
		a.setAttribute("data-toggle", "pill");
		a.innerHTML = "<strong>Account" + (i + 1) + "<strong>";
		li.appendChild(a);
		ulPill.append(li);

		var div = document.createElement("div");
		div.setAttribute("id", "acc" + i);
		div.setAttribute("class", "tab-pane fade")

		/*
		 * create a big table inside the div, containing in one td another table
		 * with the account data and in the other td button with the allowed
		 * actions for the account
		 */
		var bigT = document.createElement("table");
		// bigT.setAttribute("class","bigSep");
		var btr = document.createElement("tr");
		var btd1 = document.createElement("td");
		var btd1_2 = document.createElement("td");
		var btd2 = document.createElement("td");

		$(btr).append(btd1);
		$(btr).append(btd1_2);
		$(btr).append(btd2);
		$(bigT).append(btr);

		var accIds;// keep the account Id
		var table = document.createElement("table");
		$(btd1).append(table);
		table.setAttribute("class", "accountDisply");
		for ( var key in accs[i]) {
			// iterating inside the account object
			var tr = document.createElement("tr");
			var td1 = document.createElement("td");
			var td1_2 = document.createElement("td");
			var td2 = document.createElement("td");

			// var accIds;// keep the account Id
			if (key == 'accountId') {
				var accIds = accs[i][key];
			}
			if (key == 'accType') {
				td1.innerHTML = "<strong>" + 'type' + "</strong>";
				var dx;
				switch (accs[i][key]) {
				case 'b':
					dx = 'BASICS_CHECKING';
					break;
				case 'i':
					dx = 'INTEREST_RATING';
					break;
				case 'c':
					dx = 'CERTIFICATE_OF_DEPSITE ';
					break;
				case 'm':
					dx = 'MONEY_MARKET_DEPOSITE';
					break;
				case 's':
					dx = 'SIMPLE_BUSINESS';
					break;
				}
				td2.innerHTML = "<big>" + dx + "</big>";
			} else {
				td1.innerHTML = "<strong>" + key + "</strong>";
				td2.innerHTML = "<big>" + accs[i][key] + "</big>";
			}
			td1_2.innerHTML = "<big>&nbsp;&nbsp;&nbsp;</big>";

			tr.appendChild(td1); // append key
			tr.appendChild(td1_2); // append space
			tr.appendChild(td2); // append value
			table.appendChild(tr);
		}

		// create the buttons
		var transactBut = document.createElement("button");
		transactBut.innerHTML = " <span class='glyphicon glyphicon-book'></span>  transactions ";
		transactBut.value = accIds;
		transactBut.onclick = function(event) {
			goToTransactions(event);
		};
		var valBut = document.createElement("button");
		valBut.innerHTML = " <span class='glyphicon glyphicon-stats'></span> balance ";
		valBut.value = accIds;
		valBut.onclick = function(event) {
			goToBalance(event);
		};
		var transBut = document.createElement("button");
		transBut.innerHTML = " <span class='glyphicon glyphicon-transfer'></span> transfer ";
		transBut.value = accIds;
		transBut.onclick = function(event) {
			goToTransfers(event);
		};

		var divr = document.createElement("div");
		$(divr).append(transactBut);
		var br = document.createElement("br");
		$(divr).append(br);
		var br = document.createElement("br");
		$(divr).append(br);
		$(divr).append(valBut);
		var br = document.createElement("br");
		$(divr).append(br);
		var br = document.createElement("br");
		$(divr).append(br);
		$(divr).append(transBut);

		btd1_2.innerHTML = "&nbsp;&nbsp;&nbsp;";
		$(btd2).append(divr);
		div.appendChild(bigT);
		divPillCont.append(div);
	}
}
// -------helpful functions

$(function() {
	var amts = $(".amount").focusout(function() {
		amountCtrl(this);
	});
});

function setTransferPersId(accFrom) {
	console.log("setTransferPersId");
	var ctpi = $("#cliTransPersId");
	$(ctpi).val(persId);
	// $(ctpi).prop("pleaceholder", persId);
	$(ctpi).prop("readonly", true);

	var ctaf = $("#cliTransAccFrom");
	$(ctaf).val(accFrom);
	$(ctaf).prop("readonly", true);

}

function goToTransfers(event) {
	console.log("Transfers " + event);
	console.log("Transfers " + $(event.target).val());
	setTransferPersId($(event.target).val());
	$("#transferFunc").toggle();
	// search transactions for this account
}
function goToBalance(event) {
	cliHideShow('balanceCli'); // go to balance page
	fillHeaderTable(); // init the accounts of dropdown
	searchArray = []; // empty search array
	searchArray.push($(event.target).val());// put account in array
	displaySearchCriteria() // show the account we searched
	getBalance(); // get balance
}
function goToTransactions(event) {
	cliHideShow('transCli');
	fillHeaderTableTrans();
	searchArrayTrans = []; // empty search array
	searchArrayTrans.push($(event.target).val());// put account in array
	displaySearchCriteriaTrans();
	getTransactions();
}