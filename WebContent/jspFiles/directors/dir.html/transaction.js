function transactionHandler(jsobj) {
	if (jsobj.hasOwnProperty("translist")) {
		deleteTransContent();
		var depCount = 0;
		var witCount = 0;
		var traCount = 0;
		var trans = jsobj.translist;
		for (var i = 0; i < trans.length; i++) {
			var line = trans[i];

			if (line["acction"] === "DEPOSITE") {
				depCount++;
				transTableTr("success");
			}
			if (line["acction"] === "WITHDRAW") {
				witCount++;
				transTableTr("warning");
			}
			if (line["acction"] === "TRANSFER") {
				traCount++;
				transTableTr("active");
			}

			for ( var key in line) {
				if (i == 0) {
					transTabHeader(key);
				}

				if (line[key] === null) {
					transTabBody("-");
				} else {
					transTabBody(line[key]);
				}
				// console.log(i + " --- " + key + " --- " + line[key]);
			}
		}
		transPie(depCount, witCount, witCount);
	}
}

function transTableTr(className) {
	var tbody = $("#transTableList").find("tbody");
	var tr = document.createElement('tr');
	tr.className = className;
	tbody.append(tr);
}

function transTabHeader(head) {
	// create the table header based on the json that is received
	var trhead = $("#transTableList").find("thead tr");
	var th = document.createElement('th');
	var t = document.createTextNode(head); // Create a text node
	th.appendChild(t);
	trhead.append(th);
}

function transTabBody(val) {
	// fill the last row <tr> of the table with cells <td>
	var tbody = $("#transTableList").find("tbody tr").last();

	var td = document.createElement('td');
	var t = document.createTextNode(val); // Create a text node
	td.appendChild(t);
	tbody.append(td);
}

function deleteTransContent() {
	$("#transTableList").find("thead tr").empty();
	$("#transTableList").find("tbody").empty();
}

function transSearch() {
	var fDate = $("#datepicker3").val();
	var tDate = $("#datepicker4").val();
	var dateRangeObj = {
		head : "transaction",
		fdate : fDate,
		tdate : tDate
	};
	doSend(JSON.stringify(dateRangeObj));
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