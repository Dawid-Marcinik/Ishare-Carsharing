package org.ishare.app.controllers;

import org.ishare.app.repositories.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("entidad")
public class EntidadController {
	
	@Autowired 
	private RolRepository rolRepository;
	
	@GetMapping("c")
	public String entidadCGet(ModelMap m) {
		m.put("roles",this.rolRepository.findAll());
		m.put("view", "/entidad/c");
		return "/_t/frame";
	}

}
