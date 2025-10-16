package com.servidor.actividad1.dao.posts;

import com.servidor.actividad1.clases.Post;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DAOPostRAM implements DAOPost {
    private List<Post> listaPost = new ArrayList();

    public List<Post> getPosts() {
        return this.listaPost;
    }

    public void add(Post post) {
        this.listaPost.add(post);
    }

    public List<Post> buscarPorNombre(String nombre) {
        Optional<Post> filtrados = this.listaPost.stream().filter((post) -> post.getAutor().getNombre().equals(nombre)).findAny();
        return filtrados.stream().toList();
    }

    public List<Post> buscarPorTexto(String texto) {
        Optional<Post> filtrados = this.listaPost.stream().filter((post) -> post.getTexto().equals(texto)).findAny();
        return filtrados.stream().toList();
    }

    public List<Post> buscarPorFecha(String fecha) {
        Optional<Post> filtrados = this.listaPost.stream().filter((post) -> post.getFecha().equals(fecha)).findAny();
        return filtrados.stream().toList();
    }

    public void borrar(Post post) {
        this.listaPost.remove(post);
    }

    public int getLikes(Post post) {
        return post.getLikes();
    }

    public int getRepost(Post post) {
        return post.getReposts();
    }

    public void darLike(Post post) {
        post.darLike();
    }

    public void darRepost(Post post) {
        post.darRepost();
    }
}
