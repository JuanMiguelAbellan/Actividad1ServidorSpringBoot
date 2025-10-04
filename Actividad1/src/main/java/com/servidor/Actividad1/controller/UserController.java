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
public class UserController {

    @GetMapping("/")
    public String index() {
        List<User> listaUsuarios = DAOFactory.getInstance().getDaoUsers().getUsers();
        return "index";
    }

    @GetMapping("/registro")
    public String mostrarRegistro() {
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(User user) {
        List<User> listaUsuarios= DAOFactory.getInstance().getDaoUsers().getUsers();
        listaUsuarios.add(user);
        return "inicio";
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String validarUsuario(User user, Model model) {
        List<User> listaUsuarios = DAOFactory.getInstance().getDaoUsers().getUsers();
        for (User u : listaUsuarios) {
            if (u.equals(user)) {
                model.addAttribute("usuario", user.getNombre());
                return "inicio";
            }
        }
        model.addAttribute("error", "Usuario o contraseña incorrectos");
        return "login";
    }

    //
    @PostMapping("/post/{id}/reposts")
    public String darRepost(@PathVariable int id, @RequestParam String autor) {
        Post post = DAOFactory.getInstance().getDaoPosts().buscarPorId(id);
        post.darRepost();
        User userAutor = DAOFactory.getInstance().getDaoUsers().buscar(autor);
        post.setReferencia(userAutor);
        return "post";
    }
}

