function validarCampos(){
	if(document.getElementById("login").value == ''){
		alert('Informe o login.')
		return false;
	} else if(document.getElementById("nome").value == ''){
		alert('Informe o nome.')
		return false;
	} else if(document.getElementById("telefone").value == ''){
		alert('Informe o telefone.')
		return false;
	} else if(document.getElementById("senha").value == ''){
		alert('Informe a senha.')
		return false;
	} 

	return true;
}