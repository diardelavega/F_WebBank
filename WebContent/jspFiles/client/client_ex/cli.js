function cliSeverClientTupeling() {
//	alert("persId= "+persId);
	var reg = {
		head : "coordinate",
		persId : persId
	}
	doSend(JSON.stringify(reg));
}

function cliLogOut() {
//	alert("logout");
	var logout = {
		head : "logout",
		persId : persId,
	};
	doSend(JSON.stringify(logout));
}


//----------------display MSG
var cliTimeOut;
function dispalyMsg(msg) {
	clearTimeout(cliTimeOut);

	console.log("Dispalying MSg");

	var div = $("#cliMsgAlert");
	$(div).empty();
	$(div).addClass("alert alert-info");

	var span = document.createElement("span");
	$(span).attr("align", "right");
	span.style.padding = "230px";

	var a = document.createElement("a");
	a.innerHTML = "&times;";
	$(a).attr("href", "#");
	$(a).attr("onclick", "closeThis(event)");

	var br = document.createTextNode(br);

	span.appendChild(a);
	$(div).append(span);
	div.append(br);
	div.append(msg);

	$(div).show();
	fadeOut(div);
}

function fadeOut(div) {
	cliTimeOut = setTimeout(function() {
		$(div).hide(1000);
	}, 5000);
}

function closeThis(event) {
	var div = $(event.target).parent().parent();
	$(div).hide(1000);
}
//-------------------------