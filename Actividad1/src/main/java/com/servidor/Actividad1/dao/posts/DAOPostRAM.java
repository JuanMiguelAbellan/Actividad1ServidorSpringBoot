package com.servidor.Actividad1.dao.posts;

import com.servidor.Actividad1.clases.Post;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DAOPostRAM implements DAOPost {

    private List<Post> listaPost;

    public DAOPostRAM(){
        this.listaPost = new ArrayList<>();
    }

    @Override
    public List<Post> getPosts() {
        return listaPost;
    }

    @Override
    public void add(Post post) {
        listaPost.add(post);
    }

    @Override
    public List<Post> buscarPorNombre(String nombre) {
        Optional<Post> filtrados = listaPost.stream().filter(post -> post.getAutor().equals(nombre)).findAny();
        return filtrados.stream().toList();
    }

    @Override
    public Post buscarPorId(int id) {
        Optional<Post> filtrado = listaPost.stream().filter(post -> post.getId()==id).findFirst();
        return filtrado.orElse(null);
    }

    @Override
    public List<Post>buscarPorTexto(String texto) {
        Optional<Post> filtrados = listaPost.stream().filter(post -> post.getTexto().equals(texto)).findAny();
        return filtrados.stream().toList();
    }

    @Override
    public List<Post> buscarPorFecha(LocalDateTime fecha) {
        Optional<Post> filtrados = listaPost.stream().filter(post -> post.getFecha().equals(fecha)).findAny();
        return filtrados.stream().toList();
    }

    @Override
    public void borrar(Post post) {
        listaPost.remove(post);
    }

    @Override
    public int getLikes(Post post) {
        return post.getLikes();
    }

    @Override
    public int getRepost(Post post) {
        return post.getReposts();
    }

    @Override
    public void darLike(Post post) {
        post.darLike();
    }

    @Override
    public void darRepost(Post post) {
        post.darRepost();
    }
}
