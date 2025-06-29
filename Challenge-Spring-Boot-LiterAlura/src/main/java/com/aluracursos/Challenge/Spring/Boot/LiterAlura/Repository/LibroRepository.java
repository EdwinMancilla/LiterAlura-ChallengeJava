package com.aluracursos.Challenge.Spring.Boot.LiterAlura.Repository;

import com.aluracursos.Challenge.Spring.Boot.LiterAlura.model.Libros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository <Libros, Long> {

    Optional<Libros> findByTituloIgnoreCase(String titulo);

    @Query("SELECT l FROM Libros l WHERE l.idioma LIKE %:idioma%")
    List<Libros> findByIdiomaContaining(@Param("idioma") String idioma);

    @Query("SELECT l FROM Libros l ORDER BY l.numeroDeDescargas DESC")
    List<Libros> findTop10ByOrderByNumeroDeDescargasDesc(Pageable pageable);

    Optional<Libros> findByTitulo(String titulo);
}
