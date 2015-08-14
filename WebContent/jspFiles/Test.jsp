<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%-- <jsp:useBean id="log" class="comon.LogIn" scope="page"></jsp:useBean>
<jsp:setProperty property=" " name=""/> --%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Test.JSP</title>
<%
	HttpSession ses = request.getSession();
%>
</head>
<body>
	<h1>
		Hello o o o
		<%=ses.getAttribute("name")%></h1>


	<p>primary key is ${sessionScope.primeKey}</p>
	<h4>
		primary key is
		<%=ses.getAttribute("primeKey")%></h4>


	<form method="post" action="../EmployeeInfo.do">
<p>get employees <button type="submit" >get</button></p>
</form>
		<hr>
<jsp:include page="linkbar.jsp"></jsp:include>


</body>
</html>