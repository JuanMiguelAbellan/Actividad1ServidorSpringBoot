package com.server.Actividad1.dao.users;

import com.server.Actividad1.clases.User;
import java.util.ArrayList;
import java.util.List;

public class DAOUsersRAM implements DAOUsers {
    private List<User> listaUsers = new ArrayList();
    private User usuarioActual=new User(" ", "");

    public void add(User user) {
        this.listaUsers.add(user);
        setUsuarioActual(user);
    }

    public List<User> getUsers() {
        return this.listaUsers;
    }

    public User buscar(String nombre) {
        User encontrado = null;

        for(User u : this.listaUsers) {
            if (u.getNombre().equals(nombre)) {
                encontrado = u;
            }
        }

        return encontrado;
    }

    public User getUsuarioActual() {
        return this.usuarioActual;
    }

    public void setUsuarioActual(User usuarioActual) {
        this.usuarioActual = usuarioActual;
    }
}
