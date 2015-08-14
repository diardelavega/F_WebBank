<%@page import="entity.Employee"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script src="../../bootstrap/js/jquery.js">

</script>
</head>
<body>

<p>req:${requestScope.primeKey}</p>
<p>ses: ${sessionScope.primeKey}</p>

<c:forEach items="${el}" var="element">
fname:${element.fname }<br>
get_fname:${element.getFname() }<br>
lname:${element.lname }<br>
line:${element.print()}<br>
element:${element }<br><br><br>
<%--     <c:out value="${element.getEmpId() +' '+element.getFname()}"/>
    <c:out value="${element.getLname()}"/>
     <c:out value="${element}"/>
 --%>    
    
</c:forEach>

</body>
</html> 