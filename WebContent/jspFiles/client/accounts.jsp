<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<script type="text/javascript">
	function foo() {
		var ulPill = $("#accountPills");
		var x = ulPill.children().size();
		x++;
		var li = document.createElement("li");
		var a = document.createElement("a");
		a.setAttribute("href", "#acc" + x);
		a.setAttribute("data-toggle", "pill");
		a.innerHTML = "<strong>AAA" + x + "<strong>";
		li.appendChild(a);
		ulPill.append(li);

		var divPillCont = $("#accountPillContenet");
		var div = document.createElement("div");
		div.setAttribute("id", "acc" + x);
		div.setAttribute("class", "tab-pane fade")
		div.innerHTML = "BUGA BUGA" + x ^ 3;
		divPillCont.append(div);

	}
</script>
<div id="accCli" class="hidable">

	<h3>Your Account(s)</h3>
	<button onclick="foo();">foo</button>
	<div class="container-fluid">
		<div style="background-color: rgb(220, 220, 220); width: 100%">
			<ul class="nav nav-pills" id="accountPills">
				<li><a data-toggle="pill" href="#acc1"><strong>Account
							1</strong></a></li>
			</ul>
		</div>

		<br>
		<div class="tab-content" id="accountPillContenet">
			<div id="acc1" class="tab-pane fade">AAAAAAAAcccount 1</div>
		</div>
	</div>

</div>