package com.aluracursos.Challenge.Spring.Boot.LiterAlura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Table;
import org.springframework.stereotype.Component;

@Component
public class ConvierteDatos implements IConvierteDatos {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T obtenerDatos(String json, Class <T> clase){
        try {
            return objectMapper.readValue(json, clase);

        } catch (JsonProcessingException e){
            throw new RuntimeException("Error al convertir los datos JSON a la clase " + clase.getName(), e);
        }


    }


}
