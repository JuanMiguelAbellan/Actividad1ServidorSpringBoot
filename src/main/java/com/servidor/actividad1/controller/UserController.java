package com.servidor.actividad1.controller;

import com.servidor.actividad1.clases.User;
import com.servidor.actividad1.dao.DAOFactory;
import com.servidor.actividad1.clases.Post;
import com.servidor.actividad1.dao.DBConecctor;
import com.servidor.actividad1.dao.posts.DAOPostSQL;
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
        User user= new User(0, nombre, password);
        DAOPostSQL conexionPost= new DAOPostSQL();
        DAOUsersSQL conexionUser=DAOUsersSQL.getInstance();
        List<Post> listaPosts = listaPost();
        if(!conexionUser.validacionUser(user.getNombre(), user.getPassword())){
            conexionUser.add(user);
            model.addAttribute("usuario", user.getNombre());
            conexionUser.setUsuarioActual(conexionUser.getUser(nombre));

            model.addAttribute("posts", listaPosts);
            model.addAttribute("usuario", conexionUser.getUsuarioActual().getNombre());

            return "inicio";
        }
        return "registro";
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String validarUsuario(@RequestParam String nombre, @RequestParam String password, Model model) {
        DAOPostSQL conexionPost= new DAOPostSQL();
        DAOUsersSQL conexionUser=DAOUsersSQL.getInstance();
        List<Post> listaPosts = listaPost();
        if (conexionUser.validacionUser(nombre, password)){
            model.addAttribute("usuario", nombre);
            conexionUser.setUsuarioActual(conexionUser.getUser(nombre));

            model.addAttribute("posts", listaPosts);
            model.addAttribute("usuario", conexionUser.getUsuarioActual().getNombre()+conexionUser.getUsuarioActual().getId());

            return "inicio";
        }
        model.addAttribute("error", "Usuario o contraseña incorrectos");
        return "login";
    }
    public List<Post> listaPost(){
        DAOPostSQL conexionPost= new DAOPostSQL();
        List<Post> listaPosts = conexionPost.getPosts();
        for (int e = 0; e < listaPosts.size(); e++) {
            int likes = conexionPost.getLikes(listaPosts.get(e));
            int repost= conexionPost.getRepost(listaPosts.get(e));
            listaPosts.get(e).setLikes(repost);
            listaPosts.get(e).setLikes(likes);
        }
        return listaPosts;
    }
}

