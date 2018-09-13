<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>ChatBot Example w/ Watson and JSP</title>
<link rel="icon" href="favicon.png">
<link rel="stylesheet" type="text/css" href="css/index.css" />
</head>
<body>
	<div class="center">
		<h2>Chabot Example w/ Watson and JSP</h2>
		<div id="textchat" class="pulse"></div>
		<form method="post">
			<input type="text" id="question" name="question" class="field"
				placeholder="Type your question" />
			<button id="sendQuestion">Send</button>
		</form>
	</div>
	<script type="text/javascript" src="js/main.js"></script>
</body>
</html>