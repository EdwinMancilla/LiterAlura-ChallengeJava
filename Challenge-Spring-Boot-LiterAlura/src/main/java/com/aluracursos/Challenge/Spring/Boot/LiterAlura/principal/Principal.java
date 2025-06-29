package com.aluracursos.Challenge.Spring.Boot.LiterAlura.principal;

import com.aluracursos.Challenge.Spring.Boot.LiterAlura.Repository.AutorRepository;
import com.aluracursos.Challenge.Spring.Boot.LiterAlura.Repository.LibroRepository;
import com.aluracursos.Challenge.Spring.Boot.LiterAlura.model.*;
import com.aluracursos.Challenge.Spring.Boot.LiterAlura.service.AutorService;
import com.aluracursos.Challenge.Spring.Boot.LiterAlura.service.ConsumoAPI;
import com.aluracursos.Challenge.Spring.Boot.LiterAlura.service.ConvierteDatos;
import com.aluracursos.Challenge.Spring.Boot.LiterAlura.service.LibrosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;


@Component
public class Principal {
    private final LibroRepository repositorio;
    private final AutorRepository autorRepositorio;






    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();

    private final String URL_BASE = "https://gutendex.com/books?search=";

    private ConvierteDatos conversor = new ConvierteDatos();

    private List<Libros> libros;

// Constructor personalizado que recibe el repositorio de libros
public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
    this.repositorio= libroRepository;
    this.autorRepositorio = autorRepository;
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
         4- Listar Autores Vivos en un Determinado Año
         5- Listar Libros Por Idioma
         6- Listar Top 10 Libros Más Descargados
         0- Salir
         
         """;
    System.out.println(menu);
    opcion = teclado.nextInt();
    teclado.nextLine();

    switch (opcion){
        case 1:
            buscarLibroPorTitulo();
            break;
        case 2:
            listarLibrosRegistrados();
            break;
        case 3:
            listarAutoresRegistrados();
            break;
        case 4:
            listarAutoresPorDeterminadoAnio();
            break;
            case 5:
            listarLibrosPorIdioma();
            break;
            case 6:
            listarTop10LibrosMasDescargados();

    }
}

}

    private DatosLibro getDatosLibro() {
        System.out.println("Escriba el Titulo del Libro que desea Buscar: ");
        var nombreLibro = teclado.nextLine().trim().toLowerCase();

        var nombreCodificado = URLEncoder.encode(nombreLibro, StandardCharsets.UTF_8);
        var urlCompleta = URL_BASE + nombreCodificado;

        var json = consumoApi.obtenerDatos(urlCompleta);

        if (json == null || json.isBlank()) {
            throw new RuntimeException("No se recibió respuesta JSON de la API.");
        }

        var datosRespuesta = conversor.obtenerDatos(json, DatosRespuesta.class);

        if (datosRespuesta.results().isEmpty()) {
            throw new RuntimeException("No se encontró ningún libro con ese título.");
        }

        // Mostrar solo el primer resultado
        DatosLibro libro = datosRespuesta.results().get(0);

        String titulo = libro.titulo();
        String autores = libro.autores().isEmpty()
                ? "Desconocido"
                : libro.autores().stream()
                .map(DatosAutor::nombre)
                .reduce((a, b) -> a + ", " + b)
                .orElse("Desconocido");

        String idiomas = String.join(", ", libro.idiomas());
        int descargas = libro.numeroDeDescargas();

        System.out.println("""
    ------------------------------
    Título: %s
    Autor(es): %s
    Idioma(s): %s
    Descargas: %d
    """.formatted(titulo, autores, idiomas, descargas));

        return libro;
    }

    //METODO PARA BUSCAR LIBRO POR TITULO
    public void buscarLibroPorTitulo() {
        // 1. Pedir datos del libro desde API (tu método getDatosLibro())
        DatosLibro libroAPI = getDatosLibro();

        // Validar que el libro se haya encontrado (no nulo)
        if (libroAPI == null) {
            System.out.println("Libro no encontrado.");
            return;
        }

        // 2. Verificar si el libro ya está en la base de datos por título
        Optional<Libros> libroExistente = repositorio.findByTitulo(libroAPI.titulo());

        if (libroExistente.isPresent()) {
            System.out.println("El libro '" + libroAPI.titulo() + "' ya está guardado en la base de datos.");
            return; // Salir sin guardar nada
        }

        // Validar que la lista de autores no esté vacía
        String nombreAutor;
        Integer anoNacimiento = null;
        Integer anoFallecimiento = null;

        if (libroAPI.autores() == null || libroAPI.autores().isEmpty()) {
            System.out.println("No se encontraron autores para el libro '" + libroAPI.titulo() + "'. Se usará 'Desconocido'.");
            nombreAutor = "Desconocido";
        } else {
            nombreAutor = libroAPI.autores().get(0).nombre();
            anoNacimiento = libroAPI.autores().get(0).anoNacimiento();
            anoFallecimiento = libroAPI.autores().get(0).anoFallecimiento();
        }

        // 3. Buscar si el autor ya existe en la base de datos
        Optional<Autor> autorExistente = autorRepositorio.findByNombre(nombreAutor);

        Autor autor;
        if (autorExistente.isPresent()) {
            autor = autorExistente.get();
        } else {
            autor = new Autor(nombreAutor, anoNacimiento, anoFallecimiento);
            autorRepositorio.save(autor);
        }

        // 4. Crear objeto Libro y asignarle el autor
        Libros libro = new Libros();
        libro.setTitulo(libroAPI.titulo());

        // Validar que la lista de idiomas no esté vacía antes de asignar idioma
        if (libroAPI.idiomas() != null && !libroAPI.idiomas().isEmpty()) {
            libro.setIdioma(libroAPI.idiomas().get(0));
        } else {
            libro.setIdioma("desconocido");
        }

        libro.setNumeroDeDescargas(Double.valueOf(libroAPI.numeroDeDescargas()));
        libro.setAutor(autor);

        // 5. Guardar libro
        repositorio.save(libro);

        // 6. Mostrar mensaje de éxito
        System.out.println("Libro guardado: " + libro.getTitulo());
    }

    //METODO PARA LISTAR LIBROS REGISTRADOS
    public void listarLibrosRegistrados() {
        List<Libros> listaLibros = repositorio.findAll(); // Obtiene todos los libros

        if (listaLibros.isEmpty()) {
            System.out.println("No hay libros registrados en la base de datos.");
        } else {
            System.out.println("----- Libros Registrados -----");
            for (Libros libro : listaLibros) {
                System.out.println("Título: " + libro.getTitulo());
                System.out.println("Autor(es): " + libro.getAutor().getNombre());
                System.out.println("Idioma(s): " + libro.getIdioma());
                System.out.println("Descargas: " + libro.getNumeroDeDescargas());
                System.out.println("-------------------------------");
            }
        }
    }

    //METODO PARA LISTAR AUTORES REGISTRADOS
    public void listarAutoresRegistrados() {
        List<Autor> autores = autorRepositorio.findAll();

        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados en la base de datos.");
        } else {
            System.out.println("\n------ AUTORES REGISTRADOS ------");
            autores.forEach(autor -> {
                System.out.println("Nombre: " + autor.getNombre());
                System.out.println("Año de Nacimiento: " + (autor.getAnoNacimiento() != null ? autor.getAnoNacimiento() : "Desconocido"));
                System.out.println("Año de Fallecimiento: " + (autor.getAnoFallecimiento() != null ? autor.getAnoFallecimiento() : "Desconocido"));
                System.out.println("---------------------------------");
            });
        }
    }

    //METODO PARA LISTAR AUTORES VIVOS EN UN DETERMINADO AÑO
    public void listarAutoresPorDeterminadoAnio() {
        System.out.print("Ingrese el año para buscar autores vivos: ");
        int anio = teclado.nextInt();
        teclado.nextLine();

        List<Autor> autores = autorRepositorio.buscarAutoresVivosEnAnio(anio);

        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año " + anio + ".");
            return;
        }

        System.out.println("Autores vivos en el año " + anio + ":");
        for (Autor autor : autores) {
            System.out.println("Nombre: " + autor.getNombre());
            System.out.println("Año de Nacimiento: " + (autor.getAnoNacimiento() != null ? autor.getAnoNacimiento() : "Desconocido"));
            System.out.println("Año de Fallecimiento: " + (autor.getAnoFallecimiento() != null ? autor.getAnoFallecimiento() : "Desconocido"));
            System.out.println("---------------------------");
        }
    }

    //METODO PARA LISTAR LIBROS POR IDIOMA
    public void listarLibrosPorIdioma() {
        System.out.print("Ingrese el código del idioma (por ejemplo: en, es, fr): ");
        String idioma = teclado.nextLine().trim().toLowerCase();

        // Busca los libros con ese idioma exactamente
        List<Libros> librosPorIdioma = repositorio.findByIdiomaContaining(idioma);

        if (librosPorIdioma.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma '" + idioma + "'.");
        } else {
            System.out.println("Libros en idioma '" + idioma + "':");
            for (Libros libro : librosPorIdioma) {
                System.out.println("------------------------");
                System.out.println("Título: " + libro.getTitulo());
                System.out.println("Autor: " + libro.getAutor().getNombre());
                System.out.println("Idioma: " + libro.getIdioma());
                System.out.println("Descargas: " + libro.getNumeroDeDescargas());
            }
        }
    }

    // METODO TOP 10 LIBROS MAS DESCARGADOS
    public void listarTop10LibrosMasDescargados() {
        Pageable top10 = PageRequest.of(0, 10);
        List<Libros> libros = repositorio.findTop10ByOrderByNumeroDeDescargasDesc(top10);

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
        } else {
            System.out.println("----- Top 10 Libros Más Descargados -----");
            for (Libros libro : libros) {
                System.out.println("Título: " + libro.getTitulo());
                System.out.println("Autor: " + libro.getAutor().getNombre());
                System.out.println("Idioma: " + libro.getIdioma());
                System.out.println("Descargas: " + libro.getNumeroDeDescargas());
                System.out.println("----------------------------------------");
            }
        }
    }




}
