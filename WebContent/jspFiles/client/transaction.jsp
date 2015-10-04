<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<style>

#selctCriteriaTrans {
	border-bottom: 1pt solid black;
	border-top: 1pt solid black;
}
</style>


<div id="transCli" class="hidable">

	<h3>Check Your Account Balance</h3>
	<hr>
	<table id="headTableTrans" class="headTab">
		<tr>
			<td>
				<button class="headBut" onclick="displaySearchCriteriaTrans(persId)">ClientId</button>
			</td>
			<td>
				<div class="dropdown">
					<button class="headBut" type="button" data-toggle="dropdown">
						Accounts <span class="caret"></span>
					</button>
					<ul class="dropdown-menu" id="dropUlTrans">

					</ul>
				</div>
			</td>
			<td></td>
		</tr>
		<tr
			style="border: thin; border-color: gray; background-color: rgb(220, 220, 220);">
			<td colspan="3" id="selctCriteriaTrans"><span
				style="background-color: rgb(140, 140, 140); margin-right: 10px;">
					BALANCE OF: </span><span id="showCriteriaTrans"></span></td>
		</tr>
		<tr>
			<td>fromDate: <input id="trdp1" class="datePicker"></td>
			<td>toDate: <input id="trdp2" class="datePicker"></td>
			<td><button onclick="getTransactions()">GO,GO</button></td>
		</tr>
	</table>
	<hr>
	<div id="contentAreaTrans">
		<!-- HEADER -->
		<ul class="nav nav-tabs" role="tablist">
			<li class="active"><a data-toggle="tab" href="#transChart">Graphical
			</a></li>
			<li><a data-toggle="tab" href="#transTab">Table </a></li>
		</ul>

		<!-- CONTENT -->
		<div class="tab-content">
			<div id="transChart" class="tab-pane active">
				<div id="content">
					<div class="demo-container">
						<div id="placeholderTrans" class="demo-placeholder"></div>
					</div>
				</div>
			</div>

			<!-- style="display: none" -->
			<div id="transTab" class="tab-pane">

				<div id="translist" class="container-fluid; ">
					<table id="transTableList" class="table">
					</table>
				</div>
			</div>

		</div>
	</div>
	<!-- TRANSACTIONS -->
</div>