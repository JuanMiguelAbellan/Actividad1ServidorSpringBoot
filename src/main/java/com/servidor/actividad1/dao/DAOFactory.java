//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.servidor.actividad1.dao;

import com.servidor.actividad1.dao.posts.DAOPost;
import com.servidor.actividad1.dao.posts.DAOPostRAM;
import com.servidor.actividad1.dao.users.DAOUsers;
import com.servidor.actividad1.dao.users.DAOUsersRAM;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOUsers daoUsers;
    private DAOPost daoPost;

    private DAOFactory() {
    }

    public static DAOFactory getInstance() {
        if (daoFactory == null) {
            daoFactory = new DAOFactory();
        }

        return daoFactory;
    }

    public DAOUsers getDaoUsers() {
        if (this.daoUsers == null) {
            this.daoUsers = new DAOUsersRAM();
        }

        return this.daoUsers;
    }

    public DAOPost getDaoPosts() {
        if (this.daoPost == null) {
            this.daoPost = new DAOPostRAM();
        }

        return this.daoPost;
    }
}
