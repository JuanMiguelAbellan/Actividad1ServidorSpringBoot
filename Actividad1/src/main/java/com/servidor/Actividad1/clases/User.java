package com.servidor.Actividad1.clases;


public class User {
    private static int contadorId = 0;
    private int id =0;
    private String nombre;
    private String password;

    public User(String nombre, String password) {
        this.nombre = nombre;
        this.password = password;
        this.id=contadorId++;
    }

    public String getNombre() { return nombre; }
    public String getPassword() { return password; }
}
