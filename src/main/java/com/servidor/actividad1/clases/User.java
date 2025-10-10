package com.servidor.actividad1.clases;

public class User {
    private String nombre;
    private String password;

    public User(String nombre, String password) {
        this.nombre = nombre;
        this.password = password;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getPassword() {
        return this.password;
    }
}
