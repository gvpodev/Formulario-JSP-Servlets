<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cadastro de telefones</title>
    <link rel="stylesheet" href="resources/css/cadastro.css">

</head>
<body>
<div class="container">
    <a href="acessoliberado.jsp" class="field-long">Início</a>
    <a href="index.jsp" class="field-long">Sair</a>
    <h2>Cadastro de telefones</h2>
    <h3 style="color: red;">${msg}</h3>
   <form action="salvarTelefones" method="post" id="formTel">
       <ul class="form-style-1">
           <li>
               <table>
                   <tr>
                       <td><input type="text" id="user" name="user" value="${userEscolhido.id}" readonly="readonly"/></td>
                       <td><input type="text" id="nome" name="nome" value="${userEscolhido.nome}" readonly="readonly"/></td>
                   </tr>
                   <tr>
                       <td><input type="text" id="numero" name="numero" value=""/></td>
                       <td>
                           <select id="tipo" name="tipo" style="width: 100%;">
                               <option>Celular</option>
                               <option>Trabalho</option>
                               <option>Casa</option>
                               <option>Contato</option>
                               <option>Outro</option>
                           </select>
                       </td>
                   </tr>
                   <tr>
                       <td><input type="submit" value="Salvar" style="width: 100%;"/></td>
                       <td><input type="submit" value="Voltar" 
                       onclick="document.getElementById('formTel').action = 'salvarTelefones?acao=voltar'" style="width: 100%;"></td>
                   </tr>
               </table>
           </li>
       </ul>
   </form>
    <div class="table-wrapper">
        <table class="fl-table">
            <caption>Telefones cadastrados</caption>
            <tr>
                <th>Id</th>
                <th>Numero</th>
                <th>Tipo</th>
                <th>Excluir</th>
            </tr>
            <c:forEach items="${telefones}" var="telefones">
                <tr>
                    <td><c:out value="${telefones.id}"></c:out></td>
                    <td><c:out value="${telefones.numero}"></c:out></td>
                    <td><c:out value="${telefones.tipo}"></c:out></td>
                    <td><a href="salvarTelefones?user=${telefones.usuario}&acao=deleteFone&telefoneId=${telefones.id}">
                    <img src="resources/img/excluir_icon.png" width="20px" height="20px"></a> </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>

<script>
    function validarCamposTel() {
        if (document.getElementById("numero").value == '') {
            alert('Informe o número.');
            return false;
        }
        return false;

    }
</script>
</body>
</html>

