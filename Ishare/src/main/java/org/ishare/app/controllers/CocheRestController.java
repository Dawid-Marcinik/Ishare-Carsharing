package org.ishare.app.controllers;

import java.util.List;

import org.ishare.app.domains.Coche;
import org.ishare.app.repositories.CocheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController

@RequestMapping("/REST/coche")

public class CocheRestController {

	@Autowired
	private CocheRepository cocheRepository;
	
	@RequestMapping(value = "/recuperar",method = RequestMethod.GET)
	public ResponseEntity<Object> getCoches(){
		
		List<Coche> listaCoches = cocheRepository.findAll();
		return new ResponseEntity<>(listaCoches, HttpStatus.OK);
		
	}
	@RequestMapping(value = "/recuperarPorMarca",method = RequestMethod.GET)
	public ResponseEntity<Object> getCochesByMarca(@RequestParam("nombreMarca") String nombreMarca){
			
		List<Coche> listaCoches = cocheRepository.findByModelo_Marca_NombreContaining(nombreMarca);
		return new ResponseEntity<>(listaCoches, HttpStatus.OK);
			
	}
	@RequestMapping(value = "/recuperarPorModelo",method = RequestMethod.GET)
	public ResponseEntity<Object> getCochesByModelo(@RequestParam("nombreModelo") String nombreModelo){
		
		List<Coche> listaCoches = cocheRepository.findByModelo_NombreContaining(nombreModelo);
		return new ResponseEntity<>(listaCoches, HttpStatus.OK);
		
	}
	@RequestMapping(value = "/recuperarPorAutonomia",method = RequestMethod.GET)
	public ResponseEntity<Object> getCochesByAutonomia(@RequestParam("autonomia") Integer autonomia){
		
		List<Coche> listaCoches = cocheRepository.findByAutonomiaRestanteGreaterThan(autonomia);
		return new ResponseEntity<>(listaCoches, HttpStatus.OK);
			
	}
}
