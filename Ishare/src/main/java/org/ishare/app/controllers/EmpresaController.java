package org.ishare.app.controllers;


import javax.servlet.http.HttpSession;

import org.ishare.app.domains.Empresa;
import org.ishare.app.exceptions.DangerException;
import org.ishare.app.helpers.H;
import org.ishare.app.helpers.PRG;
import org.ishare.app.repositories.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("empresa")
public class EmpresaController {
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@GetMapping("c")
	public String empresaCGet(ModelMap m) {
		m.put("view", "/empresa/c");
		return "/_t/frame";
	}
	
	@PostMapping("c")
	public String empresaCPost(@RequestParam("nombreUsuario") String nombreUsuario,
			@RequestParam("contrasena") String contrasena,
			@RequestParam("localidad") String localidad,
			@RequestParam("direccion") String direccion,
			@RequestParam("codigoPostal") Integer codigoPostal,
			@RequestParam("telefono") Integer telefono,
			@RequestParam("email") String email,
			@RequestParam("rol") String rol,
			@RequestParam("saldo") Float saldo,
			@RequestParam("cif") String cif,@RequestParam("razonSocial") String razonSocial) throws DangerException{
		
		if((cif != "")&&(razonSocial != "")) {
			Empresa em = new Empresa(nombreUsuario,contrasena,localidad,direccion,codigoPostal,telefono,email,rol,saldo,cif, razonSocial);
			
			
			try {
				empresaRepository.save(em);
			} catch (Exception e) {
				// TODO: handle exception
				PRG.error("no pueden haber dos CIF ni Razón social iguales","/empresa/c");
			}
		}else {
			PRG.error("No puede estar el cif ni la razón social vacio","/empresa/c");
		}
		
		return "redirect:/empresa/r";
	}
	
	@GetMapping("r")
	public String empresaRGet(ModelMap m) {
		
		m.put("empresas", empresaRepository.findAll());
		m.put("view", "/empresa/r");
		
		return "/_t/frame";
	}
	
	@GetMapping("u")
	public String updateGet(@RequestParam("id") Long id, ModelMap m) {
		m.put("empresa", empresaRepository.getOne(id));
		m.put("view", "/empresa/empresaU");
		return "/_t/frame";
	}

	@PostMapping("u")
	public String updatePost(@RequestParam("nombreUsuario") String nombreUsuario,
			@RequestParam("contrasena") String contrasena,
			@RequestParam("localidad") String localidad,
			@RequestParam("direccion") String direccion,
			@RequestParam("codigoPostal") Integer codigoPostal,
			@RequestParam("telefono") Integer telefono,
			@RequestParam("email") String email,
			@RequestParam("rol") String rol,
			@RequestParam("saldo") Float saldo,
			@RequestParam("cif") String cif,
			@RequestParam("razonSocial") String razonSocial,
			@RequestParam("id") Long id,
			HttpSession s) {
		try {
			Empresa em = empresaRepository.getOne(id);
			em.setNombreUsuario(nombreUsuario);
			em.setContrasena(contrasena);
			em.setLocalidad(localidad);
			em.setDireccion(direccion);
			em.setCodigoPostal(codigoPostal);
			em.setTelefono(telefono);
			em.setEmail(email);
			em.setRol(rol);
			em.setSaldo(saldo);
			em.setCif(cif);
			em.setRazonSocial(razonSocial);
			empresaRepository.save(em);
			
			H.info(s, "Empresa " + razonSocial+ " actualizada correctamente", "info", "/empresa/r");
		} catch (Exception e) {
			H.info(s, "Empresa " + razonSocial + " duplicada", "danger", "/empresa/r");
		}
		return "redirect:/info";
	}
	
	@PostMapping("d")
	public String borrarPost(@RequestParam("id") Long id, HttpSession s) {
	
			Empresa empresa = empresaRepository.getOne(id);
			
			empresaRepository.delete(empresa);
			
		
		return "redirect:/empresa/r";
	}

}