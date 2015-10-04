window.dates = [];
window.withdraw = [];
window.deposite = [];
window.cumulative = [];

function clearTables() {
	window.dates = [];
	window.withdraw = [];
	window.deposite = [];
	window.cumulative = [];
}

function getBalance() {
	var bal = {
		head : "balance",
		persId : persId,
		searchId : persId,
		accList : searchArray,
		fdate : $("#dp1").val(),
		tdate : $("#dp2").val()
	};
	doSend(JSON.stringify(bal));
}

function cliBalanceReply(jsobj) {
	if (jsobj.hasOwnProperty('balanceData')) {
		var bal = jsobj.balanceData;
		fillBalanceTable(bal);
	} else {
		dispalyMsg(jsobj.msg);
	}
}

// HEPFUL FUNCTIONS
function plot() {
	/**
	 * initiation and implementation of plot function required by flot charts.
	 * create and fill data variables, implement the plot function with its
	 * specified parameters, implementation of assisting function i.e. hover for
	 * better functionality.
	 */

	/* chart data variables */
	var _deposite = [];
	var _withdraw = [];
	var _cumulative = [];

	console.log("on plot");
	for (var i = 0; i < dates.length; i++) {
		/* fill the chart data according to its doc. */
		_deposite.push([ dates[i], deposite[i] ]);
		_withdraw.push([ dates[i], -withdraw[i] ]);
		_cumulative.push([ dates[i], cumulative[i] ])
	}// for

	$.plot("#placeholderBal", [ {
		label : "deposite",
		data : _deposite,
		color : 13,
		bars : {
			show : true,
			barWidth : 0.2,
			align : "center"
		}
	}, {
		label : "withdraw",
		data : _withdraw,
		color : 12,
		bars : {
			show : true,
			barWidth : 0.2,
			align : "center"
		}
	}, {
		label : "cumulative",
		data : _cumulative,
		color : 4,
		lines : {
			show : true,
			fill : true,
		},
		points : {
			show : true
		}
	} ], {
		legend : {
			position : "nw"
		},
		series : {
			lines : {
				fillColor : "rgba(105, 50, 105, 0.25)"
			},
		},
		grid : {
			hoverable : true,
		},
		xaxis : {
			mode : "categories",
			tickLength : 0,
		}
	});

	$("<div id='tooltip'></div>").css({
		position : "absolute",
		display : "none",
		border : "1px solid #fdd",
		padding : "2px",
		"background-color" : "#fee",
		opacity : 0.80
	}).appendTo("body");

	$("#placeholderBal").bind("plothover", function(event, pos, item) {
		if (item) {
			var y = item.datapoint[1].toFixed(3);

			$("#tooltip").html(item.series.label + "  = " + y).css({
				top : item.pageY + 5,
				left : item.pageX + 5
			}).fadeIn(200);
		} else {
			$("#tooltip").hide();
		}
	});
}

function fillBalanceTable(bal) {
	var bod = $("#balTableList tbody");
	$(bod).empty();
	clearTables();
	balHeader();
	var prevCum = 0;

	for (var i = 0; i < bal.length; i++) {
		var tr = document.createElement("tr");
		for (key in bal[i]) {
			var td = document.createElement("td");
			if (bal[i][key] == null) {
				td.innerHTML = " - ";
			} else {
				td.innerHTML = bal[i][key];
			}
			tr.appendChild(td);
		}

		d = d = new Date(bal[i][0]);// create a new date with the db date
		dtxt = d.getDate() + "_" + d.getMonth() + "_" + d.getFullYear();
		dates.push(dtxt);
		if (bal[i][1] == null) {
			withdraw.push(0);
		} else {
			withdraw.push(bal[i][1]);
		}
		if (bal[i][2] == null) {
			deposite.push(0);
		} else {
			deposite.push(bal[i][2]);
		}

		dif = (deposite[i] - withdraw[i]);
		prevCum += dif;
		cumulative.push(prevCum);

		var td = document.createElement("td");
		td.innerHTML = cumulative[i];

		tr.appendChild(td);
		bod.append(tr);
	}// for of i
	plot();
}

function balHeader() {
	// create a static table header
	var trhead = $("#balTableList thead ");// .find("thead tr");
	$(trhead).empty();
	var tr = document.createElement('tr');

	var th0 = document.createElement('th');
	var th1 = document.createElement('th');
	var th2 = document.createElement('th');
	var th3 = document.createElement('th');

	var t0 = document.createTextNode("date"); // Create a text node
	var t1 = document.createTextNode("withdraw"); // Create a text node
	var t2 = document.createTextNode("deposite"); // Create a text node
	var t3 = document.createTextNode("cumulative"); // Create a text node

	th0.appendChild(t0);
	th1.appendChild(t1);
	th2.appendChild(t2);
	th3.appendChild(t3);

	tr.appendChild(th0);
	tr.appendChild(th1);
	tr.appendChild(th2);
	tr.appendChild(th3);
	$(trhead).append(tr);
}

function fillHeaderTable() {
	var ul = $("#dropUl");
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
			selec(this);
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
var searchArray = [];
function displaySearchCriteria(t) {
	var sc = $("#showCriteria");
	if (t != undefined || t != null) {
		$(sc).text(persId);
		$("#dropUl").find("input[type='checkbox']").prop("checked", false);
		searchArray = [];
	} else {
		$(sc).text(searchArray);
	}
}

function selec(t) {
	// get search criteria
	if (t.checked) {
		searchArray.push(t.value);
	} else {
		var x = searchArray.indexOf(t.value);
		searchArray.splice(x, 1);
	}
	displaySearchCriteria();
}
