$(function() {
	$(".datePicker").datepicker({
		changeMonth : true,
		changeYear : true,
		dateFormat : 'dd/mm/yy',
	});
});

// ----------- Unimplemented Notification
function telNotifyMsg(msgHead, msgContent, extra) {
	// write the new msg in the horizontal top panel
	var msg_li = $("#telMsgPanel");
	$(msg_li).find('h5 strong').text("resp: " + msgHead);

	var date = new Date();
	$(msg_li).find('p i').text(date);
	$(msg_li).find('p').eq(0).text("req: " + msgContent);
	$(msg_li).find('p').eq(1).text("clid: " + extra);

}

function telWriteAllMsgs(req) {
	// go to the page where all the last responses of the requests that require
	// confirmation are

	console.log("Trying to write msg");

	var date = new Date();
	req['time'] = date.getHours() + ":" + date.getMinutes() + ":"
			+ date.getSeconds();

	var table = $("#allReplays tbody");
	var tr = document.createElement("tr");

	var tr1 = document.createElement("tr");
	var tr2 = document.createElement("tr");
	tr.appendChild(tr1);
	tr.appendChild(tr2);

	for ( var key in req) {
		if(key ===null||key ===""){
			continue;
		}
		
		if (key === 'response') {
			if (req[key] === 'DENNIED') {
				$(tr).css("background-color", "rgb(240, 200, 200);");
				// .attr("class","danger");
			} else {
				$(tr).css("background-color", "rgb(200, 240, 200);");
				// .attr("class","success");
			}
		}

		if (key === 'amount')
			if (req[key] <= 0.5)
				continue;
		if (key === 'pin' || key === 'status')
			continue;

		var td1 = document.createElement("td");
		td1.innerHTML = key;
		tr1.appendChild(td1);

		if (key === 'clientIdsList') {
			var sel = document.createElement("select");
			for (k in req[key]) {
				var opt = document.createElement("option");
				opt.innerHTML = req[key][k];
				sel.appendChild(opt);
			}
			var td2 = document.createElement("td");
			td2.appendChild(sel);
			tr2.appendChild(td2);
			continue;
		}

		var td2 = document.createElement("td");
		td2.innerHTML = req[key];
		tr2.appendChild(td2);
	}
	table.prepend(tr);
}

function telNotifyColor(response) {
	// Visual signal for the arrival of a new msg
	$(".fa-arrow-up").css("color", "orange");

	var ddp = $("#telHeadNotify");
	if ($(ddp).children().length >= 5) {
		$($(ddp).children()[4]).remove();
	}
	var li = document.createElement("li");
	li.setAttribute("style", " margin: 5px");

	var a = document.createElement("a");
	a.setAttribute("href", "#");
	if (response === "ACCEPTED") {
		a.setAttribute("class", "label label-success");
	} else {
		a.setAttribute("class", "label label-info");
	}
	a.innerHTML = '<Strong>' + response + '</strong>';

	li.appendChild(a);
	$(ddp).prepend(li);
}

function colorToNorm() {
	$(".fa-arrow-up").removeAttr("style");
}

// ----------------------------Test purposes

function testOcrReciver(jsobj) {
	if (jsobj.hasOwnProperty("ocr")) {
		var req = jsobj.ocr;

		telNotifyColor(req.response);
		var txt = "";
		for (var i = 0; i < req.clientIdsList.length; i++) {
			txt += req.clientIdsList[i] + ", ";
		}
		telNotifyMsg(req.response, req.reqType, txt);
		telWriteAllMsgs(req);
	} else {
		console.log("no req");
	}
}

function lunch() {
	console.log("on lunch");
	var obj = {
		head : "testOcr",
		att : "ocr"
	}
	doSend(JSON.stringify(obj));
}

// ----------------------------Test purposes end
// --------------CONTROLS
/* TODO a set of controls for the correctness of the input variables */

$(function() {
	/*
	 * initiating the different classes
	 * "amount,name,phone,address,email,password" to be ready to display user
	 * friendly functionalities
	 */
	var addresses = $(".address").focusout(function() {
		addressCtrl(this);
	});

	var paswords = $(".password").focusout(function() {
		pswCtrl(this);
	});

	var names = $(".name").focusout(function() {
		nameCtrl(this);
	});

	var phones = $(".phone").focusout(function() {
		phoneCtrl(this);
	});

	var notes = $(".note").focusout(function() {
		noteCtrl(this);
	});

	var emails = $(".mail").focusout(function() {
		emailCtrl(this);
	});

	var amts = $(".amount").focusout(function() {
		amountCtrl(this);
	});
	for (var i = 0; i < amts.length; i++) {
		$(amts[i]).attr("placeholder", "ex: 125.6787");
	}
});

function logOut() {
	var logout = {
		head : "logout",
		empId : telId,
	};
	doSend(JSON.stringify(logout));
}

function getReplys() {
	telHideShow('alertTel');
}
//
// function logOutReplay(jsobj) {
// if (jsobj.hasOwnProperty('response')) {
// if (jsobj.response === 'OK') {
// window.location.href = "../../logout.jsp";
// }
// } else if (jsobj.hasOwnProperty('msg')) {
// console.log("on log out " + jsobj.msg);
// }
// }
