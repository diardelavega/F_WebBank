<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Destination</title>
<%
	HttpSession ses = request.getSession();
	String display;
	if (ses.getAttribute("primeKey").equals("")) {
		display = "WRONG LOG IN";
	} else {
		display = ses.getAttribute("primeKey").toString();
	}
%>
</head>
<body>
	<h1>
		<%=display%>
	</h1>

</body>
</html>