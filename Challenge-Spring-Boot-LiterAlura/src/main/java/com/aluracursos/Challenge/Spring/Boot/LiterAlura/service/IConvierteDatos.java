package com.aluracursos.Challenge.Spring.Boot.LiterAlura.service;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase); // Método genérico que recibe un String JSON y una clase tipo T, y devuelve un objeto de tipo T
}
