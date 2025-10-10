package com.servidor.actividad1.dao;

import com.servidor.actividad1.dao.users.DAOUsers;
import com.servidor.actividad1.dao.users.DAOUsersSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConecctor {

    private static Connection conexion;
    private static DAOUsers usuarios;

    private DBConecctor(){
    }

    public static Connection getInstance(){
        if(conexion == null) {
            try {
                conexion = DriverManager.getConnection(
                        "rdsservidor.csf3z3itqpav.us-east-1.rds.amazonaws.com",
                        "admin", "Qzmpwxno1029.");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return conexion;
    }

    public DAOUsers getUsers(){
        if(usuarios == null){
            usuarios = new DAOUsersSQL();
        }
        return usuarios;
    }
}
