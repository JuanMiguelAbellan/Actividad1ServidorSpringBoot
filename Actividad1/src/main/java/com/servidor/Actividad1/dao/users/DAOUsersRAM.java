package com.servidor.Actividad1.dao.users;

import com.servidor.Actividad1.clases.User;

import java.util.ArrayList;
import java.util.List;

public class DAOUsersRAM implements DAOUsers {

    private List<User> listaUsers;

    public DAOUsersRAM(){
        this.listaUsers = new ArrayList<>();
    }

    @Override
    public void add(User user) {
        listaUsers.add(user);
    }

    @Override
    public List<User> getUsers() {
        return listaUsers;
    }

    @Override
    public User buscar(String nombre) {
        User encontrado = null;

        for (User u : listaUsers){
            if(u.getNombre().equals(nombre)){
                encontrado=u;

            }
        }
        return encontrado;
    }
}
