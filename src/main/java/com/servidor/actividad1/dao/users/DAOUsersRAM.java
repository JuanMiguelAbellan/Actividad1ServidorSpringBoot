package com.servidor.actividad1.dao.users;

import com.servidor.actividad1.clases.User;
import java.util.ArrayList;
import java.util.List;

public class DAOUsersRAM implements DAOUsers {
    private List<User> listaUsers = new ArrayList();
    private User usuarioActual=new User(0," ", "");

    @Override
    public void add(User user) {
        this.listaUsers.add(user);
        setUsuarioActual(user);
    }

    @Override
    public User getUser(String nombre) {
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
