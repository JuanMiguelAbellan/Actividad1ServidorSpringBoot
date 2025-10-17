package com.servidor.actividad1.dao;

import com.servidor.actividad1.dao.users.DAOUsers;
import com.servidor.actividad1.dao.users.DAOUsersSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConecctor {

    private static final String URL= "jdbc:mysql://rdsservidor.csf3z3itqpav.us-east-1.rds.amazonaws.com:3306/actividad1";
    private static final String USER = "admin";
    private static final String PASSWORD = "Qzmpwxno1029.";

    private DBConecctor(){
    }

    public static Connection getInstance() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Error al cargar el driver JDBC", e);
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
