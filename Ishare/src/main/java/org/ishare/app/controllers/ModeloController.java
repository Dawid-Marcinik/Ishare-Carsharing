package org.ishare.app.controllers;

import org.ishare.app.domains.Marca;
import org.ishare.app.domains.Modelo;
import org.ishare.app.exceptions.DangerException;
import org.ishare.app.helpers.PRG;
import org.ishare.app.repositories.MarcaRepository;
import org.ishare.app.repositories.ModeloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("modelo")
public class ModeloController {
	@Autowired
	private MarcaRepository marcaRepository;
	@Autowired
	private ModeloRepository modeloRepository;
	
	//Recuperar
	@GetMapping("r")
	public String rGet(ModelMap modelo) {
		modelo.put("view", "modelo/r");
		modelo.put("modelos", modeloRepository.findAll());
		return ("_t/frame");
	}
	
	//Crear
	@GetMapping("c")
	public String cGet(ModelMap modelo) {
		modelo.put("marcas", marcaRepository.findAll());
		modelo.put("view", "modelo/c");
		return ("_t/frame");
	}
	@PostMapping("c")
	public String cPost(ModelMap modelo, @RequestParam("idMarca") Long idMarca, @RequestParam("nombre") String nombre, @RequestParam("numeroPasajeros") Integer numeroPasajeros, @RequestParam("tipo") String tipo, @RequestParam("autonomiaTotal") Integer autonomiaTotal, @RequestParam("tarifa") Float tarifa) throws DangerException{
			
		if(nombre == "" || numeroPasajeros <= 0 || autonomiaTotal <= 0 || tarifa <= 0) {
			PRG.error("Ningún parámetro puede estar vacío","modelo/c");
		}
		Marca marca = marcaRepository.getOne(idMarca);
			
		Modelo modeloMarca = new Modelo(nombre, numeroPasajeros, tipo, autonomiaTotal, tarifa, marca);
			
		try {
			modeloRepository.save(modeloMarca);
		} catch (Exception e) {
			PRG.error("nombre ya existente","modelo/c");
		}
		return("redirect:/modelo/r");
		}
	
	//Modificar
	@GetMapping("u")
	public String uGet(ModelMap modelo, @RequestParam("id") Long id) {
		modelo.put("marcas", marcaRepository.findAll());
		modelo.put("idMarcaModelo", modeloRepository.getOne(id).getMarca().getId());
		modelo.put("modelo", modeloRepository.getOne(id));
		modelo.put("view", "modelo/u");
		return ("_t/frame");
	}
	@PostMapping("u")
	public String uPost(ModelMap modelo, @RequestParam("id") Long id, @RequestParam("idMarca") Long idMarca, @RequestParam("nombre") String nombre, @RequestParam("numeroPasajeros") Integer numeroPasajeros, @RequestParam("tipo") String tipo, @RequestParam("autonomiaTotal") Integer autonomiaTotal, @RequestParam("tarifa") Float tarifa) throws DangerException{
				
		if(nombre == "" || numeroPasajeros <= 0 || autonomiaTotal <= 0 || tarifa <= 0) {
			PRG.error("Ningún parámetro puede estar vacío","modelo/c");
		}
		
		Marca marca = marcaRepository.getOne(idMarca);	
		Modelo modeloMarca = modeloRepository.getOne(id);
		
		try {
			modeloMarca.setMarca(marca);
			modeloMarca.setNombre(nombre);
			modeloMarca.setNumeroPasajeros(numeroPasajeros);
			modeloMarca.setTipo(tipo);
			modeloMarca.setAutonomiaTotal(autonomiaTotal);
			modeloMarca.setTarifa(tarifa);
			modeloRepository.save(modeloMarca);
		} catch (Exception e) {
			PRG.error("nombre ya existente","modelo/c");
		}
		return("redirect:/modelo/r");
	}
	
	//Borrar
	@PostMapping("d")
	public String dPost(ModelMap modelo, @RequestParam("id") Long id) {
		modeloRepository.delete(modeloRepository.getOne(id));
		return("redirect:/modelo/r");
	}
}
