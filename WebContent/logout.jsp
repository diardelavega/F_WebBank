<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>LOG_OUT</title>
<%
	HttpSession ses = request.getSession();
	if (!ses.isNew()) {
		
		ses.invalidate();
	}
%>
</head>
<body>
<jsp:forward page="./LogIn.jsp"></jsp:forward>
	<script src="./bootstrap/js/jquery.js"></script>
	<script src="./jspFiles/js/general.js"></script>
</body>
</html>