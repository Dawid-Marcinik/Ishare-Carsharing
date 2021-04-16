

/*   var patronNombre = /^[a-zA-ZáéíóúÁÉÍÓÚ çÇñÑ-]{3,30}$/;
  var patronApellidos = /^[a-zA-ZáéíóúÁÉÍÓÚ çÇñÑ-]{3,40}$/;
  var patronTelefono = /^\(\+\d{2,3}\)\-([6|7|8|9]{1})\d{8}$/;
  var patronDni = /^\d{8}[A-Z]$/;
  var patronCalle = /^[a-zA-ZáéíóúÁÉÍÓÚ çÇñÑ-]{3,25}$/;
  var patronNumero = /^[1-9]{1}[0-9]{1,4}$/;
  var patronPiso = /^[1-9]{1}[0-9]{1}$/;
  var patronPuerta = /^([1-9]|[A-I])$/; */

  function validar(form) {
    var validarU = validarNombreUsuario(form);
    var validarC = validarContrasena(form);
    var validarL = validarLocalidad(form);
    var validarD = validarDireccion(form);
    var validarCP = validarCodigoPostal(form);
    var validarT = validarTelefono(form);
    var validarE = validarEmail(form);
    var validarS = validarSaldo(form);
    var validarDN = validarDni(form);
    var validarN = validarNombre(form);
    var validarA = validarApellidos(form);
    var validarF = validarFechaNac(form);


    

    console.log(validarU,validarC,validarL,validarD,validarCP,validarT,validarE,validarS,validarDN,validarN,validarA,validarF);


  
    if (validarU && validarC && validarL && validarD && validarCP && validarT && validarE && validarS && validarDN && validarN && validarA && validarF)  {
     
       form.envio.disabled=false;
      
    } else {
       form.envio.disabled=true;
    }
  }
  
  function validarNombreUsuario(form) {//mínimo 3 caracteres, máximo 30 caracteres, vocales acentuadas mayusculas minusculas espacios en blanco, çñ-
    
    
    var dato = form.nombreUsuario.value;
    var reg = /^[a-zA-ZáéíóúÁÉÍÓÚ çÇñÑ-]{3,35}$/;
    if (reg.test(dato) == false) {
      form.nombreUsuario.style.color = "red";
      document.getElementById("nombreUsuario").style.borderColor = "red";
      return false;
    }else{
      form.nombreUsuario.style.color = "black";
      document.getElementById("nombreUsuario").style.borderColor = "black";
      return true;
    }
    
  }


  function validarContrasena(form) {
    /*La contraseña debe tener entre 8 y 16 caracteres
    Al menos un dígito, al menos una minúscula y al menos una mayúscula.
    Puede tener otros símbolos.*/

    var dato = form.contrasena.value;
    var reg = /^(?=\w*\d)(?=\w*[A-Z])(?=\w*[a-z])\S{8,16}$/;
    if (reg.test(dato) == false) {
      form.contrasena.style.color = "red";
      document.getElementById("contrasena").style.borderColor = "red";
      return false;
    }else{
      form.contrasena.style.color = "black";
      document.getElementById("contrasena").style.borderColor = "black";
      return true;
    }

  }


  function validarLocalidad(form) {

    var dato = form.localidad.value;
    var reg = /^([a-zA-ZÑñáéíóúÁÉÍÓÚ ç\-\.,]){2,}$/;
    if (reg.test(dato) == false || dato.length < 2 || dato.length > 30) {
      form.localidad.style.color = "red";
      document.getElementById("localidad").style.borderColor = "red";
      return false;
    }else{
      form.localidad.style.color = "black";
      document.getElementById("localidad").style.borderColor = "black";
      return true;
    }
    
  }

  function validarDireccion(form) {

    var dato = form.direccion.value;
    var reg = /[a-zA-Z1-9áéíóúÁÉÍÓÚ]+\.?(( |\-)[a-zA-Z1-9áéíóúÁÉÍÓÚ]+\.?)*/;
    if (reg.test(dato) == false || dato.length < 2 || dato.length > 40) {
      form.direccion.style.color = "red";
      document.getElementById("direccion").style.borderColor = "red";
      return false;
    }else{
      form.direccion.style.color = "black";
      document.getElementById("direccion").style.borderColor = "black";
      return true;
    }
    
  }
    

  function validarCodigoPostal(form) {

/*     EXPLICACION
El código postal en España son cinco números. Los dos primeros van del 01 al 52 
y los tres restantes pueden ser cualquier valor numérico
	0		'0'
------------------------------------------------------------------
	[1-9]		cualquier carácter del '1' al '9'
------------------------------------------------------------------
	[0-9]{3}	cualquier carácter del '0' al '9'(3 veces)
------------------------------------------------------------------
|			O
------------------------------------------------------------------
	[1-4]		cualquier carácter del '1' al '4'
------------------------------------------------------------------
	[0-9]{4}	cualquier carácter del '0' al '9'(4 veces)
------------------------------------------------------------------
|			O
------------------------------------------------------------------
	5		'5'
------------------------------------------------------------------
	[0-2]		cualquier carácter del '0' al '2'
------------------------------------------------------------------
	[0-9]{3}	cualquier carácter del '0' al '9'(3 veces)
------------------------------------------------------------------ */

    var dato = form.codigoPostal.value;
    var reg = /0[1-9][0-9]{3}|[1-4][0-9]{4}|5[0-2][0-9]{3}/;
    if (reg.test(dato) == false || dato.length < 5 || dato.length > 5) {
      form.codigoPostal.style.color = "red";
      document.getElementById("codigoPostal").style.borderColor = "red";
      return false;
    }else{
      form.codigoPostal.style.color = "black";
      document.getElementById("codigoPostal").style.borderColor = "black";
      return true;
    }
    
  }
  //Valida si un numero de teléfono empieza por 6,7,8 ó 9 y además tiene 8 dígitos
  function validarTelefono(form) {
    var telefono = form.telefono.value;
    var reg = /^([6|7|8|9]{1})\d{8}$/;
    if(reg.test(telefono) == false || telefono == ""){
      form.telefono.style.color = "red";
      document.getElementById("telefono").style.borderColor = "red";
      return false;
    }else{
      form.telefono.style.color = "black";
      document.getElementById("telefono").style.borderColor = "black";
      return true;
    }
    
  }
  //uno.dos@s.dor.tres.org  uno@todos-los.puntos.son
  function validarEmail(form){
    
    var dato = form.email.value;
    var reg = /^([a-z0-9]{1})((\.|\-)?([a-z0-9])){0,}@([a-z0-9]){1}((\.|\-)?([a-z0-9]))*\.([a-z]){2,4}$/;
    if (reg.test(dato) == false) {
      form.email.style.color = "red";
      document.getElementById("email").style.borderColor = "red";
      return false;
    }else{
      form.email.style.color = "black";
      document.getElementById("email").style.borderColor = "black";
      return true;
    }

  }

  function validarSaldo(form){
    //float con dos decimales positivos y negativos hasta + 9999,99 y - 9999,99
    var dato = form.saldo.value;
    var reg =  /^[+-]?\d+(\.\d+)?$/;
    if (reg.test(dato) == false || dato.length > 7) {
      form.saldo.style.color = "red";
      document.getElementById("saldo").style.borderColor = "red";
      return false;
    }else{
      form.saldo.style.color = "black";
      document.getElementById("saldo").style.borderColor = "black";
      return true;
    }
  }

 function validarDni(form) {

    var dato = form.dni.value.toUpperCase();
    var resul = false;
    var reg = /^\d{8}[A-Z]$/;
 

    var arrC = ["T","R","W","A","G","M","Y","F","P","D","X","B","N","J","Z","S","Q","V","H","L","C","K","E",];
    if (reg.test(dato) == true) {
        var numDni = dato.substr(0,8);
        var resto = parseInt(numDni)%23;
        if(dato.charAt(8)==arrC[resto]){
            document.getElementById("dni").style.borderColor = "black";
          resul = true;
        }
      	else{
        document.getElementById("dni").style.borderColor = "red";
        resul = false;
      	}
      
      } return resul;

  }

  function validarNombre(form) {//mínimo 3 caracteres, máximo 25 caracteres, vocales acentuadas mayusculas minusculas espacios en blanco, çñ-
    
    
        var dato = form.nombre.value;
        var reg = /^[a-zA-ZáéíóúÁÉÍÓÚ çÇñÑ-]{3,25}$/;
        if (reg.test(dato) == false) {
        form.nombre.style.color = "red";
        document.getElementById("nombre").style.borderColor = "red";
          return false;
        }else{
          form.nombre.style.color = "black";
          document.getElementById("nombre").style.borderColor = "black";
          return true;
        }
        
  }

  function validarApellidos(form) {//mínimo 3 caracteres, máximo 40 caracteres, vocales acentuadas mayusculas minusculas espacios en blanco, çñ-
    
    
        var dato = form.apellidos.value;
        var reg = /^[a-zA-ZáéíóúÁÉÍÓÚ çÇñÑ-]{3,40}$/;
        if (reg.test(dato) == false) {
          form.apellidos.style.color = "red";
          document.getElementById("apellidos").style.borderColor = "red";
          return false;
        }else{
          form.apellidos.style.color = "black";
          document.getElementById("apellidos").style.borderColor = "black";
          return true;
        }
        
  }
      
  function validarFechaNac(form) {
    	
        var fechaHoy = new Date();
        var fechaNac = new Date(form.fechaNacimiento.value);
        
        console.log(fechaNac);
        console.log(fechaHoy);
         var age = fechaHoy.getFullYear() - fechaNac.getFullYear();
    var m = fechaHoy.getMonth() - fechaNac.getMonth();
    if (m < 0 || (m === 0 && fechaHoy.getDate() < fechaNac.getDate())) {
        age--;
    }
    if(age>=18){
         form.fechaNacimiento.style.color = "black";
          document.getElementById("fechaNacimiento").style.borderColor = "black";
        return true;
      }else{
        form.fechaNacimiento.style.color = "red";
          document.getElementById("fechaNacimiento").style.borderColor = "red";
      return false;
      }
      
  }
      
      



    

    


 





