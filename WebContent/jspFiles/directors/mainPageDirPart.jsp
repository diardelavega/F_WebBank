<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div id="mainDir" class="hidable">
	<ul class="nav nav-tabs" role="tablist">
		<li class="active"><a data-toggle="tab" href="#home">Home</a></li>

		<div class="col-lg-12">
			<h1>Simple Sidebar</h1>
			<p>This template has a responsive menu toggling system. The menu
				will appear collapsed on smaller screens, and will appear
				non-collapsed on larger screens. When toggled using the button
				below, the menu will appear/disappear. On small screens, the page
				content will be pushed off canvas.</p>
			<p>
				Make sure to keep all page content within the
				<code>#page-content-wrapper</code>
				.
			</p>
			<a href="#menu-toggle" class="btn btn-default" id="menu-toggle">Toggle
				Menu</a>
		</div>
		<li><a href="#css" data-toggle="tab">CSS</a></li>
		<li><a href="#html" data-toggle="tab">HTML</a></li>
	</ul>
	<div class="tab-content">
		<div id="home" class="tab-pane fade in active">
			<p>WTf MOFO</p>
		</div>
		<div id="css" class="tab-pane fade ">
			<h1>SHOW MUST GO ON</h1>
		</div>
		<div id="html" class="tab-pane fade ">
			<p>OK</p>
		</div>
	</div>
</div>