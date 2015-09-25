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

table.alertTab th {
	text-align: center;
	min-width: 80px;
}

table.alertTab tr {
	border-bottom: 1pt solid black;
	border-top: 1pt solid black;
	/* min-width: 300px; */
}
</style>


<div id="mainMan" class="hidable">
	<table>
		<thead>
			<tr>
				<th colspan="2"><center>
						<h4>Fill one of the forms and act upon them</h4>
					</center></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td><label>Personal Id:&nbsp;</label><input type="text"
					id="manPersonalId" class="pesrId" style="margin-right: 20px;"></td>
				<td><label style="margin-left: 20px">Account Nr.:&nbsp;</label><input
					type="text" id="manAccountNr" class="accNr" style="margin-right: 20px;"></td>
					<td><div id="manMsgAlertMain"
						style="width: 270px; height: 60px;"></div></td>
			</tr>
		</tbody>
	</table>
	<hr>
	<table>
		<tbody>
			<tr>
				<td>
					<div class="container-fluid">
						<h3>Actions:</h3>
						<div style="background-color: rgb(220, 220, 220); width: 100%">
							<ul class="nav nav-pills ">
								<li><a data-toggle="pill" href="#accs"
									onclick="manAccountStatus();"><strong>Account's
											Status</strong></a></li>
								<li><a data-toggle="pill" href="#a_coo"
									onclick="manAccountCoo();"><strong>Account's
											Co-owners</strong></a></li>
								<li><a data-toggle="pill" href="#cli_a"
									onclick="manClientAccounts();"><strong>Client's
											Accounts</strong></a></li>
								<li><a data-toggle="pill" href="#accTrans"
									onclick="manAccountTrans();"><strong>Account
											Transactions</strong></a></li>
								<li><a data-toggle="pill" href="#cliTrans"
									onclick="manClientTrans();"><strong>Client
											Transactions</strong></a></li>
							</ul>
						</div>
						<br>
						<div class="tab-content">

							<div id="accs" class="tab-pane fade ">
								<h3>Account's Status :</h3>
								<!-- table with the response Account Status -->
								<table id="manAccountStatus" class="alertTab">
									<thead>
										<tr>
										</tr>
									</thead>
									<tbody id="manAccount">
										<tr></tr>
									</tbody>
								</table>
							</div>

							<div id="a_coo" class="tab-pane fade">
								<h3>Account's Co-owners:</h3>
								<div id="manAccCoo"></div>
							</div>

							<div id="cli_a" class="tab-pane fade">
								<h3>Client's Accounts</h3>
								<table id="manClientAccounts" 
									class="alertTab">
									<thead>
										<tr>
										</tr>
									</thead>
									<tbody>
										<tr class="tabSpace">
										</tr>
									</tbody>
								</table>
							</div>

							<div id="accTrans" class="tab-pane fade">
								<h3>Account Transactions:</h3>
								<table id="manAccTrans" class="alertTab">
									<thead>
										<tr></tr>
									</thead>
									<tbody>
										<tr></tr>
									</tbody>
								</table>
							</div>

							<div id="cliTrans" class="tab-pane fade">
								<h3>Client Transactions:</h3>
								<table id="manClientTrans" class="alertTab">
									<thead>
										<tr></tr>
									</thead>
									<tbody>
										<tr></tr>
									</tbody>
								</table>
							</div>

						</div>
					</div>
				</td>
			</tr>
		</tbody>
	</table>
</div>