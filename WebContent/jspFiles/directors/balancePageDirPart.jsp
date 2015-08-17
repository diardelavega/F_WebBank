<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!-- <link href="../../../flot_f/examples.css" rel="stylesheet" type="text/css">
<script language="javascript" type="text/javascript" src="../../../flot_f/jquery.flot.min.js"></script>
	<script language="javascript" type="text/javascript" src="../../../flot_f/jquery.flot.categories.min.js"></script> -->
<link href="../../flot_f/examples.css" rel="stylesheet" type="text/css">
<div id="balanceDir" class="hidable">
	<table>
		<thead></thead>
		<tbody>
			<tr>
				<td>From Date:<input type="text" id="datepicker1">
				</td>
				<td>To Date:<input type="text" id="datepicker2">
				</td>
				<td>
					<button id="balsearch" onclick="search();">OK</button>
				</td>
			</tr>
			<tr>
				<td><br></td>
			</tr>
			<tr>
				<td colspan="3">
					<!-- panel header -->
					<ul class="nav nav-tabs" role="tablist">
						<li class="active" onclick="showBalChart();"><a
							data-toggle="tab" href="#balTab">balance chart </a></li>
						<li onclick="showBalTab ();"><a data-toggle="tab"
							href="#balChart">balance table</a></li>
					</ul>

					<div class="tab-content">

						<div id="content">
							<div class="demo-container">
								<div id="placeholder" class="demo-placeholder"></div>
							</div>
						</div>

						<div id="balTab" class="tab-pane active" style="display: none">
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