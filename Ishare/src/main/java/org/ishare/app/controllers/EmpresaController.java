
package org.ishare.app.controllers;

import java.text.ParseException;

import javax.servlet.http.HttpSession;

import org.ishare.app.domains.Empresa;
import org.ishare.app.domains.Entidad;
import org.ishare.app.domains.Rol;
import org.ishare.app.exceptions.DangerException;
import org.ishare.app.helpers.H;
import org.ishare.app.helpers.PRG;
import org.ishare.app.repositories.EmpresaRepository;
import org.ishare.app.repositories.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	@Autowired
	private RolRepository rolRepository;

	@GetMapping("c")
	public String empresaCGet(final ModelMap m) {
		m.put("view", "empresa/c");
		m.put("roles", rolRepository.findAll());
		return "_t/frame";
	}

	@PostMapping("c")
	public String empresaCPost(@RequestParam("nombreUsuario") final String nombreUsuario,
			@RequestParam("contrasena") final String contrasena, @RequestParam("localidad") final String localidad,
			@RequestParam("direccion") final String direccion, @RequestParam("codigoPostal") final String codigoPostal,
			@RequestParam("telefono") final String telefono, @RequestParam("email") final String email,
			@RequestParam("idRol") final Long idRol, @RequestParam("saldo") final String saldo,
			@RequestParam("cif") final String cif, @RequestParam("razonSocial") final String razonSocial,final HttpSession s)
			throws DangerException, ParseException {
		
		if (nombreUsuario == "" || contrasena == "" || localidad == "" || direccion == "" || codigoPostal == ""
				|| telefono == "" || email == "" || saldo == "" || cif == "" || razonSocial == ""
				|| nombreUsuario == null || contrasena == null || localidad == null || direccion == null
				|| telefono == null || email == null || saldo == null || cif == null || razonSocial == null) {
			PRG.error("Ningún campo puede quedar vacío", "/empresa/r");
		} else {

			final int iCodigoPostal = Integer.parseInt(codigoPostal);
			final int iTelefono = Integer.parseInt(telefono);
			final float fSaldo = Float.parseFloat(saldo);
			final Rol rol = rolRepository.getOne(idRol);
			final Empresa em = new Empresa(nombreUsuario, (new BCryptPasswordEncoder()).encode(contrasena), localidad, direccion, iCodigoPostal, iTelefono,
					email, rol, fSaldo, cif, razonSocial);
			if (((Entidad)s.getAttribute("user")).getRol().getNombre() == "User"){
				PRG.error("no puede estar logueado para realizar esta operación", "/");
			}
			try {
				empresaRepository.save(em);
			} catch (final Exception e) {
				// TODO: handle exception
				PRG.error("no pueden haber dos CIF ni Razón social iguales", "/empresa/c");
			}
		}

		return "redirect:/";
	}

	@GetMapping("r")
	public String empresaRGet(final ModelMap m,final HttpSession s) throws DangerException {
		H.isRolOK("Admin", s);
		m.put("empresas", empresaRepository.findAll());
		m.put("view", "empresa/r");

		return "_t/frame";
	}

	@GetMapping("u")
	public String updateGet(@RequestParam("id") final Long id, final ModelMap m) {
		m.put("empresa", empresaRepository.getOne(id));
		m.put("roles", rolRepository.findAll());
		m.put("view", "empresa/empresaU");
		return "_t/frame";
	}

	@PostMapping("u")
	public String updatePost(@RequestParam("nombreUsuario") final String nombreUsuario,
			@RequestParam("contrasena") final String contrasena, @RequestParam("localidad") final String localidad,
			@RequestParam("direccion") final String direccion, @RequestParam("codigoPostal") final String codigoPostal,
			@RequestParam("telefono") final String telefono, @RequestParam("email") final String email,
			@RequestParam("idRol") final Long idRol, @RequestParam("saldo") final String saldo,
			@RequestParam("cif") final String cif, @RequestParam("razonSocial") final String razonSocial,
			@RequestParam("id") final Long id, final HttpSession s) throws DangerException, ParseException {
		if (nombreUsuario == "" || contrasena == "" || localidad == "" || direccion == "" || codigoPostal == ""
				|| telefono == "" || email == "" || saldo == "" || cif == "" || razonSocial == "") {
			PRG.error("Ningún campo puede quedar vacío", "/empresa/r");
		} else {
			
			
				
				final int iCodigoPostal = Integer.parseInt(codigoPostal);
				final int iTelefono = Integer.parseInt(telefono);
				final float fSaldo = Float.parseFloat(saldo);
				final Rol rol = rolRepository.getOne(idRol);
				final Empresa em = empresaRepository.getOne(id);
				em.setNombreUsuario(nombreUsuario);
				em.setContrasena((new BCryptPasswordEncoder()).encode(contrasena));
				em.setLocalidad(localidad);
				em.setDireccion(direccion);
				em.setCodigoPostal(iCodigoPostal);
				em.setTelefono(iTelefono);
				em.setEmail(email);
				em.setRol(rol);
				em.setSaldo(fSaldo);
				em.setCif(cif);
				em.setRazonSocial(razonSocial);
				H.isRolOK("Admin", s);H.isRolOK("user", s);
				try {
				empresaRepository.save(em);
				
				H.info(s, "Empresa " + razonSocial + " actualizada correctamente", "info", "/empresa/r");// No escribe
			} catch (final Exception e) {
				PRG.error(" Empresa duplicada",  "/empresa/r");
			}
		}
		return "redirect:/info";

	}

	@PostMapping("d")
	public String borrarPost(@RequestParam("id") final Long id, final HttpSession s) throws DangerException {
		H.isRolOK("Admin", s);
		try {
			final Empresa empresa = empresaRepository.getOne(id);
			empresaRepository.delete(empresa);
		}catch(Exception e) {
			PRG.error("Estas seguro que quieres borrar la empresa");
		}
		
		return "redirect:/empresa/r";
	}

}