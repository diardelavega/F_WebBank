<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Log In</title>
<link rel="stylesheet" href="./css/style.css">
<style type="text/css">
body {
	background-image: url("img/texture/blackFlower.jpg");
}
</style>
<script type="text/javascript">
	/* function foo() {
		$("#errorMsg").text("IIIIIIaaaaaaaaaooaaaaaaIIIIIasasIIIIN");
	} */
</script>
</head>
<body>

	<div id="errorMsg"
		style="text-align: center; vertical-align: text-top;">
		<%
			HttpSession ses = request.getSession(false);
			if (ses.getAttribute("validity") != "ok")
		%>
		<p style="color: red;">${sessionScope.validity}</p>
	</div>

	<div id="logmsk" style="display: block;">
		<div id="userbox">
			<form action="./Log.do" method="post">
				<img alt="logo" src="img/end_logo.png" width="150" height="120"
					align="left">
				<h1 id="signup"
					style="background-color: rgb(118, 171, 219); display: inline-block; vertical-align: middle;">
					<span>Sign In</span>
				</h1>
				<input id="name" placeholder="ID" type="text" name="usr"
					style="background-color: rgb(255, 255, 255);"> <input
					id="pass" type="password" placeholder="Password" name="psw"
					style="background-color: rgb(255, 255, 255);"><br> <br>
				<button id="signupb" type="submit">Sign in</button>
			</form>
		</div>
	</div>


	<script src="./bootstrap/js/jquery.js"></script>
	<script src="./js/general.js"></script>

</body>
</html>