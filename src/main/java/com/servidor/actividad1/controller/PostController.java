package com.servidor.actividad1.controller;

import com.servidor.actividad1.clases.User;
import com.servidor.actividad1.dao.DAOFactory;
import com.servidor.actividad1.clases.Post;

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
        DAOUsersSQL conexionUser=DAOUsersSQL.getInstance();
        List<Post> listaPosts = listaPost();
        User usuarioActual = conexionUser.getUsuarioActual();
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
        DAOUsersSQL conexionUser=DAOUsersSQL.getInstance();
        User userAutor = conexionUser.getUser(autor);

        if(userAutor==null){
            model.addAttribute("error", "No existe ese usuario");
            return "nuevoPost";
        }else {
            conexionPost.add(new Post(userAutor, texto));
            //List<Post> listaPosts = listaPost();
            //model.addAttribute("posts", listaPosts);
            //model.addAttribute("usuario", usuarioActual.getNombre());
            return "redirect:/inicio";
        }
    }

    @PostMapping({"/post/{id}"})
    public String accionBotones(@PathVariable int id, @RequestParam String boton, Model model) {
        DAOPostSQL conexionPost= new DAOPostSQL();
        User usuarioActual = DAOUsersSQL.getInstance().getUsuarioActual();

        if (boton.equals("like")) {
            conexionPost.darLike(id);
        } else if (boton.equals("repost")) {
            conexionPost.darRepost(id);
        }
        //List<Post> listaPosts = listaPost();
        //model.addAttribute("posts", listaPosts);
        //model.addAttribute("usuario", usuarioActual.getNombre());
        return "redirect:/inicio";
    }

    @PostMapping({"/busqueda"})
    public String mostrarBusqueda(@RequestParam String tipoBuscado, @RequestParam String buscado, Model model){
        DAOPostSQL conexionPost= new DAOPostSQL();
        List<Post> listaPosts = listaPost();

        User usuarioActual = DAOUsersSQL.getInstance().getUsuarioActual();
        if(tipoBuscado.equals("usuario")){
            listaPosts= conexionPost.buscarPorNombre(buscado);
        } else if (tipoBuscado.equals("post")) {
            listaPosts= conexionPost.buscarPorTexto(buscado);
        }
        else if (tipoBuscado.equals("fecha")) {
            listaPosts= conexionPost.buscarPorFecha(buscado);
        }
        model.addAttribute("posts", listaPosts);
        model.addAttribute("usuario", usuarioActual.getNombre());
        return "inicio";
    }

    @PostMapping({"/ordenar"})
    public String mostrarFiltrado(@RequestParam String tipoOrden, Model model){
        DAOPostSQL conexionPost= new DAOPostSQL();
        List<Post> listaPosts = listaPost();
        User usuarioActual = DAOUsersSQL.getInstance().getUsuarioActual();

        if(tipoOrden.equals("ascendente")){
            listaPosts= conexionPost.ordenarAscendente(true);
            model.addAttribute("posts", listaPosts);
            model.addAttribute("usuario", usuarioActual.getNombre());
            return "inicio";
        }else if(tipoOrden.equals("descendente")){
            listaPosts= conexionPost.ordenarAscendente(false);
            model.addAttribute("posts", listaPosts);
            model.addAttribute("usuario", usuarioActual.getNombre());
            return "inicio";
        }

        model.addAttribute("posts", listaPosts);
        model.addAttribute("usuario", usuarioActual.getNombre());
        return "inicio";
    }

    public List<Post> listaPost(){
        DAOPostSQL conexionPost= new DAOPostSQL();
        List<Post> listaPosts = conexionPost.getPosts();
        for (int e = 0; e < listaPosts.size(); e++) {
            int likes = conexionPost.getLikes(listaPosts.get(e));
            int repost= conexionPost.getRepost(listaPosts.get(e));
            listaPosts.get(e).setReposts(repost);
            listaPosts.get(e).setLikes(likes);
        }
        return listaPosts;
    }
}
