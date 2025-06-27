package com.aluracursos.Challenge.Spring.Boot.LiterAlura.model;

import jakarta.persistence.*;

import java.util.Optional;
import java.util.OptionalDouble;

@Entity
@Table(name = "libros")
public class Libros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(unique = true)
    private String titulo;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;
    private String idioma;
    private Double NumeroDeDescargas;

    // Constructor por defecto para JPA
    public Libros() {

    }

    public Libros (DatosLibro datosLibro){
        this.titulo = datosLibro.titulo();
        if(datosLibro.autores() != null && !datosLibro.autores().isEmpty()){
            this.autor = new DatosAutor(datosLibro.autores().get(0));
        }else {
            this.autor = null;
        }
        this.idioma = datosLibro.idiomas();
        this.NumeroDeDescargas = datosLibro.numeroDeDescargas();


    }

    // Getters and Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Double getNumeroDeDescargas() {
        return NumeroDeDescargas;
    }

    public void setNumeroDeDescargas(Double numeroDeDescargas) {
        NumeroDeDescargas = numeroDeDescargas;
    }

    @Override
    public String toString() {
        return "Libros{" +
                "titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", idioma='" + idioma + '\'' +
                ", NumeroDeDescargas=" + NumeroDeDescargas +
                '}';
    }
}
