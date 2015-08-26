<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<%-- 
<%
	HttpSession ses = request.getSession(false);
	if (ses.getAttribute("validity") != "ok") {
	}
%> --%>

</head>
<body onload="foo();">
	<form action="../Log.do" method="post">
		<p>
			username <input type="text" name="usr" >
		</p>

		<p>
			password<input name="psw" type="password">
		</p>
		<input type="submit" value="logIn">
	</form>
	<div>
		<%-- <%
			HttpSession ses = request.getSession(false);
			if (ses.getAttribute("validity") != "ok") 
		%> --%>
		
		<p>${sessionScope.validity}</p>
	</div>
	
	
	
	<!-- jQuery -->
	<script src="../bootstrap/js/jquery.js"></script>
	<script src="./js/general.js"></script>
</body>
</html>