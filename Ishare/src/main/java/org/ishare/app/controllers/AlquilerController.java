
package org.ishare.app.controllers;

import java.text.ParseException;
import java.time.LocalDateTime;

import javax.servlet.http.HttpSession;

import org.ishare.app.domains.Alquiler;
import org.ishare.app.domains.Coche;
import org.ishare.app.domains.Ubicacion;
import org.ishare.app.exceptions.DangerException;
import org.ishare.app.helpers.H;
import org.ishare.app.helpers.PRG;
import org.ishare.app.repositories.AlquilerRepository;
import org.ishare.app.repositories.CocheRepository;
import org.ishare.app.repositories.UbicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

	@GetMapping("d")
	public String alquilerDGet(@RequestParam("idAlquiler") final Long idAlquiler, final HttpSession s)
			throws DangerException {
		H.isRolOK("Admin", s);
		alquilerRepository.delete(alquilerRepository.getOne(idAlquiler));
		return "redirect:/alquiler/r";
	}

	@GetMapping("u")
	public String alquilerUGet(@RequestParam("idAlquiler") final Long idAlquiler, final ModelMap m, final HttpSession s)
			throws DangerException {
		H.isRolOK("Admin", s);
		m.put("ubicaciones", this.ubicacionRepository.findAll());
		m.put("alquiler", alquilerRepository.getOne(idAlquiler));
		m.put("coches", cocheRepository.findAll());
		m.put("view", "alquiler/uGet");
		return "/_t/frame";
	}

	@PostMapping("u")
	public String alquilerUPost(@RequestParam("idAlquiler") final Long idAlquiler,
			@RequestParam("fechaInicio") final String stringFechaInicio,
			@RequestParam("fechaFin") final String stringFechaFin,
			@RequestParam("idCocheAlquilado") final Long idCocheAlquilado,
			@RequestParam("idUbicacionInicio") final Long idUbicacionInicio,
			@RequestParam("idUbicacionFin") final Long idUbicacionFin,
			@RequestParam("puntuacion") final String puntuacion, final ModelMap data)
			throws DangerException, ParseException {
		if (puntuacion == "" || stringFechaInicio == "" || stringFechaFin == "" || idUbicacionInicio == null
				|| idUbicacionFin == null || idCocheAlquilado == null) {
			PRG.error("Hay campos vacíos", "/alquiler/r");
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
					PRG.error("Alquiler duplicado", "/alquiler/r");
				}
			}
		}
		return "redirect:/alquiler/r";
	}

	@GetMapping("c")
	public String alquilerCGet(final ModelMap m) {
		m.put("ubicaciones", this.ubicacionRepository.findAll());
		m.put("coches", this.cocheRepository.findAll());
		m.put("view", "alquiler/cGet");

		return "/_t/frame";
	}

	@PostMapping("c")
	public String alquilerCPost(@RequestParam("fechaInicio") final String stringFechaInicio,
			@RequestParam("fechaFin") final String stringFechaFin,
			@RequestParam("idCocheAlquilado") final Long idCocheAlquilado,
			@RequestParam("idUbicacionInicio") final Long idUbicacionInicio,
			@RequestParam("idUbicacionFin") final Long idUbicacionFin,
			@RequestParam("puntuacion") final String stringPuntuacion, final ModelMap data)
			throws DangerException, ParseException {

		if (stringPuntuacion == "" || stringFechaInicio == "" || stringFechaFin == "" || idUbicacionInicio == null
				|| idUbicacionFin == null || idCocheAlquilado == null || idUbicacionInicio == -1 || idUbicacionFin == -1
				|| idCocheAlquilado == -1) {
			PRG.error("Hay campos vacíos", "/alquiler/r");
		} else {
			final int puntuacion = Integer.parseInt(stringPuntuacion);
			final LocalDateTime fechaInicio = LocalDateTime.parse(stringFechaInicio);
			final LocalDateTime fechaFin = LocalDateTime.parse(stringFechaFin);
			final Alquiler alquiler = new Alquiler(fechaInicio, fechaFin, puntuacion);
			final Coche cocheAlquilado = cocheRepository.getOne(idCocheAlquilado);
			final Ubicacion ubicacionInicio = ubicacionRepository.getOne(idUbicacionInicio);
			final Ubicacion ubicacionFin = ubicacionRepository.getOne(idUbicacionFin);
			alquiler.setCoche(cocheAlquilado);
			alquiler.setIniciaEn(ubicacionInicio);
			alquiler.setFinalizaEn(ubicacionFin);
			ubicacionInicio.getIniciadosEn().add(alquiler);
			ubicacionFin.getFinalizadosEn().add(alquiler);
			if (fechaFin.compareTo(fechaInicio) < 0) {
				PRG.error("La fecha de inicio no puede ser después de la fecha de finalización", "/alquiler/r");
			} else {

				try {
					alquilerRepository.save(alquiler);
				} catch (final Exception e) {
					PRG.error("Alquiler duplicado", "/alquiler/r");
				}
			}
		}

		return "redirect:/alquiler/r";
	}

	@GetMapping("r")
	public String alquilerRGet(final ModelMap m) {
		m.put("alquileres", alquilerRepository.findAll());
		m.put("view", "alquiler/rGet");
		return "/_t/frame";
	}

}
