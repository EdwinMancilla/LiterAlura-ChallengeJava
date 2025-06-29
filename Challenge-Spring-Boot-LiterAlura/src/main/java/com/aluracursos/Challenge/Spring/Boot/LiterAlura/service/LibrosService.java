package com.aluracursos.Challenge.Spring.Boot.LiterAlura.service;

import com.aluracursos.Challenge.Spring.Boot.LiterAlura.Repository.LibroRepository;
import com.aluracursos.Challenge.Spring.Boot.LiterAlura.model.Libros;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibrosService {

    @Autowired
    private LibroRepository libroRepository;

    public List<Libros> listarLibros(){
        return libroRepository.findAll();
    }

    public List<Libros> listarLibrosPorIdioma(String idioma){
        return libroRepository.findByIdiomaContaining(idioma);
    }

    public Libros crearLibro(Libros libro){
        return libroRepository.save(libro);
    }

    public Optional<Libros> obtenerLIbroPorId(Long id){
        return libroRepository.findById(id);
    }

    public Optional<Libros> obtenerLibroPorTitulo(String titulo){
        return libroRepository.findByTituloIgnoreCase(titulo);
    }

    public Libros actualizarLibro(Long id, Libros libroDetalles){
        Libros libros = libroRepository.findById(id).orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        libros.setTitulo(libroDetalles.getTitulo());
        libros.setIdioma(libroDetalles.getIdioma());
        libros.setNumeroDeDescargas(libroDetalles.getNumeroDeDescargas());
        libros.setAutor(libroDetalles.getAutor());
        return libroRepository.save(libros);
    }

    public void eliminarLibro(Long id){
        libroRepository.deleteById(id);
    }


}
