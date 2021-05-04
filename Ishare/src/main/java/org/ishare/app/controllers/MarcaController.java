package org.ishare.app.controllers;

import javax.servlet.http.HttpSession;

import org.ishare.app.domains.Marca;
import org.ishare.app.exceptions.DangerException;
import org.ishare.app.helpers.H;
import org.ishare.app.helpers.PRG;
import org.ishare.app.repositories.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("marca")
public class MarcaController {
	@Autowired
	private MarcaRepository marcaRepository;
	
	//Recuperar
	@GetMapping("r")
	public String rGet(ModelMap modelo) {
		modelo.put("view", "marca/r");
		modelo.put("marcas", marcaRepository.findAll());
		return ("_t/frame");
	}
	
	//Crear
	@GetMapping("c")
	public String cGet(ModelMap modelo) {
		modelo.put("view", "marca/c");
		return ("_t/frame");
	}
	@PostMapping("c")
	public String cPost(ModelMap modelo, @RequestParam("nombre") String nombre, HttpSession sesion) throws DangerException{
		H.isRolOK("Admin", sesion);
		if(nombre == "" || nombre == null) {
			PRG.error("El nombre de la marca no puede estar vacío","marca/c");
		}
		
		Marca marca = new Marca(nombre);
		
		try {
			marcaRepository.save(marca);
		} catch (Exception e) {
			PRG.error("nombre ya existente","marca/c");
		}
		return("redirect:/marca/r");
	}
	
	//Modificar
	@GetMapping("u")
	public String uGet(ModelMap modelo, @RequestParam("id") Long id) {
		modelo.put("marca", marcaRepository.getOne(id));
		modelo.put("view", "marca/u");
		return ("_t/frame");
	}
	@PostMapping("u")
	public String uPost(ModelMap modelo, @RequestParam("id") Long id, @RequestParam("nombre") String nombre, HttpSession sesion) throws DangerException{
		H.isRolOK("Admin", sesion);
		if(nombre == "" || nombre == null) {
			PRG.error("El nombre de la marca no puede estar vacío","marca/u");
		}
		
		Marca marca = marcaRepository.getOne(id);
		
		try {
			marca.setNombre(nombre);
			marcaRepository.save(marca);
		} catch (Exception e) {
			PRG.error("nombre ya existente","marca/u");
		}
		return("redirect:/marca/r");
	}
	
	//Borrar
	@PostMapping("d")
	public String dPost(ModelMap modelo, @RequestParam("id") Long id, HttpSession sesion) throws DangerException {
		H.isRolOK("Admin", sesion);
		marcaRepository.delete(marcaRepository.getOne(id));
		return("redirect:/marca/r");
	}
}
