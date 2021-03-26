package org.ishare.app.controllers;

import java.text.ParseException;

import javax.servlet.http.HttpSession;

import org.ishare.app.domains.Empresa;
import org.ishare.app.domains.Rol;
import org.ishare.app.exceptions.DangerException;
import org.ishare.app.helpers.H;
import org.ishare.app.helpers.PRG;
import org.ishare.app.repositories.EmpresaRepository;
import org.ishare.app.repositories.RolRepository;
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
	@Autowired
	private RolRepository rolRepository;

	@GetMapping("c")
	public String empresaCGet(ModelMap m) {
		m.put("view", "/empresa/c");
		m.put("roles", rolRepository.findAll());
		return "/_t/frame";
	}

	@PostMapping("c")
	public String empresaCPost(@RequestParam("nombreUsuario") String nombreUsuario,
			@RequestParam("contrasena") String contrasena, @RequestParam("localidad") String localidad,
			@RequestParam("direccion") String direccion, @RequestParam("codigoPostal") String codigoPostal,
			@RequestParam("telefono") String telefono, @RequestParam("email") String email,

			@RequestParam("idRol") Long idRol, @RequestParam("saldo") String saldo, @RequestParam("cif") String cif,
			@RequestParam("razonSocial") String razonSocial) throws DangerException, ParseException {

		if (nombreUsuario == "" || contrasena == "" || localidad == "" || direccion == "" || codigoPostal == ""
				|| telefono == "" || email == "" || cif == "" || razonSocial == "" || nombreUsuario == null
				|| contrasena == null || localidad == null || direccion == null || telefono == null || email == null
				|| saldo == null || cif == null || razonSocial == null) {
			PRG.error("Ningún campo puede quedar vacío", "/empresa/r");
		} else {

			Integer iCodigoPostal = Integer.parseInt(codigoPostal);
			Integer iTelefono = Integer.parseInt(telefono);
			Float fSaldo = Float.parseFloat(saldo);

			if ((cif != "") && (razonSocial != "")) {
				Rol rol = rolRepository.getOne(idRol);
				Empresa em = new Empresa(nombreUsuario, contrasena, localidad, direccion, iCodigoPostal, iTelefono,
						email, rol, fSaldo, cif, razonSocial);

				try {
					empresaRepository.save(em);
				} catch (Exception e) {
					// TODO: handle exception
					PRG.error("no pueden haber dos CIF ni Razón social iguales", "/empresa/c");
				}
			}
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
		m.put("roles", rolRepository.findAll());
		m.put("view", "/empresa/empresaU");
		return "/_t/frame";
	}

	@PostMapping("u")
	public String updatePost(@RequestParam("nombreUsuario") String nombreUsuario,
			@RequestParam("contrasena") String contrasena, @RequestParam("localidad") String localidad,
			@RequestParam("direccion") String direccion, @RequestParam("codigoPostal") String codigoPostal,
			@RequestParam("telefono") String telefono, @RequestParam("email") String email,

			@RequestParam("saldo") String saldo,

			@RequestParam("idRol") Long idRol,

			@RequestParam("cif") String cif, @RequestParam("razonSocial") String razonSocial,
			@RequestParam("id") Long id, HttpSession s) throws DangerException {
		if (nombreUsuario == "" || contrasena == "" || localidad == "" || direccion == "" || codigoPostal == ""
				|| telefono == "" || email == "" || saldo == "" || cif == "" || razonSocial == "") {
			PRG.error("Ningún campo puede quedar vacío", "/empresa/r");
		} else {
			try {
				int iCodigoPostal = Integer.parseInt(codigoPostal);
				int iTelefono = Integer.parseInt(telefono);
				float fSaldo = Float.parseFloat(saldo);
				Empresa em = empresaRepository.getOne(id);
				Rol rol = rolRepository.getOne(idRol);
				em.setNombreUsuario(nombreUsuario);
				em.setContrasena(contrasena);
				em.setLocalidad(localidad);
				em.setDireccion(direccion);
				em.setCodigoPostal(iCodigoPostal);
				em.setTelefono(iTelefono);
				em.setEmail(email);
				em.setSaldo(fSaldo);
				em.setCif(cif);
				em.setRol(rol);
				em.setRazonSocial(razonSocial);
				empresaRepository.save(em);

				H.info(s, "Empresa " + razonSocial + " actualizada correctamente", "info", "/empresa/r");// No escribe
			} catch (Exception e) {
				H.info(s, "Empresa " + razonSocial + " duplicada", "danger", "/empresa/r");
			}
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