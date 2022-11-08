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


INSERT INTO jugadores VALUES (1, 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023', 'owner1');
INSERT INTO jugadores VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749', 'owner1');
INSERT INTO jugadores VALUES (3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763', 'owner1');
INSERT INTO jugadores VALUES (4, 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198', 'owner1');
INSERT INTO jugadores VALUES (5, 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765', 'owner1');
INSERT INTO jugadores VALUES (6, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654', 'owner1');
INSERT INTO jugadores VALUES (7, 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387', 'owner1');
INSERT INTO jugadores VALUES (8, 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683', 'owner1');
INSERT INTO jugadores VALUES (9, 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435', 'owner1');
INSERT INTO jugadores VALUES (10, 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487', 'owner1');
INSERT INTO jugadores VALUES (11, 'Alejandro', 'Garcia', '2335 Maple St.', 'Madison', '6085555555', 'alegarsan11');
INSERT INTO jugadores VALUES (12, 'Juan Carlos', 'Leon', '934 Cable St.', 'Windsor', '6085555532', 'jualeomad');
INSERT INTO jugadores VALUES (13, 'Maria', 'Escalante', '8 Blackhawk Trail', 'Madison', '678432908', 'marescram3');
INSERT INTO jugadores VALUES (14, 'Daniel', 'Diañez', '9 Emiliano St.', 'Waunakee', '654258951', 'dandiasua');
INSERT INTO jugadores VALUES (15, 'Rafael David', 'Garcia', '10 Leo Messi St.', 'Buenos Aires', '722258951', 'rafgargal');
INSERT INTO jugadores VALUES (16, 'Ernesto', 'Saquete Rios', 'New Delhi 1', 'Sevilla', '618961461', 'ernsaqrio');

INSERT INTO tipo_carta(id, name) VALUES
(0, 'defensa'),
(1, 'extraccion'),
(2, 'ayuda'),
(3, 'forja');

INSERT INTO tipo_logro(id, name) VALUES
(0, 'partidas_ganadas'),
(1, 'recursos_conseguidos'),
(2, 'puntaje_mas_alto');

INSERT INTO carta(id, imagen) VALUES ( 1, '/resources/images/Dimensionadas/001.png');
INSERT INTO carta(id, imagen) VALUES ( 2, '/resources/images/Dimensionadas/002.png');
INSERT INTO carta(id, imagen) VALUES ( 3, '/resources/images/Dimensionadas/003.png');
INSERT INTO carta(id, imagen) VALUES ( 4, '/resources/images/Dimensionadas/004.png');
INSERT INTO carta(id, imagen) VALUES ( 5, '/resources/images/Dimensionadas/005.png');
INSERT INTO carta(id, imagen) VALUES ( 6, '/resources/images/Dimensionadas/006.png');
INSERT INTO carta(id, imagen) VALUES ( 7, '/resources/images/Dimensionadas/007.png');
INSERT INTO carta(id, imagen) VALUES ( 8, '/resources/images/Dimensionadas/008.png');
INSERT INTO carta(id, imagen) VALUES ( 9, '/resources/images/Dimensionadas/009.png');
INSERT INTO carta(id, imagen) VALUES ( 10, '/resources/images/Dimensionadas/010.png');
INSERT INTO carta(id, imagen) VALUES ( 11, '/resources/images/Dimensionadas/011.png');
INSERT INTO carta(id, imagen) VALUES ( 12, '/resources/images/Dimensionadas/012.png');
INSERT INTO carta(id, imagen) VALUES ( 13, '/resources/images/Dimensionadas/013.png');
INSERT INTO carta(id, imagen) VALUES ( 14, '/resources/images/Dimensionadas/014.png');
INSERT INTO carta(id, imagen) VALUES ( 15, '/resources/images/Dimensionadas/015.png');
INSERT INTO carta(id, imagen) VALUES ( 16, '/resources/images/Dimensionadas/016.png');
INSERT INTO carta(id, imagen) VALUES ( 17, '/resources/images/Dimensionadas/017.png');
INSERT INTO carta(id, imagen) VALUES ( 18, '/resources/images/Dimensionadas/018.png');
INSERT INTO carta(id, imagen) VALUES ( 19, '/resources/images/Dimensionadas/019.png');
INSERT INTO carta(id, imagen) VALUES ( 20, '/resources/images/Dimensionadas/020.png');
INSERT INTO carta(id, imagen) VALUES ( 21, '/resources/images/Dimensionadas/021.png');
INSERT INTO carta(id, imagen) VALUES ( 22, '/resources/images/Dimensionadas/022.png');
INSERT INTO carta(id, imagen) VALUES ( 23, '/resources/images/Dimensionadas/023.png');
INSERT INTO carta(id, imagen) VALUES ( 24, '/resources/images/Dimensionadas/024.png');
INSERT INTO carta(id, imagen) VALUES ( 25, '/resources/images/Dimensionadas/025.png');
INSERT INTO carta(id, imagen) VALUES ( 26, '/resources/images/Dimensionadas/026.png');
INSERT INTO carta(id, imagen) VALUES ( 27, '/resources/images/Dimensionadas/027.png');
INSERT INTO carta(id, imagen) VALUES ( 28, '/resources/images/Dimensionadas/028.png');
INSERT INTO carta(id, imagen) VALUES ( 29, '/resources/images/Dimensionadas/029.png');
INSERT INTO carta(id, imagen) VALUES ( 30, '/resources/images/Dimensionadas/030.png');
INSERT INTO carta(id, imagen) VALUES ( 31, '/resources/images/Dimensionadas/031.png');
INSERT INTO carta(id, imagen) VALUES ( 32, '/resources/images/Dimensionadas/032.png');
INSERT INTO carta(id, imagen) VALUES ( 33, '/resources/images/Dimensionadas/033.png');
INSERT INTO carta(id, imagen) VALUES ( 34, '/resources/images/Dimensionadas/034.png');
INSERT INTO carta(id, imagen) VALUES ( 35, '/resources/images/Dimensionadas/035.png');
INSERT INTO carta(id, imagen) VALUES ( 36, '/resources/images/Dimensionadas/036.png');
INSERT INTO carta(id, imagen) VALUES ( 37, '/resources/images/Dimensionadas/037.png');
INSERT INTO carta(id, imagen) VALUES ( 38, '/resources/images/Dimensionadas/038.png');
INSERT INTO carta(id, imagen) VALUES ( 39, '/resources/images/Dimensionadas/039.png');
INSERT INTO carta(id, imagen) VALUES ( 40, '/resources/images/Dimensionadas/040.png');
INSERT INTO carta(id, imagen) VALUES ( 41, '/resources/images/Dimensionadas/041.png');
INSERT INTO carta(id, imagen) VALUES ( 42, '/resources/images/Dimensionadas/042.png');
INSERT INTO carta(id, imagen) VALUES ( 43, '/resources/images/Dimensionadas/043.png');
INSERT INTO carta(id, imagen) VALUES ( 44, '/resources/images/Dimensionadas/044.png');
INSERT INTO carta(id, imagen) VALUES ( 45, '/resources/images/Dimensionadas/045.png');
INSERT INTO carta(id, imagen) VALUES ( 46, '/resources/images/Dimensionadas/046.png');
INSERT INTO carta(id, imagen) VALUES ( 47, '/resources/images/Dimensionadas/047.png');
INSERT INTO carta(id, imagen) VALUES ( 48, '/resources/images/Dimensionadas/048.png');
INSERT INTO carta(id, imagen) VALUES ( 49, '/resources/images/Dimensionadas/049.png');
INSERT INTO carta(id, imagen) VALUES ( 50, '/resources/images/Dimensionadas/050.png');
INSERT INTO carta(id, imagen) VALUES ( 51, '/resources/images/Dimensionadas/051.png');
INSERT INTO carta(id, imagen) VALUES ( 52, '/resources/images/Dimensionadas/052.png');
INSERT INTO carta(id, imagen) VALUES ( 53, '/resources/images/Dimensionadas/053.png');
INSERT INTO carta(id, imagen) VALUES ( 54, '/resources/images/Dimensionadas/054.png');
