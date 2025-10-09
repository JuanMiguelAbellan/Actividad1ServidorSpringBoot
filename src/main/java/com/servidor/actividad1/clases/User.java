package com.servidor.actividad1.clases;

public class User {
    private static int contadorId = 0;
    private final int id;
    private String nombre;
    private String password;

    public User(String nombre, String password) {
        this.nombre = nombre;
        this.password = password;
        this.id = contadorId++;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getPassword() {
        return this.password;
    }
}
