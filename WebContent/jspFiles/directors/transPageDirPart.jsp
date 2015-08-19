<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<style>
<!--
#translist {
	height: 400px;
	overflow: auto;
}

body {
	font-family: Verdana, Arial, sans-serif;
	font-size: 12px;
}

#placeholderTrans {
	width: 350px;
	height: 350px;
}

.legend table, .legend>div {
	height: 82px !important;
	opacity: 1 !important;
	left: 170px;
	top: 10px;
	width: 116px !important;
}

.legend table {
	border: 1px solid #555;
	padding: 5px;
}
-->
</style>

<div id="transDir" class="hidable">
	<table>
		<thead>
			<tr>
				<th colspan="3" align="center"><h4>search for the
						transactions in the date range</h4></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>From Date:<input type="text" id="datepicker3"
					class="datePicker">
				</td>
				<td>To Date:<input type="text" id="datepicker4"
					class="datePicker">
				</td>
				<td>
					<button id="balsearch" onclick="transSearch();">OK</button>
				</td>
			</tr>
		</tbody>
	</table>

	<hr>
	<br>
	<!-- <div id="transTab" class="tab-pane "> -->

	<!-- panel header -->
	<ul class="nav nav-tabs" role="tablist">
		<li class="active"><a data-toggle="tab" href="#transChart">transaction
				chart </a></li>
		<li><a data-toggle="tab" href="#transTab">transaction table</a></li>
	</ul>
	<div class="tab-content">

		<div id="transChart" class="tab-pane active">
			<div id="placeholderTrans">
			</div>
		</div>


		<div id="transTab" class="tab-pane">
			<div id="translist" class="container-fluid; ">
				<table id="transTableList" border="1" class="table">
					<thead>
						<tr>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>




	</div>
	<!-- </div> -->


</div>