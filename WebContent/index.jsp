<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- Bootstrap Core CSS -->
<link href="./bootstrap/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom CSS -->
<!-- <link href="./bootstrap/css/simple-sidebar.css" rel="stylesheet"> -->

<!-- Custom CSS -->
<!-- <link href="./bootstrap/css/sb-admin.css" rel="stylesheet"> -->

<!-- Custom Fonts -->
<link href="./bootstrap/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">

<style>
td {
	min-width: 200px;
}

body {
	background-image: url("img/texture/blackFlower.jpg");
}

.list {
	text-align: left;
	text-transform: uppercase;
	font-size: 12pt;
	font-weight: 700;
}

.showHide {
	z-index: 1;
	margin-left: 20%;
	margin-right: 20%;
	background-color: rgb(10, 10, 10);
	opacity: 0.85;
	color: rgb(170, 170, 170);
	margin-left: 20%;
}

.showHide p, h3 {
	text-align: center;
	padding-bottom: 10px;
	padding-top: 10px:
}

.carousel-caption {
	background-color: black;
	opacity: 0.5;
	filter: alpha(opacity = 60);
}

.dropdown:hover .dropdown-menu {
	display: inline-block;;
	z-index: 999;
	margin-top: 0;
	position: absolute;
	left: 0px;
}
</style>

<script>
	function showOn(event) {
		var evt = $(event.target);
		//set active inactive clases in nav menu		
		$(evt.parent()).attr("class", "active");
		$(evt.parent()).siblings().removeClass("active");

		//hide show analogous div in the contents
		var show = $(".showHide");
		for (var i = 0; i < show.length; i++) {
			if (show[i].id != $(evt.parent()).attr('id')) {
				$(show[i]).hide();
			} else {
				$(show[i]).show();
			}
		}
	}
</script>
<title>Your WebBank</title>
</head>
<body>
	<h1
		style="background: rgb(20, 20, 20); color: gray; padding-left: 80px; display: inline;">
		<img alt="logo" src="img/end_logo.png" width="150" height="120">
		Your Web Banking Platform
	</h1>

	<div class="container-fluid"
		style="color: gray; text-align: right; display: inline; float: right; margin-top: 30px; margin-right: 30px;">
		<form action="./Log.do" method="post">
			<table>
				<tr>
					<td col><label>e-mail &ensp; </label> <input name="usr"
						style="background-color: rgb(70, 70, 70);" type="text"></td>
					<td style="min-width: 50px;"></td>
				</tr>
				<tr>
					<td><label>password &ensp; </label><input name="psw" 
						style="background-color: rgb(70, 70, 70);" type="password"></td>
					<td style="min-width: 50px;"><input type="submit" value="log"
						style="width: 40px;"></td>
				</tr>
				<tr>
					<td>${sessionScope.validity}</td>
				</tr>
			</table>


		</form>
	</div>


	<div style="background-color: gray;">&emsp; .</div>
	<table width="100%" border="0">
		<thead>
			<tr>
				<td colspan="3" style="background-color: inherit; opacity: 0.8;"><nav
						class="navbar navbar-inverse">
					<div class="container-fluid">
						<div style="margin-left: 25%; margin-right: 25%">
							<ul class="nav navbar-nav">
								<li id="carusel" class="active"><a href="#"
									onclick="showOn(event);">Home</a></li>
								<li id="services"><a href="#" onclick="showOn(event);">Services</a></li>
								<li id="orientation"><a href="#" onclick="showOn(event);">Orientation</a></li>
								<li id="about"><a href="#" onclick="showOn(event);">About
										The System</a></li>
								<li id="tryIt"><a href="#" onclick="showOn(event);">Try
										It</a></li>
							</ul>
						</div>
					</div>
					</nav></td>
			</tr>
		</thead>
		<tbody>
			<tr style="height: 650px; vertical-align: top;">
				<td align="center" colspan="3" width="100%">
					<div id="carusel" class="showHide"
						style="display: inline; padding-left: 0%; padding-right: 0%">
						<div id="myCarousel" class="carousel slide" data-ride="carousel">
							<!-- Indicators -->
							<ol class="carousel-indicators">
								<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
								<li data-target="#myCarousel" data-slide-to="1"></li>
								<li data-target="#myCarousel" data-slide-to="2"></li>
								<li data-target="#myCarousel" data-slide-to="3"></li>
							</ol>

							<!-- Wrapper for slides -->
							<div class="carousel-inner" role="listbox">
								<div class="item active">
									<img src="img/cl-money-2.jpg" alt="Money">
									<div class="carousel-caption">
										<h3>Growth</h3>
										<p>Let your savings gain value under out care!</p>
									</div>
								</div>

								<div class="item">
									<img src="img/connectivity.jpg" alt="connectivity">
									<div class="carousel-caption">
										<h3>Connectivity</h3>
										<p>Stay on top of the circumstances and manage your
											accounts from everyehre/</p>
									</div>
								</div>

								<div class="item">
									<img src="img/security-photo.jpg" alt="Seccurity">
									<div class="carousel-caption">
										<h3>Security</h3>
										<p>Keep your head on the strategy and dont warry about
											security</p>
									</div>
								</div>

								<div class="item">
									<img src="img/mtmm.jpg" alt="Balance" style="height: 500px;">
									<div class="carousel-caption">
										<h3>Control</h3>
										<p>Contol, Analyze, Manage and Win!!</p>
									</div>
								</div>
							</div>

							<!-- Left and right controls -->
							<a class="left carousel-control" href="#myCarousel" role="button"
								data-slide="prev"> <span
								class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
								<span class="sr-only">Previous</span>
							</a> <a class="right carousel-control" href="#myCarousel"
								role="button" data-slide="next"> <span
								class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
								<span class="sr-only">Next</span>
							</a>
						</div>
					</div>

					<div id="services" class="showHide" style="display: none;">
						<p
							style="font-stretch: condensed; font-weight: 600; font-size: 15pt;">
							This web based platform allows clients, tellers managers and
							directors to interact in real time with the data and furthermore
							, to make decisions and act upon the accounts, clients and
							employees. The Bank Employees are connected in the ibternal bank
							Branch network, wheras the client can connect through the intrnet</p>
						<center>
							<img align="top" alt="internet/intranet architecture"
								src="img/interIntra.jpg">
						</center>
						<p>
							<i> internet intranet architecture</i>
						</p>

						<h4>The available action that the system allows are:</h4>
						<h3 id="information">
							Information:<b><span
								style="font-size: 13pt; font-style: oblique;">&emsp; The
									Users, Clients and Managers can see All informations regarding
									the accounts or cliente thei are handeling</span></b>
						</h3>
						<h3 id="actions">
							Actions:<b><span
								style="font-size: 13pt; font-style: oblique;">&emsp;
									Open/Close Accounts, Account Information</span></b>
						</h3>
						<h3 id="transactions">
							Transactions:<b><span
								style="font-size: 13pt; font-style: oblique;">&emsp;
									Deposite, Withdraw, Transfer </span></b>
						</h3>
						<h3 id="analysys">
							Analysys:<b><span
								style="font-size: 13pt; font-style: oblique;">&emsp;
									Controle dayly balance and all transactions</span></b>
						</h3>
					</div>

					<div id="orientation" class="showHide" style="display: none;">
						<ol class="list">
							<li><p>Evry user must log into the system with their
									username and password</p></li>
							<li><p>A vertical menu in the right side of the screen
									shows the actions you can peform For example regarding the
									Client below are few of the features
								<p>
								<hr>
								<p>
									<label>The <b>menu</b> wich shows tha available actions
										that this user can make:
									</label>
								<center>
									<img alt="client menu" src="img/cliMenu.png" width="150px"
										height="150px"> <img alt="teller menu"
										src="img/telMenu.png" width="150px" height="150px"> <img
										alt="manager menu" src="img/manMenu.png" width="150px"
										height="150px"> <img alt="director menu"
										src="img/dirMenu.png" width="150px" height="155px">
								</center>
								</p>
								<hr>
								<p>
									<label>A preview of the <b>accounts </b> that the user
										owns or co-ocwns and the analog information:
									</label><img alt="menu" src="img/cliAccounts.png">
								</p>
								<hr>
								<p>
									<label>A preview of the <b>balance </b> pannel and the
										allowed search criteria :
									</label><img alt="menu" src="img/cliBalance.png">
								</p>
								<hr>
								<h3>Note !! :</h3>
								<p style="color: red;">Not all the links or buttons work for
									all the Type of users, they might be there just for consistency</p>

								<hr></li>

							<li><b>After you are finished utilizing the System you
									can log out of it </b>
								<p>
									<img alt="menu" src="img/topMenu.png">
								</p></li>
						</ol>
					</div>

					<div id="about" class="showHide" style="display: none;">
						<h1>Technical details of creation. Technology, tools
							architecture and functionality.</h1>

						<table>
							<tr>
								<th><h3>Technologies :</h3></th>
								<td><h3>Tools :</h3></td>
							</tr>
							<tr>
								<td><ul>
										<li>Java</li>
										<li>JSP</li>
										<li>Servlets</li>
										<li>JavaScript</li>
										<li>JQuery</li>
										<li>JSON</li>
										<li>WebSockets</li>
										<li>HTML</li>
										<li>CSS</li>
									</ul></td>
								<td><ul>
										<li>Eclipse</li>
										<li>Hibernate</li>
										<li>Gson</li>
										<li>Slf4j</li>
										<li>MySql</li>
										<li>Glassfish</li>
									</ul></td>
							</tr>
						</table>
						<hr>

						<h3>Architecture</h3>
						<p>The communication between Client and Server id done through
							Web Sockets.</p>
						<p>In the Server We have a structure named "Coordinator";
							Acting as as static repository holding all the logged in tellers,
							managers, directors, clients and their accounts. The other (most)
							important structure is what is represented as "Functions &
							Queries". These are a series of clases acting as midleware for
							the functionality of every main entity and implement virtually
							all the functionality in the system.</p>
						<p>The third layer consist of two ways to persist the data.
							The database persistency, which holds all important data such as
							clients, employees, accounts, transactions etc. is handeled by
							hibernate. In parallel with the database persistency we keep a
							log file with data regarding the changes that an employee
							makes,(open/close accounts, transactions etc.).</p>
						<img alt="Architecture" src="./img/Architecture2.png">
					</div>

					<div id="tryIt" class="showHide" style="display: none;">
						<h3>Use this credentials and see for yourselves</h3>
						<h4>Client :</h4>
						<label>JK@OCEAN.COM</label> <label style="display: inline-block;">123456</label><br>
						<label>this11@that.com</label> <label
							style="display: inline-block;">321321</label> <br> <br>
						<h4>Teller :</h4>
						<label>CF@WEBANK.COM</label> <label style="display: inline-block;">12345</label><br>
						<label>mblue@webank.com</label> <label
							style="display: inline-block;">11115</label> <br> <br>
						<h4>Manager :</h4>
						<label>EH@WEBANK.COM</label> <label style="display: inline-block;">ELEMENTARY</label><br>
						<label>MP@WEBANK.COM</label> <label style="display: inline-block;">12345</label>
						<br> <br>
						<h4>Director :</h4>
						<label>BH@WEBANK.COM</label> <label style="display: inline-block;">123456</label><br>
						<label>TB@WEBANK.COM</label> <label style="display: inline-block;">123456</label>
						<br>
					</div>
				</td>

				<!-- <td></td> -->
			</tr>
		</tbody>
		<tfoot>
			<tr>
				<td colspan="3"><img alt="foot"
					src="img/footer_sponsors_en.png" width="100%" height="170px"></td>
			</tr>
		</tfoot>
	</table>
	<script src="./bootstrap/js/jquery.js"></script>
	<script src="./js/general.js"></script>
	<!-- Bootstrap Core JavaScript -->
	<script src="./bootstrap/js/bootstrap.min.js"></script>
</body>
</html>