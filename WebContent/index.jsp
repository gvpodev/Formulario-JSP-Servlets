<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Projeto JSP</title>
<link rel="stylesheet" href="resources/css/estilo.css">
</head>
<body>
	<div class="login-page">
	<center><h2>JSP + Servlets + JDBC</h2></center>
		<div class="form">
			<form action="LoginServlet" method="post" class="login-form">
				Login: <input type="text" id="login" name="login"> <br>
				Senha: <input type="password" id="senha" name="senha"> <br>
				<button type="submit" value="Logar">Logar</button>
			</form>
		</div>
		<center><h3><a href="https://github.com/gvpodev/Formulario-JSP-Servlets"><img alt="GitHub Image" 
		src="resources/img/github.png" height="50px" width="200px"> </a></h3></center>
	</div>
</body>
</html>