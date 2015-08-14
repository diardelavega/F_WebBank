function balanceHandler(jsobj) {
	if (jsobj.hasOwnProperty("employees")) {
		// TODO delete table content

		val
		bal = jsobj.balance;
		console.log(bal);
		console.log(bal.balist);
		for (var i = 0; i < bal.balist.length; i++) {
			console.log(bal.balist[i])
		}
		// TODO create new header

		// TODO Fill body
	}
}

function dateRange() {
	var dateRange = {
		head : "balance",
		fdate : fDate,
		tdate : tDate
	};
	doSend(JSON.stringify(dateRange));
	// return dateRange;
}

$(function() {

	$("#balsearch").click(function() {
		// alert("from : "+fromDateAsString +" to :"+toDateAsString);
		dateRange();
	});

});

$(function() {
	$("#datepicker1").datepicker({
		changeMonth : true,
		changeYear : true,
		dateFormat : 'dd/mm/yy',
		onSelect : function(dateText, inst) {
			// window.fDate= dateText;
			window.fDate = $(this).datepicker('getDate');
			// alert(dateAsString);
			// alert(dateAsObject);
		}
	});
	$("#datepicker2").datepicker({
		changeMonth : true,
		changeYear : true,
		dateFormat : 'dd/mm/yy',
		onSelect : function(dateText, inst) {
			// window.toDate= dateText;
			window.tDate = $(this).datepicker('getDate');
			// alert(dateAsString);
			// alert(dateAsObject);
		}
	});
});
