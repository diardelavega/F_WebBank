<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<style>
.accountDisply td {
	text-align: left;
	padding-top: 5px;
	padding-left: 10px;
	padding-right: 10px;
	border-spacing: 5px;
	/* min-width: 30px; */
}

.accountDisply tr {
	border-bottom: 1px solid black;
	vertical-align: bottom;
}

table.format {
	table-layout: fixed;
	width: 400px;
	margin-top: 30px;
	border-spacing: 5px;
}

table.format td {
	overflow: hidden;
	padding-left: 10px;
	padding-right: 10px;
}
</style>
<script type="text/javascript">
	
</script>
<div id="accCli" class="hidable">

	<h3>Your Account(s)</h3>
	<br>
	<div class="container-fluid">
		<div style="background-color: rgb(220, 220, 220); width: 100%">
			<ul class="nav nav-pills" id="accountPills">
			</ul>
		</div>

		<br>
		<div class="tab-content" id="accountPillContenet"></div>
	</div>
	<div id="transferFunc" style="margin: 30px; display: none;">
		<hr>
		<table border="0" id="cliTransfer" class="format">
			<tbody>
				<tr>
					<td width="720px">
						<table>
							<tbody>
								<tr>
									<td><label>Account From :</label></td>
									<td><input id="cliTransAccFrom" type="text" class="accNr"
										name="accNrFrom"></td>
									<td><label>Account To :</label></td>
									<td><input type="text" name="accNrTo" class="accNr">
									</td>
								</tr>
								<tr>
									<td><label>Personal ID :</label></td>
									<td><input id="cliTransPersId" type="text" class="persId"
										name="transfPersId"></td>
									<td><label>Amount :</label></td>
									<td><input type="text" name="transfAmount" class="amount"></td>
								</tr>
								<tr align="center">
									<td colspan="4" style="padding-top: 10px">
										<button id="telTransOk" onclick="cliTransfer();">OK</button> <span
										class="space"></span>
										<button id="cliTransClear" onclick="cliClearTransfer();">Clear</button>
									</td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
	</div>

</div>