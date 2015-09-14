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
	<div id="requestDetails">
		<p id="requestType"></p>
		<p id="clients"></p>
		<p id="accountType"></p>
		<p id="accountFrom"></p>
		<p id="accountTo"></p>
		<p id="ammount"></p>
	</div>
	<div id="responseDetails" style="visibility: collapse;">
		<hr>
		<label>Decision :</label><select>
			<option value="APPROVE">APPROVE</option>
			<option value="DENIE">DENIE</option>
		</select> <label>Note :</label>
		<textarea rows="" cols=""></textarea>
	</div>
</div>