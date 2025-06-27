package com.aluracursos.Challenge.Spring.Boot.LiterAlura;

import com.aluracursos.Challenge.Spring.Boot.LiterAlura.Repository.LibroRepository;
import com.aluracursos.Challenge.Spring.Boot.LiterAlura.model.DatosLibro;
import com.aluracursos.Challenge.Spring.Boot.LiterAlura.model.Libros;
import com.aluracursos.Challenge.Spring.Boot.LiterAlura.principal.Principal;
import com.aluracursos.Challenge.Spring.Boot.LiterAlura.service.ConsumoAPI;
import com.aluracursos.Challenge.Spring.Boot.LiterAlura.service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class ChallengeSpringBootLiterAluraApplication implements CommandLineRunner {
	private final LibroRepository libroRepository;

	@Autowired
	public ChallengeSpringBootLiterAluraApplication(LibroRepository libroRepository) {
		this.libroRepository = libroRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(ChallengeSpringBootLiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(libroRepository);
		principal.muestraMenu();


	}
}
