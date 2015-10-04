<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<style>
.headTab {
	border-collapse: separate;
	border-spacing: 15px;
}

button {
	width: 120px;
}

#selctCriteria {
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

table.alertTab th {
	text-align: center;
	min-width: 80px;
}

table.alertTab tr {
	border-bottom: 1pt solid black;
	border-top: 1pt solid black;
	/* min-width: 300px; */
}

.artAlert {
	position: fixed;
	bottom: -30;
	right: 0;
	width: 300px;
	/* border: 3px solid #8AC007; */
}
</style>
<div id="balanceCli" class="hidable">
	<h3>Check Your Account Balance</h3>
	<hr>
	<table id="headTable" class="headTab">
		<tr>
			<td>
				<button class="headBut" onclick="displaySearchCriteria(persId)">ClientId</button>
			</td>
			<td>
				<div class="dropdown">
					<button class="headBut" type="button" data-toggle="dropdown">
						Accounts <span class="caret"></span>
					</button>
					<ul class="dropdown-menu" id="dropUl">

					</ul>
				</div>
			</td>
			<td></td>
		</tr>
		<tr
			style="border: thin; border-color: gray; background-color: rgb(220, 220, 220);">
			<td colspan="3" id="selctCriteria"><span
				style="background-color: rgb(140, 140, 140); margin-right: 10px;"> BALANCE OF: </span><span id="showCriteria"></span></td>
		</tr>
		<tr>
			<td>fromDate: <input id="dp1" class="datePicker"></td>
			<td>toDate: <input id="dp2" class="datePicker"></td>
			<td><button onclick="getBalance()">GO,GO</button></td>
		</tr>
	</table>
	<hr>
	<div id="contentArea">
		<!-- HEADER -->
		<ul class="nav nav-tabs" role="tablist">
			<li class="active"><a data-toggle="tab" href="#balChart">Graphical
			</a></li>
			<li><a data-toggle="tab" href="#balTab">Table </a></li>
		</ul>
		<!-- CONTENT -->
		<div class="tab-content">

			<div id="balChart" class="tab-pane active">
				<div id="content">
					<div class="demo-container">
						<div id="placeholderBal" class="demo-placeholder"></div>
					</div>
				</div>
			</div>

			<!-- style="display: none" -->
			<div id="balTab" class="tab-pane ">
				<div id="balancelist" class="container-fluid; ">
					<table id="balTableList" class="alertTab">
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
	</div>

	<!-- <table id="content">
		<thead>
			<tr></tr>
		</thead>
		<tbody>
			<tr></tr>
		</tbody>
	</table>


	BALANCE -->
</div>