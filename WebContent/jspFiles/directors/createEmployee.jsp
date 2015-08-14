<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>create employee</title>
</head>
<body>
	hello o o !
	<h1>${sessionScope.name}</h1>
	<table style="width: 100%;" border="1">
		<tbody>
			<tr>
				<th>linkbar</th>
				<th colspan="2">data</th>
			</tr>

			<tr>
				<td><jsp:include page="../linkbar.jsp"></jsp:include></td>
				<td colspan="2">
					<form id="f1" method="post" action="../../AddEmployee.do" >
						<table style="width: 60%; margin-left: 50px;">
							<tr>
								<td><label>first name:</label></td>
								<td><input type="text" name="fname"></td>
							</tr>
							<tr>
								<td><label>last name:</label></td>
								<td><input type="text" name="lname"></td>
							</tr>
							<tr>
								<td><label>address:</label></td>
								<td><input type="text" name="address"></td>
							</tr>
							<tr>
								<td><label>e-mail:</label><p>xxx@webank.com</p></td>
								<td><input type="text" name="mail"></td>
								<!--compose the email automatically by uniting name and surname -->
								<!--  and add @webank.com-->
							</tr>
							<tr>
								<td><label>password:</label></td>
								<td><input type="text" name="password"></td>
							</tr>
							<tr>
								<td><label>position:</label></td>
								<td><select name="pso"><option value="TELLER">TELLER</option>
										<option value="MNAGER">MNAGER</option>
										<option value="DIRECTOR">DIRECTOR</option>
								</select></td>
							</tr>
							<tr>
								<td colspan="2"><button style="margin-left: 80px"
										type="submit">SAVE</button></td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
		</tbody>
	</table>
</body>
</html>

