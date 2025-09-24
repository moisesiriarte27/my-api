CREATE TABLE lanzamiento (
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
INSERT INTO lanzamiento (descripcion, fecha_vencimiento, fecha_pago, valor, observacion, tipo, codigo_categoria, codigo_personas) VALUES ('Alquiler de oficina', '2025-09-15', '2025-09-15', 1200.00, 'Pago mensual de la oficina', 'RECETA', 1, 3);