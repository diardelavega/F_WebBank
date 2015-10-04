function getTransactions() {
	var bal = {
		head : "transactions",
		persId : persId,
		searchId : persId,
		accList : searchArrayTrans,
		fdate : $("#trdp1").val(),
		tdate : $("#trdp2").val()
	};
	doSend(JSON.stringify(bal));
}

function cliTransactionReply(jsobj) {
	if (jsobj.hasOwnProperty('transactionList')) {
		var trans = jsobj.transactionList;
		console.log(trans);
		fillTransactionTab(trans);
	} else {
		dispalyMsg(jsobj.msg);
	}
}

// ----HELPFUL METHODS
function fillTransactionTab(trans) {
	var tab = ("#transTableList");
	$(tab).empty();

	var depCount = 0;
	var witCount = 0;
	var traCount = 0;
	var trh = document.createElement("tr");
	for (var i = 0; i < trans.length; i++) {
		var trb = document.createElement("tr");
		if (trans[i]["acction"] === "DEPOSITE") {
			depCount++;
			trb.className = "success";
		}
		if (trans[i]["acction"] === "WITHDRAW") {
			witCount++;
			trb.className = "warning";
		}
		if (trans[i]["acction"] === "TRANSFER") {
			traCount++;
			trb.className = "info";
		}

		for (key in trans[i]) {
			if (i == 0) {
				var td0 = document.createElement("td");
				td0.innerHTML = "<strong>" + key + "</strong>";
				$(trh).append(td0);
			}
			var td = document.createElement("td");
			td.innerHTML = trans[i][key];
			trb.appendChild(td);
		}
		$(tab).append(trb);
	}
	$(tab).prepend(trh);

	transPie(depCount, witCount, traCount);
}

function fillHeaderTableTrans() {
	// add the account-checkbox pair on accounts dropdown
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
		$("#dropUlTrans").find("input[type='checkbox']").prop("checked", false);
		searchArrayTrans = [];
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

function transPie(deposites, withdraws, transfers) {
	var data = [ {
		label : "Depositions",
		data : deposites,
		color : "#4572A7"
	}, {
		label : "Withdraws",
		data : withdraws,
		color : "#80699B"
	}, {
		label : "Transfers",
		data : transfers,
		color : "#AA4643"
	} ];

	$(document)
			.ready(
					function() {
						$
								.plot(
										$("#placeholderTrans"),
										data,
										{
											series : {
												pie : {
													show : true,
													radius : 1,
													label : {
														show : true,
														radius : 1,
														formatter : function(
																label, series) {
															return '<div style="font-size:11px; text-align:center; padding:2px; color:white;">'
																	+ label
																	+ ' - '
																	+ Math
																			.round(series.percent)
																	+ '% <br>'
																	+ series.data[0][1]
																	+ '</div>';
														},
														background : {
															opacity : 0.8
														}
													}
												}
											},
											legend : {
												show : false
											}
										});
					});
}