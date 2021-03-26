package org.ishare.app.controllers;

import org.ishare.app.domains.Marca;
import org.ishare.app.domains.Rol;
import org.ishare.app.exceptions.DangerException;
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
	
	//Recuperar
	@GetMapping("r")
	public String rGet(ModelMap modelo) {
		modelo.put("view", "rol/r");
		modelo.put("roles", rolRepository.findAll());
		return ("_t/frame");
	}
	
	//Crear
	@GetMapping("c")
	public String cGet(ModelMap modelo) {
		modelo.put("view", "rol/c");
		return ("_t/frame");
	}
	@PostMapping("c")
	public String cPost(ModelMap modelo, @RequestParam("nombre") String nombre) throws DangerException{
		
		if(nombre == "" || nombre == null) {
			PRG.error("El nombre del rol no puede estar vacío","rol/c");
		}
		
		Rol rol = new Rol(nombre);
		
		try {
			rolRepository.save(rol);
		} catch (Exception e) {
			PRG.error("nombre ya existente","rol/c");
		}
		return("redirect:/rol/r");
	}
	
	//Modificar
	@GetMapping("u")
	public String uGet(ModelMap modelo, @RequestParam("id") Long id) {
		modelo.put("rol", rolRepository.getOne(id));
		modelo.put("view", "rol/u");
		return ("_t/frame");
	}
	@PostMapping("u")
	public String uPost(ModelMap modelo, @RequestParam("id") Long id, @RequestParam("nombre") String nombre) throws DangerException{
		
		if(nombre == "" || nombre == null) {
			PRG.error("El nombre del rol no puede estar vacío","rol/u");
		}
			
		Rol rol = rolRepository.getOne(id);
			
		try {
			rol.setNombre(nombre);
			rolRepository.save(rol);
		} catch (Exception e) {
			PRG.error("nombre ya existente","rol/u");
		}
		return("redirect:/rol/r");
	}
	
	//Borrar
		@PostMapping("d")
		public String dPost(ModelMap modelo, @RequestParam("id") Long id) {
			rolRepository.delete(rolRepository.getOne(id));
			return("redirect:/rol/r");
		}
}
