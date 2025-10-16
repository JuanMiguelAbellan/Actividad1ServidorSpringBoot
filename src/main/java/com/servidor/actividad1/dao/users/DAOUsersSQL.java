package com.servidor.actividad1.dao.users;

import com.servidor.actividad1.clases.User;
import com.servidor.actividad1.dao.DBConecctor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOUsersSQL implements DAOUsers{
    private User usuarioActual=new User(" ", "");

    @Override
    public void add(User user) {
        String query="insert into usuarios (nombre, contraseña) values('?', '?')";
        try {
            PreparedStatement statement=DBConecctor.getInstance().prepareStatement(query);
            statement.setString(1, user.getNombre());
            statement.setString(2, user.getPassword());

            statement.executeQuery();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        setUsuarioActual(user);
    }

    @Override
    public User getUser(String nombre) {
        User usuario=null;
        String query="select * from usuario where nombre= ? ";
        try {
            PreparedStatement statement=DBConecctor.getInstance().prepareStatement(query);
            statement.setString(1, nombre);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                usuario=new User( rs.getString("nombre"), rs.getNString("contraseña"));
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return usuario;
    }

    @Override
    public void setUsuarioActual(User user) {
        this.usuarioActual=user;
    }

    @Override
    public User getUsuarioActual(){
        return usuarioActual;
    }

    public boolean validacionUser(String nombre, String contraseña) {
        String query="select * from usuario where nombre= ? && contraseña = ?";
        try {
            PreparedStatement statement=DBConecctor.getInstance().prepareStatement(query);
            statement.setString(1, nombre);
            statement.setString(2, contraseña);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
