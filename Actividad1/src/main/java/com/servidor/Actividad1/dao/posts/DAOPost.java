package com.servidor.Actividad1.dao.posts;

import com.servidor.Actividad1.clases.Post;

import java.time.LocalDateTime;
import java.util.List;

public interface DAOPost {

    public List<Post> getPosts();
    public void add(Post post);
    public List<Post> buscarPorNombre(String nombre);
    public Post buscarPorId(int id);
    public List<Post> buscarPorTexto(String texto);
    public List<Post> buscarPorFecha(LocalDateTime fecha);
    public void borrar(Post post);
    public int getLikes(Post post);
    public int getRepost(Post post);
    public void darLike(Post post);
    public void darRepost(Post post);
}
