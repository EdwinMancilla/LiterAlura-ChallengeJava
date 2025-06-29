package com.aluracursos.Challenge.Spring.Boot.LiterAlura.dto;

import com.aluracursos.Challenge.Spring.Boot.LiterAlura.model.DatosAutor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AutorDTO {
    @JsonProperty("name")
    private String nombre;

    @JsonProperty("birth_year")
    private Integer anoNacimiento;

    @JsonProperty("death_year")
    private Integer anoFallecimiento;

    public AutorDTO() {
    }

    //getters y setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAnoNacimiento() {
        return anoNacimiento;
    }

    public void setAnoNacimiento(Integer anoNacimiento) {
        this.anoNacimiento = anoNacimiento;
    }

    public Integer getAnoFallecimiento() {
        return anoFallecimiento;
    }

    public void setAnoFallecimiento(Integer anoFallecimiento) {
        this.anoFallecimiento = anoFallecimiento;
    }

    public void DatosAutor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.anoNacimiento = datosAutor.anoNacimiento();
        this.anoFallecimiento = datosAutor.anoFallecimiento();
    }
}
