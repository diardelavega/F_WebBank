<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	HttpSession ses = request.getSession();
%>
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

<link href="../../jquery-ui/jquery-ui.min.css" rel="stylesheet">
<link href="../../jquery-ui/jquery-ui.theme.min.css" rel="stylesheet">
<link href="../../jquery-ui/jquery-ui.structure.min.css"
	rel="stylesheet">

<!-- flot charts -->
<!-- <link href="../../flot_f/examples.css" rel="stylesheet" type="text/css"> -->
<style>
body {
	font-family: Verdana, Arial, sans-serif;
	font-size: 12px;
}

.space {
	margin: 9px;
}
</style>

<script type="text/javascript">
window.telId="<%=ses.getAttribute("primeKey")%>"
</script>
<title>Teller's Page</title>
</head>
<body onload="telDivhide(); capitalize(); ">

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
				<a class="navbar-brand" href="#" onclick="telSeverClientTupeling();">Teller
				</a>
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
							onclick="colorToNorm();">
								<div id="telMsgPanel" style="background-color: white;">
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
						<li class="message-footer"><a href="#" onclicl="gtReply();">Read
								All New Messages</a></li>
					</ul></li>
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" onclick="colorToNorm();"> <i
						class="fa fa-arrow-up"></i> <b class="caret"></b></a>
					<ul class="dropdown-menu alert-dropdown" id="telHeadNotify">
						<!-- <li><a href="#">Alert Name <span
								class="label label-default">Alert Badge</span></a></li> -->

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
						<li><a href="../logout.jsp"><i
								class="fa fa-fw fa-power-off"></i> Log Out</a></li>
					</ul></li>
			</ul>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container --> </nav>

		<!-- Sidebar -->
		<jsp:include page="./tel_ex/sidebar.html"></jsp:include>
		<!-- /#sidebar-wrapper -->

		<!-- Page Content -->
		<div id="page-content-wrapper" style="background-color: azure">
			<div class="container-fluid">

				<jsp:include page="./mainPageTelPart.jsp"></jsp:include>
				<jsp:include page="./transPageTelPart.jsp"></jsp:include>
				<jsp:include page="./ocPageTelPart.jsp"></jsp:include>
				<jsp:include page="./replyAlert.jsp"></jsp:include>

			</div>
		</div>
		<!-- /#page-content-wrapper -->

	</div>
	<!-- /#wrapper -->

	<!-- jQuery -->
	<script src="../../bootstrap/js/jquery.js"></script>

	<!-- teller scripts -->
	<script src="../js/general.js"></script>
	<script src="./tel_ex/telInit.js"></script>
	<script src="./tel_ex/tel.js"></script>
	<script src="./tel_ex/main.js"></script>
	<script src="./tel_ex/transactions.js"></script>
	<script src="./tel_ex/oc.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="../../bootstrap/js/bootstrap.min.js"></script>

	<!-- jquery-ui -->
	<script src="../../jquery-ui/jquery-ui.min.js"></script>

</body>
<!-- <script type="text/javascript">
	telSeverClientTupeling();
</script> -->
</html>