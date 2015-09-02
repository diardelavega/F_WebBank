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

/* td {
	padding-top: 5px;
	padding-bottom: 5px;
	padding-left: .5em;
	padding-right: 5em;
	margin: 5px;
} */
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
					<div class="container-fluid">
						<h3>Transactions:</h3>
						<ul class="nav nav-tabs ">
							<li class="active"><a data-toggle="tab" href="#dep"><strong>DEPOSITE</strong></a></li>
							<li><a data-toggle="tab" href="#transf"><strong>TRANSFERS</strong></a></li>
							<li><a data-toggle="tab" href="#with"><strong>WITHDRAW</strong></a></li>
							<li><a data-toggle="tab" href="#manInfo"><strong>INFO</strong></a></li>
						</ul>

						<div class="tab-content">
							<div id="dep" class="tab-pane fade in active">

								<table border="0" id="telDeposite">
									<tbody>
										<tr>
											<td width="65%">
												<div class="box-l">
													<label>Account Nr. :</label><input type="text" name="accNr">
													<span class="space"></span> <label>Amount :</label> <input
														type="text" name="amount">

													<center>
														<label>Note :</label>
														<textarea rows="4" cols="60" name="note"></textarea>
													</center>
													<br>
													<center>
														<button id="telDepOk" onclick="deposite();">OK</button>
														<span class="space"></span>
														<button id="telDepClear" onclick="clearDep();">Clear</button>
													</center>
												</div>
											</td>
											<td width="35%"><div id="telDepResponse" class="box-r"></div>

												<p>Lorem ipsum dolor sit amet, consectetur adipisicing
													elit, sed do eiusmod tempor incididunt ut labore et dolore
													magna aliqua.</p></td>
										</tr>

									</tbody>
								</table>

							</div>
							<div id="transf" class="tab-pane fade">

								<table border="0" id="telTransfer">
									<tbody>
										<tr>
											<td width="70%">
												<div class="box-l">
													<label>Account From :</label><input type="text"
														class="accNr" name="accNrFrom"> <span
														class="space"></span> <label>Account To :</label> <input
														type="text" name="accNrTo" class="accNr"> <br>
													<label>Personal ID :</label><input type="text"
														class="persId" name="transfPersId"> <span class="space"></span>
													<label>Amount :</label> <input type="text" name="transfAmount"
														class="amount">
													<br>
													<center>
														<button id="telDepOk">OK</button>
														<span class="space"></span>
														<button id="telDepClear">Clear</button>
													</center>
												</div>
											</td>
											<td width="30%"><div id="telDepResponse" class="box-r"></div>
												<p>Ut enim ad minim veniam, quis nostrud exercitation
													ullamco laboris nisi ut aliquip ex ea commodo consequat.</p></td>
										</tr>
									</tbody>
								</table>

							</div>
							<div id="with" class="tab-pane fade">
								<table border="0" id="telWithdraw">
									<tbody>
										<tr>
											<td width="65%">
												<div class="box-l">
													<label>Account From :</label><input type="text"
														class="accNr" name="withAccNr"> <span
														class="space"></span> <br> <label>Personal ID
														:</label><input type="text" name="withPersId" class="persId">
													<br> <label>Amount :</label> <input type="text"
														class="amount" name="withAmount">
												</div> <br>
												<center>
													<button id="telDepOk" onclick="withdraw();">OK</button>
													<span class="space"></span>
													<button id="telDepClear" onclick="clearWithdraw();">Clear</button>
												</center>
											</td>
											<td width="35%"><div id="telDepResponse" class="box-r"></div>
												<p>Sed ut perspiciatis unde omnis iste natus error sit
													voluptatem accusantium doloremque laudantium, totam rem
													aperiam.</p></td>
										</tr>
									</tbody>
								</table>



							</div>
							<div id="manInfo" class="tab-pane fade">
								<h1>maby it souldn't be here ??</h1>
							</div>

						</div>
					</div>
				</td>
			</tr>
		</tbody>
	</table>
</div>