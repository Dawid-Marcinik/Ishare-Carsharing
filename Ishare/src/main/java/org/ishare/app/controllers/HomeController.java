package org.ishare.app.controllers;

import javax.servlet.http.HttpServletRequest;
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


	@GetMapping("/tarifas")
	public String tarifas(final ModelMap modelo) {
		modelo.put("view", "/home/tarifas");
		return "_t/frame";
	}

	@GetMapping("/")
	public String home(final ModelMap modelo, final HttpServletRequest request) {
		modelo.put("view", "/home/index");
		return "_t/frame";
	}

	@GetMapping("/info")
	public String info(final ModelMap m, final HttpSession s) {
		final String mensaje = s.getAttribute("_mensaje") != null ? (String) s.getAttribute("_mensaje")
				: "Pulsa para volver a home";
		final String severity = s.getAttribute("_severity") != null ? (String) s.getAttribute("_severity") : "info";
		final String link = s.getAttribute("_link") != null ? (String) s.getAttribute("_link") : "/";

		s.removeAttribute("_mensaje");
		s.removeAttribute("_severity");
		s.removeAttribute("_link");

		m.put("mensaje", mensaje);
		m.put("severity", severity);
		m.put("link", link);

		m.put("view", "/_t/info");
		return "/_t/frame";
	}

	// REGISTRO
	@GetMapping("/registro")
	public String registroGet(final ModelMap m) throws DangerException {
		m.put("view", "/home/registro");
		return "/_t/frame";
	}

	@PostMapping("/registro")
	public String registroPost(@RequestParam("tipoEntidad") final String tipoEntidad, final ModelMap m)
			throws DangerException {
		String link = "";
		if (tipoEntidad.equals("Empresa")) {
			link = "/empresa/c";
		} else if (tipoEntidad.equals("Particular")) {
			link = "/particular/c";
		}
		m.put("roles", rolRepository.findAll());
		m.put("view", link);
		return "redirect:" + link;
	}

	// LOGIN
	@GetMapping("/login")
	public String loginGet(final ModelMap m, final HttpSession s) throws DangerException {
		H.isRolOK("anon", s);
		m.put("view", "/home/login");
		return "/_t/frame";
	}

	@PostMapping("/login")
	public String loginPost(@RequestParam("nombreUsuario") final String nombreUsuario,
			@RequestParam("contrasena") final String contrasena, final HttpSession s) throws DangerException {
		final Entidad entidad = entidadRepository.getByNombreUsuario(nombreUsuario);
		if (entidad == null) {
			PRG.error("No existe una persona con el nombre de usuario " + nombreUsuario, "/login");
		}
		if (!contrasena.equals(entidad.getContrasena())) {
			PRG.error("Contraseña incorrecta para la persona con nombre de usuario " + nombreUsuario, "/login");
		}
		s.setAttribute("user", entidad);
		s.setAttribute("saldo", entidad.getSaldo());
		return "redirect:/";
	}

	// LOGOUT
	@GetMapping("/logout")
	public String logout(final HttpSession s) throws DangerException {
		H.isRolOK("auth", s);
		s.removeAttribute("user");
		return "redirect:/";
	}

	@GetMapping("quienes")
	public String empresaQGet(final ModelMap m, final HttpSession s) throws DangerException {
		H.isRolOK("anon", s);

		m.put("view", "/home/quienes");

		return "/_t/frame";
	}

	@GetMapping("donde")
	public String empresaDGet(final ModelMap m,final HttpSession s) throws DangerException {
		m.put("view", "/home/donde");

		return "/_t/frame";
	}
	
	//Añadido por si acaso
	@GetMapping("/administracion")
	public String administracion(final ModelMap modelo, final HttpSession s) throws DangerException {
		H.isRolOK("Admin", s);
		modelo.put("view", "/home/menuAdmin");
		return "_t/frame";
	}
	@GetMapping("/politicasC")
	public String politicasC(final ModelMap m, final HttpSession s) throws DangerException {
		m.put("view", "/home/donde");

		return "/_t/frame";
	}
	@GetMapping("/terminosC")
	public String terminosC(final ModelMap m, final HttpSession s) throws DangerException {
		m.put("view", "/home/donde");

		return "/_t/frame";
	}
	@GetMapping("/privacidadP")
	public String privacidadP(final ModelMap m, final HttpSession s) throws DangerException {
		m.put("view", "/home/donde");

		return "/_t/frame";
	}
	@GetMapping("/preciosP")
	public String politicasP(final ModelMap m, final HttpSession s) throws DangerException {

		m.put("view", "/home/donde");

		return "/_t/frame";
	}

	@GetMapping("recarga-tokens")
	public String recargaTokensGet(@RequestParam("id") final Long id, final ModelMap m, final HttpSession s) {
		m.put("entidad", entidadRepository.getOne(id));
		m.put("view", "/home/recarga-tokens");
		return "/_t/frame";

	}

	@PostMapping("recarga-tokens")
	public String recargaTokensPost(@RequestParam("id") final Long id, @RequestParam("saldo") final String saldo,
			final HttpSession s) throws DangerException {
		if (saldo == "") {
			PRG.error("No has añadido saldo a tu cuenta");
		} else {
			final float fSaldo = Float.parseFloat(saldo);
			final Entidad en = entidadRepository.getOne(id);
			final float saldoActual = en.getSaldo();
			en.setSaldo(saldoActual + fSaldo);
			final float saldoFinal = saldoActual + fSaldo;
			entidadRepository.save(en);
			s.setAttribute("saldo", saldoFinal);
		}
		return "redirect:/";
	}

}
