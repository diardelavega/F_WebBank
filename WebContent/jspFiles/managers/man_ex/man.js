function manSeverClientTupeling() {
	var reg = {
		head : "coordinate",
		empId : manEmpId
	}
	doSend(JSON.stringify(reg));
}

// HEPL FUNCTIONALITY
function telNotifyMsg(msgHead, msgContent, extra) {
	// write the new msg in the horizontal top panel
	var msg_li = $("#manMsgPanel");
	console.log($(msg_li).find('h5').text());
	$(msg_li).find('h5 strong').text("req: " + msgHead);

	var date = new Date();
	$(msg_li).find('p i').text(date);
	$(msg_li).find('p').eq(0).text("data: " + msgContent);
	$(msg_li).find('p').eq(1).text(" " + extra);

}

function manNotifyColor(request) {
	// Visual signal for the arrival of a new msg
	$(".fa-arrow-up").css("color", "orange");

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
}

function manRequestAlert(jsobj) {
	console.log("manRequestAlert");
	if (jsobj.hasOwnProperty('reqType')) {
		reqType = jsobj.reqType;
		d2 = jsobj.d2;
		ed = jsobj.ed;
		telNotifyMsg(reqType, d2, ed);
	} else {
		console.log(jsobj.msg);
		alert(jsobj.msg);
	}
	manNotifyColor(reqType);
}

function goGetReqs(){
	ManHideShow('reqMan');
}
// ---------------------
function openAcc(jsobj) {
	console.log(jsobj);
}

function closeAcc(jsobj) {
	console.log(jsobj);
}

function p1kDep(jsobj) {
	console.log(jsobj);
}

function p1kWith(jsobj) {
	console.log(jsobj);
}

function p_1k_trans(jsobj) {
	console.log(jsobj);
}

function p6Acc(jsobj) {
	console.log(jsobj);
}

function newReq(jsobj) {
	console.log(jsobj);
}

function manClientAccounts(jsobj) {
	console.log(jsobj);
}

function ManAccountStatus(jsobj) {
	console.log(jsobj);
}
function clearAccountStatus(jsobj) {
	console.log(jsobj);
}

function balanceReply(jsobj) {
	console.log(jsobj);
}

function transactionReply(jsobj) {
	console.log(jsobj);
}

function errorRes(jsobj) {
	console.log(jsobj.msg)
}