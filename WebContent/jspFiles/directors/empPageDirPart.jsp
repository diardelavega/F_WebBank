<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<style>
td {
	padding-top: 5px;
	padding-bottom: 5px;
	padding-left: .5em;
	padding-right: .5em;
}

.Container {
	width: 610px;
	overflow: auto;
}

.Content {
	/*width: 300px;*/
	
}

.Flipped, .Flipped .Content {
	transform: rotateX(180deg);
	-ms-transform: rotateX(180deg); /* IE 9 */
	-webkit-transform: rotateX(180deg); /* Safari and Chrome */
}
</style>
<div id="empDir" class="hidable">

	<table>
		<tbody>
			<!-- divade the page to display the results -->
			<tr>
				<td>

					<ul class="nav nav-tabs" role="tablist">
						<li class="active" onclick="enableId();"><a data-toggle="tab"
							href="#info">Employee Info</a></li>
						<li onclick="disableid();"><a data-toggle="tab" href="#new">New
								Employee</a></li>
					</ul>
					<div class="tab-content">

						<div id="new" class="tab-pane  ">
							<table class="normal"
								style="margin: 30px; margin-left: 10px; padding: 10px;">
								<tr>
									<td><label> F. Name &nbsp;</label></td>
									<td><input type="text" name="fname1"></td>
									<td><div id=extrabutton>
											<button id="clearbut" onclick="clear1();">
												<span class="glyphicon glyphicon-erase"></span>
											</button>
										</div></td>
								</tr>
								<tr>
									<td><label> L. Name &nbsp;</label></td>
									<td><input type="text" name="lname1"></td>
								</tr>
								<tr>
									<td><label> Address&nbsp;</label></td>
									<td><input type="text" name="address1"></td>
								</tr>
								<tr>
									<td><label> E-mail&nbsp;</label></td>
									<td><input id="email" onkeyup="addemail(this);"
										type="text" name="email1"></td>
								</tr>
								<tr>
									<td><label> Password&nbsp;</label></td>
									<td><input type="text" name="psw1"></td>
								</tr>
								<tr>
									<td><label> Possition&nbsp;</label></td>
									<td><select id="possition1" name="possition1">
											<option value="TELLER">TELLER</option>
											<option value="MANAGER">MANAGER</option>
											<option value="DIRECTOR">DIRECTOR</option>
									</select></td>
								</tr>
								<tr id="trid1" style="visibility: collapse">
									<td><label> Emp. Id &nbsp;</label></td>
									<td><input type="text" name="id1"></td>
								</tr>
								<tr>
									<td colspan="2" align="center"><input id="newbut"
										onclick="newEmployee();" type="button" value="SAVE"></td>
								</tr>
							</table>
						</div>

						<div id="info" class="tab-pane active ">
							<table class="normal"
								style="margin: 30px; margin-left: 10px; padding: 10px;">
								<tr>
									<td><label> F. Name &nbsp;</label></td>
									<td><input type="text" name="fname2"></td>
									<td><div id=extrabutton>
											<button id="clearbut" onclick="clear2();">
												<span class="glyphicon glyphicon-erase"></span>
											</button>
										</div></td>
								</tr>
								<tr>
									<td><label> L. Name &nbsp;</label></td>
									<td><input type="text" name="lname2"></td>
								</tr>
								<tr>
									<td><label> Address&nbsp;</label></td>
									<td><input type="text" name="address2"></td>
								</tr>
								<tr>
									<td><label> E-mail&nbsp;</label></td>
									<td><input id="email2" onkeyup="addemail(this);"
										type="text" name="email2"></td>
								</tr>
								<tr>
									<td><label> Password&nbsp;</label></td>
									<td><input type="text" name="psw2"></td>
								</tr>
								<tr>
									<td><label> Possition&nbsp;</label></td>
									<td><select id="possition2" name="possition2">
											<option value=""></option>
											<option value="TELLER">TELLER</option>
											<option value="MANAGER">MANAGER</option>
											<option value="DIRECTOR">DIRECTOR</option>
									</select></td>
								</tr>

								<!-- onkeyup="disableFields();" -->
								<tr id="trid2" style="visibility: visible">
									<td><label> Emp. Id &nbsp;</label></td>
									<td><input type="text" name="id2"></td>
								</tr>
								<tr>
									<td colspan="3" align="center"
										style="margin: 15px; padding-left: 10px; padding-right: 10px">
										<button id="delbut" onclick="deleteEmployee();"
											style="visibility: collapse">DELETE</button> <input
										id="searchbut" onclick="getEmpInfo();" type="button"
										value="SEARCH">
										<button id="altbut" onclick="alterEmployee();"
											style="visibility: collapse">ALTER</button>
									</td>

								</tr>
							</table>

						</div>
					</div> <!-- tab content -->

				</td>
				<td>
					<div id="emplist" class="container-fluid; ">
						<div class="Container Flipped;">

							<!-- <div id=extrabutton>
							<button id="clearbut" onclick="clear2();"><span class="glyphicon glyphicon-refresh"></span></button>
						</div> -->
							<!-- style="background-color: gray; padding: 30px; margin: 20px" -->
							<div class="Content">
								<table id="empTableList" border="1" class="table-condensed">
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