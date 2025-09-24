CREATE TABLE personas(
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nombre VARCHAR(50) NOT NULL,
	lugarpublico VARCHAR(30),
	numero VARCHAR (30),
	complemento VARCHAR(30),
	barrio VARCHAR(30),
	cep VARCHAR(30),
	ciudad VARCHAR(30),
	estado VARCHAR(30),
	activo VARCHAR(30)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO personas(nombre,lugarpublico,numero,complemento,barrio,cep,ciudad,estado,activo)values("Matias gimenez","franco","10",null,"fatima","567234","franco","virgen",true);