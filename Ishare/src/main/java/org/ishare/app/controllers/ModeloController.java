package org.ishare.app.controllers;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.ishare.app.domains.Marca;
import org.ishare.app.domains.Modelo;
import org.ishare.app.domains.Tipo;
import org.ishare.app.exceptions.DangerException;
import org.ishare.app.helpers.H;
import org.ishare.app.helpers.PRG;
import org.ishare.app.repositories.MarcaRepository;
import org.ishare.app.repositories.ModeloRepository;
import org.ishare.app.repositories.TipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("modelo")
public class ModeloController {
	@Autowired
	private MarcaRepository marcaRepository;
	@Autowired
	private ModeloRepository modeloRepository;
	@Autowired
	private TipoRepository tipoRepository;
	
	//Recuperar
	@GetMapping("r")
	public String rGet(ModelMap modelo, HttpSession sesion) {
		modelo.put("view", "modelo/r");
		modelo.put("tipos", tipoRepository.findAll());
		modelo.put("modelos", modeloRepository.findAll());
		return ("_t/frame");
	}
	
	//Crear
	@GetMapping("c")
	public String cGet(ModelMap modelo) {
		modelo.put("marcas", marcaRepository.findAll());
		modelo.put("tipos", tipoRepository.findAll());
		modelo.put("view", "modelo/c");
		return ("_t/frame");
	}
	@PostMapping("c")
	public String cPost(ModelMap modelo, @RequestParam("idMarca") Long idMarca, @RequestParam("nombre") String nombre, @RequestParam("numeroPasajeros") Integer numeroPasajeros, @RequestParam("idTipo") Long idTipo, @RequestParam("autonomiaTotal") Integer autonomiaTotal, @RequestParam("tarifa") Float tarifa, @RequestParam("imagen") MultipartFile imagen, HttpSession sesion) throws DangerException, IOException{
		H.isRolOK("Admin", sesion);
		if(nombre == "" || numeroPasajeros <= 0 || numeroPasajeros == null || autonomiaTotal <= 0 || autonomiaTotal == null || tarifa <= 0 || tarifa == null) {
			PRG.error("Ningún parámetro puede estar vacío","modelo/c");
		}
		
		Marca marca = marcaRepository.getOne(idMarca);
		Tipo tipo = tipoRepository.getOne(idTipo);
		byte[] imagenByte = imagen.getBytes();
		Modelo modeloMarca = new Modelo(nombre, numeroPasajeros, tipo, autonomiaTotal, tarifa, imagenByte, marca);
			
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
		modelo.put("tipos", tipoRepository.findAll());
		modelo.put("idMarcaModelo", modeloRepository.getOne(id).getMarca().getId());
		modelo.put("modelo", modeloRepository.getOne(id));
		modelo.put("view", "modelo/u");
		return ("_t/frame");
	}
	@PostMapping("u")
	public String uPost(ModelMap modelo, @RequestParam("id") Long id, @RequestParam("idMarca") Long idMarca, @RequestParam("nombre") String nombre, @RequestParam("numeroPasajeros") Integer numeroPasajeros, @RequestParam("idTipo") Long idTipo, @RequestParam("autonomiaTotal") Integer autonomiaTotal, @RequestParam("tarifa") Float tarifa, @RequestParam("imagen") MultipartFile imagen, HttpSession sesion) throws DangerException, IOException{
		H.isRolOK("Admin", sesion);
		if(nombre == "" || numeroPasajeros <= 0 || numeroPasajeros == null || autonomiaTotal <= 0 || autonomiaTotal == null || tarifa <= 0 || tarifa == null) {
			PRG.error("Ningún parámetro puede estar vacío","modelo/c");
		}
		
		Marca marca = marcaRepository.getOne(idMarca);	
		Tipo tipo = tipoRepository.getOne(idTipo);
		Modelo modeloMarca = modeloRepository.getOne(id);
		byte[] imagenByte = imagen.getBytes();
		
		try {
			modeloMarca.setMarca(marca);
			modeloMarca.setNombre(nombre);
			modeloMarca.setNumeroPasajeros(numeroPasajeros);
			modeloMarca.setTipo(tipo);
			modeloMarca.setAutonomiaTotal(autonomiaTotal);
			modeloMarca.setTarifa(tarifa);
			modeloMarca.setImagen(imagenByte);
			modeloRepository.save(modeloMarca);
		} catch (Exception e) {
			PRG.error("nombre ya existente","modelo/c");
		}
		return("redirect:/modelo/r");
	}
	
	//Borrar
	@PostMapping("d")
	public String dPost(ModelMap modelo, @RequestParam("id") Long id, HttpSession sesion) throws DangerException {
		H.isRolOK("Admin", sesion);
		modeloRepository.delete(modeloRepository.getOne(id));
		return("redirect:/modelo/r");
	}
}
