package com.aluracursos.Challenge.Spring.Boot.LiterAlura;

import com.aluracursos.Challenge.Spring.Boot.LiterAlura.principal.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallengeSpringBootLiterAluraApplication implements CommandLineRunner {

	private final Principal principal;

	@Autowired
	public ChallengeSpringBootLiterAluraApplication(Principal principal) {
		this.principal = principal;
	}

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ChallengeSpringBootLiterAluraApplication.class);
		app.setWebApplicationType(WebApplicationType.NONE); // 👈 desactiva modo web
		app.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		principal.muestraMenu();
	}
}