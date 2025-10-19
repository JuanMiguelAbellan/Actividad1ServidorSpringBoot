DROP DATABASE IF EXISTS actividad1;
create database actividad1;
use actividad1;

create table usuarios(
idUsuario int auto_increment primary key,
nombre varchar(100),
    contraseña varchar(100)
);

create table posts(
idPost int auto_increment primary key,
    idPropietario int,
    texto varchar(250),
    fecha date
);

create table likes(
idUsuario int,
    idPost int,
    primary key (idUsuario, idPost),
    foreign key (idUsuario) references usuarios(idUsuario),
    foreign key (idPost) references posts(idPost)
);

create table repost(
idUsuarioRepost int,
idUsuarioReferencia int,
    idPost int,
    primary key (idUsuarioRepost, idPost),
    foreign key (idUsuarioRepost) references usuarios(idUsuario),
    foreign key (idUsuarioReferencia) references usuarios(idUsuario),
    foreign key (idPost) references posts(idPost)
);