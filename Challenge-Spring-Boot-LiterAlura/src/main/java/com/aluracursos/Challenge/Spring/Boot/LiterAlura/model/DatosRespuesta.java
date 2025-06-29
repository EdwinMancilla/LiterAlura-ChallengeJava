package com.aluracursos.Challenge.Spring.Boot.LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)

public record DatosRespuesta(int count, List<DatosLibro> results) {
}
