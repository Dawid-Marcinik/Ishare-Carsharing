package org.ishare.app.controllers;

import javax.servlet.http.HttpSession;

import org.ishare.app.domains.Rol;
import org.ishare.app.exceptions.DangerException;
import org.ishare.app.helpers.H;
import org.ishare.app.helpers.PRG;
import org.ishare.app.repositories.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("rol")
public class RolController {

	@Autowired
	private RolRepository rolRepository;

	// Recuperar
	@GetMapping("r")
	public String rGet(final ModelMap modelo,final HttpSession s) throws DangerException {
		H.isRolOK("Admin", s);
		modelo.put("view", "rol/r");
		modelo.put("roles", rolRepository.findAll());
		return "_t/frame";
	}

	// Crear
	@GetMapping("c")
	public String cGet(final ModelMap modelo) {
		modelo.put("view", "rol/c");
		return "_t/frame";
	}

	@PostMapping("c")
	public String cPost(final ModelMap modelo, @RequestParam("nombre") final String nombre,final HttpSession s) throws DangerException {
		H.isRolOK("Admin", s);
		if (nombre == "" || nombre == null) {
			PRG.error("El nombre del rol no puede estar vacío", "rol/c");
		}

		final Rol rol = new Rol(nombre);

		try {
			rolRepository.save(rol);
		} catch (final Exception e) {
			PRG.error("nombre ya existente", "rol/c");
		}
		return "redirect:/rol/r";
	}

	// Modificar
	@GetMapping("u")
	public String uGet(final ModelMap modelo, @RequestParam("id") final Long id) {
		modelo.put("rol", rolRepository.getOne(id));
		modelo.put("view", "rol/u");
		return "_t/frame";
	}

	@PostMapping("u")
	public String uPost(final ModelMap modelo, @RequestParam("id") final Long id,
			@RequestParam("nombre") final String nombre, final HttpSession s) throws DangerException {

		if (nombre == "" || nombre == null) {
			PRG.error("El nombre del rol no puede estar vacío", "rol/u");
		}

		final Rol rol = rolRepository.getOne(id);
		H.isRolOK("Admin", s);
		try {
			rol.setNombre(nombre);
			rolRepository.save(rol);
		} catch (final Exception e) {
			PRG.error("nombre ya existente", "rol/u");
		}
		return "redirect:/rol/r";
	}

	// Borrar
	@PostMapping("d")
	public String dPost(final ModelMap modelo, @RequestParam("id") final Long id, HttpSession sesion) throws DangerException {
		H.isRolOK("Admin", sesion);
		try {
		rolRepository.delete(rolRepository.getOne(id));
		}catch(Exception e) {
			PRG.error("¿Realmente desea borrar el rol?");
		}
		return "redirect:/rol/r";
	}
}
