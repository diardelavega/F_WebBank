<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<title>SO question 4112686</title>
<!-- <script src="http://code.jquery.com/jquery-latest.min.js"></script> -->
<script src="../bootstrap/js/jquery.js"></script>
<script>
	function foo() {
		/* 	 $.post('../Test.do', function(responseText) {
				$('#somediv').text(responseText);
			});  */

		var obj = {
			id : "",
			fname : "Mario",
			lname : "Rista",
			address : " na mal",
			possition : "TELLER",
			eMail : "rrokot@yahoo.com",
			password : "12345"
		}

		$.ajax({
			url : "../Test.do",
			type : 'POST',
			dataType : 'json',
			contentType : 'application/json',
			data : JSON.stringify(obj),
			success : function(data) {
				$('#somediv').text(data[0].fname);
				bar(data);
			},
			error : function(data, status, er) {
				alert("error: " + data.text + " status: " + status + " er:"
						+ er.text);
			}
		});
	}
	function bar(data) {
		var $table = $('<table border="1">').appendTo($('#somediv2')); // Create HTML <table> element and append it to HTML DOM element with ID "somediv".
		$.each(data, function(index, emp) { // Iterate over the JSON array.
			$('<tr>').appendTo($table) 
			.append($('<td>').text(emp.address)) 
			.append($('<td>').text(emp.fname)) 
			.append($('<td>').text(emp.lname)) 
			.append($('<td>').text(emp.eMail));
		});
	}

	function viz(data) {
	alert("in viz");
		
		for ( var i in json.query.results.entities.entity) {
			var fname = json.query.results.entities.entity[i].fname;
			$('#somediv2').text(fname);
		}
	}
</script>
</head>
<body>
	<button id="somebutton" onclick="foo();">press here</button>
	<div id="somediv"></div>

	<div id="somediv2"></div>

	<!-- <form id="someform" action="../Test.do" method="post">
		<input type="text" name="foo" /> <input type="text" name="bar" /> <input
			type="text" name="baz" /> <input type="submit" name="submit"
			onclick="bar();" value="Submit" />
	</form> -->
</body>
</html>