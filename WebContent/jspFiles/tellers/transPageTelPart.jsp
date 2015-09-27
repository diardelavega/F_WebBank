<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<style>
.space {
	margin: 9px;
}

.box-l {
	/* width: 60%; */
	top: 46%;
	left: 2%;
	overflow: auto;
}

.box-r {
	overflow: auto;
	/* width: 40%; */
	top: 46%;
	right: 2%;
}

table.format {
	table-layout: fixed;
	width: 400px;
	margin-top: 30px;
}

table.format td {
	overflow: hidden;
}

table, th, td {
	/* border: 1px solid black; */
	
}

body {
	font-family: Verdana, Arial, sans-serif;
	font-size: 12px;
}
</style>
<div id="transTel" class="hidable">
	<table>
		<tbody>
			<tr>
				<td>
					<table width="850px">
						<tr>
							<td>
								<h3>Transactions</h3>
							</td>
							<td align="left" width="30px">
								<div class="artAlert" id="telTransMsgAlert"></div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<div class="container-fluid">

						<ul class="nav nav-tabs ">
							<li class="active"><a data-toggle="tab" href="#dep"><strong>DEPOSITE</strong></a></li>
							<li><a data-toggle="tab" href="#transf"><strong>TRANSFERS</strong></a></li>
							<li><a data-toggle="tab" href="#with"><strong>WITHDRAW</strong></a></li>
						</ul>

						<div class="tab-content">
							<div id="dep" class="tab-pane fade in active">

								<table border="0" id="telDeposite" class="format">
									<tbody>
										<tr>
											<td width="620px">
												<!-- <div class="box-l"> --> <label style="margin-left: 18px;">Account Nr. :</label><input
												type="text" name="accNr"> <span class="space"></span>
												<label>Amount :</label> <input type="text" name="amount"
												class="amount">

												<center>
													<label>Note :</label>
													<textarea class="note" rows="4" cols="60" name="note"></textarea>
												</center>
												<center style="padding-top: 10px">
													<button id="telDepOk" onclick="deposite();">OK</button>
													<span class="space"></span>
													<button id="telDepClear" onclick="clearDep();">Clear</button>
												</center> <!-- </div> -->
											</td>
											<td width="230px"><div id="telDepResponse" class="box-r"></div>
											</td>
										</tr>

									</tbody>
								</table>

							</div>
							
							<div id="transf" class="tab-pane fade">

								<table border="0" id="telTransfer" class="format">
									<tbody>
										<tr>
											<td width="620px">
												<table>
													<tbody>
														<tr>
															<td><label>Account From :</label></td>
															<td><input type="text" class="accNr"
																name="accNrFrom"></td>
															<td><label>Account To :</label></td>
															<td><input type="text" name="accNrTo" class="accNr">
															</td>
														</tr>
														<tr>
															<td><label>Personal ID :</label></td>
															<td><input type="text" class="persId"
																name="transfPersId"></td>
															<td><label>Amount :</label></td>
															<td><input type="text" name="transfAmount"
																class="amount"></td>
														</tr>
														<tr align="center">
															<td colspan="4" style="padding-top: 10px">
																<button id="telTransOk" onclick="tranfer();">OK</button> <span class="space"></span>
																<button id="telTransClear" onclick="clearTransfer();">Clear</button>
															</td>
														</tr>
													</tbody>
												</table>
											</td>
											<td width="230px"><div id="telDepResponse" class="box-r"></div>
											</td>
										</tr>
									</tbody>
								</table>

							</div>
							
							<div id="with" class="tab-pane fade">
								<table border="0" id="telWithdraw" class="format">
									<tbody>
										<tr>
											<td width="620px">
												<table border="0">
													<tbody>
														<tr>
															<td><label>Account From :</label></td>
															<td><input type="text" class="accNr"
																name="withAccNr"></td>
															<td><label>Personal ID :</label></td>
															<td><input type="text" name="withPersId"
																class="persId"></td>
														</tr>
														<tr>
															<!-- <td></td> -->
															<td colspan="2" align="right"><label>Amount :</label></td>
															<td colspan="2" align="left"><input type="text"
																class="amount" name="withAmount"></td>
															<!-- <td></td> -->
														</tr>
														<tr>
															<td colspan="4" style="padding-top: 10px" align="center">
																<button id="telWithOk" onclick="withdraw();">OK</button>
																<span class="space"></span>
																<button id="telWithClear" onclick="clearWithdraw();">Clear</button>
															</td>
														</tr>
													</tbody>
												</table>
											</td>
											<td width="230px"><div id="telDepResponse" class="box-r"></div>
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