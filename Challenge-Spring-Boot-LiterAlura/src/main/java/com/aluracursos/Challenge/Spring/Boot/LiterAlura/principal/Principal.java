package com.aluracursos.Challenge.Spring.Boot.LiterAlura.principal;

import com.aluracursos.Challenge.Spring.Boot.LiterAlura.Repository.LibroRepository;
import com.aluracursos.Challenge.Spring.Boot.LiterAlura.model.DatosLibro;
import com.aluracursos.Challenge.Spring.Boot.LiterAlura.model.Libros;
import com.aluracursos.Challenge.Spring.Boot.LiterAlura.service.ConsumoAPI;
import com.aluracursos.Challenge.Spring.Boot.LiterAlura.service.ConvierteDatos;
import com.aluracursos.Challenge.Spring.Boot.LiterAlura.service.LibrosService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {

    @Autowired
    private LibrosService librosService;


    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();

    private final String URL_BASE = "https://gutendex.com/books?search=";

    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository repositorio;
    private List<Libros> libros;

// Constructor personalizado que recibe el repositorio de libros
    public Principal(LibroRepository repository){
        this.repositorio = repository;
    }


public void muestraMenu() {
    var opcion = -1;
while (opcion !=0) {
 var menu = """
         ------------------ MENU -----------------
         Eliga La Opcion a traves de su Numero:
         
         1- Buscar Libro Por Titulo
         2- Listar Libros Registrados
         3- Listar Autores Registrados
         4- Listar Autores Vivos en un Determoinado Año
         5- Listar Libros Por Idioma
         0- Salir
         
         """;
    System.out.println(menu);
    opcion = teclado.nextInt();
    teclado.nextLine();

    switch (opcion){
        case 1:
            buscarLibroPorTitulo();
            break;

    }
}

}

private DatosLibro getDatosLibro(){
    System.out.println("Escriba el Titulo del Libro que desea Buscar: ");
    var nombreLibro = teclado.nextLine();
    var json = consumoApi.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "+"));
    System.out.println("Datos obtenidos: " + json);
    DatosLibro datos= conversor.obtenerDatos(json, DatosLibro.class);
    Optional<Libros> librosBuscados = libros.stream()
            .filter(l -> l.getTitulo().toLowerCase().contains(nombreLibro.toLowerCase())).findFirst();
    return datos;




}

    private void buscarLibroPorTitulo() {
        DatosLibro datosLibro = getDatosLibro();
        Libros libro = new Libros(datosLibro);
        repositorio.save(libro);
        System.out.println(datosLibro);

    }


}
