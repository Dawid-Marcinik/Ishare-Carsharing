package org.ishare.app.controllers;

import org.ishare.app.repositories.CocheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping("/REST/coche")

public class CocheRestController {

	@Autowired
	private CocheRepository cocheRepository;
	
}
