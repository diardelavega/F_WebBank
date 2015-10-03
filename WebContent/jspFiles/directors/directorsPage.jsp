<!--%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%-->
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
	bottom: 30;
	right: 0;
	width: 300px;
	/* border: 3px solid #8AC007;  */
}
</style>

<title>Directors Page</title>

<%
	HttpSession ses = request.getSession();
%>

<script type="text/javascript">
window.dirEmpId="<%=ses.getAttribute("primeKey")%>"

	function eid() {
		alert(dirEmpId);
	}
</script>
</head>
<body onload="divhide();">
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
					onclick="dirSeverClientTupeling(); eid();">Director</a>
			</div>

			
			<ul class="nav navbar-right top-nav">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown"> <i class="fa fa-envelope"></i> <b
						class="caret"></b></a>
					<ul class="dropdown-menu message-dropdown">
						<li class="message-preview"><a href="#"> <!--<div class="media">-->
								<span class="pull-left"> <img class="media-object"
									src="http://placehold.it/50x50" alt="fing placee holder">
							</span>
								<div class="media-body">
									<h5 class="media-heading">
										<strong>John Smith</strong>
									</h5>
									<p class="small text-muted">
										<i class="fa fa-clock-o"></i> Yesterday at 4:32 PM
									</p>
									<p>Lorem ipsum dolor sit amet, consectetur...</p>
								</div> <!--</div>-->
						</a></li>
						<li class="message-footer"><a href="#">Read All New
								Messages</a></li>
					</ul></li>
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown"><i class="fa fa-arrow-up"></i> <b
						class="caret"></b></a>
					<ul class="dropdown-menu alert-dropdown">
						<li><a href="#">Alert Name <span
								class="label label-default">Alert Badge</span></a></li>

						<li><a href="#">Alert Name <span
								class="label label-danger">Alert Badge</span></a></li>
						<li class="divider"></li>
						<li><a href="#">View All</a></li>
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
			<div id="dirMsgAlert" class="artdAlert"></div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container --> </nav>

		<!-- Sidebar -->
		<jsp:include page="./dir.html/sidebar.html"></jsp:include>
		<!-- /#sidebar-wrapper -->



		<!-- Page Content -->
		<div id="page-content-wrapper" style="background-color: azure">
			<div class="container-fluid">

				<!-- import director main details -->
				<%-- <jsp:include page="./mainPageDirPart.jsp"></jsp:include> --%>
				<!-- import employee details -->
				<jsp:include page="./empPageDirPart.jsp"></jsp:include>
				<!-- import employee details -->
				<jsp:include page="./balancePageDirPart.jsp"></jsp:include>
				<jsp:include page="./transPageDirPart.jsp"></jsp:include>


			</div>
		</div>
		<!-- /#page-content-wrapper -->

	</div>
	<!-- /#wrapper -->

	<!-- jQuery -->
	<script src="../../bootstrap/js/jquery.js"></script>

	<!-- director scripts -->
	<script src="../js/general.js"></script>
	<script src="./dir.html/dirInit.js"></script>
	<script src="./dir.html/dir.js"></script>
	<script src="./dir.html/balance.js"></script>
	<script src="./dir.html/employee.js"></script>
	<script src="./dir.html/transaction.js"></script>

	<!-- flot charts -->
	<script language="javascript" type="text/javascript"
		src="../../flot_f/jquery.flot.min.js"></script>
	<script language="javascript" type="text/javascript"
		src="../../flot_f/jquery.flot.pie.min.js"></script>
	<script language="javascript" type="text/javascript"
		src="../../flot_f/jquery.flot.categories.min.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="../../bootstrap/js/bootstrap.min.js"></script>


	<!-- jquery-ui -->
	<script src="../../jquery-ui/jquery-ui.min.js"></script>


</body>
</html>