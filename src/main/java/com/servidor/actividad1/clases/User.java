package com.servidor.actividad1.clases;

public class User {
    private static int contador=0;
    private String nombre;
    private String password;
    private final int id;

    public User(String nombre, String password) {
        this.id = contador++;
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
