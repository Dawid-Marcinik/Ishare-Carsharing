package org.ishare.app.controllers;


import javax.servlet.http.HttpSession;

import org.ishare.app.domains.Entidad;
import org.ishare.app.domains.Rol;
import org.ishare.app.exceptions.DangerException;
import org.ishare.app.helpers.H;
import org.ishare.app.helpers.PRG;
import org.ishare.app.repositories.AlquilerRepository;
import org.ishare.app.repositories.CocheRepository;
import org.ishare.app.repositories.EmpresaRepository;
import org.ishare.app.repositories.EntidadRepository;
import org.ishare.app.repositories.MarcaRepository;
import org.ishare.app.repositories.ModeloRepository;
import org.ishare.app.repositories.ParticularRepository;
import org.ishare.app.repositories.RolRepository;
import org.ishare.app.repositories.UbicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AnonymousController {
	
	@Autowired
	private AlquilerRepository alquilerRepository;
	
	@Autowired
	private CocheRepository cocheRepository;
	
	@Autowired
	private MarcaRepository marcaRepository;
	
	@Autowired
	private ModeloRepository modeloRepository;
	
	@Autowired
	private UbicacionRepository ubicacionRepository;
	
	@Autowired
	private ParticularRepository particularRepository;
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Autowired
	private RolRepository rolRepository;
	
	@Autowired
	private EntidadRepository entidadRepository;
	
	@GetMapping("/")
	public String index(ModelMap m, HttpSession s) throws DangerException {
		m.put("view","home/index");
		return "_t/frame";
	}
	

	@GetMapping("/init")
	public String init() {
		
		alquilerRepository.deleteAll();
		cocheRepository.deleteAll();
		rolRepository.deleteAll();
		marcaRepository.deleteAll();
		modeloRepository.deleteAll();
		ubicacionRepository.deleteAll();
		particularRepository.deleteAll();
		empresaRepository.deleteAll();
		entidadRepository.deleteAll();
		
		rolRepository.save(new Rol("user"));
		Rol admin = new Rol("admin");
		rolRepository.save(admin);
		
		Entidad e = new Entidad((long) 1,"admin","admin");
		e.setRol(admin);
		entidadRepository.save(e);
		
		
		return "redirect:/";
	}
	
	@GetMapping("/info")
	public String info(
			ModelMap m,
			HttpSession s
			) {
		String mensaje = s.getAttribute("_mensaje") != null ? (String) s.getAttribute("_mensaje")
				: "Pulsa para volver a home";
		String severity = s.getAttribute("_severity") != null ? (String) s.getAttribute("_severity") : "info";
		String link = s.getAttribute("_link") != null ? (String) s.getAttribute("_link") : "/";

		s.removeAttribute("_mensaje");
		s.removeAttribute("_severity");
		s.removeAttribute("_link");

		m.put("mensaje", mensaje);
		m.put("severity", severity);
		m.put("link", link);

		m.put("view", "/_t/info");
		return "/_t/frame";
	}
	
	@GetMapping("/login")
	public String loginGet(ModelMap m,HttpSession s) throws DangerException {
		H.isRolOK("anon", s);
		m.put("entidades",this.entidadRepository.findAll());
		m.put("view","/home/login");
		return "/_t/frame";
	}
	
	@PostMapping("/login")
	public String loginPost(
			@RequestParam("nombreUsuario") String nombreUsuario,
			@RequestParam("contrasena") String contrasena,
			HttpSession s
			) throws DangerException {
		Entidad e = entidadRepository.getByNombreUsuario(nombreUsuario);
		if (e==null) {
			PRG.error("No existe una persona con el nombre de usuario "+nombreUsuario,"/login");
		}
		if  (!(contrasena.equals(e.getContrasena()))) {
			PRG.error("Password incorrecto para la persona con nombre de usuario "+nombreUsuario,"/login");
		}
		s.setAttribute("user", e);
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession s) throws DangerException {
		H.isRolOK("auth", s);
		s.removeAttribute("user");
		return "redirect:/";
	}
	
}
