
package org.ishare.app.controllers;

import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpSession;

import org.ishare.app.domains.Alquiler;
import org.ishare.app.domains.Coche;
import org.ishare.app.domains.Entidad;
import org.ishare.app.domains.Ubicacion;
import org.ishare.app.exceptions.DangerException;
import org.ishare.app.exceptions.InfoException;
import org.ishare.app.helpers.H;
import org.ishare.app.helpers.PRG;
import org.ishare.app.repositories.AlquilerRepository;
import org.ishare.app.repositories.CocheRepository;
import org.ishare.app.repositories.EntidadRepository;
import org.ishare.app.repositories.UbicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/alquiler")
public class AlquilerController {

	@Autowired
	AlquilerRepository alquilerRepository;

	@Autowired
	UbicacionRepository ubicacionRepository;

	@Autowired
	CocheRepository cocheRepository;
	
	@Autowired
	EntidadRepository entidadRepository;

	@GetMapping("d")
	public void alquilerDGet(@RequestParam("idAlquiler") final Long idAlquiler, final HttpSession s)
			throws DangerException, InfoException {
		H.isRolOK("User", s);
		Alquiler alquilerAfectado = alquilerRepository.getOne(idAlquiler);
		Entidad entidad = entidadRepository.getOne(((Entidad)s.getAttribute("user")).getId());
		Float importeDevol = alquilerAfectado.getImporteTotal();
		Float saldoRestante = entidad.getSaldo()+importeDevol;
		LocalDateTime ahora = LocalDateTime.now();
		LocalDateTime fechaInitAlq = alquilerAfectado.getFechaInicio();
		if((alquilerAfectado.getEntidad().getId() == ((Entidad)s.getAttribute("user")).getId())) {
			if(ahora.isAfter(fechaInitAlq)) {
				PRG.error("No puede cancelar un alquiler que está en curso o ya ha ocurrido", "/coche/alquilar");
			}
			else{
				entidad.setSaldo(saldoRestante);
				s.setAttribute("saldo", entidad.getSaldo());
				alquilerRepository.delete(alquilerAfectado);
			}
		}
		else {
			PRG.error("Usted no es el usuario adecuado para realizar esta operación", "/coche/alquilar");
		}
		PRG.info("Alquiler cancelado", "/");
	}
/*
	@GetMapping("u")
	public String alquilerUGet(@RequestParam("idAlquiler") final Long idAlquiler, @RequestParam("idCoche") final Long idCoche, final ModelMap m, final HttpSession s)
			throws DangerException {
		H.isRolOK("User", s);
		try {
			
			m.put("ubicaciones", ubicacionRepository.findAll());
			m.put("alquiler", alquilerRepository.getOne(idAlquiler));
			m.put("alquileres", alquilerRepository.findAllByCocheId(idCoche));
			m.put("coche", cocheRepository.getOne(idCoche));
			m.put("view", "alquiler/uGet");
			return "/_t/frame";
		} catch (Exception e) {
			m.put("view", "/login");
			return "/_t/frame";
		}
		
	}

	@PostMapping("u")
	public String alquilerUPost(@RequestParam("idAlquiler") final Long idAlquiler,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam("fechaInicio") final String stringFechaInicio,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam("fechaFin") final String stringFechaFin,
			@RequestParam("idCocheAlquilado") final Long idCocheAlquilado,
			@RequestParam("idUbicacionInicio") final Long idUbicacionInicio,
			@RequestParam("idUbicacionFin") final Long idUbicacionFin,
			@RequestParam("horaInicio") final Integer horaInicio,
			@RequestParam("horaFin") final Integer horaFin,
			@RequestParam("puntuacion") final String puntuacion, final ModelMap data)
			throws DangerException, ParseException {
		if (puntuacion == "" || stringFechaInicio == "" || stringFechaFin == "" || idUbicacionInicio == null
				|| idUbicacionFin == null || idCocheAlquilado == null) {
			PRG.error("Hay campos vacíos", "/coche/alquilar");
		} else {

			final int punt = Integer.parseInt(puntuacion);
			final LocalDateTime fechaInicio = LocalDateTime.parse(stringFechaInicio);
			final LocalDateTime fechaFin = LocalDateTime.parse(stringFechaFin);
			final Alquiler alquiler = alquilerRepository.getOne(idAlquiler);
			final Ubicacion ubicacionInicio = ubicacionRepository.getOne(idUbicacionInicio);
			final Ubicacion ubicacionFin = ubicacionRepository.getOne(idUbicacionFin);
			alquiler.setFechaInicio(fechaInicio);
			alquiler.setFechaFin(fechaFin);
			alquiler.setPuntuacion(punt);

			alquiler.setIniciaEn(ubicacionInicio);
			ubicacionInicio.getIniciadosEn().add(alquiler);

			alquiler.setFinalizaEn(ubicacionFin);
			ubicacionInicio.getFinalizadosEn().add(alquiler);
			if (fechaFin.compareTo(fechaInicio) < 0) {
				PRG.error("La fecha de inicio no puede ser después de la fecha de finalización", "/alquiler/r");
			} else {
				try {
					alquilerRepository.save(alquiler);
				} catch (final Exception e) {
					PRG.error("Alquiler duplicado", "/coche/alquilar");
				}
			}
		}
		return "redirect:/alquiler/alquiler-alquiler-actualizado";
	}
*/
	@GetMapping("c")
	public String alquilerCGet(@RequestParam("idCoche") Long idCoche,final ModelMap m, final HttpSession s) throws DangerException, InfoException {
		//H.isRolOK("User", s);
		if(((Entidad)s.getAttribute("user")) == null) {
			PRG.info("Debe loguearse para realizar esta operación", "/login");
		}
		try {
			m.put("ubicaciones", ubicacionRepository.findAll());
			m.put("alquileres", alquilerRepository.findByCoche_Id(idCoche));
			m.put("coches", cocheRepository.findAll());
			m.put("view", "alquiler/cGet");

			return "_t/frame";
		} catch (Exception e) {
			m.put("view", "login");
			return "_t/frame";
		}
	}

	@PostMapping("c")
	public String alquilerCPost(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam("fechaInicio") final String stringFechaInicio,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam("fechaFin") final String stringFechaFin,
			@RequestParam("idCocheAlquilado") final Long idCocheAlquilado,
			@RequestParam("idUbicacionInicio") final Long idUbicacionInicio,
			@RequestParam("idUbicacionFin") final Long idUbicacionFin,
			@RequestParam("horaInicio") final Integer horaInicio,
			@RequestParam("horaFin") final Integer horaFin,
			final ModelMap data,
			final HttpSession s)
			throws DangerException, ParseException {

		if (stringFechaInicio == "" || stringFechaFin == "" || idUbicacionInicio == null
				|| idUbicacionFin == null || idCocheAlquilado == null || idUbicacionInicio == -1 || idUbicacionFin == -1
				|| idCocheAlquilado == -1) {
			PRG.error("Hay campos vacíos", "/alquiler/r");
		} else {
			String horaInicial = Integer.toString(horaInicio);
			String horaFinal = Integer.toString(horaFin);
			if(horaInicio < 10) {
				horaInicial = "0"+horaInicio;
			}
			if(horaFin < 10) {
				horaFinal = "0"+horaFin;
			}
			DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			final LocalDateTime fechaInicio = LocalDateTime.parse(stringFechaInicio+" "+horaInicial+":00",formato);
			final LocalDateTime fechaFin = LocalDateTime.parse(stringFechaFin+" "+horaFinal+":00",formato);
			final Coche cocheAlquilado = cocheRepository.getOne(idCocheAlquilado);
			final Ubicacion ubicacionInicio = ubicacionRepository.getOne(idUbicacionInicio);
			final Ubicacion ubicacionFin = ubicacionRepository.getOne(idUbicacionFin);
			final Integer plazasOcupadas = (ubicacionInicio == ubicacionFin)?ubicacionFin.getCochesAlquilados().size() : ubicacionFin.getCochesAlquilados().size()+1;
			final Entidad entidad = entidadRepository.getOne(((Entidad)s.getAttribute("user")).getId());
			Long tiempoAlquiler = Duration.between(fechaInicio, fechaFin).getSeconds();
			Long conversionHorasAlquiler = tiempoAlquiler/60;
			Float importeTotal = cocheAlquilado.getModelo().getTarifa() * conversionHorasAlquiler;
			Float saldoRestante = (entidad.getSaldo() - (cocheAlquilado.getModelo().getTarifa() * conversionHorasAlquiler));
			final Alquiler alquiler = new Alquiler(fechaInicio, fechaFin, entidad, cocheAlquilado, ubicacionInicio, ubicacionFin,importeTotal);
			if (fechaFin.compareTo(fechaInicio) < 0) {
				PRG.error("La fecha de inicio no puede ser después de la fecha de finalización", "/alquiler/r");
			}
			else if (saldoRestante<0){
				PRG.error("No tiene saldo suficiente en su cartera de Tokens", "/alquiler/r");
			}
			else if (plazasOcupadas>ubicacionFin.getPlazasTotales()) {
				PRG.error("No quedan plazas disponibles reservadas en este aparcamiento", "/alquiler/r");
			}
			else {

				try {
					alquilerRepository.save(alquiler);
					entidad.setSaldo(saldoRestante);
					entidadRepository.save(entidad);
					cocheAlquilado.setUbicacion(ubicacionFin);
					cocheRepository.save(cocheAlquilado);
					s.setAttribute("saldo", entidad.getSaldo());
				} catch (final Exception e) {
					PRG.error("Alquiler duplicado", "/coche/alquilar");
				}
			}
		}

		return "redirect:/alquiler/alquiler-exitoso";
	}

	@GetMapping("r")
	public String alquilerRGet(final ModelMap m, HttpSession s) throws DangerException {
		H.isRolOK("Admin", s);
		m.put("alquileres", alquilerRepository.findAll());
		m.put("view", "alquiler/rGet");
		return "_t/frame";
	}
	
	@GetMapping("puntuar")
	public String alquilerPuntuar(final ModelMap m, @RequestParam("idAlquiler") Long idAlquiler, HttpSession s) throws DangerException {
		H.isRolOK("User", s);
		Alquiler alquilerAfectado = alquilerRepository.getOne(idAlquiler);
		m.put("alquiler", alquilerRepository.getOne(idAlquiler));
		m.put("view", "alquiler/rGet");
		if((alquilerAfectado.getEntidad().getId() == ((Entidad)s.getAttribute("user")).getId())) {
			m.put("view", "alquiler/puntuar");
		}
		else {
			PRG.error("Usted no es el usuario adecuado para realizar esta operación", "/coche/alquilar");
		}
		return "_t/frame";
	}
	@PostMapping("puntuar")
	public String alquilerPuntuarPost(final ModelMap m, @RequestParam("idAlquiler") Long idAlquiler, @RequestParam("id") Long idUsuario, @RequestParam("puntuacion") Integer puntuacion, HttpSession s) throws DangerException {
		H.isRolOK("User", s);
		Alquiler alquiler = alquilerRepository.getOne(idAlquiler);
		try {
			alquiler.setPuntuacion(puntuacion);
			alquilerRepository.save(alquiler);
		} catch (Exception e) {
			PRG.error("No se pudo realizar la operación", "/coche/alquilar");
		}
		return "redirect:/alquiler/mis-alquileres?id="+idUsuario;
	}
	
	@GetMapping("mis-alquileres")
	public String listarAlquileres(final ModelMap m, @RequestParam("id") Long idUsuario, HttpSession s) throws DangerException {
		m.put("alquileres", alquilerRepository.findByEntidad_Id(idUsuario));
		if((((Entidad)s.getAttribute("user")).getId() != idUsuario)||((Entidad)s.getAttribute("user")).getId() == null) {
			PRG.error("Ups, algo salió mal :(", "/");
		}
		m.put("view", "alquiler/rGet");
		return "_t/frame";
	}
	@GetMapping("alquiler-exitoso")
	public void alquilerExitoso() throws InfoException {
		PRG.info("Alquiler realizado", "/");
	}
	@GetMapping("alquiler-actualizado")
	public void alquilerActualizado() throws InfoException {
		PRG.info("Alquiler actualizado", "/");
	}

}
