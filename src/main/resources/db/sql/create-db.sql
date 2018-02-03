DROP TABLE citizen IF EXISTS;
DROP TABLE agent IF EXISTS;

CREATE TABLE agent (
  id BIGINT PRIMARY KEY,
  identificador VARCHAR(50) UNIQUE NOT NULL,--nombre de usuario y único
  contrasena VARCHAR(30) NOT NULL,
  nombre VARCHAR(50) NOT NULL,
  email VARCHAR(50) NOT NULL,
  localizacion VARCHAR(50),
  tipo VARCHAR(50) NOT NULL,
  tipoCodigo INT NOT NULL
);

--DROP TABLE citizen IF EXISTS;

--CREATE TABLE citizen (
--  id      BIGINT PRIMARY KEY,
--  contrasena VARCHAR(30),
--  nombreUsuario  VARCHAR(50),
--  dni VARCHAR(50),
--  nombre VARCHAR(50),
--  apellidos VARCHAR(50),
--  fechaNacimiento DATE,
--  email VARCHAR(50),
--  direccionPostal VARCHAR(50),
--  nacionalidad VARCHAR(50)
--);
