<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="../EmpReg.do" method="post">
		<p>
			first-name: <input name="fname" type="text">
		</p>
		<p>
			last-name: <input name="lname" type="text">
		</p>
		<p>
			address: <input name="address" type="text">
		</p>

		<p>
			e-mail: <input name="mail" type="text">
		</p>
		<p>
			password: <input name="psw" type="text">
		</p>
		<p>
			position: <select name="pos">
				<option value="TELLER">TELLER</option>
				<option value="TELLER">MANGER</option>
				<option value="TELLER">DIRECTOR</option>
			</select>
		</p>



		<button type="submit">register</button>
	</form>

</body>
</html>