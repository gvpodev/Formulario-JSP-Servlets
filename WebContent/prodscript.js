function validarCamposProd(){
	if(document.getElementById("nome").value == ''){
		alert('Informe o nome.')
		return false;
	} else if(document.getElementById("quantidade").value == ''){
		alert('Informe a quantidade.')
		return false;
	} else if(document.getElementById("valor").value == ''){
		alert('Informe o valor.')
		return false;
	}
	
	return true;
}