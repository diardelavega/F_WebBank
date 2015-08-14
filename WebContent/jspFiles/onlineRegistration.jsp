<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="/WebBank/OnlineRegistration" method="post">
		<p>
			first name<input type="text" name="fname">
		<p>
		<p>
			last name<input type="text" name="lname">
		<p>
		<p>
			birth date<input type="text" name="bdate">
		<p>
		<p>
			Personal Id<input type="text" name="pid">
		<p>
		<p>
			address<input type="text" name="address">
		<p>
		<p>
			phone nr.<input type="text" name="phone">
		<p>
		<p>
			e-mail<input type="text" name="mail"> 
		<p>
		<p>
			password<input type="password" name="psw">
		<p>
		<p>
			<input type="submit" value="apply">
		<p>
	</form>
</body>
</html>