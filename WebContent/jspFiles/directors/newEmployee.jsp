<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="functions.DirectorFunctions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<%
	Integer primeKey = (Integer) request.getSession().getAttribute(
			"primeKey");
	if (primeKey == null) {
		response.sendRedirect("../Test.jsp");
	}
	DirectorFunctions df = new DirectorFunctions(primeKey);
	long empId = df.createEmployee(request.getParameter("fname"),
			request.getParameter("lname"),
			request.getParameter("address"),
			request.getParameter("pos"), request.getParameter("mail"),
			request.getParameter("password"));

	if (empId == -1) {
		response.sendRedirect("../Error.jsp");
	} else {
		session.setAttribute("validity", "employee registered");
	}
	/* fname=request.getParameter("fname"); */
	response.sendRedirect("../director");
%>
<body>

</body>
</html>