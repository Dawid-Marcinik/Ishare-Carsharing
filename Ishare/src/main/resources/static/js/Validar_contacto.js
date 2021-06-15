var form = document.getElementById("contacto");
function validarEmail(form){
    
    var dato = form.email.value;
    var reg = /^([a-z0-9]{1})((\.|\-)?([a-z0-9])){0,}@([a-z0-9]){1}((\.|\-)?([a-z0-9]))*\.([a-z]){2,4}$/;
    if (reg.test(dato) == false) {
      form.email.style.color = "red";
      document.getElementById("email").style.borderColor = "red";
      $("#mail").slideDown();
      return false;
    }else{
      form.email.style.color = "black";
      document.getElementById("email").style.borderColor = "black";
      $("#mail").slideUp();
      return true;
    }
}
function validarNombre(form) {//mínimo 3 caracteres, máximo 25 caracteres, vocales acentuadas mayusculas minusculas espacios en blanco, çñ-
    
        var dato = form.nombre.value;
        var reg = /^[a-zA-ZáéíóúÁÉÍÓÚ çÇñÑ-]{3,25}$/;
        if (reg.test(dato) == false) {
        form.nombre.style.color = "red";
        document.getElementById("nombre").style.borderColor = "red";
        $("#nomAp").slideDown();
          return false;
        }else{
          form.nombre.style.color = "black";
          document.getElementById("nombre").style.borderColor = "black";
          $("#nomAp").slideUp();
          return true;
        }
        
  }
 form.addEventListener('submit', function (event) {
  event.preventDefault()
	
  if (validarEmail(form)&&validarNombre(form)) {
	console.log("Si");
    form.submit();
    window.setTimeout(function () { location.href="/gracias" }, 0);
  } else {
    // Give some sort of error feedback
    console.log("No");
    form.classList.add('invalid');
  }
}) 