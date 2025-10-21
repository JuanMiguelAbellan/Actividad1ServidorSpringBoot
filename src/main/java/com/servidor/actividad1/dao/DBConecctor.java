package com.servidor.actividad1.dao;

import com.servidor.actividad1.dao.users.DAOUsers;
import com.servidor.actividad1.dao.users.DAOUsersSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConecctor {

    //private static final String URL= "jdbc:mysql://rdsservidor.csf3z3itqpav.us-east-1.rds.amazonaws.com:3306/actividad1";
    private static final String URL = "jdbc:mysql://rdsservidor.csf3z3itqpav.us-east-1.rds.amazonaws.com:3306/actividad1?connectTimeout=5000&socketTimeout=5000&useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "admin";
    private static final String PASSWORD = "Qzmpwxno1029.";

    private DBConecctor(){
    }

    public static Connection getInstance() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
