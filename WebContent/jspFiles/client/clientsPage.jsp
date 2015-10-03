<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<!-- Bootstrap Core CSS -->
<link href="../../bootstrap/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="../../bootstrap/css/simple-sidebar.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="../../bootstrap/css/sb-admin.css" rel="stylesheet">

<!-- Custom Fonts -->
<link href="../../bootstrap/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">

<link href="../../jquery-ui/jquery-ui.min.css" rel="stylesheet">
<link href="../../jquery-ui/jquery-ui.theme.min.css" rel="stylesheet">
<link href="../../jquery-ui/jquery-ui.structure.min.css"
	rel="stylesheet">

<!-- flot charts -->
<link href="../../flot_f/examples.css" rel="stylesheet" type="text/css">

<style type="text/css">
.artdAlert {
	position: fixed;
	bottom: -30;
	right: 0;
	width: 300px;
	/* border: 3px solid #8AC007;  */
}
</style>

<title>Client's Page</title>

<%
	HttpSession ses = request.getSession();
%>

<script type="text/javascript">
window.persId="<%=ses.getAttribute("primeKey")%>"
	function eid() {
		alert(persId);
	}
</script>
</head>
<body onload="divhide()";>

	<div id="wrapper">

		<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#"
					onclick="manSeverClientTupeling(); eid();">Client</a>
			</div>

			<ul class="nav navbar-right top-nav">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown"> <i class="fa fa-envelope"></i> <b
						class="caret"></b></a>
					<ul class="dropdown-menu message-dropdown">
						<li class="message-preview"><a href="#"
							style="background-color: rgb(248, 248, 248);"
							onclick="cliColorToNorm();">
								<div id="cliMsgPanel" style="background-color: white;">
									<h5
										style="background-color: rgb(210, 210, 210); padding: 10px;">
										<strong></strong>
									</h5>
									<!-- <p class="divider"></li> -->
									<p style="font-size: 13pt;"></p>
									<p style="font-size: 9pt;"></p>
									<p class="small text-muted"
										style="font-size: 8pt; background-color: rgb(240, 240, 225);">
										<i> </i>
									</p>
								</div>
						</a></li>
						<li class="message-footer"><a href="#" onclick="goGetReqs();">Go
								Get Requests</a></li>
					</ul></li>

				<!-- <li><a href="#"> <span id="reqNrs"
						style="font-size: 25px; font-weight: bolder;"> - </span>
				</a></li> -->

				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" onclick="cliColorToNorm();"><i
						class="fa fa-arrow-up"></i> <b class="caret"></b></a>
					<ul class="dropdown-menu alert-dropdown" id="cliHeadNotify">
					</ul></li>
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown"><i class="fa fa-user"></i>
						${sessionScope.name} <b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="#"><i class="fa fa-fw fa-user"></i> Profile</a>
						</li>
						<li><a href="#"><i class="fa fa-fw fa-envelope"></i>
								Inbox</a></li>
						<li><a href="#"><i class="fa fa-fw fa-gear"></i> Settings</a>
						</li>
						<li class="divider"></li>
						<li><a href="#" onclick="cliLogOut();"><i
								class="fa fa-fw fa-power-off"></i> Log Out</a></li>
					</ul></li>
			</ul>
			<div id="cliMsgAlert" class="artdAlert"></div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container --> </nav>

		<!-- Sidebar -->
		<jsp:include page="./client_ex/sidebar.html"></jsp:include>
		<!-- /#sidebar-wrapper -->
		<!-- Page Content -->
		<div id="page-content-wrapper" style="background-color: azure">
			<div class="container-fluid">

				<!-- import manager main details -->
				<jsp:include page="./accounts.jsp"></jsp:include>
				<jsp:include page="./transaction.jsp"></jsp:include>
				<jsp:include page="./balance.jsp"></jsp:include>

			</div>
		</div>
		<!-- /#page-content-wrapper -->
	</div>
	<!-- /#wrapper -->

	<!-- jQuery -->
	<script src="../../bootstrap/js/jquery.js"></script>


	<!--clients scripts  -->
	<script src="../js/general.js"></script>
	<script src="./client_ex/cliInit.js"></script>
	<script src="./client_ex/cli.js"></script>
	<script src="./client_ex/account.js"></script>
	<script src="./client_ex/transaction.js"></script>
	<script src="./client_ex/balance.js"></script>


	<!-- flot charts -->
	<script src="../../flot_f/jquery.flot.min.js"></script>
	<script src="../../flot_f/jquery.flot.pie.min.js"></script>
	<script src="../../flot_f/jquery.flot.categories.min.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="../../bootstrap/js/bootstrap.min.js"></script>

	<!-- jquery-ui -->
	<script src="../../jquery-ui/jquery-ui.min.js"></script>

</body>
</html>