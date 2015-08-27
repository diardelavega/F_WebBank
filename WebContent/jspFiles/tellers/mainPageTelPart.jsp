<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<style>
td {
	padding-top: 5px;
	padding-bottom: 5px;
	padding-left: .5em;
	padding-right: 5em;
}

body {
	font-family: Verdana, Arial, sans-serif;
	font-size: 12px;
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
				<td><label>Personal Id:&nbsp;</label><input type="text"
					id="telPersonalId"></td>
				<td><label>Account Nr.:&nbsp;</label><input type="text"
					id="telAccountNr"></td>
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
					<div style="background-color:rgb(220,220,220); width: 100%">
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
							<li><a data-toggle="pill" href="#regCli"
								onclick="telRegisterClient();"><strong>Register
										Client</strong></a></li>
						</ul>
						</div>
						<br>
						<div class="tab-content" >
							<div id="accs" class="tab-pane fade ">
								<h3>Account's Status :</h3>
								<!-- table with the response Account Status -->
								<table>
									<thead>
										<tr>
											<th>Account Id</th>
											<th>Open Date</th>
											<th>Balance</th>
											<th>Account Type</th>
											<th>Account Status</th>
										</tr>
									</thead>
									<tbody id="telAccountStatus">
										<tr></tr>
									</tbody>
								</table>

								<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit,
									sed do eiusmod tempor incididunt ut labore et dolore magna
									aliqua.</p>
							</div>
							<div id="a_coo" class="tab-pane fade">
								<h3>Account's Co-owners:</h3>
								<p>Ut enim ad minim veniam, quis nostrud exercitation
									ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
							</div>
							<div id="cli_a" class="tab-pane fade">
								<h3>Client's Accounts</h3>
								<p>Sed ut perspiciatis unde omnis iste natus error sit
									voluptatem accusantium doloremque laudantium, totam rem
									aperiam.</p>
							</div>
							<div id="regCli" class="tab-pane fade">
								<h3>Register Client:</h3>
								
								<!-- client registration Form -->
								<table class="normal"
									style="margin: 30px; margin-left: 10px; padding: 10px;">
									<tr>
										<td><label> F. Name &nbsp;</label></td>
										<td><input type="text" name="fname1"></td>
										<td><div id=extrabutton>
												<button id="clearbut" onclick="clear1();">
													<span class="glyphicon glyphicon-erase"></span>
												</button>
											</div></td>
									</tr>
									<tr>
										<td><label> L. Name &nbsp;</label></td>
										<td><input type="text" name="lname1"></td>
									</tr>
									<tr>
										<td><label> BirthDate&nbsp;</label></td>
										<td><input type="text" name="bdate" class="datePicker"></td>
									</tr>
									<tr>
										<td><label> Address&nbsp;</label></td>
										<td><input type="text" name="address1"></td>
									</tr>
									<tr>
										<td><label> phone Nr.&nbsp;</label></td>
										<td><input type="text" name="phone"></td>
									</tr>
									<tr>
										<td><label> E-mail&nbsp;</label></td>
										<td><input id="email" type="text" name="email1"></td>
									</tr>
									<tr>
										<td><label> Password&nbsp;</label></td>
										<td><input type="text" name="psw1"></td>
									</tr>
									<tr id="trid1" style="visibility: collapse">
										<td><label> Emp. Id &nbsp;</label></td>
										<td><input type="text" name="id1"></td>
									</tr>
									<tr>
										<td colspan="2" align="center"><input id="newbut"
											onclick="newEmployee();" type="button" value="SAVE"></td>
									</tr>
								</table>

							</div>
						</div>
					</div>
				</td>
			</tr>
		</tbody>
	</table>
</div>