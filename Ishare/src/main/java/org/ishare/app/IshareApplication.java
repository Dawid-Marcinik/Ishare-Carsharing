package org.ishare.app;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.ishare.app.domains.Modelo;
import org.ishare.app.repositories.ModeloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {
SecurityAutoConfiguration.class })
public class IshareApplication implements CommandLineRunner{

	@Autowired
	ModeloRepository modeloRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(IshareApplication.class, args);
	}
	
	@Override
	public void run(String... arg0) throws Exception {
		// retrieve image from MySQL via SpringJPA
		for(Modelo modelo : modeloRepository.findAll()){
			Files.write(Paths.get("src/main/resources/static/retrieve-dir/" + Long.toString(modelo.getId())), modelo.getImagen());
		}
	}
}
