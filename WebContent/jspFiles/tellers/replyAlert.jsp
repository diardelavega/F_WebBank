<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<style>
tr.border_bottom td {
	border-bottom: 1pt solid black;
	border-top: 1pt solid black;
}

table.alertTab td {
	padding-left: 15px;
	padding-right: 15px;
	text-align: center;
	padding-bottom: 5px;
	padding-top: 5px;
}

table.alertTab tr {
	border-bottom: 1pt solid black;
	border-top: 1pt solid black;
}
</style>

<div id="alertTel" class="hidable">

	<h1>ALERT!!!!!</h1>

	<button onclick="lunch();">LUNCH</button>
	<table id="allReplays" class="alertTab">
		<tbody>
			<tr></tr>
		</tbody>
	</table>
</div>