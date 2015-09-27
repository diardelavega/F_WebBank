<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<style>
.scroll {
	overflow: auto;
}

th {
	text-align: center;
}

.tabSpace {
	border-color: olive;
}

table {
	display: table;
	border-spacing: 2px;
	border-color: gray;
}

th, td {
	padding-left: 5px;
	padding-right: 5px;
	/* text-align: center; */
}
.space {
	margin: 9px;
}

body {
	font-family: Verdana, Arial, sans-serif;
	font-size: 12px;
}
.artAlert{
 position: fixed;
    bottom: -30;
    right: 0;
    width: 300px;
    /* border: 3px solid #8AC007; */
}
</style>

<div id="mainTel" class="hidable">
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
				<td><label style="margin-left: 15px">Account Nr.:&nbsp;</label><input
					class="accNr" type="text" id="telAccountNr"></td>
				<td><label>Personal Id:&nbsp;</label><input type="text"
					class="persId" id="telPersonalId" style="margin-right: 15px"></td>
				<td><div id="telMainMsgAlert" class="artAlert"></div></td>
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
									onclick="telAccountStatus();"><strong>Account's
											Status</strong></a></li>
								<li><a data-toggle="pill" href="#a_coo"
									onclick="telAccountCoo();"><strong>Account's
											Co-owners</strong></a></li>
								<li><a data-toggle="pill" href="#cli_a"
									onclick="telClientAccounts();"><strong>Client's
											Accounts</strong></a></li>
								<li><a data-toggle="pill" href="#infoCli"
									onclick="clientIdDataSearch();"><strong> Client
											Details</strong></a></li>
							</ul>
						</div>
						<br>
						<div class="tab-content">
							<div id="accs" class="tab-pane fade ">
								<h3>Account's Status :</h3>
								<!-- table with the response Account Status -->
								<table id="telAccountStatus" border="0" bordercolor="olive" class="alertTab">
									<thead>
										<tr>
										</tr>
									</thead>
									<tbody class="tabSpace">
										<tr class="tabSpace"></tr>
									</tbody>
								</table>
							</div>

							<div id="a_coo" class="tab-pane fade">
								<h3>Account's Co-owners:</h3>
								<div id="telCoowner"></div>
							</div>
							<div id="cli_a" class="tab-pane fade">
								<h3>Client's Accounts</h3>
								<!-- table with the response Account Status -->
								<table id="telClientAccounts" border="0" bordercolor="olive" class="alertTab">
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
							<div id="infoCli" class="tab-pane fade">
								<!-- big table -->
								<table align="center" border="0" style="margin-left: 85pt" >
									<tbody>
										<tr>
											<td>
												<!-- client registration Form -->
												<table class="normal"
													style="margin: 30px; margin-left: 10px; padding: 10px;"
													id="telClientForm">
													<tr>
														<td><label> Id&nbsp;</label></td>
														<td><input type="text" name="id" class="persId"></td>
													</tr>
													<tr>
														<td><label> F. Name &nbsp;</label></td>
														<td><input type="text" name="fname1" class="name"></td>
													</tr>
													<tr>
														<td><label> L. Name &nbsp;</label></td>
														<td><input type="text" name="lname1" class="name"></td>
													</tr>
													<tr>
														<td><label> BirthDate&nbsp;</label></td>
														<td><input type="text" name="bdate"
															class="datePicker"></td>
													</tr>

												</table>
											</td>
											<td>
												<table>
													<tbody>
														<tr>
															<td><label> Address&nbsp;</label></td>
															<td><input type="text" name="address1"
																class="address"></td>
														</tr>
														<tr>
															<td><label> phone Nr.&nbsp;</label></td>
															<td><input type="text" name="phone" class="phone"></td>
														</tr>
														<tr>
															<td><label> E-mail&nbsp;</label></td>
															<td><input id="email" type="text" name="email1"
																class="mail"></td>
														</tr>
														<tr>
															<td><label> Password&nbsp;</label></td>
															<td><input type="text" name="psw1" class="password"></td>
														</tr>

													</tbody>
												</table>
											</td>
										</tr>
										<tr>
											<td colspan="1" align="center">
												<button id="newbut" onclick="clientFormDataSearch();">Search</button>
												<span class="space"></span>
												<button id="clearbut" onclick="clear1();">
													Clear <span class="glyphicon glyphicon-erase"></span>
												</button>
											</td>
											<td><button onclick="newClientReg();">Reg. New</button>
												<span class="space"></span>
												<button onclick="alterClient();">Alter</button> <span
												class="space"></span>
												<button onclick="deleteClient();">Delete</button></td>
										</tr>
									</tbody>
								</table>
								<br>
								<div class="scroll">
									<table id="telClientTableList" border="0" bordercolor="olive"
										class="table-condensed alertTab" width="100%" >
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
				</td>
			</tr>
		</tbody>
	</table>
</div>