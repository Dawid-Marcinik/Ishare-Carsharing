//Cambio de color en función del estado
function reseteaColor(elemento){
	elemento.style = "border-color:black";
}

function validar_campo_aspecto(elemento, valor){
	
	if(valor == true) elemento.style = "border-color:black";

	else elemento.style = "border-color:red";
	
}
//Establecimiento del estado de validación
function validar_modelo(modelo,pasajeros,autonomia,tarifa){
	modeloBien = validar_nombre(modelo);
	pasajerosBien = validar_pasajeros(pasajeros);
	autonomiaBien = validar_autonomia(autonomia);
	tarifaBien = validar_tarifa(tarifa);
	
	if(modeloBien == true && pasajerosBien == true && autonomiaBien == true && tarifaBien == true) return true;
	else return false;
	
}

function validar_matricula(elemento){
	var matricula = elemento.value.toLowerCase();
	var matricula_exprReg = /^\d\d\d\d [bcdfghjklmnprstvwxyz]{3}$/;
	var matriculaBien = matricula_exprReg.test(matricula);
	var devuelvo = false;
	
	if(matriculaBien){ 
		devuelvo = true;
		validar_campo_aspecto(elemento, devuelvo);
	}

	else validar_campo_aspecto(elemento, devuelvo);
	
	return devuelvo;
}

function validar_nombre(elemento){
	var nombre = elemento.value.toLowerCase();
	var nombre_exprReg = /^[a-z áéíóúüëïöä\-]{3,20}$/;
	var nombreBien = nombre_exprReg.test(nombre);
	var devuelvo = false;
	
	if(nombreBien){ 
		devuelvo = true;
		validar_campo_aspecto(elemento, devuelvo);
	}

	else validar_campo_aspecto(elemento, devuelvo);
	
	return devuelvo;
}

function validar_pasajeros(elemento){
	var numPasajeros = elemento.value;
	var numPasajeros_exprReg = /^[1-6]$/;
	var numPasajerosBien = numPasajeros_exprReg.test(numPasajeros);
	var devuelvo = false;
	
	if(numPasajerosBien){ 
		devuelvo = true;
		validar_campo_aspecto(elemento, devuelvo);
	}

	else validar_campo_aspecto(elemento, devuelvo);
	
	return devuelvo;
}

function validar_autonomia(elemento){
	var autonomia = elemento.value;
	var autonomia_exprReg = /^[1-9]\d\d$/;
	var autonomiaBien = autonomia_exprReg.test(autonomia);
	var devuelvo = false;
	
	if(autonomiaBien){ 
		devuelvo = true;
		validar_campo_aspecto(elemento, devuelvo);
	}

	else validar_campo_aspecto(elemento, devuelvo);
	
	return devuelvo;
}

function validar_tarifa(elemento){
	var tarifa = elemento.value;
	var tarifa_exprReg = /^\d.\d\d|\d.\d$/;
	var tarifaBien = tarifa_exprReg.test(tarifa);
	var devuelvo = false;
	console.log(tarifa);
	if(tarifaBien){ 
		devuelvo = true;
		validar_campo_aspecto(elemento, devuelvo);
	}

	else validar_campo_aspecto(elemento, devuelvo);
	
	return devuelvo;
}