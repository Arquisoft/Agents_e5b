/* Formato de la tabla Agent:
  AGENT(id BIGINT PRIMARY KEY,
    identificador VARCHAR(50) UNIQUE NOT NULL,--nombre de usuario y único
    contrasena VARCHAR(30) NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    localizacion VARCHAR(50),
    tipo VARCHAR(50) NOT NULL,) */

--Localización es opcional para personas y entidades (pero obligatorio para sensores)
--Para personas, el nombre es el nombre completo (nombre y apellidos)

INSERT INTO agent VALUES (1, '31668313G' , '1234' , 'Juan Martinez Pérez' , 'juanmp@gmail.com' , NULL , 'Person');
INSERT INTO agent VALUES (2, 'A58818501' , '1234' , 'Entidad1' , 'entidad1@gmail.com' , NULL , 'Entity');
INSERT INTO agent VALUES (3, '525695S' , '1234' , 'Sensor1' , 'raulAdminSensor1@gmail.com' , 
                          '-90.86948943473118,48.25450093195546' , 'Sensor');
