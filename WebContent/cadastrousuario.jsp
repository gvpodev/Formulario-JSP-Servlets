<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<meta charset="UTF-8">
<title>Cadastro</title>
<link rel="stylesheet" href="resources/css/cadastro.css">
<script src="https://code.jquery.com/jquery-3.4.1.min.js"
	integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
	crossorigin="anonymous"></script>
</head>
<body>
	<div class="container">
		<a href="acessoliberado.jsp" class="field-long">Início</a> <a
			href="index.jsp" class="field-long">Sair</a>
		<h2>Cadastro de usuário</h2>
		<h3 style="color: red;">${msg}</h3>
		<form action="salvarUsuario" method="post" id="formUser"
			onsubmit="return validarCamposUser() ? true : false;"
			enctype="multipart/form-data">
			<ul class="form-style-1">
				<li>
					<table>
						<tr>
							<td><input type="text" readonly="readonly" id="id" name="id"
								value="${user.id}" placeholder="Id - Gerado auto."
								hidden="hidden"></td>
						</tr>
						<tr>
							<td><input type="text" id="login" name="login"
								value="${user.login}" placeholder="Login" maxlength="12"></td>
							<td><input type="text" id="cep" name="cep"
								value="${user.cep}" placeholder="CEP" onblur="consultaCEP();" maxlength="9"></td>
						</tr>
						<tr>
							<td><input type="text" id="nome" name="nome"
								value="${user.nome}" placeholder="Nome"></td>
							<td><input type="password" id="senha" name="senha"
								value="${user.senha}" placeholder="Senha" maxlength="12"></td>
						</tr>
						<tr>
							<td><input type="text" id="rua" name="rua"
								value="${user.rua}" placeholder="Rua"></td>
							<td><input type="text" id="bairro" name="bairro"
								value="${user.bairro}" placeholder="Bairro"></td>

						</tr>
						<tr>
							<td><input type="text" id="cidade" name="cidade"
								value="${user.cidade}" placeholder="Cidade"></td>
							<td><input type="text" id="estado" name="estado"
								value="${user.estado}" placeholder="Estado"></td>
						</tr>
						<tr>
							<td><input type="text" id="ibge" name="ibge"
								value="${user.ibge}" hidden="hidden"></td>

						</tr>
						<tr>
							<td>Foto:</td>
							<td><input type="file" name="foto"
								value="${user.fotoBase64}" /> <input type="text"
								name="fotoTemp" value="${user.fotoBase64}" readonly="readonly"
								hidden="hidden" /> <input type="text" name="contentFotoTemp"
								value="${user.contentType}" readonly="readonly" hidden="hidden" /></td>
						</tr>
						<tr>
							<td>Currículo:</td>
							<td><input type="file" name="curriculo"
								value="${user.curriculoBase64}" /> <input type="text"
								name="curriculoTemp" value="${user.curriculoBase64}"
								readonly="readonly" hidden="hidden" /> <input type="text"
								name="contentCurriculoTemp" value="${user.contentTypeCurriculo}"
								readonly="readonly" hidden="hidden" /></td>
						</tr>
						<tr>
							<td><input type="submit" value="Salvar" class="field-long"></td>
						</tr>
						<tr>
							<td><input type="submit" value="Cancelar" class="field-long"
								style="background-color: brown"
								onclick="document.getElementById('formUser').action='salvarUsuario?acao=reset'"></td>
						</tr>
					</table>
				</li>
			</ul>
		</form>
		<div class="table-wrapper">
			<table class="fl-table">
				<caption>Usuários cadastrados</caption>
				<tr>
					<th>Id</th>
					<th>Login</th>
					<th>Foto</th>
					<th>Currículo</th>
					<th>Nome</th>
					<th>Telefones</th>
					<th>Excluir</th>
					<th>Editar</th>
				</tr>
				<c:forEach items="${usuarios}" var="user">
					<tr>
						<td><c:out value="${user.id}"></c:out></td>
						<td><c:out value="${user.login}"></c:out></td>
						<c:if test="${user.fotoBase64.isEmpty() == false}">
							<td><a
								href="salvarUsuario?acao=download&tipo=imagem&user=${user.id}"><img
									src="<c:out value="${user.tempFotoUser}"></c:out>"
									alt="Imagem User" title="Imagem User" width="32px"
									height="32px"></a></td>
						</c:if>
						<c:if test="${user.fotoBase64.isEmpty() == true}">
							<td><img alt="Imagem Padrão"
								src="resources/img/userpadrao.jpg" width="32px" height="32px"
								onclick="alert('Não possui imagem')"></td>
						</c:if>
						<c:if test="${user.curriculoBase64.isEmpty() == false}">
							<td><a
								href="salvarUsuario?acao=download&tipo=curriculo&user=${user.id}"><img
									alt="Curriculo" src="resources/img/pdf.png" width="32px"
									height="32px"> </a></td>
						</c:if>
						<c:if test="${user.curriculoBase64.isEmpty() == true}">
							<td>Sem currículo</td>
						</c:if>
						<td><c:out value="${user.nome}"></c:out></td>
						<td><a href="salvarTelefones?acao=addFone&user=${user.id}">
								<img title="Telefones" alt="Telefones"
								src="resources/img/phone.png" width="20px" height="20px">
						</a></td>
						<td><a href="salvarUsuario?acao=delete&user=${user.id}">
								<img title="Excluir" alt="Excluir"
								src="resources/img/excluir_icon.png" width="20px" height="20px">
						</a></td>
						<td><a href="salvarUsuario?acao=editar&user=${user.id}">
								<img title="Editar" alt="Editar"
								src="resources/img/edit_icon.png" width="20px" height="20px">
						</a></td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>

	<script>
		function validarCamposUser() {
			if (document.getElementById("login").value == '') {
				alert('Informe o login.');
				return false;
			} else if (document.getElementById("nome").value == '') {
				alert('Informe o nome.');
				return false;
			} else if (document.getElementById("telefone").value == '') {
				alert('Informe o telefone.');
				return false;
			} else if (document.getElementById("senha").value == '') {
				alert('Informe a senha.');
				return false;
			}

			return true;
		}

		function consultaCEP() {
			var cep = $("#cep").val();
			$.getJSON("https://viacep.com.br/ws/" + cep + "/json/?callback=?",
					function(dados) {

						if (!("erro" in dados)) {
							$("#rua").val(dados.logradouro);
							$("#bairro").val(dados.bairro);
							$("#cidade").val(dados.localidade);
							$("#estado").val(dados.uf);
							$("#ibge").val(dados.ibge);
						} else {
							$("#cep").val('');
							$("#rua").val('');
							$("#bairro").val('');
							$("#cidade").val('');
							$("#estado").val('');
							$("#ibge").val('');
							alert("CEP não encontrado.");
						}
					});
		}
	</script>
</body>
</html>