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
            statement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Post> getPosts() {
        List<Post> listaPosts=new ArrayList<>();
        List<User> listaUsuarios= new ArrayList<>();

        String query1= "select * from usuarios";
        try {
            PreparedStatement statement= DBConecctor.getInstance().prepareStatement(query1);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                listaUsuarios.add(new User(rs.getInt("idUsusario"), rs.getString("nombre"), null));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String query3= "select * from posts";
        try {
            PreparedStatement statement= DBConecctor.getInstance().prepareStatement(query3);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                for(User u : listaUsuarios){
                    if(u.getId() == rs.getInt("idPropietario")) {
                        listaPosts.add(new Post(rs.getInt("idPost") ,u, rs.getNString("texto")));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaPosts;
    }



    @Override
    public List<Post> buscarPorNombre(String nombre) {
        User user = new User(0 , nombre, null);

        List<Post> listaPosts=new ArrayList<>();
        String query = "select * from posts where idUsuario= (select idUsuario where nombre = ?)";

        try {
            PreparedStatement statement= DBConecctor.getInstance().prepareStatement(query);
            statement.setString(1, nombre);
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                Post post = new Post(rs.getInt("idPost"),user, rs.getString("texto"));
                listaPosts.add(post);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaPosts;
    }

    @Override
    public List<Post> buscarPorTexto(String texto) {
        List<Post> listaPosts=new ArrayList<>();
        List<User> listaUsuarios= new ArrayList<>();

        String query1= "select * from usuarios";
        try {
            PreparedStatement statement= DBConecctor.getInstance().prepareStatement(query1);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                listaUsuarios.add(new User(rs.getInt("idUsusario"), rs.getString("nombre"), null));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String query3= "select * from posts where texto like '%"+texto+"%'";
        try {
            PreparedStatement statement= DBConecctor.getInstance().prepareStatement(query3);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                for(User u : listaUsuarios){
                    if(u.getId() == rs.getInt("idPropietario")) {
                        listaPosts.add(new Post(rs.getInt("idPost"), u, rs.getNString("texto")));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaPosts;
    }

    @Override
    public List<Post> buscarPorFecha(LocalDate fecha) {
        return List.of();
    }

    @Override
    public void borrar(Post post) {
        String query = "delete from posts where idPost= ?";

        try {
            PreparedStatement statement= DBConecctor.getInstance().prepareStatement(query);
            statement.setInt(1, post.getId());
            ResultSet rs = statement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getLikes(Post post) {
        String query="select idPost from likes where idPost= ?";
        int likes=0;
        try {
            PreparedStatement statement= DBConecctor.getInstance().prepareStatement(query);
            statement.setInt(1, post.getId());
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                likes++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return likes;
    }

    @Override
    public int getRepost(Post post) {
        String query="select idPost from repost where idPost= ?";
        int reposts=0;
        try {
            PreparedStatement statement= DBConecctor.getInstance().prepareStatement(query);
            statement.setInt(1, post.getId());
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                reposts++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reposts;
    }

    @Override
    public void darLike(Post post) {
        DAOUsersSQL conexionUser=new DAOUsersSQL();

        String query = "insert into likes (idUsuario, idPost) values ( "+
                "?" +
                ", ?)";
        try {
            PreparedStatement statement= DBConecctor.getInstance().prepareStatement(query);
            statement.setInt(1, conexionUser.getUsuarioActual().getId());
            statement.setInt(2, post.getId());
            statement.setInt(3, post.getId());
            statement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void darRepost(Post post) {
        DAOUsersSQL conexionUser=new DAOUsersSQL();

        String query = "insert into repost (idUsuarioRepost, idUsuarioReferencia, idPost) values ( ?, "+
                "(select idUsuario from posts where idPost = ?)" +
                ", ?)";
        try {
            PreparedStatement statement= DBConecctor.getInstance().prepareStatement(query);
            statement.setInt(1, conexionUser.getUsuarioActual().getId());
            statement.setInt(2, post.getId());
            statement.setInt(3, post.getId());
            statement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
