package org.ishare.app.controllers;

import javax.servlet.http.HttpSession;

import org.ishare.app.domains.Ubicacion;
import org.ishare.app.exceptions.DangerException;
import org.ishare.app.helpers.PRG;
import org.ishare.app.repositories.UbicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/ubicacion")
public class UbicacionController {

	@Autowired
	private UbicacionRepository ubicacionRepository;

	@GetMapping("d")
	public String ubicacionDGet(@RequestParam final Long idUbicacion, final HttpSession s) throws DangerException {

		try {

			final Ubicacion ubicacion = ubicacionRepository.getOne(idUbicacion);
			ubicacionRepository.delete(ubicacion);
		} catch (final Exception e) {
			PRG.error("Antes de borrar la ubicación tienes que borrar el coche y el alquiler hilado a ello");
		}

		return "redirect:/ubicacion/r";
	}

	@GetMapping("u")
	public String ubicacionUGet(@RequestParam("idUbicacion") final Long idUbicacion, final ModelMap m) {
		m.put("ubicacion", ubicacionRepository.getOne(idUbicacion));
		m.put("view", "ubicacion/uGet");
		return "/_t/frame";
	}

	@PostMapping("u")
	public String ubicacionUPost(@RequestParam("direccion") final String direccion,
			@RequestParam("plazasTotales") final String stringPlazasTotales,
			@RequestParam("idUbicacion") final Long idUbicacion, final ModelMap data) throws DangerException {
		if (direccion == "" || stringPlazasTotales == "") {
			PRG.error("Hay campos vacíos", "/ubicacion/r");
		} else {
			final int plazasTotales = Integer.parseInt(stringPlazasTotales);
			final Ubicacion ubicacion = ubicacionRepository.getOne(idUbicacion);
			ubicacion.setDireccion(direccion);
			ubicacion.setPlazasTotales(plazasTotales);

			try {
				ubicacionRepository.save(ubicacion);
			} catch (final Exception e) {
				PRG.error("Ubicación duplicada", "/ubicacion/r");
			}
		}

		return "redirect:/ubicacion/r";
	}

	@GetMapping("c")
	public String ubicacionCGet(final ModelMap m) {
		m.put("view", "ubicacion/cGet");
		return "/_t/frame";
	}

	@PostMapping("c")
	public String ubicacionCPost(@RequestParam("direccion") final String direccion,
			@RequestParam("plazasTotales") final String stringPlazasTotales, final ModelMap data)
			throws DangerException {
		if (direccion == "" || stringPlazasTotales == "") {
			PRG.error("Hay campos vacíos", "/ubicacion/r");
		} else {
			final int plazasTotales = Integer.parseInt(stringPlazasTotales);

			final Ubicacion ubicacion = new Ubicacion(direccion, plazasTotales);

			try {
				ubicacionRepository.save(ubicacion);
			} catch (final Exception e) {
				PRG.error("Ubicación duplicada", "/ubicacion/r");
			}
		}

		return "redirect:/ubicacion/r";
	}

	@GetMapping("r")
	public String ubicacionRGet(final ModelMap m) {
		m.put("ubicaciones", ubicacionRepository.findAll());
		m.put("view", "ubicacion/rGet");
		return "/_t/frame";
	}
}
