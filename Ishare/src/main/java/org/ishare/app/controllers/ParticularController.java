package org.ishare.app.controllers;

import java.time.LocalDate;

import javax.servlet.http.HttpSession;

import org.ishare.app.domains.Entidad;
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

import com.sun.el.parser.ParseException;

@Controller
@RequestMapping("particular")
public class ParticularController {

	@Autowired
	ParticularRepository particularRepository;
	@Autowired
	private RolRepository rolRepository;

	@GetMapping("c")
	public String particularCGet(final ModelMap m) {
		m.put("view", "/particular/c");
		m.put("roles", rolRepository.findAll());
		return "/_t/frame";
	}

	@PostMapping("c")
	public String particularCPost(@RequestParam("nombreUsuario") final String nombreUsuario,
			@RequestParam("contrasena") final String contrasena, @RequestParam("localidad") final String localidad,
			@RequestParam("direccion") final String direccion, @RequestParam("codigoPostal") final String codigoPostal,
			@RequestParam("telefono") final String telefono, @RequestParam("email") final String email,
			@RequestParam("idRol") final Long idRol, @RequestParam("saldo") final String saldo,
			@RequestParam("dni") final String dni, @RequestParam("nombre") final String nombre,
			@RequestParam("apellidos") final String apellidos,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fechaNacimiento") final String fechaNacimiento,final HttpSession s)
			throws DangerException, ParseException {
		
		if (nombreUsuario == "" || contrasena == "" || localidad == "" || direccion == "" || codigoPostal == ""
				|| telefono == "" || email == "" || saldo == "" || dni == "" || nombre == "" || apellidos == ""
				|| fechaNacimiento == "" || nombreUsuario == null || contrasena == null || localidad == null
				|| direccion == null || codigoPostal == null || telefono == null || email == null || saldo == null
				|| dni == null || nombre == null || apellidos == null || fechaNacimiento == null) {
			PRG.error("Ningún campo puede quedar vacío", "/particular/r");
		} else {
			final int iCodigoPostal = Integer.parseInt(codigoPostal);
			final int iTelefono = Integer.parseInt(telefono);
			final float fSaldo = Float.parseFloat(saldo);
			final LocalDate lFechaNacimiento = LocalDate.parse(fechaNacimiento);
			final Rol rol = rolRepository.getOne(idRol);
			final Particular p = new Particular(nombreUsuario, contrasena, localidad, direccion, iCodigoPostal,
					iTelefono, email, rol, fSaldo, dni, nombre, apellidos, lFechaNacimiento);
			if (((Entidad)s.getAttribute("user")).getRol().getNombre() == "User"){
				PRG.error("no puede estar logueado para realizar esta operación", "/");
			}
			else {
				try {
					particularRepository.save(p);
	
				} catch (final Exception e) {
					// TODO: handle exception
					PRG.error("no pueden haber dos Dni iguales", "/particular/c");
				}
			}
		}

		return "redirect:/";
	}

	@GetMapping("r")
	public String particularRGet(final ModelMap m,final HttpSession s) throws DangerException {
		H.isRolOK("Admin", s);
		m.put("particulares", particularRepository.findAll());
		m.put("view", "/particular/r");

		return "/_t/frame";
	}

	@GetMapping("u")
	public String updateGet(@RequestParam("id") final Long id, final ModelMap m) {
		m.put("particular", particularRepository.getOne(id));
		m.put("roles", rolRepository.findAll());
		m.put("view", "/particular/particularU");
		return "/_t/frame";
	}

	@PostMapping("u")
	public String updatePost(@RequestParam("nombreUsuario") final String nombreUsuario,
			@RequestParam("contrasena") final String contrasena, @RequestParam("localidad") final String localidad,
			@RequestParam("direccion") final String direccion, @RequestParam("codigoPostal") final String codigoPostal,
			@RequestParam("telefono") final String telefono, @RequestParam("email") final String email,
			@RequestParam("idRol") final Long idRol, @RequestParam("saldo") final String saldo,
			@RequestParam("dni") final String dni, @RequestParam("nombre") final String nombre,
			@RequestParam("apellidos") final String apellidos,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fechaNacimiento") final String fechaNacimiento,
			@RequestParam("id") final Long id, final HttpSession s) throws DangerException, ParseException {
		if (nombreUsuario == "" || contrasena == "" || direccion == "" || telefono == "" || dni == ""
				|| apellidos == "" ) {
			PRG.error("Ningún campo puede quedar vacío", "/empresa/r");
		} else {
			final int iCodigoPostal = Integer.parseInt(codigoPostal);
			final int iTelefono = Integer.parseInt(telefono);
			final float fSaldo = Float.parseFloat(saldo);
			final Rol rol = rolRepository.getOne(idRol);
			final LocalDate lFechaNacimiento = LocalDate.parse(fechaNacimiento);
			final Particular p = particularRepository.getOne(id);
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
			H.isRolOK("Admin", s);H.isRolOK("user", s);
			try {
			particularRepository.save(p);

			H.info(s, "Particular " + nombre + " actualizado correctamente", "info", "/particular/r");
		} catch (final Exception e) {
			PRG.error("Particular duplicado",  "/particular/r");
		}
		}
		return "redirect:/info";
	}

	@PostMapping("d")
	public String borrarPost(@RequestParam("id") final Long id, final HttpSession s) throws DangerException {
		H.isRolOK("Admin", s);
		try {
		final Particular particular = particularRepository.getOne(id);
		particularRepository.delete(particular);
		}catch(Exception e) {
			PRG.error("Estas seguro que quieres borrar al particular");
		}

		return "redirect:/particular/r";
	}

}