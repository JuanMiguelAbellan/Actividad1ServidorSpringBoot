//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.server.Actividad1.dao.users;

import com.server.Actividad1.clases.User;
import java.util.List;

public interface DAOUsers {
    void add(User user);

    List<User> getUsers();

    User buscar(String nombre);

    User getUsuarioActual();

    void setUsuarioActual(User user);
}
