<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>AjTest2</title>
</head>
<body>
	<button id="somebutton" onclick="call2();">send</button>

	<div id="somediv"></div>
	<script>
		function call() {
			alert(" SMD");
			$.get('../Test.do', function(responseJson) {
				alert(empData.text);
				/* $.each(responseJson, function(i, empData) {
					alert(empData.text);
				}); */
				alert("got it");
				/* $('#somediv').text(responseJson);
				alert("got it") */
			});
		}

		function call2() {
			var obj = {
				id : "",
				fname : "Mario",
				lname : "Rista",
				address : " na mal",
				possition : "TELLER",
				eMail : "rrokot@yahoo.com",
				password : "12345"
			}

			$
					.ajax({
						url : "../Test2.do",
						type : 'POST',
						//dataType : 'json',
						contentType : 'application/json',
						mimeType : 'application/json',
						data : JSON.stringify(obj),
						contentType : 'application/json',
						/* mimeType : 'application/json', */
						success : function(data) {
							$('#somediv').text(data);
						},
						error : function(data, status, er) {
							alert("error: " + data + " status: " + status
									+ " er:" + er);
						}
					});
		}
	</script>
	<script src="../bootstrap/js/jquery.js"></script>
</body>
</html>