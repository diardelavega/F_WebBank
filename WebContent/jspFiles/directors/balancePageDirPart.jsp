<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

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
					<button id="balsearch">OK</button>
				</td>
			</tr>
			<tr><td>
			<br>
			</td></tr>
			<tr>
				<td colspan="3">
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
				</td>
			</tr>
		</tbody>
	</table>
</div>