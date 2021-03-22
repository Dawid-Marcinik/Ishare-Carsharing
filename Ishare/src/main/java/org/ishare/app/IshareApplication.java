package org.ishare.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {
SecurityAutoConfiguration.class })
public class IshareApplication {

	public static void main(String[] args) {
		SpringApplication.run(IshareApplication.class, args);
	}

}
