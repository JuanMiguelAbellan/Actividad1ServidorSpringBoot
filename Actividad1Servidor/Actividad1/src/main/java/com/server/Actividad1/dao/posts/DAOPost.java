//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.server.Actividad1.dao.posts;

import com.server.Actividad1.clases.Post;
import java.time.LocalDateTime;
import java.util.List;

public interface DAOPost {
    List<Post> getPosts();

    void add(Post post);

    List<Post> buscarPorNombre(String nombre);

    Post buscarPorId(int id);

    List<Post> buscarPorTexto(String texto);

    List<Post> buscarPorFecha(LocalDateTime fecha);

    void borrar(Post post);

    int getLikes(Post post);

    int getRepost(Post post);

    void darLike(Post post);

    void darRepost(Post post);
}
