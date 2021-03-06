<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<style>
div.scrollReqs {
	/* background-color: #00FFFF; */
	/* width: 100px; */
	height: 300px;
	overflow: scroll;
}
</style>
<div id="reqMan" class="hidable">
	<div id="reqManField"></div>
	<table width="850px">
		<tbody>
			<tr>
				<td><h1>Evaluate your requests</h1></td>
				<td align="right" width="30px"><div class="artAlert"
						id="manMsgAlertReq"></div></td>
			</tr>
		</tbody>
	</table>
	<hr>
	<p>
		<button onclick="manGetRequest();"
			style="width: 150pt; margin-left: 60pt">GET A NEW REQUEST</button>
	</p>
	<br> <br>
	<div id="requestDetails" style="background-color: rgb(235, 240, 240);">
		<span style="margin-left: 120px;"><strong>Curent
				Request</strong></span> <br> <br>
		<table id="curentRequest" class="alertTab">
			<tbody>
				<tr></tr>
			</tbody>
		</table>
		<div style="margin-left: 20px;">
			<button onclick="approve();" class="btn btn-success"
				style="width: 100px;">APPROVE</button>
			<span class="space"></span>
			<button onclick="denie();" class="btn btn-danger"
				style="width: 100px;">DENNIE</button>
			<span class="space"></span>
			<button onclick="manLeaveRequest();" class="btn btn-info"
				style="width: 100px;">LEAVE</button>
			<br> <br>
			<textarea id="manReqNote" rows="5" cols="50"
				placeholder="write your notes"></textarea>
			<br> <br>
		</div>
	</div>

	<hr>
	<div align="right">
	<button onclick="clearReviewedRequesrs();">
		Clear<span class="glyphicon glyphicon-erase"></span>
	</button>
	</div>
	<div class="scrollReqs">
		<table id="reviewedRequests" class="alertTab">
			<tbody>
				<tr></tr>
			</tbody>
		</table>
	</div>

</div>