drop database if exists `grupo2-bdd-oo2-2021`;
create database if not exists `grupo2-bdd-oo2-2021`;
use `grupo2-bdd-oo2-2021`;

-- PARTE UNO

CREATE TABLE `perfil`(
  `id_perfil` int NOT NULL AUTO_INCREMENT,
  `createdat` datetime(6) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `nombre` varchar(50) NOT NULL,
  `updatedat` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id_perfil`),
  UNIQUE KEY `UK_3b0dloqo94v7r6tjahpid9hc3` (`nombre`),
  UNIQUE KEY `UK_2h0kpmwd7jmkuhc4w71rgflnk` (`descripcion`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `usuario` (
  `id_usuario` int NOT NULL AUTO_INCREMENT,
  `apellido` varchar(255) NOT NULL,
  `clave` varchar(60) NOT NULL,
  `createdat` datetime(6) DEFAULT NULL,
  `documento` bigint NOT NULL,
  `email` varchar(255) NOT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `nombre` varchar(255) NOT NULL,
  `nombre_usuario` varchar(45) NOT NULL,
  `tipo_doc` varchar(5) NOT NULL,
  `updatedat` datetime(6) DEFAULT NULL,
  `id_perfil` int NOT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `UK_sqdsrgo7yd5nlfxh382v44rj9` (`documento`),
  UNIQUE KEY `UK_puhr3k3l7bj71hb7hk7ktpxn0` (`nombre_usuario`),
  KEY `FK131gkl0dt1966rsw6dmesnsxw` (`id_perfil`),
  CONSTRAINT `FK131gkl0dt1966rsw6dmesnsxw` FOREIGN KEY (`id_perfil`) REFERENCES `perfil` (`id_perfil`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

insert into perfil values(1,now(),'Permisos para ver, crear, actualizar y eliminar perfiles y usuarios','ADMINISTRADOR',now());
insert into perfil values(2,now(),'Permisos para ver los usuarios y perfiles pudiendo descargar la informacion en formato pdf','AUDITOR',now());
insert into usuario values(1,'Garcia Misa','$2a$10$StaNanAZ120Lkw2iipBlOuXcdNeEH2tV.GAWpUcSpk6p4REiMCpAC',now(),43182591,'cami@gmail.com',true,'Camila','camimisa','DNI',now(),1);
insert into usuario values(2,'Godirio','$2a$10$StaNanAZ120Lkw2iipBlOuXcdNeEH2tV.GAWpUcSpk6p4REiMCpAC',now(),43183331,'seba@gmail.com',true,'Sebastian','seba','DNI',now(),2);
insert into usuario values(3,'Gomez','$2a$10$StaNanAZ120Lkw2iipBlOuXcdNeEH2tV.GAWpUcSpk6p4REiMCpAC',now(),43184444,'nico@gmail.com',true,'Nico','nico','DNI',now(),1);
insert into usuario values(4,'Administrador','$2a$10$UV0qQrH59sxV0oq5Hnw5PeeNQ7C5OH/TLY2d.OK7Znie8e2DRwNpK',now(),1111111,'admin@gmail.com',true,'Admin','admin','DNI',now(),1);
insert into usuario values(6,'Auditor','$2a$10$05SxzR2MJn4I4I9siN0eJ.4Bb/G.fAYT9McOHYi16kNxEwXhpqMJq',now(),22222222,'auditor@gmail.com',true,'Audi','auditor','DNI',now(),2);

-- PARTE DOS 
CREATE TABLE `rodado` (
  `idRodado` int NOT NULL AUTO_INCREMENT,
  `dominio` varchar(10) NOT NULL,
  `vehiculo` varchar(30) NOT NULL,
  PRIMARY KEY (`idRodado`)
);

CREATE TABLE `persona` (
  `idPersona` int NOT NULL AUTO_INCREMENT,
  `dni` int NOT NULL,
  `apellido` varchar(30) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  PRIMARY KEY (`idPersona`)
);

CREATE TABLE `permiso` (
  `idPermiso` int NOT NULL AUTO_INCREMENT,
  `fecha` date NOT NULL,
  `idPersona` int NOT NULL,
  PRIMARY KEY (`idPermiso`),
  KEY `fk_Permiso_1_idx` (`idPersona`),
  CONSTRAINT `fk_Permiso_1` FOREIGN KEY (`idPersona`) REFERENCES `persona` (`idPersona`)
);

CREATE TABLE `permisodiario` (
  `idPermisoDiario` int NOT NULL,
  `motivo` varchar(45) NOT NULL,
  PRIMARY KEY (`idPermisoDiario`),
  CONSTRAINT `fk_PermisoDiario_1` FOREIGN KEY (`idPermisoDiario`) REFERENCES `permiso` (`idPermiso`)
);

CREATE TABLE `permisoperiodo` (
  `idPermisoPeriodo` int NOT NULL,
  `cantDias` int NOT NULL,
  `vacaciones` tinyint NOT NULL,
  `idRodado` int NOT NULL,
  PRIMARY KEY (`idPermisoPeriodo`),
  KEY `fk_PermisoPeriodo_1_idx` (`idRodado`),
  CONSTRAINT `fk_PermisoPeriodo_1` FOREIGN KEY (`idRodado`) REFERENCES `rodado` (`idRodado`),
  CONSTRAINT `fk_PermisoPeriodo_2` FOREIGN KEY (`idPermisoPeriodo`) REFERENCES `permiso` (`idPermiso`)
);

CREATE TABLE `lugar` (
  `idLugar` int NOT NULL AUTO_INCREMENT,
  `lugar` varchar(35) NOT NULL,
  `codPostal` varchar(10) NOT NULL,
  PRIMARY KEY (`idLugar`)
);

CREATE TABLE `permisoxlugar` (
  `idPermiso` int NOT NULL,
  `idLugar` int NOT NULL,
  PRIMARY KEY (`idPermiso`,`idLugar`),
  KEY `fk_PermisoXLugar_2_idx` (`idLugar`),
  CONSTRAINT `fk_PermisoXLugar_1` FOREIGN KEY (`idPermiso`) REFERENCES `permiso` (`idPermiso`),
  CONSTRAINT `fk_PermisoXLugar_2` FOREIGN KEY (`idLugar`) REFERENCES `lugar` (`idLugar`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
