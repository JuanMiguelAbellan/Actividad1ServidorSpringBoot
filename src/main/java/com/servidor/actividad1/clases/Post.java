package com.servidor.actividad1.clases;

import com.servidor.actividad1.dao.DBConecctor;
import com.servidor.actividad1.dao.users.DAOUsersSQL;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

public class Post {
    private static int contador=0;
    private final int id;
    private final User autor;
    private String texto;
    private Date fecha;
    private int likes;
    private int reposts;
    private User referencia;

    public Post( User autor, String texto) {
        this.id=contador++;
        this.autor = autor;
        this.texto = texto;
        this.fecha = Date.from(Instant.now());
        this.likes = 0;
        this.reposts = 0;
        this.referencia = autor;
    }

    public Post(int id, User autor, String texto) {
        this.id=id;
        this.autor = autor;
        this.texto = texto;
        this.fecha = Date.from(Instant.now());
        this.likes = 0;
        this.reposts = 0;
        this.referencia = autor;
    }

    public Post(int idPost, int idPropietario, String texto) {
        this.id=idPost;
        this.autor = new User(DAOUsersSQL.getInstance().getUser(idPropietario));
        this.texto = texto;
        this.fecha = Date.from(Instant.now());
        this.likes = 0;
        this.reposts = 0;
        this.referencia = autor;
    }

    public int getId() {
        return id;
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

    public Date getFecha() {
        return this.fecha;
    }

    public void darLike() {
        ++this.likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setReposts(int reposts) {
        this.reposts = reposts;
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

    public void setFecha(Date date) {
        this.fecha= date;
    }
}
