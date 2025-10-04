package com.servidor.Actividad1.clases;

import java.time.LocalDateTime;

public class Post {
    private static int contadorId = 0;
    private int id=0;
    private User autor;
    private String texto;
    private LocalDateTime fecha;
    private int likes;
    private int reposts;
    private User referencia;

    public Post(User autor, String texto){
        this.autor=autor;
        this.texto=texto;
        this.fecha=LocalDateTime.now();
        this.likes=0;
        this.reposts=0;
        this.id=contadorId++;
    }

    public int getLikes() {
        return likes;
    }

    public int getReposts() {
        return reposts;
    }

    public String getTexto() {
        return texto;
    }

    public User getAutor() {
        return autor;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public int getId() {
        return id;
    }

    public void darLike() {
        this.likes++;
    }

    public void darRepost() {
        this.reposts++;
    }

    public void setReferencia(User referencia) {
        this.referencia = referencia;
    }
}
