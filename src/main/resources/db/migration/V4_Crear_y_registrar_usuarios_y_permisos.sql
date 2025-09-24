CREATE TABLE usuario (
    codigo BIGINT(20) PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    correo VARCHAR(50) NOT NULL,
    clave VARCHAR(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE permiso (
    codigo BIGINT(20) PRIMARY KEY,
    descripcion VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE usuario_permiso (
    codigo_usuario BIGINT(20) NOT NULL,
    codigo_permiso BIGINT(20) NOT NULL,
    PRIMARY KEY (codigo_usuario, codigo_permiso),
    FOREIGN KEY (codigo_usuario) REFERENCES usuario(codigo),
    FOREIGN KEY (codigo_permiso) REFERENCES permiso(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Usuarios
INSERT INTO usuario (codigo, nombre, correo, clave) 
VALUES (1, 'Administrador', 'Cherembo123', '$2a$10$X607ZPhQ4EgGNaYKt3n4SONjIv9zc.VMWdEuhCuba7oLAL5IvcL5.');

INSERT INTO usuario (codigo, nombre, correo, clave) 
VALUES (2, 'Maria Silva', 'Cherembo2002', '$2a$10$Zc3w6HyuPOPXamaMhh.PQOXvDnEsadztbfi6/RyZWJDzimE8WQjaq');

-- Permisos
INSERT INTO permiso (codigo, descripcion) VALUES (1, 'ROLE_REGISTRAR_CATEGORIA');
INSERT INTO permiso (codigo, descripcion) VALUES (2, 'ROLE_CONSULTAR_CATEGORIA');

INSERT INTO permiso (codigo, descripcion) VALUES (3, 'ROLE_REGISTRAR_PERSONA');
INSERT INTO permiso (codigo, descripcion) VALUES (4, 'ROLE_ELIMINAR_PERSONA');
INSERT INTO permiso (codigo, descripcion) VALUES (5, 'ROLE_CONSULTAR_PERSONA');

INSERT INTO permiso (codigo, descripcion) VALUES (6, 'ROLE_REGISTRAR_LANZAMIENTO');
INSERT INTO permiso (codigo, descripcion) VALUES (7, 'ROLE_ELIMINAR_LANZAMIENTO');
INSERT INTO permiso (codigo, descripcion) VALUES (8, 'ROLE_CONSULTAR_LANZAMIENTO');

-- Relación admin con todos los permisos
INSERT INTO usuario_permiso (codigo_usuario, codigo_permiso) VALUES (1, 1);
INSERT INTO usuario_permiso (codigo_usuario, codigo_permiso) VALUES (1, 2);
INSERT INTO usuario_permiso (codigo_usuario, codigo_permiso) VALUES (1, 3);
INSERT INTO usuario_permiso (codigo_usuario, codigo_permiso) VALUES (1, 4);
INSERT INTO usuario_permiso (codigo_usuario, codigo_permiso) VALUES (1, 5);
INSERT INTO usuario_permiso (codigo_usuario, codigo_permiso) VALUES (1, 6);
INSERT INTO usuario_permiso (codigo_usuario, codigo_permiso) VALUES (1, 7);
INSERT INTO usuario_permiso (codigo_usuario, codigo_permiso) VALUES (1, 8);

-- Relación Maria con permisos limitados
INSERT INTO usuario_permiso (codigo_usuario, codigo_permiso) VALUES (2, 2);
INSERT INTO usuario_permiso (codigo_usuario, codigo_permiso) VALUES (2, 5);
INSERT INTO usuario_permiso (codigo_usuario, codigo_permiso) VALUES (2, 8);
