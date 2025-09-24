-- Crear tabla personas
CREATE TABLE IF NOT EXISTS personas (
    codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    lugarpublico VARCHAR(30),
    numero VARCHAR(30),
    complemento VARCHAR(30),
    barrio VARCHAR(30),
    cep VARCHAR(30),
    ciudad VARCHAR(30),
    estado VARCHAR(30),
    activo BOOLEAN
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Insertar registro inicial en personas
INSERT INTO personas (nombre,lugarpublico,numero,complemento,barrio,cep,ciudad,estado,activo)
SELECT 'Matias gimenez','franco','10',NULL,'fatima','567234','franco','virgen',TRUE
WHERE NOT EXISTS (
    SELECT 1 FROM personas WHERE nombre='Matias gimenez' AND numero='10'
);

-- Crear tabla categoria
CREATE TABLE IF NOT EXISTS categoria (
    codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Insertar categorías iniciales
INSERT INTO categoria (nombre)
SELECT 'lazer' WHERE NOT EXISTS (SELECT 1 FROM categoria WHERE nombre='lazer');

INSERT INTO categoria (nombre)
SELECT 'alimentacion' WHERE NOT EXISTS (SELECT 1 FROM categoria WHERE nombre='alimentacion');

INSERT INTO categoria (nombre)
SELECT 'supermercado' WHERE NOT EXISTS (SELECT 1 FROM categoria WHERE nombre='supermercado');

INSERT INTO categoria (nombre)
SELECT 'Farmacia' WHERE NOT EXISTS (SELECT 1 FROM categoria WHERE nombre='Farmacia');

INSERT INTO categoria (nombre)
SELECT 'otros' WHERE NOT EXISTS (SELECT 1 FROM categoria WHERE nombre='otros');

-- Crear tabla lanzamiento
CREATE TABLE IF NOT EXISTS lanzamiento (
    codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    descripcion VARCHAR(50) NOT NULL,
    fecha_vencimiento DATE NOT NULL,
    fecha_pago DATE,
    valor DECIMAL(10,2) NOT NULL,
    observacion VARCHAR(100),
    tipo VARCHAR(20) NOT NULL,
    codigo_categoria BIGINT(20) NOT NULL,
    codigo_personas BIGINT(20) NOT NULL,
    FOREIGN KEY (codigo_categoria) REFERENCES categoria(codigo),
    FOREIGN KEY (codigo_personas) REFERENCES personas(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Insertar registro inicial en lanzamiento
INSERT INTO lanzamiento (descripcion, fecha_vencimiento, fecha_pago, valor, observacion, tipo, codigo_categoria, codigo_personas)
SELECT 'Alquiler de oficina', '2025-09-15', '2025-09-15', 1200.00, 'Pago mensual de la oficina', 'RECETA', 1, 3
WHERE EXISTS (SELECT 1 FROM categoria WHERE codigo = 1)
  AND EXISTS (SELECT 1 FROM personas WHERE codigo = 3)
  AND NOT EXISTS (
      SELECT 1 FROM lanzamiento WHERE descripcion='Alquiler de oficina' AND codigo_categoria=1 AND codigo_personas=3
  );
