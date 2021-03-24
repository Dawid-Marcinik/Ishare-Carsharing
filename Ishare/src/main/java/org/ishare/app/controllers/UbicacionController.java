package org.ishare.app.controllers;

import javax.servlet.http.HttpSession;

import org.ishare.app.domains.Alquiler;
import org.ishare.app.domains.Ubicacion;
import org.ishare.app.exceptions.DangerException;
import org.ishare.app.helpers.PRG;
import org.ishare.app.repositories.AlquilerRepository;
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
	
	@Autowired
	private AlquilerRepository alquilerRepository;
	
	@GetMapping("d")
	public String ubicacionDGet(
			@RequestParam Long idUbicacion,
			HttpSession s) throws DangerException {
		
		Ubicacion ubicacion = ubicacionRepository.getOne(idUbicacion);
		if(ubicacion.getIniciadosEn()==null) {
			
		}else {
			for (Alquiler alquiler:ubicacion.getIniciadosEn()) {
				alquiler.setIniciaEn(null);
				alquilerRepository.save(alquiler);
				ubicacion.getIniciadosEn().remove(alquiler);
				ubicacionRepository.save(ubicacion);
			}
		}
		if(ubicacion.getFinalizadosEn()==null) {
			
		}else {
			for (Alquiler alquiler:ubicacion.getFinalizadosEn()) {
				alquiler.setFinalizaEn(null);
				alquilerRepository.save(alquiler);
				ubicacion.getFinalizadosEn().remove(alquiler);
				ubicacionRepository.save(ubicacion);
			}
		}
		ubicacionRepository.delete(ubicacion);
		return "redirect:/ubicacion/r";
	}
	
	@GetMapping("u")
	public String ubicacionUGet(
			@RequestParam("idUbicacion") Long idUbicacion,
			ModelMap m) {
		m.put("ubicacion",ubicacionRepository.getOne(idUbicacion));
		m.put("view","ubicacion/uGet");
		return "/_t/frame";
	}
	
	@PostMapping("u")
	public String ubicacionUPost( 
			@RequestParam("direccion") String direccion,
			@RequestParam("plazasTotales") Integer plazasTotales,
			@RequestParam("idUbicacion") Long idUbicacion,
			ModelMap data
			) throws DangerException {
		
		Ubicacion ubicacion = ubicacionRepository.getOne(idUbicacion);
		ubicacion.setDireccion(direccion);
		ubicacion.setPlazasTotales(plazasTotales);
		
		try {
			ubicacionRepository.save(ubicacion);
		}
		catch (Exception e) {
			PRG.error("Ubicación duplicada","/ubicacion/r");
		}
		return "redirect:/ubicacion/r";
	}
	
	@GetMapping("c")
	public String ubicacionCGet(ModelMap m) {
		m.put("view","ubicacion/cGet");
		return "/_t/frame";
	}
	
	@PostMapping("c")
	public String ubicacionCPost( 
			@RequestParam("direccion") String direccion,
			@RequestParam("plazasTotales") Integer plazasTotales,
			ModelMap data
			) throws DangerException {
		
		Ubicacion ubicacion = new Ubicacion(direccion,plazasTotales);
		
		try {
			ubicacionRepository.save(ubicacion);
		}
		catch (Exception e) {
			PRG.error("Ubicación duplicada","/ubicacion/r");
		}
		return "redirect:/ubicacion/r";
	}
	
	@GetMapping("r")
	public String ubicacionRGet(
			ModelMap m
			) {
		m.put("ubicaciones",ubicacionRepository.findAll());
		m.put("view", "ubicacion/rGet");
		return "/_t/frame";
	}
}
