	function validarDireccion(){
		var direccion=document.getElementById("direccion").value;
		var expresion=/^[a-zA-ZñÑçÇáéíóúÁÉÍÓÚ\d\/\.\-]{4,10}[\s - a-zA-ZñÑçÇáéíóúÁÉÍÓÚ\d\/\.\-]{0,36}$/
		var valido;
		
		var ret;

		valido=expresion.test(direccion);

		if (valido==true){
			$("#dir").slideUp();
			document.getElementById("direccion").setAttribute("style","border-color:black");
			ret=true;
			
		}else{
			$("#dir").slideDown();
			document.getElementById("direccion").setAttribute("style","border-color:red");
			ret=false;
			
		}
			
		return ret;
		
	}
	
	function validarNumPlazas(){
		var ret;
		if(document.getElementById("plazasTotales").value==""){
			$("#numTot").slideDown();
			ret=false;
		}else{
			$("#numTot").slideUp();
			ret=true;
		}
		
		return ret;
		
	}
	
	function validar(){
		validarDireccion();
		validarNumPlazas();
		
		if(validarDireccion()==true && validarNumPlazas()==true){
			document.getElementById("enviar").disabled=false;
			
		}else{
			document.getElementById("enviar").disabled=true;
		}
			
	}