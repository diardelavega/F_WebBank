function balanceHandler(jsobj) {
	/*
	 * get data from the json response, create a table and arrays, fill the html
	 * table and the js arrays
	 */
	if (jsobj.hasOwnProperty("balist")) {
		// TODO delete table content
		deleteBalContent();
		window.dates = [];
		window.withdraw = [];
		window.deposite = [];
		
		var bal = jsobj.balist;
		balHeader();
		for (var i = 0; i < bal.length; i++) {
			var tbody = $("#balTableList").find("tbody");
			var tr = document.createElement('tr');
			tbody.append(tr);
			for (var j = 0; j < bal[i].length; j++) {
				var td = document.createElement('td');
				var t = document.createTextNode(bal[i][j]);
				td.appendChild(t);
				tr.appendChild(td);
			}
			dates.push(bal[i][0]);
			withdraw.push(bal[i][1]);
			deposite.push(bal[i][2]);
		}

		// console.log(dates);
		// console.log(withdraw);
		// console.log(deposite);

	}
}



function balHeader() {
	// create a static table header
	var trhead = $("#balTableList").find("thead tr");

	var th0 = document.createElement('th');
	var th1 = document.createElement('th');
	var th2 = document.createElement('th');

	var t0 = document.createTextNode("date"); // Create a text node
	var t1 = document.createTextNode("withdraw"); // Create a text node
	var t2 = document.createTextNode("deposite"); // Create a text node

	th0.appendChild(t0);
	th1.appendChild(t1);
	th2.appendChild(t2);

	trhead.append(th0);
	trhead.append(th1);
	trhead.append(th2);
}

function deleteBalContent(){
	$("#balTableList").find("tbody").empty();
	$("#balTableList").find("thead tr").empty();
}

function dateRange() {
	var fDate = $("#datepicker1").val();
	var tDate = $("#datepicker2").val();
//	alert("from : " + fDate + " to :" + tDate);
	var dateRangeObj = {
		head : "balance",
		fdate : fDate,
		tdate : tDate
	};
	doSend(JSON.stringify(dateRangeObj));
	// return dateRange;
}

$(function() {
	$("#balsearch").click(function() {
		// alert("from : "+fDate +" to :"+tDate);
		dateRange();
	});
});

$(function() {
	$("#datepicker1").datepicker({
		changeMonth : true,
		changeYear : true,
		dateFormat : 'dd/mm/yy',
	// onSelect : function(dateText, inst) {
	// window.fDate= dateText;
	// window.fDate = $(this).datepicker('getDate');
	// alert(dateAsString);
	// alert(fDate);
	// }
	});
	$("#datepicker2").datepicker({
		changeMonth : true,
		changeYear : true,
		dateFormat : 'dd/mm/yy',
	// onSelect : function(dateText, inst) {
	// window.tDate= dateText;
	// window.tDate = $(this).datepicker('getDate');
	// alert(dateAsString);
	// alert(tDate);
	// }
	});
});
