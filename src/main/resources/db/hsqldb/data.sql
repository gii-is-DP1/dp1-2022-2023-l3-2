-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled) VALUES ('admin1','4dm1n',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (1,'admin1','admin');
-- One owner user, named owner1 with passwor 0wn3r
INSERT INTO users(username,password,enabled) VALUES ('owner1','0wn3r',TRUE);
INSERT INTO users(username,password,enabled, imgperfil) VALUES ('alegarsan11', '1234',TRUE,'https://previews.123rf.com/images/yupiramos/yupiramos1708/yupiramos170831273/84892638-icono-del-avatar-hombre-sobre-ilustraci%C3%B3n-de-vectores-de-fondo-blanco.jpg');
INSERT INTO users(username,password,enabled) VALUES ('jualeomad', '1234',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('marescram3', '1234',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('dandiasua', '1234',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('ernsaqrio', '7896',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('rafgargal', '1234',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (2,'owner1','owner');
INSERT INTO authorities(id,username,authority) VALUES (4,'alegarsan11','jugador');
INSERT INTO authorities(id,username,authority) VALUES (5,'jualeomad','jugador');
INSERT INTO authorities(id,username,authority) VALUES (6,'marescram3','jugador');
INSERT INTO authorities(id,username,authority) VALUES (7,'dandiasua','jugador');
INSERT INTO authorities(id,username,authority) VALUES (8,'rafgargal','jugador');
INSERT INTO authorities(id,username,authority) VALUES (9,'ernsaqrio','jugador');

-- One vet user, named vet1 with passwor v3t
INSERT INTO users(username,password,enabled) VALUES ('vet1','v3t',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (3,'vet1','veterinarian');

INSERT INTO vets(id, first_name,last_name) VALUES (1, 'James', 'Carter');
INSERT INTO vets(id, first_name,last_name) VALUES (2, 'Helen', 'Leary');
INSERT INTO vets(id, first_name,last_name) VALUES (3, 'Linda', 'Douglas');
INSERT INTO vets(id, first_name,last_name) VALUES (4, 'Rafael', 'Ortega');
INSERT INTO vets(id, first_name,last_name) VALUES (5, 'Henry', 'Stevens');
INSERT INTO vets(id, first_name,last_name) VALUES (6, 'Sharon', 'Jenkins');

INSERT INTO specialties VALUES (1, 'radiology');
INSERT INTO specialties VALUES (2, 'surgery');
INSERT INTO specialties VALUES (3, 'dentistry');


INSERT INTO vet_specialties VALUES (2, 1);
INSERT INTO vet_specialties VALUES (3, 2);
INSERT INTO vet_specialties VALUES (3, 3);
INSERT INTO vet_specialties VALUES (4, 2);
INSERT INTO vet_specialties VALUES (5, 1);



INSERT INTO estadistica(id, partidas_ganadas, partidas_perdidas, user_id) VALUES
(1, 0, 0, 'jualeomad'),
(2, 0, 0, 'rafgargal'),
(3, 0, 0, 'dandiasua'),
(4, 0, 0, 'alegarsan11');


INSERT INTO tipo_carta(id, name) VALUES
(0, 'defensa'),
(1, 'extraccion'),
(2, 'ayuda'),
(3, 'forja'),
(4, 'especial'),
(5, 'base');

INSERT INTO tipo_logro(id, name) VALUES
(0, 'partidas_ganadas'),
(1, 'recursos_conseguidos'),
(2, 'puntaje_mas_alto');

INSERT INTO logro(id, name, descripcion, dificultad, requisito, tipo) VALUES
(1, '10 victorias', 'Se consigue al ganar 10 partidas', 1, 10, 0),
(2, '50 victorias', 'Se consigue al ganar 50 partidas', 2, 50, 0),
(3, '100 victorias', 'Se consigue al ganar 100 partidas', 2, 100, 0),
(4, '1000 victorias', 'Se consigue al ganar 1000 partidas', 3, 1000, 0),
(5, '100 hierro', 'Se consigue al coleccionar 100 de hierro', 1, 100, 1),
(6, '1000 hierro', 'Se consigue al coleccionar 1000 de hierro', 2, 1000, 1),
(7, '10000 hierro', 'Se consigue al coleccionar 10000 de hierro', 3, 10000, 1),
(8, '100 oro', 'Se consigue al coleccionar 100 de oro', 1, 100, 1),
(9, '1000 oro', 'Se consigue al coleccionar 1000 de oro', 2, 1000, 1),
(10, '5000 oro', 'Se consigue al coleccionar 5000 de oro', 3, 5000, 1),
(11, '100 acero', 'Se consigue al coleccionar 100 de acero', 1, 100, 1),
(12, '1000 acero', 'Se consigue al coleccionar 1000 de acero', 2, 1000, 1),
(13, '5000 acero', 'Se consigue al coleccionar 5000 de acero', 3, 5000, 1),
(14, '50 objetos', 'Se consigue al coleccionar 50 objetos', 1, 50, 1),
(15, '200 objetos', 'Se consigue al coleccionar 200 objetos', 2, 200, 1),
(16, '1000 objetos', 'Se consigue al coleccionar 1000 objetos', 3, 1000, 1),
(17, '50 medallas', 'Se consigue al coleccionar 50 medallas', 1, 50, 1),
(18, '200 medallas', 'Se consigue al coleccionar 200 medallas', 2, 200, 1),
(19, '1000 medallas', 'Se consigue al coleccionar 1000 medallas', 3, 1000, 1);



INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(1, '/resources/images/Dimensionadas/001.png', 1, 1, null, null, 'hierro',  3   , 'extraccion');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(2, '/resources/images/Dimensionadas/002.png', 2, 1, null, null, 'hierro',  3   , 'extraccion');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(3, '/resources/images/Dimensionadas/003.png', 3, 1, null, null, 'hierro',  3   , 'extraccion');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(4, '/resources/images/Dimensionadas/004.png', 4, 1, null, null, 'hierro',  3   , 'extraccion');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(5, '/resources/images/Dimensionadas/005.png', 5, 1, null, null, 'hierro',  3   , 'extraccion');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(6, '/resources/images/Dimensionadas/006.png', 6, 1, null, null, 'hierro',  3   , 'extraccion');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(7, '/resources/images/Dimensionadas/007.png', 7, 1, null, null, 'oro',     1   , 'extraccion');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(8, '/resources/images/Dimensionadas/008.png', 8, 1, null, null, 'hierro',  3   , 'extraccion');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(9, '/resources/images/Dimensionadas/009.png', 9, 1, null, null, 'oro',     1   , 'extraccion');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(10, '/resources/images/Dimensionadas/010.png', 1, 1, 'hierro', 3, 'acero', 2   , 'forja');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(11, '/resources/images/Dimensionadas/011.png', 9, 0, null, null, 'medalla',1   , 'defensa');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(12, '/resources/images/Dimensionadas/012.png', 3, 3, 'acero', 3, 'objeto', 1   , 'forja');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(13, '/resources/images/Dimensionadas/013.png', 4, 0, null, null, 'medalla',1   , 'defensa');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(14, '/resources/images/Dimensionadas/014.png', 5, 0, null, null, 'medalla',1   , 'defensa');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(15, '/resources/images/Dimensionadas/015.png', 6, 1, 'hierro', 3, 'acero', 2   , 'forja');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(16, '/resources/images/Dimensionadas/016.png', 7, 3, null, null, null, null    , 'forja');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(17, '/resources/images/Dimensionadas/017.png', 8, 1, null, null, 'oro',    1   , 'extraccion');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(18, '/resources/images/Dimensionadas/018.png', 9, 1, null, null, 'hierro', 3   , 'extraccion');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(19, '/resources/images/Dimensionadas/019.png', 1, 1, 'hierro', 3, 'acero', 2   , 'forja');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(20, '/resources/images/Dimensionadas/020.png', 2, 0, null, null, 'medalla',1   , 'defensa');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(21, '/resources/images/Dimensionadas/021.png', 3, 1, 'hierro', 3, 'acero', 2   , 'forja');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(22, '/resources/images/Dimensionadas/022.png', 4, 0, null, null, 'medalla',1   , 'defensa');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(23, '/resources/images/Dimensionadas/023.png', 5, 1, 'hierro', 3, 'acero', 2   , 'forja');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(24, '/resources/images/Dimensionadas/024.png', 6, 2, null, null, null, null    , 'base');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(25, '/resources/images/Dimensionadas/025.png', 7, 3, null, null, null, null    , 'forja');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(26, '/resources/images/Dimensionadas/026.png', 8, 3, null, null, null, null    , 'forja');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(27, '/resources/images/Dimensionadas/027.png', 9, 0, null, null, 'medalla',1   , 'defensa');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(28, '/resources/images/Dimensionadas/028.png', 1, 1, null, null, 'oro', 1      , 'extraccion');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(29, '/resources/images/Dimensionadas/029.png', 2, 1, 'hierro', 3, 'acero', 2   , 'forja');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(30, '/resources/images/Dimensionadas/030.png', 3, 0, null, null, 'medalla',1   , 'defensa');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(31, '/resources/images/Dimensionadas/031.png', 4, 3, 'acero', 2, 'objeto', 1   , 'forja');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(32, '/resources/images/Dimensionadas/032.png', 5, 2, null, null, null, null    , 'base');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(33, '/resources/images/Dimensionadas/033.png', 6, 3, 'oro', 3, 'objeto', 1     , 'forja');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(34, '/resources/images/Dimensionadas/034.png', 7, 1, null, null, 'hierro', 3   , 'extraccion');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(35, '/resources/images/Dimensionadas/035.png', 8, 0, null, null, 'medalla',1   , 'defensa');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(36, '/resources/images/Dimensionadas/036.png', 9, 1, null, null, 'hierro', 3   , 'extraccion');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(37, '/resources/images/Dimensionadas/037.png', 1, 0, null, null, 'medalla',1   , 'defensa');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(38, '/resources/images/Dimensionadas/038.png', 2, 0, null, null, 'medalla',1   , 'defensa');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(39, '/resources/images/Dimensionadas/039.png', 3, 2, null, null, null, null    , 'base');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(40, '/resources/images/Dimensionadas/040.png', 4, 1, null, null, 'oro', 1      , 'extraccion');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(41, '/resources/images/Dimensionadas/041.png', 5, 1, null, null, 'oro', 1      , 'extraccion');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(42, '/resources/images/Dimensionadas/042.png', 6, 0, null, null, 'medalla',1   , 'defensa');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(43, '/resources/images/Dimensionadas/043.png', 7, 0, null, null, 'medalla',1   , 'defensa');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(44, '/resources/images/Dimensionadas/044.png', 8, 1, 'hierro', 3, 'acero', 2   , 'forja');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(45, '/resources/images/Dimensionadas/045.png', 9, 3, null, null, null, null    , 'forja');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(46, '/resources/images/Dimensionadas/046.png', 1, 1, null, null, 'oro', 1      , 'extraccion');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(47, '/resources/images/Dimensionadas/047.png', 2, 0, null, null, 'medalla',1   , 'defensa');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(48, '/resources/images/Dimensionadas/048.png', 3, 0, null, null, 'medalla',1   , 'defensa');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(49, '/resources/images/Dimensionadas/049.png', 4, 0, null, null, 'medalla',1   , 'defensa');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(50, '/resources/images/Dimensionadas/050.png', 5, 1, null, null, 'oro', 1      , 'extraccion');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(51, '/resources/images/Dimensionadas/051.png', 6, 3, null, null, null, null    , 'forja');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(52, '/resources/images/Dimensionadas/052.png', 7, 2, null, null, null, null    , 'base');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(53, '/resources/images/Dimensionadas/053.png', 8, 0, null, null, 'medalla',1   , 'defensa');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(54, '/resources/images/Dimensionadas/054.png', 9, 1, 'hierro', 3, 'acero', 2   , 'forja');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(55, '/resources/images/Dimensionadas/055-A.png', 10, 4, null, null, null, null , 'especial');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(56, '/resources/images/Dimensionadas/056-A.png', 10, 4, null, null, null, null , 'especial');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(57, '/resources/images/Dimensionadas/057-A.png', 10, 4, null, null, null, null , 'especial');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(58, '/resources/images/Dimensionadas/058-A.png', 11, 4, null, null, null, null , 'especial');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(59, '/resources/images/Dimensionadas/059-A.png', 11, 4, null, null, null, null , 'especial');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(60, '/resources/images/Dimensionadas/060-A.png', 11, 4, null, null, null, null , 'especial');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(61, '/resources/images/Dimensionadas/061-A.png', 12, 4, null, null, null, null , 'especial');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(62, '/resources/images/Dimensionadas/062-A.png', 12, 4, null, null, null, null , 'especial');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(63, '/resources/images/Dimensionadas/063-A.png', 12, 4, null, null, null, null , 'especial');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(64, '/resources/images/Dimensionadas/carta_vacia.png', 10, 5, null, null, null, null , 'base');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(65, '/resources/images/Dimensionadas/carta_vacia.png', 11, 5, null, null, null, null , 'base');
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve, type) VALUES(66, '/resources/images/Dimensionadas/carta_vacia.png', 12, 5, null, null, null, null , 'base');
