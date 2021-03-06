package org.ishare.app.controllers;

import javax.servlet.http.HttpSession;

import org.ishare.app.domains.Coche;
import org.ishare.app.domains.Modelo;
import org.ishare.app.domains.Ubicacion;
import org.ishare.app.exceptions.DangerException;
import org.ishare.app.helpers.H;
import org.ishare.app.helpers.PRG;
import org.ishare.app.repositories.AlquilerRepository;
import org.ishare.app.repositories.CocheRepository;
import org.ishare.app.repositories.ModeloRepository;
import org.ishare.app.repositories.TipoRepository;
import org.ishare.app.repositories.UbicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("coche")
public class CocheController {
	@Autowired
	private ModeloRepository modeloRepository;
	@Autowired
	private CocheRepository cocheRepository;
	@Autowired
	private AlquilerRepository alquilerRepository;
	@Autowired
	private UbicacionRepository ubicacionRepository;
	@Autowired
	private TipoRepository tipoRepository;
	
	//Recuperar
	@GetMapping("r")
	public String rGet(ModelMap modelo) {
		modelo.put("view", "/coche/r");
		modelo.put("coches", cocheRepository.findAll());
		return ("_t/frame");
	}
	//Crear
	@GetMapping("c")
	public String cGet(ModelMap modelo) {
		modelo.put("modelos", modeloRepository.findAll());
		modelo.put("tipos", tipoRepository.findAll());
		modelo.put("ubicaciones", ubicacionRepository.findAll());
		modelo.put("view", "coche/c");
		return ("_t/frame");
	}
	@PostMapping("c")
	public String cPost(ModelMap modelo, @RequestParam("matricula") String matricula, @RequestParam("idModelo") Long idModelo, @RequestParam("idUbicacion") Long idUbicacion, HttpSession sesion) throws DangerException{
		H.isRolOK("Admin", sesion);
		if(matricula == null || idModelo == null || idUbicacion == null) {
			PRG.error("Debe rellenar todos los datos","coche/c");
		}
		
		Modelo modeloMarca = modeloRepository.getOne(idModelo);
		Ubicacion ubicacion = ubicacionRepository.getOne(idUbicacion);
		Coche coche = new Coche(matricula,modeloMarca.getAutonomiaTotal(), modeloMarca, ubicacion);
		
		try {
			cocheRepository.save(coche);
		} catch (Exception e) {
			PRG.error("Algo sali?? mal","coche/c");
		}
		return("redirect:/coche/r");
	}
	//Modificar
		@GetMapping("u")
		public String uGet(ModelMap modelo, @RequestParam("id") Long id) {
			modelo.put("coche", cocheRepository.getOne(id));
			modelo.put("modelos", modeloRepository.findAll());
			modelo.put("tipos", tipoRepository.findAll());
			modelo.put("ubicaciones", ubicacionRepository.findAll());
			modelo.put("view", "coche/u");
			return ("_t/frame");
		}
		@PostMapping("u")
		public String uPost(ModelMap modelo, @RequestParam("matricula") String matricula, @RequestParam("autonomiaRestante") Integer autonomiaRestante, @RequestParam("id") Long id, @RequestParam("idModelo") Long idModelo, @RequestParam("idUbicacion") Long idUbicacion, HttpSession sesion) throws DangerException{
			H.isRolOK("Admin", sesion);
			if(matricula == null || idModelo == null || idUbicacion == null) {
				PRG.error("Debe rellenar todos los datos","coche/c");
			}
			
			Modelo modeloMarca = modeloRepository.getOne(idModelo);
			Ubicacion ubicacion = ubicacionRepository.getOne(idUbicacion);
			Coche coche = cocheRepository.getOne(id);
			
			try {
				coche.setAutonomiaRestante(autonomiaRestante);
				coche.setModelo(modeloMarca);
				coche.setUbicacion(ubicacion);
				coche.setMatricula(matricula);
				cocheRepository.save(coche);
			} catch (Exception e) {
				PRG.error("Algo sali?? mal","coche/c");
			}
			return("redirect:/coche/r");
		}
		//Borrar
		@PostMapping("d")
		public String dPost(@RequestParam("id") Long id, HttpSession sesion) throws DangerException {
			H.isRolOK("Admin", sesion);
			try {
			cocheRepository.delete(cocheRepository.getOne(id));
			}catch(Exception e) {
				PRG.error("Antes de borrar un coche has de borrar el alquiler al que esta hilado");
			}
			return("redirect:/coche/r");
		}
		//Alquilar
		@GetMapping("alquilar")
		public String alquilarGet(ModelMap modelo) {
			modelo.put("view", "coche/alquilar");
			modelo.put("alquileres", alquilerRepository.findAll());
			modelo.put("coches", cocheRepository.findAll());
			return ("_t/frame");
		}
}
