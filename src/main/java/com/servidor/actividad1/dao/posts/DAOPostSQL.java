package com.servidor.actividad1.dao.posts;

import com.servidor.actividad1.clases.Post;
import com.servidor.actividad1.clases.User;
import com.servidor.actividad1.dao.DBConecctor;
import com.servidor.actividad1.dao.users.DAOUsers;
import com.servidor.actividad1.dao.users.DAOUsersSQL;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DAOPostSQL implements DAOPost{

    @Override
    public void add(Post post) {
        DAOUsersSQL conexionUser=DAOUsersSQL.getInstance();
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
        String query = "INSERT INTO posts (idPropietario, texto, fecha) VALUES (?, ?, ?)";
        try (Connection conn = DBConecctor.getInstance()){
            PreparedStatement statement=conn.prepareStatement(query);
            statement.setInt(1, conexionUser.getUsuarioActual().getId());
            statement.setString(2, post.getTexto());
            statement.setString(3, DATE_FORMAT.format(post.getFecha()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Post> getPosts() {
        List<Post> listaPosts=new ArrayList<>();

        String query= "select * from posts";
        try (Connection conn = DBConecctor.getInstance()){
            PreparedStatement statement=conn.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                listaPosts.add(new Post(rs.getInt("idPost"), rs.getInt("idPropietario"), rs.getString("texto")));
                for(Post p : listaPosts){
                    if(getRepost(p)>0){
                        p.setReferencia(getReferencia(p.getId()));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaPosts;
    }

    public User getReferencia(int idPost){
        User referencia=null;
        String query = "select * from usuarios where idUsuario =" +
                "(select idUsuarioReferencia from repost where idPost = ? limit 1)";
        try (Connection conn = DBConecctor.getInstance()){
            PreparedStatement statement=conn.prepareStatement(query);
            statement.setInt(1, idPost);
            ResultSet rs = statement.executeQuery();
            if (rs.next()){
                referencia= new User(rs.getInt("idUsuario"), rs.getString("nombre"), null);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return referencia;
    }

    @Override
    public List<Post> buscarPorNombre(String nombre) {
        List<Post> listaPosts=new ArrayList<>();
        String query = "select * from posts where idPropietario = (select idUsuario from usuarios where nombre = ?);";

        try (Connection conn = DBConecctor.getInstance()){
            PreparedStatement statement=conn.prepareStatement(query);
            statement.setString(1, nombre);
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                Post post = new Post(rs.getInt("idPost"), new User(rs.getInt("idPropietario"), null, null), rs.getString("texto"));
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

        String query = "select p.idPost, p.texto, p.idPropietario, u.idUsuario, u.nombre " +
                "from posts p " +
                "join usuarios u ON p.idPropietario = u.idUsuario " +
                "where p.texto like ?";
        try (Connection conn = DBConecctor.getInstance()) {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, "%" + texto + "%");

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                User u = new User(rs.getInt("idUsuario"), rs.getString("nombre"), null);

                Post p = new Post(rs.getInt("idPost"), u, rs.getString("texto"));

                listaPosts.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaPosts;
    }

    @Override
    public List<Post> buscarPorFecha(String fecha) {
        List<Post> listaPosts=new ArrayList<>();
        String query = "select p.idPost, p.texto, p.idPropietario, p.fecha,u.idUsuario, u.nombre " +
                "from posts p " +
                "join usuarios u on p.idPropietario = u.idUsuario " +
                "where p.fecha < ?";
        try (Connection conn = DBConecctor.getInstance()) {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, fecha);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                User u = new User(rs.getInt("idUsuario"), rs.getString("nombre"), null);

                Post p = new Post(rs.getInt("idPost"), u, rs.getString("texto"));

                listaPosts.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaPosts;

    }

    @Override
    public void borrar(Post post) {
        String query = "delete from posts where idPost= ?";

        try (Connection conn = DBConecctor.getInstance()){
            PreparedStatement statement=conn.prepareStatement(query);
            statement.setInt(1, post.getId());
            ResultSet rs = statement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getLikes(Post post) {
        String query="select count(idPost) as totalLikes from likes where idPost= ?";
        int likes=0;
        try (Connection conn = DBConecctor.getInstance()){
            PreparedStatement statement=conn.prepareStatement(query);
            statement.setInt(1, post.getId());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                likes = rs.getInt("totalLikes");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return likes;
    }

    @Override
    public int getRepost(Post post) {
        String query="select count(idPost) as totalRepost from repost where idPost= ?";
        int reposts=0;
        try (Connection conn = DBConecctor.getInstance()){
            PreparedStatement statement=conn.prepareStatement(query);
            statement.setInt(1, post.getId());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                reposts= rs.getInt("totalRepost");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reposts;
    }

    @Override
    public void darLike(int idPost) {

        String query = "insert into likes (idUsuario, idPost) values ( "+
                "?" +
                ", ?)";
        try (Connection conn = DBConecctor.getInstance()){
            PreparedStatement statement=conn.prepareStatement(query);
            statement.setInt(1, DAOUsersSQL.getInstance().getUsuarioActual().getId());
            statement.setInt(2, idPost);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void darRepost(int idPost) {

        String query = "insert into repost (idUsuarioRepost, idUsuarioReferencia, idPost) values ( ?, "+
                "(select idPropietario from posts where idPost = ?)" +
                ", ?)";
        try (Connection conn = DBConecctor.getInstance()){
            PreparedStatement statement=conn.prepareStatement(query);
            statement.setInt(1, DAOUsersSQL.getInstance().getUsuarioActual().getId());
            statement.setInt(2, idPost);
            statement.setInt(3, idPost);

            Post nuevoPost = buscarPorId(idPost);
            nuevoPost.setReferencia(getAutor(idPost));
            add(nuevoPost);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getAutor(int idPost) {
        User autor = null;
        String query = "select * from usuarios where idUsuario = " +
                "(select idPropietario from posts where idPost = ?)";
        try (Connection conn = DBConecctor.getInstance()) {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, idPost);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                autor= new User(rs.getInt("idUsuario"), rs.getString("nombre"), null);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return autor;
    }

        public Post buscarPorId(int idPost){
        Post encontrado= null;
        String query = "select * from posts where idPost = ?";
        try (Connection conn = DBConecctor.getInstance()){
            PreparedStatement statement=conn.prepareStatement(query);
            statement.setInt(1, idPost);
            ResultSet rs = statement.executeQuery();
            if (rs.next()){
                encontrado= new Post(idPost, rs.getInt("idPropietario"), rs.getString("texto"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return encontrado;
    }

        public List<Post> ordenarAscendente(boolean ascendente){
        List<Post> listaPosts=new ArrayList<>();

        String query;
        if (ascendente){
            query = "select p.idPost, p.texto, p.fecha, u.idUsuario, u.nombre " +
                    "from posts p " +
                    "join usuarios u on p.idPropietario = u.idUsuario " +
                    "order by p.fecha ASC";
        }else {
            query= "select p.idPost, p.texto, p.fecha, u.idUsuario, u.nombre " +
                    "from posts p " +
                    "join usuarios u on p.idPropietario = u.idUsuario " +
                    "order by p.fecha DESC";;
        }
        try (Connection conn = DBConecctor.getInstance()) {
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                User u = new User(rs.getInt("idUsuario"), rs.getString("nombre"), null);

                Post p = new Post(rs.getInt("idPost"), u, rs.getString("texto"));

                listaPosts.add(p);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener los posts ordenados", e);
        }

        return listaPosts;
    }
}
