<<<<<<< HEAD
package org.ishare.app.controllers;



import java.text.ParseException;
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
		m.put("view", "/particular/c");
		m.put("roles", rolRepository.findAll());
		return "/_t/frame";
	}
	
	@PostMapping("c")
	public String particularCPost(@RequestParam("nombreUsuario") String nombreUsuario,
			@RequestParam("contrasena") String contrasena,
			@RequestParam("localidad") String localidad,
			@RequestParam("direccion") String direccion,
			@RequestParam("codigoPostal") String codigoPostal,
			@RequestParam("telefono") String telefono,
			@RequestParam("email") String email,
			@RequestParam("idRol") Long idRol,
			@RequestParam("saldo") String saldo,
			@RequestParam("dni") String dni, 
			@RequestParam("nombre") String nombre, 
			@RequestParam("apellidos") String apellidos,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
			@RequestParam("fechaNacimiento") String fechaNacimiento) throws DangerException, ParseException{
			
		if(nombreUsuario==""||contrasena==""||localidad==""||direccion==""||codigoPostal==""||telefono==""||email==""||saldo==""||dni==""||nombre==""||apellidos==""||fechaNacimiento==""||nombreUsuario==null||contrasena==null||localidad==null||direccion==null||codigoPostal==null||telefono==null||email==null||saldo==null||dni==null||nombre==null||apellidos==null||fechaNacimiento==null) {
			PRG.error("Ningún campo puede quedar vacío", "/particular/r");
		}else {
			int iCodigoPostal=Integer.parseInt(codigoPostal);
			int iTelefono=Integer.parseInt(telefono);
			float fSaldo=Float.parseFloat(saldo);
			LocalDate lFechaNacimiento=LocalDate.parse(fechaNacimiento);
			Rol rol = rolRepository.getOne(idRol);
			Particular p = new Particular(nombreUsuario,contrasena,localidad,direccion,iCodigoPostal,iTelefono,email,rol,fSaldo,dni,nombre,apellidos,lFechaNacimiento);
	
			try {
				particularRepository.save(p);
				
			} catch (Exception e) {
				// TODO: handle exception
				PRG.error("no pueden haber dos Nombre usuario, telefono, email, y/o dni iguales","/particular/c");
			}
			
			
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
		m.put("roles", rolRepository.findAll());
		m.put("view", "/particular/particularU");
		return "/_t/frame";
	}

	@PostMapping("u")
	public String updatePost(@RequestParam("nombreUsuario") String nombreUsuario,
			@RequestParam("contrasena") String contrasena,
			@RequestParam("localidad") String localidad,
			@RequestParam("direccion") String direccion,
			@RequestParam("codigoPostal") String codigoPostal,
			@RequestParam("telefono") String telefono,
			@RequestParam("email") String email,
			@RequestParam("idRol") Long idRol,
			@RequestParam("saldo") String saldo,
			@RequestParam("dni") String dni,
			@RequestParam("nombre") String nombre,
			@RequestParam("apellidos") String apellidos,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
			@RequestParam("fechaNacimiento") String fechaNacimiento, 
			@RequestParam("id") Long id,
			HttpSession s)throws DangerException, ParseException {
		try {
			int iCodigoPostal=Integer.parseInt(codigoPostal);
			int iTelefono=Integer.parseInt(telefono);
			float fSaldo=Float.parseFloat(saldo);
			Rol rol = rolRepository.getOne(idRol);
			LocalDate lFechaNacimiento=LocalDate.parse(fechaNacimiento);
			Particular p = particularRepository.getOne(id);
			p.setNombreUsuario(nombreUsuario);
			p.setContrasena(contrasena);
			p.setLocalidad(localidad);
			p.setDireccion(direccion);
			p.setCodigoPostal(iCodigoPostal);
			p.setTelefono(iTelefono);
			p.setEmail(email);
			p.setRol(rol);
			p.setSaldo(fSaldo);
			p.setDni(dni);
			p.setNombre(nombre);
			p.setApellidos(apellidos);
			p.setFechaNacimiento(lFechaNacimiento);
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

=======

package org.ishare.app.controllers;



import java.text.ParseException;
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
		m.put("view", "/particular/c");
		m.put("roles", rolRepository.findAll());
		return "/_t/frame";
	}
	
	@PostMapping("c")
	public String particularCPost(@RequestParam("nombreUsuario") String nombreUsuario,
			@RequestParam("contrasena") String contrasena,
			@RequestParam("localidad") String localidad,
			@RequestParam("direccion") String direccion,
			@RequestParam("codigoPostal") String codigoPostal,
			@RequestParam("telefono") String telefono,
			@RequestParam("email") String email,
			@RequestParam("idRol") Long idRol,
			@RequestParam("saldo") String saldo,
			@RequestParam("dni") String dni, 
			@RequestParam("nombre") String nombre, 
			@RequestParam("apellidos") String apellidos,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
			@RequestParam("fechaNacimiento") String fechaNacimiento) throws DangerException, ParseException{
			
		if(nombreUsuario==""||contrasena==""||localidad==""||direccion==""||codigoPostal==""||telefono==""||email==""||saldo==""||dni==""||nombre==""||apellidos==""||fechaNacimiento==""||nombreUsuario==null||contrasena==null||localidad==null||direccion==null||codigoPostal==null||telefono==null||email==null||saldo==null||dni==null||nombre==null||apellidos==null||fechaNacimiento==null) {
			PRG.error("Ningún campo puede quedar vacío", "/particular/r");
		}else {
			int iCodigoPostal=Integer.parseInt(codigoPostal);
			int iTelefono=Integer.parseInt(telefono);
			float fSaldo=Float.parseFloat(saldo);
			LocalDate lFechaNacimiento=LocalDate.parse(fechaNacimiento);
			Rol rol = rolRepository.getOne(idRol);
			Particular p = new Particular(nombreUsuario,contrasena,localidad,direccion,iCodigoPostal,iTelefono,email,rol,fSaldo,dni,nombre,apellidos,lFechaNacimiento);
	
			try {
				particularRepository.save(p);
				
			} catch (Exception e) {
				// TODO: handle exception
				PRG.error("no pueden haber dos Dni iguales","/particular/c");
			}
			
			
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
		m.put("roles", rolRepository.findAll());
		m.put("view", "/particular/particularU");
		return "/_t/frame";
	}

	@PostMapping("u")
	public String updatePost(@RequestParam("nombreUsuario") String nombreUsuario,
			@RequestParam("contrasena") String contrasena,
			@RequestParam("localidad") String localidad,
			@RequestParam("direccion") String direccion,
			@RequestParam("codigoPostal") String codigoPostal,
			@RequestParam("telefono") String telefono,
			@RequestParam("email") String email,
			@RequestParam("idRol") Long idRol,
			@RequestParam("saldo") String saldo,
			@RequestParam("dni") String dni,
			@RequestParam("nombre") String nombre,
			@RequestParam("apellidos") String apellidos,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
			@RequestParam("fechaNacimiento") String fechaNacimiento, 
			@RequestParam("id") Long id,
			HttpSession s)throws DangerException, ParseException {
		try {
			int iCodigoPostal=Integer.parseInt(codigoPostal);
			int iTelefono=Integer.parseInt(telefono);
			float fSaldo=Float.parseFloat(saldo);
			Rol rol = rolRepository.getOne(idRol);
			LocalDate lFechaNacimiento=LocalDate.parse(fechaNacimiento);
			Particular p = particularRepository.getOne(id);
			p.setNombreUsuario(nombreUsuario);
			p.setContrasena(contrasena);
			p.setLocalidad(localidad);
			p.setDireccion(direccion);
			p.setCodigoPostal(iCodigoPostal);
			p.setTelefono(iTelefono);
			p.setEmail(email);
			p.setRol(rol);
			p.setSaldo(fSaldo);
			p.setDni(dni);
			p.setNombre(nombre);
			p.setApellidos(apellidos);
			p.setFechaNacimiento(lFechaNacimiento);
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


>>>>>>> branch 'master' of https://github.com/Dawid-Marcinik/Ishare-Carsharing
}