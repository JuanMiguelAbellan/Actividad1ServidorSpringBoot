package com.servidor.Actividad1.controller;

import com.servidor.Actividad1.clases.Post;
import com.servidor.Actividad1.clases.User;
import com.servidor.Actividad1.dao.DAOFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PostController {

    @GetMapping("/post")
    public String listarPosts(Model model) {
        List<Post> listaPosts = DAOFactory.getInstance().getDaoPosts().getPosts();
        model.addAttribute("posts", listaPosts);
        return "post";
    }

    @GetMapping("/nuevoPost")
    public String mostrarFormulario() {
        return "nuevoPost";
    }

    @PostMapping("/nuevoPost")
    public String crearPost(@RequestParam String texto,
                            @RequestParam String autor,
                            Model model) {
        List<Post> listaPosts = DAOFactory.getInstance().getDaoPosts().getPosts();
        User userAutor = DAOFactory.getInstance().getDaoUsers().buscar(autor);

        listaPosts.add(new Post(userAutor, texto));

        model.addAttribute("posts", listaPosts);

        return "post";
    }

    @PostMapping("/post/{id}")
    public String darLike(@PathVariable int id, @RequestParam String like) {
        Post post = DAOFactory.getInstance().getDaoPosts().buscarPorId(id);
        post.darLike();
        return "post";
    }
}
