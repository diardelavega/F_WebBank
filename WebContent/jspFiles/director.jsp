<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>DIRECTOR</title>
</head>
<body>
hello o o !
<h1>${sessionScope.name}</h1>
<h5>${sessionScope.validity}</h5>
	<form id="f1" action="" method="get">
		<p>
			<button type="submit" onclick="create();">create Employee</button>
			<input id="cr" type="text" name="crEmpId">
		</p>
		<p>
			<button type="submit" onclick="dele();">delete Employee</button>
			<input id="del" type="text" name="delEmpId">
		</p>
		<p>
			<button type="submit" onclick="get();">get Employee</button>
			<input id="get" type="text" name="getEmpId">
		</p>
		<p>
			<button type="submit" onclick="getAll();">get All Employee</button>
		</p>
	</form>
	
	<jsp:include page="./linkbar.jsp"></jsp:include>
	<script type="text/javascript">
		var form = document.getElementById("f1");
		function create() {
/* document.f1.acction */
			form.setAttribute("action", "./directors/createEmployee.jsp");
		};
		function dele() {
			form.setAttribute("action", "./directors/deleteEmployee.jsp");
		};
		function get() {
			form.setAttribute("action", "./directors/getEmployee.jsp");
		};
		function getAll() {
			form.setAttribute("action", "./directors/getAllEmployee.jsp");
		};
	</script>
</body>
</html>