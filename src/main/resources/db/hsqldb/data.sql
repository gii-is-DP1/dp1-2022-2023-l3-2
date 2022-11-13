-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled) VALUES ('admin1','4dm1n',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (1,'admin1','admin');
-- One owner user, named owner1 with passwor 0wn3r
INSERT INTO users(username,password,enabled) VALUES ('owner1','0wn3r',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('alegarsan11', '1234',TRUE);
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


INSERT INTO jugadores(id,first_name, last_name, acero,esespectador,hierro,medalla,objeto,oro,primerjugador,username) VALUES (1, 'Ernesto', 'Saquete Rios', 0, false,0,0,0,0,true,'ernsaqrio');
INSERT INTO jugadores(id,first_name, last_name, acero,esespectador,hierro,medalla,objeto,oro,primerjugador,username) VALUES (2, 'Daniel', 'Diañez',0 ,false,0,0,0,0,false,'dandiasua');
INSERT INTO jugadores(id,first_name, last_name, acero,esespectador,hierro,medalla,objeto,oro,primerjugador,username) VALUES (3, 'Rafael David', 'Garcia', 0,false,0,0,0,0,false,'rafgargal');


INSERT INTO tipo_carta(id, name) VALUES
(0, 'defensa'),
(1, 'extraccion'),
(2, 'ayuda'),
(3, 'forja'),
(4, 'especial');

INSERT INTO tipo_logro(id, name) VALUES
(0, 'partidas_ganadas'),
(1, 'recursos_conseguidos'),
(2, 'puntaje_mas_alto'); 

INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(1, '/resources/images/Dimensionadas/001.png', 1, 1, null, null, 'hierro', 3);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(2, '/resources/images/Dimensionadas/002.png', 2, 1, null, null, 'hierro', 3);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(3, '/resources/images/Dimensionadas/003.png', 3, 1, null, null, 'hierro', 3);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(4, '/resources/images/Dimensionadas/004.png', 4, 1, null, null, 'hierro', 3);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(5, '/resources/images/Dimensionadas/005.png', 5, 1, null, null, 'hierro', 3);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(6, '/resources/images/Dimensionadas/006.png', 6, 1, null, null, 'hierro', 3);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(7, '/resources/images/Dimensionadas/007.png', 7, 1, null, null, 'oro', 1);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(8, '/resources/images/Dimensionadas/008.png', 8, 1, null, null, 'hierro', 3);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(9, '/resources/images/Dimensionadas/009.png', 9, 1, null, null, 'oro', 1);

INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(10, '/resources/images/Dimensionadas/010.png', 1, 1, 'hierro', 3, 'acero', 2);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(11, '/resources/images/Dimensionadas/011.png', 9, 0, null, null, null, null);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(12, '/resources/images/Dimensionadas/012.png', 3, 3, 'acero', 3, 'objeto', 1);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(13, '/resources/images/Dimensionadas/013.png', 4, 0, null, null, null, null);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(14, '/resources/images/Dimensionadas/014.png', 5, 0, null, null, null, null);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(15, '/resources/images/Dimensionadas/015.png', 6, 1, 'hierro', 3, 'acero', 2);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(16, '/resources/images/Dimensionadas/016.png', 7, 3, null, null, null, null);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(17, '/resources/images/Dimensionadas/017.png', 8, 1, null, null, 'oro', 1);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(18, '/resources/images/Dimensionadas/018.png', 9, 1, null, null, 'hierro', 3);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(19, '/resources/images/Dimensionadas/019.png', 1, 1, 'hierro', 3, 'acero', 2);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(20, '/resources/images/Dimensionadas/020.png', 2, 0, null, null, null, null);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(21, '/resources/images/Dimensionadas/021.png', 3, 1, 'hierro', 3, 'acero', 2);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(22, '/resources/images/Dimensionadas/022.png', 4, 0, null, null, null, null);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(23, '/resources/images/Dimensionadas/023.png', 5, 1, 'hierro', 3, 'acero', 2);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(24, '/resources/images/Dimensionadas/024.png', 6, 2, null, null, null, null);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(25, '/resources/images/Dimensionadas/025.png', 7, 3, null, null, null, null);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(26, '/resources/images/Dimensionadas/026.png', 8, 3, null, null, null, null);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(27, '/resources/images/Dimensionadas/027.png', 9, 0, null, null, null, null);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(28, '/resources/images/Dimensionadas/028.png', 1, 1, null, null, 'oro', 1);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(29, '/resources/images/Dimensionadas/029.png', 2, 1, 'hierro', 3, 'acero', 2);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(30, '/resources/images/Dimensionadas/030.png', 3, 0, null, null, null, null);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(31, '/resources/images/Dimensionadas/031.png', 4, 3, 'acero', 2, 'objeto', 1);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(32, '/resources/images/Dimensionadas/032.png', 5, 2, null, null, null, null);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(33, '/resources/images/Dimensionadas/033.png', 6, 3, 'oro', 3, 'objeto', 1);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(34, '/resources/images/Dimensionadas/034.png', 7, 1, null, null, 'hierro', 3);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(35, '/resources/images/Dimensionadas/035.png', 8, 0, null, null, null, null);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(36, '/resources/images/Dimensionadas/036.png', 9, 1, null, null, 'hierro', 3);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(37, '/resources/images/Dimensionadas/037.png', 1, 0, null, null, null, null);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(38, '/resources/images/Dimensionadas/038.png', 2, 0, null, null, null, null);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(39, '/resources/images/Dimensionadas/039.png', 3, 2, null, null, null, null);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(40, '/resources/images/Dimensionadas/040.png', 4, 1, null, null, 'oro', 1);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(41, '/resources/images/Dimensionadas/041.png', 5, 1, null, null, 'oro', 1);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(42, '/resources/images/Dimensionadas/042.png', 6, 0, null, null, null, null);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(43, '/resources/images/Dimensionadas/043.png', 7, 0, null, null, null, null);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(44, '/resources/images/Dimensionadas/044.png', 8, 1, 'hierro', 3, 'acero', 2);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(45, '/resources/images/Dimensionadas/045.png', 9, 3, null, null, null, null);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(46, '/resources/images/Dimensionadas/046.png', 1, 1, null, null, 'oro', 1);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(47, '/resources/images/Dimensionadas/047.png', 2, 0, null, null, null, null);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(48, '/resources/images/Dimensionadas/048.png', 3, 0, null, null, null, null);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(49, '/resources/images/Dimensionadas/049.png', 4, 0, null, null, null, null);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(50, '/resources/images/Dimensionadas/050.png', 5, 1, null, null, 'oro', 1);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(51, '/resources/images/Dimensionadas/051.png', 6, 3, null, null, null, null);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(52, '/resources/images/Dimensionadas/052.png', 7, 2, null, null, null, null);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(53, '/resources/images/Dimensionadas/053.png', 8, 0, null, null, null, null);
INSERT INTO carta(id, imagen, posicion, tipo, entrada, cantidadEntrada, devuelve, cantidadDevuelve) VALUES(54, '/resources/images/Dimensionadas/054.png', 9, 1, 'hierro', 3, 'acero', 2);

INSERT INTO carta(id, imagen, posicion, tipo, entrada,cantidadEntrada, devuelve, cantidadDevuelve) VALUES(55, '/resources/images/Dimensionadas/055-A.png', 10, 4, null, null, null, null);
INSERT INTO carta(id, imagen, posicion, tipo, entrada,cantidadEntrada, devuelve, cantidadDevuelve) VALUES(56, '/resources/images/Dimensionadas/056-A.png', 10, 4, null, null, null, null);
INSERT INTO carta(id, imagen, posicion, tipo, entrada,cantidadEntrada, devuelve, cantidadDevuelve) VALUES(57, '/resources/images/Dimensionadas/057-A.png', 10, 4, null, null, null, null);
INSERT INTO carta(id, imagen, posicion, tipo, entrada,cantidadEntrada, devuelve, cantidadDevuelve) VALUES(58, '/resources/images/Dimensionadas/058-A.png', 11, 4, null, null, null, null);
INSERT INTO carta(id, imagen, posicion, tipo, entrada,cantidadEntrada, devuelve, cantidadDevuelve) VALUES(59, '/resources/images/Dimensionadas/059-A.png', 11, 4, null, null, null, null);
INSERT INTO carta(id, imagen, posicion, tipo, entrada,cantidadEntrada, devuelve, cantidadDevuelve) VALUES(60, '/resources/images/Dimensionadas/060-A.png', 11, 4, null, null, null, null);
INSERT INTO carta(id, imagen, posicion, tipo, entrada,cantidadEntrada, devuelve, cantidadDevuelve) VALUES(61, '/resources/images/Dimensionadas/061-A.png', 12, 4, null, null, null, null);
INSERT INTO carta(id, imagen, posicion, tipo, entrada,cantidadEntrada, devuelve, cantidadDevuelve) VALUES(62, '/resources/images/Dimensionadas/062-A.png', 12, 4, null, null, null, null);
INSERT INTO carta(id, imagen, posicion, tipo, entrada,cantidadEntrada, devuelve, cantidadDevuelve) VALUES(63, '/resources/images/Dimensionadas/063-A.png', 12, 4, null, null, null, null);
