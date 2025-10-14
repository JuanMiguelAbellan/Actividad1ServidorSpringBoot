//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.servidor.actividad1.dao.users;

import com.servidor.actividad1.clases.User;
import java.util.List;

public interface DAOUsers {
    void add(User user);

    User getUser(String nombre);


    void setUsuarioActual(User user);

    User getUsuarioActual();
}
