package com.server.Actividad1.controller;

import com.server.Actividad1.clases.Post;
import com.server.Actividad1.clases.User;
import com.server.Actividad1.dao.DAOFactory;
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
        return "index";
    }

    @GetMapping("/registro")
    public String mostrarRegistro() {
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(User user, Model model) {
        List<User> listaUsuarios= DAOFactory.getInstance().getDaoUsers().getUsers();
        listaUsuarios.add(user);
        DAOFactory.getInstance().getDaoUsers().setUsuarioActual(user);
        //System.out.println(DAOFactory.getInstance().getDaoUsers().getUsuarioActual().getNombre());
        model.addAttribute("usuario", user.getNombre());
        List<Post> listaPosts = DAOFactory.getInstance().getDaoPosts().getPosts();
        model.addAttribute("posts", listaPosts);
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
            if (u.getNombre().equals(user.getNombre()) && u.getPassword().equals(user.getPassword())) {
                model.addAttribute("usuario", user.getNombre());
                List<Post> listaPosts = DAOFactory.getInstance().getDaoPosts().getPosts();
                DAOFactory.getInstance().getDaoUsers().setUsuarioActual(user);
                model.addAttribute("posts", listaPosts);
                return "inicio";
            }
        }
        model.addAttribute("error", "Usuario o contraseña incorrectos");
        return "login";
    }
}

