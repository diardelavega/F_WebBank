window.dates = [];
window.withdraw = [];
window.deposite = [];

function clearTables() {
	window.dates = [];
	window.withdraw = [];
	window.deposite = [];

}

function balanceHandler(jsobj) {
	/*
	 * get data from the json response, create a table and arrays, fill the html
	 * table and the js arrays
	 */
	if (jsobj.hasOwnProperty("balist")) {
		// TODO delete table content
		deleteBalContent();
		clearTables();

		balHeader();

		var bal = jsobj.balist;
		var tbody = $("#balTableList").find("tbody");
		// console.log(bal);

		var prevCum = 0;
		for (var i = 0; i < bal.length; i++) {
			var tr = document.createElement('tr');
			tbody.append(tr);

			for (var j = 0; j < bal[i].length; j++) {
				var td = document.createElement('td');
				var t;
				if (bal[i][j] == null) {
					t = document.createTextNode("-");
				} else {
					t = document.createTextNode(bal[i][j]);
				}
				td.appendChild(t);
				tr.appendChild(td);
			}

			d = new Date(bal[i][0]);// create a new date with the db date
			dates.push(d.ddmmyyy());// format to yy_mm_dd and store
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
			

			prevCum += deposite[i] - withdraw[i];

			// append the analog cumulative val
			var td = document.createElement('td');
			var t = document.createTextNode(prevCum);
			td.appendChild(t);
			tr.appendChild(td);

		}// for

		plot();
	}
}

function showBalTab() {
	// alert("showTabBal");
	document.getElementById("balTab").style.display = 'block';
	document.getElementById("content").style.display = 'none';

	// $("#balTab").show();
	// $("#content").hide();
}

function showBalChart() {
	// alert("showTabChart");
	document.getElementById("balTab").style.display = 'none';
	document.getElementById("content").style.display = 'block';
}

// function balBody

function balHeader() {
	// create a static table header
	var trhead = $("#balTableList").find("thead tr");

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

	trhead.append(th0);
	trhead.append(th1);
	trhead.append(th2);
	trhead.append(th3);
}

function deleteBalContent() {
	$("#balTableList").find("tbody").empty();
	$("#balTableList").find("thead tr").empty();
}

function dateRange() {
	/*
	 * get a range of dates or a single date to search for the balance affecting /
	 * transactions that occurred
	 */
	var fDate = $("#datepicker1").val();
	var tDate = $("#datepicker2").val();
	var dateRangeObj = {
		head : "balance",
		fdate : fDate,
		tdate : tDate
	};
	doSend(JSON.stringify(dateRangeObj));
	// return dateRange;
}

function search() {
	dateRange();
	// $("#content").attr("style", "visibility: visible");
}

$(function() {
	/*
	 * get date JS object from a string based date and format it as dd_mm_yy
	 */

	Date.prototype.ddmmyyy /* yyyymmdd */= function() {
		var yyyy = this.getFullYear().toString();
		var mm = (this.getMonth() + 1).toString(); // getMonth() is zero-based
		var dd = this.getDate().toString();
		return (dd[1] ? dd : "0" + dd[0]) + "_" + (mm[1] ? mm : "0" + mm[0])
				+ "_" + yyyy;
	};
});

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
	var cumulative = [];

	console.log("on plot");
	for (var i = 0; i < dates.length; i++) {
		console.log(i + " - " + dates[i]);
		/* fill the chart data according to its doc. */
		_deposite.push([ dates[i], deposite[i] ]);
		_withdraw.push([ dates[i], -withdraw[i] ]);
		if (i <= 0) {// the first cumulative var
			cumulative.push([ dates[i], deposite[i] - withdraw[i] ]);
		} else {
			cumulative.push([ dates[i],
					deposite[i] - withdraw[i] + cumulative[i - 1][1] ]);
		}
	}// for

	for (var j = 0; j < _deposite.length; j++) {
		console.log(_deposite[j]);
	}
	console.log("");
	for (var j = 0; j < _withdraw.length; j++) {
		console.log(_withdraw[j]);
	}
	console.log("");
	for (var j = 0; j < cumulative.length; j++) {
		console.log(cumulative[j]);
	}

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
		data : cumulative,
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
