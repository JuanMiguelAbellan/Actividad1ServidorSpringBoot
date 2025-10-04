package com.servidor.Actividad1.dao.users;

import com.servidor.Actividad1.clases.User;

import java.util.List;

public interface DAOUsers {
    public void add(User user);
    public List<User> getUsers();
    public User buscar(String nombre);
    public User getUsuarioActual();
    public void setUsuarioActual(User user);
}
