package org.ishare.app.controllers;

import java.util.List;

import org.ishare.app.domains.Coche;
import org.ishare.app.repositories.AlquilerRepository;
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
	private AlquilerRepository alquilerRepository;
	
	@Autowired
	private CocheRepository cocheRepository;
	
	@RequestMapping(value = "/recuperar",method = RequestMethod.GET)
	public ResponseEntity<Object> getCoches(@RequestParam("filtro") String filtro, @RequestParam("argumento") String argumento){
		
		List<Coche> listaCoches = null;
		
		switch(filtro) {
			case "ninguno":
				listaCoches = cocheRepository.findAll();
				break;	
			case "marca":
				listaCoches = cocheRepository.findByModelo_Marca_NombreContaining(argumento);
				break;
			case "modelo":
				listaCoches = cocheRepository.findByModelo_NombreContaining(argumento);
				break;
			case "autonomia":
				listaCoches = cocheRepository.findByAutonomiaRestanteGreaterThan(Integer.parseInt(argumento));
				break;
		}
		return new ResponseEntity<>(listaCoches, HttpStatus.OK);
	}
}
