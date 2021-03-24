package org.ishare.app.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
@RequestMapping("/alquiler")
public class AlquilerController {
	
	@Autowired 
	AlquilerRepository alquilerRepository;
	
	@Autowired
	UbicacionRepository ubicacionRepository;

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
			@RequestParam("idUbicacionInicio") Long idUbicacionInicio,
			@RequestParam("idUbicacionFin") Long idUbicacionFin,
			@RequestParam("puntuacion") Integer puntuacion,
			ModelMap data
			) throws DangerException, ParseException {
		Date fechaInicio=new SimpleDateFormat("yyyy-mm-dd").parse(stringFechaInicio);
		Date fechaFin=new SimpleDateFormat("yyyy-mm-dd").parse(stringFechaFin);
		Alquiler alquiler = alquilerRepository.getOne(idAlquiler);
		Ubicacion ubicacionInicio=ubicacionRepository.getOne(idUbicacionInicio);
		Ubicacion ubicacionFin=ubicacionRepository.getOne(idUbicacionFin);
		alquiler.setFechaInicio(fechaInicio);
		alquiler.setFechaFin(fechaFin);
		alquiler.setPuntuacion(puntuacion);
		
		if(alquiler.getIniciaEn().getIniciadosEn()==null) {
			alquiler.setIniciaEn(ubicacionInicio);
			ubicacionInicio.getIniciadosEn().add(alquiler);
			}else {
				alquiler.getIniciaEn().getIniciadosEn().remove(alquiler);
				alquiler.setIniciaEn(ubicacionInicio);
				ubicacionInicio.getIniciadosEn().add(alquiler);
		}
		
		if(alquiler.getFinalizaEn().getFinalizadosEn()==null) {
			alquiler.setFinalizaEn(ubicacionFin);
			ubicacionInicio.getFinalizadosEn().add(alquiler);
		}else {
		alquiler.getFinalizaEn().getFinalizadosEn().remove(alquiler);
		alquiler.setFinalizaEn(ubicacionFin);
		ubicacionInicio.getFinalizadosEn().add(alquiler);
		}
		try {
			alquilerRepository.save(alquiler);
		}
		catch (Exception e) {
			PRG.error("Alquiler duplicado","/alquiler/r");
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
			@RequestParam("idUbicacionInicio") Long idUbicacionInicio,
			@RequestParam("idUbicacionFin") Long idUbicacionFin,
			@RequestParam("puntuacion") Integer puntuacion,
			ModelMap data
			) throws DangerException, ParseException {
		Date fechaInicio=new SimpleDateFormat("yyyy-mm-dd").parse(stringFechaInicio);
		Date fechaFin=new SimpleDateFormat("yyyy-mm-dd").parse(stringFechaFin);
		Alquiler alquiler = new Alquiler(fechaInicio,fechaFin,puntuacion);
		Ubicacion ubicacionInicio=ubicacionRepository.getOne(idUbicacionInicio);
		Ubicacion ubicacionFin=ubicacionRepository.getOne(idUbicacionFin);
		alquiler.setIniciaEn(ubicacionInicio);
		alquiler.setFinalizaEn(ubicacionFin);
		ubicacionInicio.getIniciadosEn().add(alquiler);
		ubicacionFin.getFinalizadosEn().add(alquiler);
		try {
			alquilerRepository.save(alquiler);
		}
		catch (Exception e) {
			PRG.error("Alquiler duplicado","/alquiler/r");
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
