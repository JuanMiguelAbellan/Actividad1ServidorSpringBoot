//

package com.servidor.actividad1.dao.posts;

import com.servidor.actividad1.clases.Post;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface DAOPost {
    List<Post> getPosts();

    void add(Post post);

    List<Post> buscarPorNombre(String nombre);

    List<Post> buscarPorTexto(String texto);

    List<Post> buscarPorFecha(String fecha);

    void borrar(Post post);

    int getLikes(Post post);

    int getRepost(Post post);

    void darLike(int idPost);

    void darRepost(int idPost);
}
