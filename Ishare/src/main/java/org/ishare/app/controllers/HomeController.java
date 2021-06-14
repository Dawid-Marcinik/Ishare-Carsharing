package org.ishare.app.controllers;

import java.text.ParseException;
import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.ishare.app.domains.Empresa;
import org.ishare.app.domains.Entidad;
import org.ishare.app.domains.Particular;
import org.ishare.app.exceptions.DangerException;
import org.ishare.app.exceptions.InfoException;
import org.ishare.app.helpers.H;
import org.ishare.app.helpers.PRG;
import org.ishare.app.repositories.EmpresaRepository;
import org.ishare.app.repositories.EntidadRepository;
import org.ishare.app.repositories.ParticularRepository;
import org.ishare.app.repositories.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

	@Autowired
	ParticularRepository particularRepository;
	@Autowired
	private EntidadRepository entidadRepository;
	@Autowired
	private RolRepository rolRepository;
	@Autowired
	private EmpresaRepository empresaRepository;

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
			PRG.error("Nombre de usuario o contraseña incorrectos","/login");
		}
		if (!(new BCryptPasswordEncoder()).matches(contrasena,entidad.getContrasena())) {
			PRG.error("Nombre de usuario o contraseña incorrectos","/login");
		}
		s.setAttribute("user", entidad);
		s.setAttribute("saldo", entidad.getSaldo());
		s.setAttribute("type", entidad.getDtype());
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
		m.put("view", "/home/quienes");

		return "/_t/frame";
	}

	@GetMapping("donde")
	public String empresaDGet(final ModelMap m, final HttpSession s) throws DangerException {
		m.put("view", "/home/donde");

		return "/_t/frame";
	}

	// Añadido por si acaso
	@GetMapping("/administracion")
	public String administracion(final ModelMap modelo, final HttpSession s) throws DangerException {
		H.isRolOK("Admin", s);
		modelo.put("view", "/home/menuAdmin");
		return "_t/frame";
	}

	@GetMapping("/politicasC")
	public String politicasC(final ModelMap m, final HttpSession s) throws DangerException {
		m.put("view", "/home/politicasC");

		return "/_t/frame";
	}

	@GetMapping("/terminosC")
	public String terminosC(final ModelMap m, final HttpSession s) throws DangerException {
		m.put("view", "/home/terminosC");

		return "/_t/frame";
	}

	@GetMapping("/privacidadP")
	public String privacidadP(final ModelMap m, final HttpSession s) throws DangerException {
		m.put("view", "/home/privacidadP");

		return "/_t/frame";
	}

	@GetMapping("/preciosP")
	public String politicasP(final ModelMap m, final HttpSession s) throws DangerException {

		m.put("view", "/home/preciosP");

		return "/_t/frame";
	}

	@GetMapping("/faq")
	public String faq(final ModelMap m, final HttpSession s) throws DangerException {

		m.put("view", "/home/faq");

		return "/_t/frame";
	}

	@GetMapping("/ecoTierra")
	public String ecoTierra(final ModelMap m, final HttpSession s) throws DangerException {

		m.put("view", "/home/ecoTierra");

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

	@GetMapping("/flota")
	public String nuestraFlota(final ModelMap m, final HttpSession s) throws DangerException {

		m.put("view", "/home/flota");

		return "/_t/frame";
	}

	@GetMapping("/editar-perfil")
	public String getEditarPerfil(@RequestParam("id") final Long id, final ModelMap m, final HttpSession s) {
		final String tipo = (String) s.getAttribute("type");
		if (tipo.equals("Particular")) {
			m.put("view", "/home/editar-perfil-particular");
			m.put("particular", particularRepository.getOne(id));
		} else {
			m.put("view", "/home/editar-perfil-empresa");
			m.put("empresa", empresaRepository.getOne(id));
		}
		return "/_t/frame";
	}

	@PostMapping("/editar-perfil-empresa")
	public String postEditarPerfilEmpresa(@RequestParam("nombreUsuario") final String nombreUsuario,
			@RequestParam("contrasena") final String contrasena, @RequestParam("localidad") final String localidad,
			@RequestParam("direccion") final String direccion, @RequestParam("codigoPostal") final String codigoPostal,
			@RequestParam("telefono") final String telefono, @RequestParam("email") final String email,
			@RequestParam("cif") final String cif, @RequestParam("razonSocial") final String razonSocial,
			@RequestParam("id") final Long id, final HttpSession s) throws DangerException, ParseException {
		if (nombreUsuario == "" || contrasena == "" || localidad == "" || direccion == "" || codigoPostal == ""
				|| telefono == "" || email == "" || cif == "" || razonSocial == "") {
			PRG.error("Ningún campo puede quedar vacío", "/");
		} else {

			final int iCodigoPostal = Integer.parseInt(codigoPostal);
			final int iTelefono = Integer.parseInt(telefono);
			final Empresa em = empresaRepository.getOne(id);
			em.setNombreUsuario(nombreUsuario);
			em.setContrasena(contrasena);
			em.setLocalidad(localidad);
			em.setDireccion(direccion);
			em.setCodigoPostal(iCodigoPostal);
			em.setTelefono(iTelefono);
			em.setEmail(email);
			em.setCif(cif);
			em.setRazonSocial(razonSocial);
			try {
				empresaRepository.save(em);

				H.info(s, "Perfil actualizado correctamente", "info", "/");// No escribe
			} catch (final Exception e) {
				PRG.error(" Empresa duplicada", "/");
			}
		}
		return "redirect:/";

	}

	@PostMapping("/editar-perfil-particular")
	public String postEditarPerfilParticular(@RequestParam("nombreUsuario") final String nombreUsuario,
			@RequestParam("contrasena") final String contrasena, @RequestParam("localidad") final String localidad,
			@RequestParam("direccion") final String direccion, @RequestParam("codigoPostal") final String codigoPostal,
			@RequestParam("telefono") final String telefono, @RequestParam("email") final String email,
			@RequestParam("dni") final String dni, @RequestParam("nombre") final String nombre,
			@RequestParam("apellidos") final String apellidos,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fechaNacimiento") final String fechaNacimiento,
			@RequestParam("id") final Long id, final HttpSession s) throws DangerException, ParseException {
		if (nombreUsuario == "" || contrasena == "" || direccion == "" || telefono == "" || dni == ""
				|| apellidos == "") {
			PRG.error("Ningún campo puede quedar vacío", "/empresa/r");
		} else {
			final int iCodigoPostal = Integer.parseInt(codigoPostal);
			final int iTelefono = Integer.parseInt(telefono);
			final LocalDate lFechaNacimiento = LocalDate.parse(fechaNacimiento);
			final Particular p = particularRepository.getOne(id);
			p.setNombreUsuario(nombreUsuario);
			p.setContrasena(contrasena);
			p.setLocalidad(localidad);
			p.setDireccion(direccion);
			p.setCodigoPostal(iCodigoPostal);
			p.setTelefono(iTelefono);
			p.setEmail(email);
			p.setDni(dni);
			p.setNombre(nombre);
			p.setApellidos(apellidos);
			p.setFechaNacimiento(lFechaNacimiento);
			try {
				particularRepository.save(p);

				H.info(s, "Perfil actualizado correctamente", "info", "/");
			} catch (final Exception e) {
				PRG.error("Particular duplicado", "/");
			}
		}
		return "redirect:/";
	}
	
}
