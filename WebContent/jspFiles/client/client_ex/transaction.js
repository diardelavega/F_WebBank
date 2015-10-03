
function getTransactions() {
	var bal = {
		head : "transactions",
		persId : persId,
		searchId : persId,
		accList : searchArray,
		fdate : $("#dp1").val(),
		tdate : $("#dp2").val()
	};
	doSend(JSON.stringify(bal));
}

function cliTransactionReply(jsobj) {
	if (jsobj.hasOwnProperty('balanceData')) {
		var bal = jsobj.balanceData;
		fillBalanceTable(bal);
	} else {
		dispalyMsg(jsobj.msg);
	}
}

//----HELPFUL METHODS
function fillHeaderTableTrans() {
	var ul = $("#dropUlTrans");
	$(ul).empty();
	for (var i = 0; i < accArr.length; i++) {
		var li = document.createElement("li");
		var label = document.createElement("label");
		$(label).text(accArr[i]["accountId"]);
		var cbox = document.createElement("input");
		cbox.type = "checkbox";
		cbox.value = accArr[i]["accountId"];
		cbox.className = "cbox";
		cbox.onclick = function() {
			selecTrans(this);
		};
		li.appendChild(label);
		label.appendChild(cbox);
		ul.append(li);
	}
}

$(function() {
	$(".dropdown-menu").click(function(event) {
		event.stopPropagation();
	});
});

// display the selected values
var searchArrayTrans = [];
function displaySearchCriteriaTrans(t) {
	var sc = $("#showCriteriaTrans");
	if (t != undefined || t != null) {
		$(sc).text(persId);
	} else {
		$(sc).text(searchArrayTrans);
	}
}

function selecTrans(t) {
	// get search criteria
	if (t.checked) {
		searchArrayTrans.push(t.value);
	} else {
		var x = searchArrayTrans.indexOf(t.value);
		searchArrayTrans.splice(x, 1);
	}
	displaySearchCriteriaTrans();
}