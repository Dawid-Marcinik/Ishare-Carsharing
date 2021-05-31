function validarNumTarjeta(){
	var ret;
	if(document.getElementById("numTarjeta").value==""||document.getElementById("numTarjeta").value.toString().length<16||document.getElementById("numTarjeta").value.toString().length>16){
		document.getElementById("numTarjeta").setAttribute("style","border-color:red");	
		ret=false;

	}else{
		document.getElementById("numTarjeta").setAttribute("style","border-color:black");
		if(document.getElementById("numTarjeta").value.charAt(0)=="4"||document.getElementById("numTarjeta").value.charAt(0)=="5"){
			document.getElementById("numTarjeta").setAttribute("style","border-color:black");
			ret=true;
		}else{
			document.getElementById("numTarjeta").setAttribute("style","border-color:red");	
			ret=false;
		}
	}
		
	return ret;
}

function validarNombreTarjeta(){
	var ret;
	var valorExp;
	var expReg = /^[a-záéíóúüëïöä\-A-ZÁÉÍÓÚ]{3}[a-z áéíóúüëïöä\- A-Z ÁÉÍÓÚ]{0,37}$/;
	valorExp=expReg.test(document.getElementById("nomTarjeta").value)
	if(valorExp==false){
		document.getElementById("nomTarjeta").setAttribute("style","border-color:red");	
		ret=false;
	}else{
		document.getElementById("nomTarjeta").setAttribute("style","border-color:black");
		ret=true;
	}
	
	return ret;
}

function validarFechaTarjeta(){
	var ret;
	var mes=document.getElementById("mesTarjeta");
	var anio=document.getElementById("anioTarjeta");
	var f = new Date();;
	var anioAct = f.getFullYear();
	var mesAct = f.getMonth()+1;
	
	if(mes.value==""||mes.value<1||mes.value>12){
		mes.setAttribute("style","border-color:red;width:50px;");
		ret=false;
	}else{
		mes.setAttribute("style","border-color:black;width:50px;");
		if(anio.value==""||anio.value<anioAct){
			ret=false;
			anio.setAttribute("style","border-color:red;width:75px;");
		}else{
			if(anio.value==anioAct){
				if(mes.value==mesAct||mes.value<mesAct){
					mes.setAttribute("style","border-color:red;width:50px;");
					anio.setAttribute("style","border-color:red;width:75px;");
					ret=false;
				}else{
					mes.setAttribute("style","border-color:black;width:50px;");
					anio.setAttribute("style","border-color:black;width:75px;");
					ret=true;
				}
			}else{
				ret=true;
				anio.setAttribute("style","border-color:black;width:75px;");
			}
		}
	}
	
	return ret;
	
}

function validarCVV(){
	var ret;
	if(document.getElementById("cvv").value.toString().length<3||document.getElementById("cvv").value.toString().length>3){
		document.getElementById("cvv").setAttribute("style","border-color:red;width:75px;");	
		ret=false;
	}else{
		document.getElementById("cvv").setAttribute("style","border-color:black;width:75px;");	
		ret=true;
	}
	
	return ret;
}

function validarTokens(){
	var ret;
	if (document.getElementById("saldo").value.toString().length==0||document.getElementById("saldo").value<1){
		document.getElementById("saldo").setAttribute("style","border-color:red;width:100px;");	
		ret=false;
	}else{
		document.getElementById("saldo").setAttribute("style","border-color:black;width:100px;");	
		ret=true;
	}
	
	return ret;
}

function validar(){
	validarNumTarjeta();
	validarNombreTarjeta();
	validarFechaTarjeta();
	validarCVV();
	validarTokens();
	if(validarNumTarjeta()==true && validarNombreTarjeta()==true && validarFechaTarjeta()==true && validarCVV()==true && validarTokens()==true){
		document.getElementById("enviar").disabled=false;
	}else{
		document.getElementById("enviar").disabled=true;
	}
}
	