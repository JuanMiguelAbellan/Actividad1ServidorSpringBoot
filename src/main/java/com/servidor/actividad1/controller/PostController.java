package com.servidor.actividad1.controller;

import com.servidor.actividad1.clases.User;
import com.servidor.actividad1.dao.DAOFactory;
import com.servidor.actividad1.clases.Post;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.servidor.actividad1.dao.posts.DAOPostSQL;
import com.servidor.actividad1.dao.users.DAOUsersSQL;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PostController {
    @GetMapping({"/post"})
    public String listarPosts(Model model) {
        DAOPostSQL conexionPost= new DAOPostSQL();
        List<Post> listaPosts = conexionPost.getPosts();
        User usuarioActual = DAOFactory.getInstance().getDaoUsers().getUsuarioActual();
        model.addAttribute("posts", listaPosts);
        model.addAttribute("usuario", usuarioActual.getNombre());
        return "inicio";
    }

    @GetMapping({"/nuevoPost"})
    public String mostrarFormulario() {
        return "nuevoPost";
    }

    @PostMapping({"/nuevoPost"})
    public String crearPost(@RequestParam String texto, @RequestParam String autor, Model model) {
        DAOPostSQL conexionPost= new DAOPostSQL();
        DAOUsersSQL conexionUser= new DAOUsersSQL();
        User userAutor = conexionUser.getUser(autor);

        User usuarioActual = DAOFactory.getInstance().getDaoUsers().getUsuarioActual();

        if(userAutor==null){
            model.addAttribute("error", "No existe ese usuario");
            return "nuevoPost";
        }else {
            conexionPost.add(new Post(userAutor, texto));
            List<Post> listaPosts = conexionPost.getPosts();
            model.addAttribute("posts", listaPosts);
            model.addAttribute("usuario", usuarioActual.getNombre());
            return "inicio";
        }
    }

    @PostMapping({"/post/{id}"})
    public String accionBotones(@PathVariable int id, @RequestParam String boton, Model model) {
        List<Post> listaPosts = DAOFactory.getInstance().getDaoPosts().getPosts();
        User usuarioActual = DAOFactory.getInstance().getDaoUsers().getUsuarioActual();
        Post nuevoPost = null;

        if (boton.equals("like")) {
            for(Post p : listaPosts) {
                if (p.getId() == id) {
                    p.darLike();
                }
            }
        } else if (boton.equals("repost")) {
            for(Post p : listaPosts) {
                if (p.getId() == id) {
                    p.darRepost();

                    nuevoPost = new Post(usuarioActual, p.getTexto());
                    nuevoPost.setReferencia(p.getAutor());

                }
            }
            listaPosts.add(nuevoPost);
        }

        model.addAttribute("posts", listaPosts);
        model.addAttribute("usuario", usuarioActual.getNombre());
        return "inicio";
    }

    @PostMapping({"/busqueda"})
    public String mostrarBusqueda(@RequestParam String tipoBuscado, @RequestParam String buscado, Model model){
        DAOPostSQL conexionPost= new DAOPostSQL();
        List<Post> listaPost=new ArrayList<>();

        User usuarioActual = DAOFactory.getInstance().getDaoUsers().getUsuarioActual();
        if(tipoBuscado.equals("usuario")){
            listaPost= conexionPost.buscarPorNombre(buscado);
        } else if (tipoBuscado.equals("post")) {
            listaPost= conexionPost.buscarPorTexto(buscado);
        }
        else if (tipoBuscado.equals("fecha")) {
            listaPost= conexionPost.buscarPorFecha(buscado);
        }
        model.addAttribute("posts", listaPost);
        model.addAttribute("usuario", usuarioActual.getNombre());
        return "inicio";
    }

    @PostMapping({"/ordenar"})
    public String mostrarFiltrado(@RequestParam String tipoOrden, Model model){
        DAOPostSQL conexionPost= new DAOPostSQL();
        List<Post> listaPost=new ArrayList<>();
        User usuarioActual = DAOFactory.getInstance().getDaoUsers().getUsuarioActual();

        if(tipoOrden.equals("ascendente")){
            listaPost= conexionPost.ordenarAscendente(true);
            model.addAttribute("posts", listaPost);
            model.addAttribute("usuario", usuarioActual.getNombre());
            return "inicio";
        }else if(tipoOrden.equals("descendente")){
            listaPost= conexionPost.ordenarAscendente(false);
            model.addAttribute("posts", listaPost);
            model.addAttribute("usuario", usuarioActual.getNombre());
            return "inicio";
        }

        model.addAttribute("posts", listaPost);
        model.addAttribute("usuario", usuarioActual.getNombre());
        return "inicio";
    }
}
