<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!-- <link href="../../flot_f/examples.css" rel="stylesheet" type="text/css"> -->
<div id="balanceDir" class="hidable">
	<table>
		<thead>
			<tr>
				<th colspan="3" align="center"><h4>search for the balance
						in the date range</h4></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>From Date:<input type="text" id="datepicker1"
					class="datePicker">
				</td>
				<td>To Date:<input type="text" id="datepicker2"
					class="datePicker">
				</td>
				<td>
					<button id="balsearch" onclick="search();">OK</button>
				</td>
			</tr>
		</tbody>
	</table>
	<hr>
	<table>
		<thead></thead>
		<tbody>
			<tr>
				<td><br></td>
			</tr>
			<tr>
				<td colspan="3">
					<!-- panel header -->
					<ul class="nav nav-tabs" role="tablist">
						<li class="active"><a data-toggle="tab" href="#balChart">balance
								chart </a></li>
						<li><a data-toggle="tab" href="#balTab">balance table</a></li>
					</ul>

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
								<table id="balTableList" border="1" class="table-condensed">
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
				</td>
			</tr>
		</tbody>
	</table>
</div>