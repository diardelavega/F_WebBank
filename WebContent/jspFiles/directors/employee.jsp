<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- Bootstrap Core CSS -->
<link href="../../bootstrap/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="../../bootstrap/css/simple-sidebar.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="../../bootstrap/css/sb-admin.css" rel="stylesheet">

<!-- Morris Charts CSS -->
<!-- <link href="../../bootstrap/css/plugins/morris.css" rel="stylesheet"> -->

<!-- Custom Fonts -->
<link href="../../bootstrap/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Employee Dir</title>
</head>
<body>


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
				<a class="navbar-brand" href="#">Director</a>
			</div>
			<!-- Collect the nav links, forms, and other content for toggling -->
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
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container --> </nav>

		<!-- Sidebar -->
		<jsp:include page="./dir.html/sidebar.html"></jsp:include>
		<!-- /#sidebar-wrapper -->

		<!-- Page Content -->
		<div id="page-content-wrapper" style="background-color: azure">
			<div class="container-fluid">
				<!-- <div class="col-lg-12"> -->
				
				<div id="employee" class="hero">
					<ul class="nav nav-tabs" role="tablist">
						<li class="active" onclick="enableId();"><a data-toggle="tab"
							href="#info">Employee Info</a></li>
						<li onclick="disableid();"><a data-toggle="tab" href="#new">New
								Employee</a></li>
						<li onclick="enableId();"><a data-toggle="tab" href="#all">
								All employees list</a></li>
					</ul>
					<div class="tab-content">
						<div id="new" class="tab-pane  active">
							<!-- TODO the different part -->
							<form method="post" action="../../logout.jsp">
								<jsp:include page="./dir.html/employeeTable.html"></jsp:include>
								<!-- <button type="submit">SAVE</button> -->
							</form>
							<!-- different -->
						</div>
						<div id="info" class="tab-pane  ">
							<form method="post" action="../logout.jsp">
								<jsp:include page="./dir.html/employeeTable.html"></jsp:include>
								<!-- <button type="submit">SEARCH</button> -->
							</form>
						</div>
						<div id="all" class="tab-pane  ">
							<jsp:include page="./dir.html/employeeTable.html"></jsp:include>
						</div>
					</div>
					<!-- tab content -->
				</div>

				<div id=dirmain>
				
				</div>

			</div>
		</div>
		<!-- /#page-content-wrapper -->


	</div>
	<!-- /#wrapper -->


	<!-- jQuery -->
	<script src="../../bootstrap/js/jquery.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="../../bootstrap/js/bootstrap.min.js"></script>

	<!-- Morris Charts JavaScript -->
	<script src="../../bootstrap/js/plugins/morris/raphael.min.js"></script>
	<script src="../../bootstrap/js/plugins/morris/morris.min.js"></script>
	<script src="../../bootstrap/js/plugins/morris/morris-data.js"></script>

	<!-- Menu Toggle Script -->
	<script>
		$("#menu-toggle").click(function(e) {
			e.preventDefault();
			$("#wrapper").toggleClass("toggled");
		});

		function disableid() {
			$('#trid').attr("style", "visibility: collapse");
			$('#butid').attr('value', 'SAVE');
			$('#butid').attr('onclick', '');
		}

		function enableId() {
			$('#trid').attr("style", "visibility: visible");
			$('#butid').attr('value', 'SEARCH');
		}
	</script>
</body>
</html>