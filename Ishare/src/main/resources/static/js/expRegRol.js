function validar(form) {
    var validarNR = validarNombreRol(form);
   
  

    

    console.log(validarNR);


  
    if (validarNR)  {
     
      form.envio.disabled=false;
      
    } else {
        form.envio.disabled=true;
    }
  }

function validarNombreRol(form) {//mínimo 3 caracteres, máximo 30 caracteres, vocales acentuadas mayusculas minusculas espacios en blanco, çñ-
    
    
    var dato = form.nombre.value;
    var reg = /^[a-zA-ZáéíóúÁÉÍÓÚ çÇñÑ-]{3,35}$/;
    if (reg.test(dato) == false) {
      form.nombre.style.color = "red";
      document.getElementById("nombre").style.borderColor = "red";
      $("#nom").slideDown();
      return false;
    }else{
      form.nombre.style.color = "black";
      $("#nom").slideUp();
      document.getElementById("nombre").style.borderColor = "black";
      return true;
    }
    
  }