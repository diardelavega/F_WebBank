<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- Bootstrap Core CSS -->
<link href="../../bootstrap/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="../../bootstrap/css/simple-sidebar.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="../../bootstrap/css/sb-admin.css" rel="stylesheet">

<!-- Custom Fonts -->
<link href="../../bootstrap/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">

<!-- flot charts
<link href="../../flot_f/examples.css" rel="stylesheet" type="text/css"> -->

<style type="text/css">
button {
	width: 80px;
}
</style>
<title>Manager</title>
<%
	HttpSession ses = request.getSession();
%>

<script type="text/javascript">
window.manEmpId="<%=ses.getAttribute("primeKey")%>"

	function eid() {
		alert(manEmpId);
	}
</script>
</head>
<body onload="divhide(); capitalize(); ">

	<div id="wrapper">
		<!-- Navigation -->
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
					onclick="manSeverClientTupeling(); eid();">Manager</a>
			</div>
			<!-- Collect the nav links, forms, and other content for toggling -->
			<!--  <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li>
                        <a href="#">About</a>
                    </li>
                    <li>
                        <a href="#">Services</a>
                    </li>
                    <li>
                        <a href="#">Contact</a>
                    </li>
                </ul>
            </div>-->
			<ul class="nav navbar-right top-nav">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown"> <i class="fa fa-envelope"></i> <b
						class="caret"></b></a>
					<ul class="dropdown-menu message-dropdown">
						<li class="message-preview"><a href="#"
							style="background-color: rgb(248, 248, 248);"
							onclick="manColorToNorm();">
								<div id="manMsgPanel" style="background-color: white;">
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

				<li><a href="#"> <span id="reqNrs"
						style="font-size: 25px; font-weight: bolder;"> - </span>

				</a></li>

				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" onclick="manColorToNorm();"><i
						class="fa fa-arrow-up"></i> <b class="caret"></b></a>
					<ul class="dropdown-menu alert-dropdown" id="manHeadNotify">
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
						<li><a href="#" onclick="logOut();"><i
								class="fa fa-fw fa-power-off"></i> Log Out</a></li>
					</ul></li>
			</ul>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container --> </nav>

		<!-- Sidebar -->
		<jsp:include page="./man_ex/sidebar.html"></jsp:include>
		<!-- /#sidebar-wrapper -->



		<!-- Page Content -->
		<div id="page-content-wrapper" style="background-color: azure">
			<div class="container-fluid">

				<!-- import manager main details -->
				<jsp:include page="./mainPageManPart.jsp"></jsp:include>
				<jsp:include page="./reqPartManPage.jsp"></jsp:include>

			</div>
		</div>
		<!-- /#page-content-wrapper -->
	</div>
	<!-- /#wrapper -->

	<!-- jQuery -->
	<script src="../../bootstrap/js/jquery.js"></script>

	<!-- manager scripts -->
	<script src="./man_ex/manInit.js"></script>
	<script src="./man_ex/man.js"></script>
	<script src="./man_ex/manMain.js"></script>
	<script src="./man_ex/request.js"></script>
	<script src="../js/general.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="../../bootstrap/js/bootstrap.min.js"></script>

	<!-- jquery-ui -->
	<script src="../../jquery-ui/jquery-ui.min.js"></script>


</body>
<!-- <script type="text/javascript">
		manSeverClientTupeling();
	</script> -->
</html>