function manSeverClientTupeling() {
	var reg = {
		head : "coordinate",
		empId : manEmpId
	}
	doSend(JSON.stringify(reg));
}

// HEPL FUNCTIONALITY
function requestInit(jsobj) {
	// get the request from the server side, to be fresh and ready after each
	// reload
	if (jsobj.hasOwnProperty('relRequest')) {
		window.relRequest=jsobj.relRequest;
	}
	
	
}

function manNotifyMsg(msgHead, msgContent, extra) {
	// write the new msg in the horizontal top panel
	var msg_li = $("#manMsgPanel");
	// console.log($(msg_li).find('h5').text());
	$(msg_li).find('h5 strong').text("req: " + msgHead);

	var date = new Date();
	$(msg_li).find('p i').text(date);
	$(msg_li).find('p').eq(0).text("data: " + msgContent);
	$(msg_li).find('p').eq(1).text(" " + extra);

}

function manNotifyColor(request) {
	// Visual signal for the arrival of a new msg
	$(".fa-arrow-up").css("color", "orange");
	console.log("color notify " + request);
	var ddp = $("#manHeadNotify");
	if ($(ddp).children().length >= 5) {
		$($(ddp).children()[4]).remove();
	}
	var li = document.createElement("li");
	li.setAttribute("style", " margin: 5px");

	var a = document.createElement("a");
	a.setAttribute("href", "#");
	a.setAttribute("class", "label label-info");
	a.innerHTML = '<Strong>' + request + '</strong>';

	li.appendChild(a);
	$(ddp).prepend(li);
}

function manColorToNorm() {
	// return the visual signal to normal
	$(".fa-arrow-up").removeAttr("style");
	// $("#reqNrAvailable").removeAttr("style:color");
}

function manRequestAlert(jsobj) {
	/*
	 * get the notification for the arrival of a new request and call
	 * appropriate functions
	 */
	console.log("manRequestAlert");
	if (jsobj.hasOwnProperty('reqType')) {
		reqType = jsobj.reqType;
		console.log(reqType);
		d2 = jsobj.d2;
		ed = jsobj.ed;
		manNotifyMsg(reqType, d2, ed);
	} else {
		console.log(jsobj.msg);
		alert(jsobj.msg);
	}
	manNotifyColor(reqType);
}

function goGetReqs() {
	ManHideShow('reqMan');
}
// --------------notification popup

var manTimeOut;
function manMainAlertDisplay(msg, alertType, div) {

	// var divs = $(".manMsgAlert");
	// for (var i = 0; i < divs.length; i++) {
	// var div = divs[i];
	clearTimeout(manTimeOut);
	$(div).empty();
	if (alertType === 'info') {
		$(div).addClass("alert alert-info");
	} else {
		$(div).addClass("alert alert-danger");
	}
	// $(div).attr("style:padding", "5px");

	var span = document.createElement("span");
	$(span).attr("align", "left");
	// span.style.padding = "230px";

	var a = document.createElement("a");
	a.innerHTML = "&times;";
	$(a).attr("href", "#");
	$(a).attr("onclick", "closeThis(event)");

	var t = document.createTextNode(msg);
	var p = document.createElement("p");
	p.appendChild(t);

	span.appendChild(a);
	$(span).append(p);
	$(div).append(span);

	$(div).show();
	fadeOut(div);
}

function fadeOut(div) {
	manTimeOut = setTimeout(function() {
		$(div).hide(1000);
	}, 5000);
}

function closeThis(event) {
	var div = $(event.target).parent().parent();
	$(div).hide(1000);
}

function requestNumber(jsobj) {
	if (jsobj.hasOwnProperty('number')) {
		var num = jsobj.number;
		var displayNr = 9311 + num;
		var displayVar = "&#" + displayNr + ";";
		var span = $("#reqNrs");
		$(span).html(displayVar);
	} else {

	}
}

function logOut() {
	var logout = {
		head : "logout",
		empId : window.manEmpId
	};
	doSend(JSON.stringify(logout));
}
