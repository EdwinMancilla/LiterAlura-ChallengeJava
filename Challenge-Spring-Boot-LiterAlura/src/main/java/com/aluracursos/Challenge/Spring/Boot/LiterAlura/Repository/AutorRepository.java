package com.aluracursos.Challenge.Spring.Boot.LiterAlura.Repository;

import com.aluracursos.Challenge.Spring.Boot.LiterAlura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long > {


    Optional<Autor> findByNombre(String nombre);


    @Query("SELECT a FROM Autor a LEFT JOIN a.libros")
    List<Autor> findAllConLibros();

    @Query("SELECT a FROM Autor a LEFT JOIN FETCH a.libros WHERE (a.anoFallecimiento IS NULL OR a.anoFallecimiento > :ano) AND a.anoNacimiento <= :ano")
    List<Autor> findAutoresVivosEnAnoConLibros(@Param("ano") int ano);


}
