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
        return "index";
    }

    @GetMapping("/registro")
    public String mostrarRegistro() {
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(User user, Model model) {
        DAOFactory.getInstance().getDaoUsers().add(user);
        User usuarioActual = DAOFactory.getInstance().getDaoUsers().getUsuarioActual();
        model.addAttribute("usuario", usuarioActual.getNombre());
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
            if (u.getNombre().equals(user.getNombre())&& u.getPassword().equals(user.getPassword())) {
                DAOFactory.getInstance().getDaoUsers().setUsuarioActual(user);
                User usuarioActual = DAOFactory.getInstance().getDaoUsers().getUsuarioActual();
                model.addAttribute("usuario", usuarioActual.getNombre());
                return "inicio";
            }
        }
        model.addAttribute("error", "Usuario o contraseña incorrectos");
        return "login";
    }
}

