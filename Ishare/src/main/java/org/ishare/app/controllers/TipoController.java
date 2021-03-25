package org.ishare.app.controllers;

import org.ishare.app.domains.Tipo;
import org.ishare.app.exceptions.DangerException;
import org.ishare.app.helpers.PRG;
import org.ishare.app.repositories.TipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("tipo")
public class TipoController {

	@Autowired
	private TipoRepository tipoRepository;
	
	//Recuperar
		@GetMapping("r")
		public String rGet(ModelMap modelo) {
			modelo.put("view", "tipo/r");
			modelo.put("tipos", tipoRepository.findAll());
			return ("_t/frame");
		}
	//Crear
	@GetMapping("c")
	public String cGet(ModelMap modelo) {
		modelo.put("view", "tipo/c");
		return ("_t/frame");
	}
	@PostMapping("c")
	public String cPost(ModelMap modelo, @RequestParam("nombre") String nombre) throws DangerException{
		
		if(nombre == "" || nombre == null) {
			PRG.error("El nombre del tipo no puede estar vacío","tipo/c");
		}
		
		Tipo tipo = new Tipo(nombre);
		
		try {
			tipoRepository.save(tipo);
		} catch (Exception e) {
			PRG.error("nombre ya existente","marca/c");
		}
		return("redirect:/tipo/r");
	}
	//Modificar
	@GetMapping("u")
	public String uGet(ModelMap modelo, @RequestParam("id") Long id) {
		modelo.put("tipo", tipoRepository.getOne(id));
		modelo.put("view", "tipo/u");
		return ("_t/frame");
	}
	@PostMapping("u")
	public String uPost(ModelMap modelo, @RequestParam("id") Long id, @RequestParam("nombre") String nombre) throws DangerException{
		
		if(nombre == "" || nombre == null) {
			PRG.error("El nombre del tipo no puede estar vacío","marca/u");
		}
			
		Tipo tipo = tipoRepository.getOne(id);
		
		try {
			tipo.setNombre(nombre);
			tipoRepository.save(tipo);
		} catch (Exception e) {
			PRG.error("nombre ya existente","tipo/u");
		}
		return("redirect:/tipo/r");
	}
	//Borrar
	@PostMapping("d")
	public String dPost(ModelMap modelo, @RequestParam("id") Long id) {
		tipoRepository.delete(tipoRepository.getOne(id));
		return("redirect:/tipo/r");
	}
}
