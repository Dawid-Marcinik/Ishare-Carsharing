	function validarFechaInicio(){
	var ret;
	if (document.getElementById("fechaInicio").value==""){
		document.getElementById("fechaInicio").setAttribute("style","border-color:red");
		ret=false;
	}else{
		document.getElementById("fechaInicio").setAttribute("style","border-color:black");
		ret=true;
	}
	
	return ret;
	}
	
	function validarFechaFin(){
	var ret;
	if (document.getElementById("fechaFin").value==""){
		document.getElementById("fechaFin").setAttribute("style","border-color:red");
		ret=false;
	}else{
		document.getElementById("fechaFin").setAttribute("style","border-color:black");
		ret=true;
	}
	
	
	return ret;
	}
	
	function validarDifFechas(){
	var ret;
	var fechaI=new Date(document.getElementById("fechaInicio").value);
	var fechaF=new Date(document.getElementById("fechaFin").value);
	
	if(fechaI>fechaF){
		ret=false;
		document.getElementById("fechaInicio").setAttribute("style","border-color:red");
	}else{
		document.getElementById("fechaFin").setAttribute("style","border-color:black");
		ret=true;
	}
		return ret;
	}
	
	function validarDifHoras(){
		var ret;
		var fechaI=new Date(document.getElementById("fechaInicio").value);
		var fechaF=new Date(document.getElementById("fechaFin").value);
		var horaInicio = Number.parseInt(document.getElementById("horaInicio").value);
		var horaFin = Number.parseInt(document.getElementById("horaFin").value);
		if(fechaI.getTime() == fechaF.getTime()){
			console.log(horaInicio+"|"+horaFin);
			console.log("entro");
			if(horaFin <= horaInicio){
				console.log("epluf");
				ret = false;
				document.getElementById("horaInicio").setAttribute("style","border-color:red");
				document.getElementById("horaFin").setAttribute("style","border-color:red");
			}
			else{
				console.log("si");
				document.getElementById("horaFin").setAttribute("style","border-color:black");
				document.getElementById("horaInicio").setAttribute("style","border-color:black");
				ret = true;
			}
		}
		else{
			console.log("no");
			document.getElementById("horaFin").setAttribute("style","border-color:black");
			document.getElementById("horaInicio").setAttribute("style","border-color:black");
			ret = true;
		}
		return ret;
	}
	
	function validarCocheAlquilado(){
	var ret;
		if(document.getElementById("idCocheAlquilado").selectedIndex=="0"){
		document.getElementById("idCocheAlquilado").setAttribute("style","border-color:red");	
		ret=false;
		}else{
			document.getElementById("idCocheAlquilado").setAttribute("style","border-color:black");
			ret=true;
		}
		
		return ret;
	}
	function validarUbicacionInicio(){
	var ret;
		if(document.getElementById("idUbicacionInicio").selectedIndex=="0"){
		document.getElementById("idUbicacionInicio").setAttribute("style","border-color:red");	
		ret=false;
		}else{
			document.getElementById("idUbicacionInicio").setAttribute("style","border-color:black");
			ret=true;
		}
		
		return ret;
	}
	function validarUbicacionFin(){
	var ret;
		if(document.getElementById("idUbicacionFin").selectedIndex=="0"){
		document.getElementById("idUbicacionFin").setAttribute("style","border-color:red");	
		ret=false;
		}else{
			document.getElementById("idUbicacionFin").setAttribute("style","border-color:black");
			ret=true;
		}
		
		return ret;
		
	}

	function validarPuntuacion(){
	var ret;
		if(document.getElementById("puntuacion").value==""){
		document.getElementById("puntuacion").setAttribute("style","border-color:red");	
		ret=false;
		}else{
			document.getElementById("puntuacion").setAttribute("style","border-color:black");
			ret=true;
		}
		
		return ret;
	}