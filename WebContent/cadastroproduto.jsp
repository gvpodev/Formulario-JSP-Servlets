<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cadastro</title>
<link rel="stylesheet" href="resources/css/cadastro.css">
<script type="text/javascript" src="prodscript.js"></script>
</head>
<body>
	<div class="container">
		<h2>Cadastro de produto</h2>
		<h3 style="color: red;">${msg}</h3>
		<form action="salvarProduto" method="post" id="formProduto" onsubmit="return validarCamposProd() ? true : false;">
			<ul class="form-style-1">
				<li>
					<table>
						<tr>
							<td><input type="text" readonly="readonly" id="id" name="id"
								value="${produto.id}" placeholder="Id - Gerado auto."></td>
						</tr>
						<tr>
							<td><input type="text" id="nome" name="nome"
								value="${produto.nome}" placeholder="Nome"></td>
						</tr>
						<tr>
							<td><input type="text" id="quantidade" name="quantidade"
								value="${produto.quantidade}" placeholder="Quantidade"></td>
						</tr>
						<tr>
							<td><input type="text" id="valor" name="valor"
								value="${produto.valor}" placeholder="Valor"></td>
						</tr>
						<tr>
							<td><input type="submit" value="Salvar" class="field-long"></td>
						</tr>
						<tr>
							<td><input type="submit" value="Cancelar" class="field-long"
								onclick="document.getElementById('formProduto').action='salvarProduto?acao=reset'"></td>
						</tr>
					</table>
				</li>
			</ul>
		</form>
		<div class="table-wrapper">
			<table class="fl-table">
				<caption>Produtos cadastrados</caption>
				<tr>
					<th>Id</th>
					<th>Nome</th>
					<th>Quantidade</th>
					<th>Valor</th>
					<th>Excluir</th>
					<th>Editar</th>
				</tr>
				<c:forEach items="${produtos}" var="produto">
					<tr>
						<td><c:out value="${produto.id}"></c:out></td>
						<td><c:out value="${produto.nome}"></c:out></td>
						<td><c:out value="${produto.quantidade}"></c:out></td>
						<td><c:out value="${produto.valor}"></c:out></td>
						<td><a href="salvarProduto?acao=delete&produto=${produto.id}">
								<img title="Excluir" alt="Excluir"
								src="resources/img/excluir_icon.png" width="20px" height="20px">
						</a></td>
						<td><a href="salvarProduto?acao=editar&produto=${produto.id}">
								<img title="Editar" alt="Editar"
								src="resources/img/edit_icon.png" width="20px" height="20px">
						</a></td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</body>
</html>