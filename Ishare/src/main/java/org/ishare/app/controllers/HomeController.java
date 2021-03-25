package org.ishare.app.controllers;

import javax.servlet.http.HttpSession;

import org.ishare.app.domains.Entidad;
import org.ishare.app.exceptions.DangerException;
import org.ishare.app.helpers.H;
import org.ishare.app.helpers.PRG;
import org.ishare.app.repositories.EntidadRepository;
import org.ishare.app.repositories.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
	
	@Autowired
	private EntidadRepository entidadRepository;
	@Autowired
	private RolRepository rolRepository;
	
	
	@GetMapping("/")
	public String home(ModelMap modelo) {
		modelo.put("view", "/home/index");
		return("_t/frame");
	}
	@GetMapping("/info")
	public String info(ModelMap m, HttpSession s) {
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
	
	//LOGIN
	@GetMapping("/login")
	public String loginGet(ModelMap m,HttpSession s) throws DangerException {
		H.isRolOK("anon", s);
		m.put("view","/home/login");
		return "/_t/frame";
	}
	@PostMapping("/login")
	public String loginPost(
			@RequestParam("nombreUsuario") String nombreUsuario,
			@RequestParam("contrasena") String contrasena,
			HttpSession s
			) throws DangerException {
		Entidad entidad = entidadRepository.getByNombreUsuario(nombreUsuario);
		if (entidad==null) {
			PRG.error("No existe una persona con el nombre de usuario "+nombreUsuario,"/login");
		}
		if  (!(contrasena.equals(entidad.getContrasena()))) {
			PRG.error("Contraseña incorrecta para la persona con nombre de usuario "+nombreUsuario,"/login");
		}
		s.setAttribute("user", entidad);
		return "redirect:/";
	}
	
	//LOGOUT
	@GetMapping("/logout")
	public String logout(HttpSession s) throws DangerException {
		H.isRolOK("auth", s);
		s.removeAttribute("user");
		return "redirect:/";
	}
}
