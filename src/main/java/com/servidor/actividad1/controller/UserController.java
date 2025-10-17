package com.servidor.actividad1.controller;

import com.servidor.actividad1.clases.User;
import com.servidor.actividad1.dao.DAOFactory;
import com.servidor.actividad1.clases.Post;
import com.servidor.actividad1.dao.DBConecctor;
import com.servidor.actividad1.dao.users.DAOUsersSQL;
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
    public String registrarUsuario(@RequestParam String nombre, @RequestParam String password, Model model) {
        User user= new User(nombre, password);
        DAOUsersSQL conexionUser=new DAOUsersSQL();
        if(!conexionUser.validacionUser(user.getNombre(), user.getPassword())){
            conexionUser.add(user);
            model.addAttribute("usuario", user.getNombre());
            conexionUser.setUsuarioActual(user);

            List<Post> listaPosts = DAOFactory.getInstance().getDaoPosts().getPosts();
            model.addAttribute("posts", listaPosts);

            return "inicio";
        }
        System.out.println("Usuario ya creado");
        return "registro";
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String validarUsuario(@RequestParam String nombre, @RequestParam String password, Model model) {
        User user= new User(nombre, password);
        DAOUsersSQL users = new DAOUsersSQL();
        if (users.validacionUser(user.getNombre(), user.getPassword())){
            model.addAttribute("usuario", user.getNombre());
            users.setUsuarioActual(user);

            List<Post> listaPosts = DAOFactory.getInstance().getDaoPosts().getPosts();
            model.addAttribute("posts", listaPosts);

            return "inicio";
        }
        model.addAttribute("error", "Usuario o contraseña incorrectos");
        return "login";
    }
}

