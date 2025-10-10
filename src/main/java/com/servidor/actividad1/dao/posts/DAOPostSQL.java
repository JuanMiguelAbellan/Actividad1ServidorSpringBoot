package com.servidor.actividad1.dao.posts;

import com.servidor.actividad1.clases.Post;
import com.servidor.actividad1.clases.User;
import com.servidor.actividad1.dao.DBConecctor;
import com.servidor.actividad1.dao.users.DAOUsers;
import com.servidor.actividad1.dao.users.DAOUsersSQL;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DAOPostSQL implements DAOPost{
    @Override
    public List<Post> getPosts() {
        List<Post> listaPosts=new ArrayList<>();
        String query= "select * from posts";
        try {
            PreparedStatement statement= DBConecctor.getInstance().prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                String queryUser="select * from usuarios where idUsuarios = " +rs.getString("idUsuario");
                PreparedStatement statement2= DBConecctor.getInstance().prepareStatement(queryUser);
                ResultSet rs2 = statement2.executeQuery();
                while (rs2.next()){
                    listaPosts.add(new Post(new User(rs2.getString("nombre"), rs.getNString("contraseña")), rs.getNString("texto")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaPosts;
    }

    @Override
    public void add(Post post) {
        DAOUsersSQL conexionUser=new DAOUsersSQL();
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

        String query = "insert into posts (idPropietario, texto, fecha) values (" +
                "(select idUsuario from usuarios where nombre = ?)" +
                ", ?, ?)";
        try {
            PreparedStatement statement= DBConecctor.getInstance().prepareStatement(query);
            statement.setString(1, conexionUser.getUsuarioActual().getNombre());
            statement.setString(2, post.getTexto());
            statement.setString(3, DATE_FORMAT.format(post.getFecha()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Post> buscarPorNombre(String nombre) {
        return List.of();
    }

    @Override
    public List<Post> buscarPorTexto(String texto) {
        return List.of();
    }

    @Override
    public List<Post> buscarPorFecha(LocalDate fecha) {
        return List.of();
    }

    @Override
    public void borrar(Post post) {

    }

    @Override
    public int getLikes(Post post) {
        return 0;
    }

    @Override
    public int getRepost(Post post) {
        return 0;
    }

    @Override
    public void darLike(Post post) {

    }

    @Override
    public void darRepost(Post post) {

    }
}
