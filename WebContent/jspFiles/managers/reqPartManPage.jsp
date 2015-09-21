<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<div id="reqMan" class="hidable">
	<div id="reqManField"></div>
	<h1>Evaluate your requests</h1>
	<hr>
	<button onclick="manGetRequest();">GET</button>
	<button onclick="manLeaveRequest();">LEAVE</button>
	<br> <br>

	<div id="requestDetails" >
		<span>Curent Request</span>
		<table id="curentRequest" class="alertTab">
			<tbody>
				<tr></tr>
			</tbody>
		</table>
		<div>
			<button class="btn btn-success">APPROVE</button>
			<span class="space"></span>
			<button class="btn btn-danger">DENNIE</button>
			<br> Note:
			<textarea rows="" cols=""></textarea>
		</div>

		<hr>
		<table id="allRequests" class="alertTab">
			<tbody>
				<tr></tr>
			</tbody>
		</table>
	</div>
</div>