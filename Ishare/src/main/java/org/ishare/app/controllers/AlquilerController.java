package org.ishare.app.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import javax.servlet.http.HttpSession;


import org.ishare.app.domains.Alquiler;
import org.ishare.app.domains.Coche;
import org.ishare.app.domains.Ubicacion;
import org.ishare.app.exceptions.DangerException;
import org.ishare.app.helpers.PRG;
import org.ishare.app.repositories.AlquilerRepository;
import org.ishare.app.repositories.CocheRepository;
import org.ishare.app.repositories.UbicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/alquiler")
public class AlquilerController {
	
	@Autowired 
	AlquilerRepository alquilerRepository;
	
	@Autowired
	UbicacionRepository ubicacionRepository;
	
	@Autowired
	CocheRepository cocheRepository;

	@GetMapping("d")
	public String alquilerDGet(
			@RequestParam("idAlquiler") Long idAlquiler,
			HttpSession s) throws DangerException{
			alquilerRepository.delete(alquilerRepository.getOne(idAlquiler));
			return "redirect:/alquiler/r";
	}
	
	@GetMapping("u")
	public String alquilerUGet(
			@RequestParam("idAlquiler") Long idAlquiler,
			ModelMap m) {
		m.put("ubicaciones",this.ubicacionRepository.findAll());
		m.put("alquiler",alquilerRepository.getOne(idAlquiler));
		m.put("view","alquiler/uGet");
		return "/_t/frame";
	}
	
	@PostMapping("u")
	public String alquilerUPost( 
			@RequestParam("idAlquiler") Long idAlquiler,
			@RequestParam("fechaInicio") String stringFechaInicio,
			@RequestParam("fechaFin") String stringFechaFin,
			@RequestParam("idCocheAlquilado") Long idCocheAlquilado,
			@RequestParam("idUbicacionInicio") Long idUbicacionInicio,
			@RequestParam("idUbicacionFin") Long idUbicacionFin,
			@RequestParam("puntuacion") String puntuacion,
			ModelMap data
			) throws DangerException, ParseException {
		if(puntuacion==""||stringFechaInicio==""||stringFechaFin==""||idUbicacionInicio==null||idUbicacionFin==null) {
			PRG.error("Hay campos vacíos", "/alquiler/r");
		}else {
			
		
		int punt=Integer.parseInt(puntuacion);
		LocalDate fechaInicio=LocalDate.parse(stringFechaInicio);
		LocalDate fechaFin=LocalDate.parse(stringFechaFin);
		Alquiler alquiler = alquilerRepository.getOne(idAlquiler);
		Ubicacion ubicacionInicio=ubicacionRepository.getOne(idUbicacionInicio);
		Ubicacion ubicacionFin=ubicacionRepository.getOne(idUbicacionFin);
		alquiler.setFechaInicio(fechaInicio);
		alquiler.setFechaFin(fechaFin);
		alquiler.setPuntuacion(punt);
		
		
			alquiler.setIniciaEn(ubicacionInicio);
			ubicacionInicio.getIniciadosEn().add(alquiler);
		
		

			alquiler.setFinalizaEn(ubicacionFin);
			ubicacionInicio.getFinalizadosEn().add(alquiler);
			if(fechaFin.compareTo(fechaInicio)<0) {
				PRG.error("La fecha de inicio no puede ser después de la fecha de finalización", "/alquiler/r");
			}else {
				try {
					alquilerRepository.save(alquiler);
				}	
				catch (Exception e) {
					PRG.error("Alquiler duplicado","/alquiler/r");
				}	
			}
		}
		return "redirect:/alquiler/r";
	}
	
	@GetMapping("c")
	public String alquilerCGet(ModelMap m) {
		m.put("ubicaciones",this.ubicacionRepository.findAll());
		m.put("view","alquiler/cGet");
		return "/_t/frame";
	}
	
	@PostMapping("c")
	public String alquilerCPost( 
			@RequestParam("fechaInicio") String stringFechaInicio,
			@RequestParam("fechaFin") String stringFechaFin,
			@RequestParam("idCocheAlquilado") Long idCocheAlquilado,
			@RequestParam("idUbicacionInicio") Long idUbicacionInicio,
			@RequestParam("idUbicacionFin") Long idUbicacionFin,
			@RequestParam("puntuacion") String stringPuntuacion,
			ModelMap data
			) throws DangerException, ParseException {
		
		
		if(stringPuntuacion==""||stringFechaInicio==""||stringFechaFin==""||idUbicacionInicio==null||idUbicacionFin==null) {
			PRG.error("Hay campos vacíos", "/alquiler/r");
		}else {
			int puntuacion=Integer.parseInt(stringPuntuacion);
			LocalDate fechaInicio=LocalDate.parse(stringFechaInicio);
			LocalDate fechaFin=LocalDate.parse(stringFechaFin);
			Alquiler alquiler = new Alquiler(fechaInicio,fechaFin,puntuacion);
			Coche cocheAlquilado=cocheRepository.getOne(idCocheAlquilado);
			Ubicacion ubicacionInicio=ubicacionRepository.getOne(idUbicacionInicio);
			Ubicacion ubicacionFin=ubicacionRepository.getOne(idUbicacionFin);
			alquiler.setCoche(cocheAlquilado);
			alquiler.setIniciaEn(ubicacionInicio);
			alquiler.setFinalizaEn(ubicacionFin);
			ubicacionInicio.getIniciadosEn().add(alquiler);
			ubicacionFin.getFinalizadosEn().add(alquiler);
			if(fechaFin.compareTo(fechaInicio)<0) {
				PRG.error("La fecha de inicio no puede ser después de la fecha de finalización", "/alquiler/r");
			}else {
				
				try {
					alquilerRepository.save(alquiler);
				}
				catch (Exception e) {
					PRG.error("Alquiler duplicado","/alquiler/r");
				}
			}
		}
	
				return "redirect:/alquiler/r";
	}
		
		
		
		
		
	
	@GetMapping("r")
	public String alquilerRGet(
			ModelMap m
			) {
		m.put("alquileres",alquilerRepository.findAll());
		m.put("view", "alquiler/rGet");
		return "/_t/frame";
	}
	
}
