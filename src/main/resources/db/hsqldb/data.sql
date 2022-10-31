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
INSERT INTO authorities(id,username,authority) VALUES (5,'jualeomad','owner');
INSERT INTO authorities(id,username,authority) VALUES (6,'marescram3','owner');
INSERT INTO authorities(id,username,authority) VALUES (7,'dandiasua','owner');
INSERT INTO authorities(id,username,authority) VALUES (8,'rafgargal','owner');
INSERT INTO authorities(id,username,authority) VALUES (9,'ernsaqrio','owner');

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

