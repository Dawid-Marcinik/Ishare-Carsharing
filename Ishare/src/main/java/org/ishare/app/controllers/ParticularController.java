package org.ishare.app.controllers;



import java.time.LocalDate;

import javax.servlet.http.HttpSession;


import org.ishare.app.domains.Particular;
import org.ishare.app.domains.Rol;
import org.ishare.app.exceptions.DangerException;
import org.ishare.app.helpers.H;
import org.ishare.app.helpers.PRG;

import org.ishare.app.repositories.ParticularRepository;
import org.ishare.app.repositories.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;





@Controller
@RequestMapping("particular")
public class ParticularController {
	
	@Autowired
	ParticularRepository particularRepository;
	
	@Autowired 
	private RolRepository rolRepository;
	
	@GetMapping("c")
	public String particularCGet(ModelMap m) {
		m.put("roles",this.rolRepository.findAll());
		m.put("view", "/particular/c");
		return "/_t/frame";
	}
	
	@PostMapping("c")
	public String particularCPost(
			@RequestParam(value="idRol", required=false) Long idRol, 
			@RequestParam("nombreUsuario") String nombreUsuario,
			@RequestParam("contrasena") String contrasena,
			@RequestParam("localidad") String localidad,
			@RequestParam("direccion") String direccion,
			@RequestParam("codigoPostal") Integer codigoPostal,
			@RequestParam("telefono") Integer telefono,
			@RequestParam("email") String email,
			@RequestParam("saldo") Float saldo,
			@RequestParam("dni") String dni, 
			@RequestParam("nombre") String nombre, 
			@RequestParam("apellidos") String apellidos,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
			@RequestParam("fechaNacimiento") LocalDate fechaNacimiento) throws DangerException{
		
		if(dni != "") {
			Particular p = new Particular(nombreUsuario,contrasena,localidad,direccion,codigoPostal,telefono,email,saldo,dni,nombre,apellidos,fechaNacimiento);
			Rol rol=null;
			if(idRol==null) {
				rol=rolRepository.getByNombre("user");
			}else {
				rol=rolRepository.getOne(idRol);
			}
			p.setRol(rol);
			rol.getEntidades().add(p);
			try {
				particularRepository.save(p);
			} catch (Exception e) {
				// TODO: handle exception
				PRG.error("no pueden haber dos Dni iguales","/particular/c");
			}
		}else {
			PRG.error("No puede quedar el dni vacio","/particular/c");
		}
		
		return "redirect:/particular/r";
	}
	
	@GetMapping("r")
	public String particularRGet(ModelMap m) {
		
		m.put("particulares", particularRepository.findAll());
		m.put("view", "/particular/r");
		
		return "/_t/frame";
	}
	
	@GetMapping("u")
	public String updateGet(@RequestParam("id") Long id, ModelMap m) {
		m.put("particular", particularRepository.getOne(id));
		m.put("roles",this.rolRepository.findAll());
		m.put("view", "/particular/particularU");
		return "/_t/frame";
	}

	@PostMapping("u")
	public String updatePost(
			@RequestParam(value="idRol", required=false) Long idRol,
			@RequestParam("nombreUsuario") String nombreUsuario,
			@RequestParam("contrasena") String contrasena,
			@RequestParam("localidad") String localidad,
			@RequestParam("direccion") String direccion,
			@RequestParam("codigoPostal") Integer codigoPostal,
			@RequestParam("telefono") Integer telefono,
			@RequestParam("email") String email,
			@RequestParam("saldo") Float saldo,
			@RequestParam("dni") String dni,
			@RequestParam("nombre") String nombre,
			@RequestParam("apellidos") String apellidos,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
			@RequestParam("fechaNacimiento") LocalDate fechaNacimiento, 
			@RequestParam("id") Long id,
			HttpSession s) {
		try {
			Particular p = particularRepository.getOne(id);
			Rol rol=rolRepository.getOne(idRol);
			p.setRol(rol);
			p.setNombreUsuario(nombreUsuario);
			p.setContrasena(contrasena);
			p.setLocalidad(localidad);
			p.setDireccion(direccion);
			p.setCodigoPostal(codigoPostal);
			p.setTelefono(telefono);
			p.setEmail(email);
			p.setSaldo(saldo);
			p.setDni(dni);
			p.setNombre(nombre);
			p.setApellidos(apellidos);
			p.setFechaNacimiento(fechaNacimiento);
			particularRepository.save(p);
			
			H.info(s, "Particular " + nombre+ " actualizado correctamente", "info", "/particular/r");
		} catch (Exception e) {
			H.info(s, "Particular " + nombre + " duplicado", "danger", "/particular/r");
		}
		return "redirect:/info";
	}
	
	@PostMapping("d")
	public String borrarPost(@RequestParam("id") Long id, HttpSession s) {

	
			Particular particular = particularRepository.getOne(id);
			
			particularRepository.delete(particular);
			
		
		return "redirect:/particular/r";
	}

}