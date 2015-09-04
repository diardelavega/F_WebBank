<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<style>
label, input {
	margin: 5px;
}

body {
	font-family: Verdana, Arial, sans-serif;
	font-size: 12px;
}
</style>


<div id="ocTel" class="hidable">
	<table>
		<tbody>
			<tr>
				<td>
					<div class="container-fluid">
						<h3>Confirmation Actions:</h3>
						<!-- <div
							style="background-color: rgb(220, 220, 220); width: 100%; position: static;"> -->
						<ul class="nav nav-tabs ">
							<li class="active"><a data-toggle="tab" href="#open"><strong>OPEN</strong></a></li>
							<li><a data-toggle="tab" href="#close"><strong>CLOSE</strong></a></li>
							<!-- <li><a data-toggle="tab" href="#reg"><strong>REGISTER</strong></a></li>
							<li><a data-toggle="tab" href="#p_1k">+1000
										TRANSACTION</a></li>
							<li><a data-toggle="tab" href="#p_six">+6
										ACCOUNT OPEN</a></li> -->
						</ul>
						<!-- </div> -->
						<br>
						<div class="tab-content">
							<div id="open" class="tab-pane fade in active">
								<table border="0" id="telOpen" class="normal"
									style="margin: 30px; margin-left: 10px; padding: 10px;">
									<tbody>
										<tr>
											<td width="65%">
												<!--small inner table  --> <!-- <table>
											<tbody>
											<tr><td><label>Personal Id</label><input type="text" name="telOpenId"> </td></tr>
											</tbody>
											</table> --> <!--small inner table  -->
												<div id="ocPersIdFields">
													<label>Personal Id 1 :</label><input type="text"
														name="telOpenId1"><br> <label>Personal
														Id 2 :</label><input type="text" name="telOpenId2"><br>
													<label>Personal Id 3 :</label><input type="text"
														name="telOpenId3"><br> <label>Personal
														Id 4 :</label><input type="text" name="telOpenId4">
													<!-- <span
														class=space></span><a href="#"><span class="glyphicon glyphicon-plus"
														onclick="addPersIdField();"></span></a> -->
													<br>
												</div>
												<div style="overflow: auto; width: 50%; margin-top: 10px;">
													<center>
														<button onclick="openAccount();">OK</button>
														&emsp;
														<button onclick="clearOpenAcc();">Clear</button>
													</center>
												</div>

											</td>
											<td width="35%"><div id="telOpenResponse" class="box-r"></div>

												<p>Lorem ipsum dolor sit amet, consectetur adipisicing
													elit, sed do eiusmod tempor incididunt ut labore et dolore
													magna aliqua.</p></td>
										</tr>
									</tbody>
								</table>

							</div>
							<div id="close" class="tab-pane fade">

								<table border="1" id="telClose" class="normal"
									style="margin: 30px; margin-left: 10px; padding: 10px;">
									<tbody>
										<tr>
											<td width="32%"><label>Personal Id 1 :</label><input
												type="text" name="telCloseId1"><br> <label>Personal
													Id 2 :</label><input type="text" name="telCloseId2"><br>
												<label>Personal Id 3 :</label><input type="text"
												name="telCloseId3"><br> <label>Personal
													Id 4 :</label><input type="text" name="telCloseId4"><br>
											</td>
											<td width="32%"><label>Account Nr. :</label><input
												type="text" name="telCloseAcc"><br></td>
											<td><div id="telCloseResponse" class="box-r"></div>
												<p>Ut enim ad minim veniam, quis nostrud exercitation
													ullamco laboris nisi ut aliquip ex ea commodo consequat.</p></td>
										</tr>
										<tr>
											<td colspan="2">
												<div style="overflow: auto; width: 100%;">
													<center>
														<button onclick="tellCloseAcc();">OK</button>
														&emsp;
														<button onclick="tellClearClose();">Clear</button>
													</center>
												</div>
											</td>
										</tr>
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