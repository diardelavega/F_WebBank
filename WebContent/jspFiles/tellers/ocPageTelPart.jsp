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

table.format {
	table-layout: fixed;
	width: 400px;
}

table.format td {
	overflow: hidden;
}

button {
	width: 80px;
}
</style>


<div id="ocTel" class="hidable">
	<table>
		<tbody>
			<tr>
				<td>
					<table width="850px">
						<tr>
							<td>
								<h3>Confirmation Actions:</h3>
							</td>
							<td align="right" width="30px">
								<div style="width: 300px;" align="right" id="telOcMsgAlert"></div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<div class="container-fluid">
						<ul class="nav nav-tabs ">
							<li class="active"><a data-toggle="tab" href="#open"><strong>OPEN</strong></a></li>
							<li><a data-toggle="tab" href="#close"><strong>CLOSE</strong></a></li>
						</ul>

						<br>
						<div class="tab-content">
							<div id="open" class="tab-pane fade in active">
								<table border="0" id="telOpen" class="format"
									style="margin: 30px; margin-left: 10px; padding: 10px;">
									<tbody>
										<tr>
											<td width="330px">
												<div id="ocPersIdFields">
													<label>Personal Id 1 :</label><input type="text"
														name="telOpenId1"><br> <label>Personal
														Id 2 :</label><input type="text" name="telOpenId2"><br>
													<label>Personal Id 3 :</label><input type="text"
														name="telOpenId3"><br> <label>Personal
														Id 4 :</label><input type="text" name="telOpenId4"> <br>
												</div>
											</td>
											<td width="335px"><label>Account Type :</label> <select
												name="accType">
													<option value="b">BASICS CHECKING</option>
													<option value="i">INTEREST BARING</option>
													<option value="c">CERTIFICATE OF DEPSITE</option>
													<option value="m">MONEY MARKET DEPOSITE</option>
													<option value="s">SIMPLE BUSINESS</option>
											</select></td>
											<td width="200px"><div id="telOpenResponse">
													<!-- <a href="#" class="close" data-dismiss="alert"
														aria-label="close">&times;</a> -->
												</div></td>
										</tr>
										<tr>
											<td colspan="2" style="padding-top: 10px">
												<div style="overflow: auto; width: 100%;">
													<center>
														<button onclick="openAccount();">OK</button>
														&emsp;
														<button onclick="clearOpenAcc();">Clear</button>
													</center>
												</div>
											</td>
										</tr>
									</tbody>
								</table>

							</div>
							<div id="close" class="tab-pane fade">

								<table border="0" id="telClose" class="format"
									style="margin: 30px; margin-left: 10px; padding: 10px;">
									<tbody>
										<tr>
											<td width="330px"><label>Personal Id 1 :</label><input
												type="text" name="telCloseId1"><br> <label>Personal
													Id 2 :</label><input type="text" name="telCloseId2"><br>
												<label>Personal Id 3 :</label><input type="text"
												name="telCloseId3"><br> <label>Personal
													Id 4 :</label><input type="text" name="telCloseId4"><br>
											</td>
											<td width="330px"><label>Account Nr. :</label><input
												type="text" name="telCloseAcc"><br></td>
											<td width="200px"><div id="telCloseResponse"></div>
										</tr>
										<tr>
											<td colspan="2" style="padding-top: 10px">
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