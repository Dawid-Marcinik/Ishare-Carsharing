$(document).ready(function () {
		var peticionAjax = cargarAjax('http://localhost:8080/REST/coche/recuperar', '');
		function cargarAjax(donde, queEnvio){
			console.log(donde);
			console.log(queEnvio);
			$.ajax({
				url : donde,
				data : queEnvio,
				type : 'GET',
				//cache : false,
				//timeout : 200,
				dataType : 'json',
				success : function(json){
					var $resultado = "";
					$.each(json, function (clave, coche) {
						console.log(coche);
						$resultado += (`
						<div  class="col-xlg-3 col-lg-3 col-md-6 col-sm-12 mb-3">
						<div class="card-coche h-100">
						<div class="card-coche-img-container">
						<img class="card-coche-img card-img-top" src="/retrieve-dir/coche-numero-${coche.modelo.id}" alt="coche-numero-${coche.id}">
						</div><div class="card-body">
						<h4 class="card-title">${coche.modelo.marca.nombre} - ${coche.modelo.nombre}</h4>
						<p class="card-text">${coche.autonomiaRestante} Km</p><p class="card-text">Estacionado en: ${coche.ubicacion.direccion}</p>
						<form action="/alquiler/c" method="GET"><input type="hidden" name="idCoche" value="${coche.id}">
						<input type="submit" class="btn-base" value="${coche.modelo.tarifa} Tokens/Km"></a></form>
						</div>
						</div>
						</div>`);
					});
					DatosListos($resultado);
				},
				error : function(xhr, status) {
					xhr.abort();
			    },
			    complete : function(xhr, status) {
					xhr.abort();
	    		}
			});
		}
		function DatosListos(resultado) {
			$("#TABLAAJAX").html("");
			$("#TABLAAJAX").html(resultado);
		}
		$("#buscador").on('keyup',function () {
			
			peticionAjax = cargarAjax('http://localhost:8080/REST/coche/recuperar', "filtro="+$("#filtro").val()+"&argumento="+$("#buscador").val());

		});
		$("#filtro").on('change',function () {
			
			if ($("#filtro").val() == "ninguno"){
				$("#buscador").val("");
				$("#buscador").hide();
			}
			else{
				$("#buscador").show();
			}
			
			peticionAjax = cargarAjax('http://localhost:8080/REST/coche/recuperar', "filtro="+$("#filtro").val()+"&argumento="+$("#buscador").val());

		});
		function abortar(){
			if (peticionAjax) {
				peticionAjax.abort();
			}
		}
		function comprobarFiltro(valor){
			if(valor == "ninguno"){
				$("#buscador").hide();
			}
		}
		cargarAjax('http://localhost:8080/REST/coche/recuperar', "filtro="+$("#filtro").val()+"&argumento="+$("#buscador").val());
		comprobarFiltro($("#filtro").val());
	});