package com.servidor.actividad1.clases;

public class User {
    private String nombre;
    private String password;
    private final int id;

    public User(int id, String nombre, String password) {
        this.id = id;
        this.nombre = nombre;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getPassword() {
        return this.password;
    }
}
