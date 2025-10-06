package com.server.Actividad1.clases;

import com.server.Actividad1.clases.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Post {
    private static int contadorId = 0;
    private final int id;
    private final User autor;
    private String texto;
    private LocalDate fecha;
    private int likes;
    private int reposts;
    private User referencia;

    public Post(User autor, String texto) {
        this.autor = autor;
        this.texto = texto;
        this.fecha = LocalDate.now();
        this.likes = 0;
        this.reposts = 0;
        this.id = contadorId++;
        this.referencia = autor;
    }

    public int getLikes() {
        return this.likes;
    }

    public int getReposts() {
        return this.reposts;
    }

    public String getTexto() {
        return this.texto;
    }

    public User getAutor() {
        return this.autor;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public int getId() {
        return this.id;
    }

    public void darLike() {
        ++this.likes;
    }

    public void darRepost() {
        ++this.reposts;
    }

    public void setReferencia(User referencia) {
        this.referencia = referencia;
    }

    public User getReferencia() {
        return this.referencia;
    }
}
