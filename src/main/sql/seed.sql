INSERT INTO Succursales VALUES (DEFAULT, );

SET search_path = Dentist;

INSERT INTO Adresses VALUES (DEFAULT, '45', 'Rue du Sommelier', 'Ottawa', 'Ontario');
INSERT INTO Adresses VALUES (DEFAULT, '47', 'Rue du Sommelier', 'Ottawa', 'Ontario');
INSERT INTO Adresses VALUES (DEFAULT, '49', 'Rue du Sommelier', 'Ottawa', 'Ontario');
INSERT INTO Adresses VALUES (DEFAULT, '1067', 'Boulevard du Dentist', 'Ottawa', 'Ontario');
INSERT INTO Adresses VALUES (DEFAULT, '13', 'Rue de Montreal', 'Gatineau', 'Québec');

INSERT INTO Persons VALUES (DEFAULT, 'Pooh', 'Samir', '123456789', 'M', '2000-06-06', 'Intact', 's.p@gmail.com', '6138439221', 1);
INSERT INTO Persons VALUES (DEFAULT, 'Pooh', 'Jimmy', '987654321', 'M', '2015-12-28', 'Intact', 'j.p@gmail.com', '6135556666', 1);
INSERT INTO Persons VALUES (DEFAULT, 'Smite', 'Julie', '786543012', 'F', '1945-01-01', 'Desjardins', 'j.smite@hotmail.com', '6130009911', 2);
INSERT INTO Persons VALUES (DEFAULT, 'Eagle', 'Mike', '010203040', 'M', '1969-06-09', 'Sunlife', 'm.e@gmail.com', '4167776666', 3);
INSERT INTO Persons VALUES (DEFAULT, 'Osterhuis', 'Jeanne', '01033140', 'F', '1980-07-09', 'Farmers', 'j.o@gmail.com', '4162291010', 5);

INSERT INTO Users VALUES ('sampoo', '1234', 1);
INSERT INTO Users VALUES ('joster', '4321', 5);
INSERT INTO Users VALUES ('julsmi', '1243', 3);
INSERT INTO Users VALUES ('mikeag', '2134', 4);

INSERT INTO Patients VALUES (DEFAULT, 1, 'sampoo');
INSERT INTO Patients VALUES (DEFAULT, 2, 'sampoo');
INSERT INTO Patients VALUES (DEFAULT, 3, 'julsmi');

INSERT INTO Succursales VALUES (DEFAULT, 'Ottawa', 4);

INSERT INTO Employees VALUES (DEFAULT, 'julsmi', 1, 'Secretaire', 'Full-time', 55000);
INSERT INTO Employees VALUES (DEFAULT, 'mikeag', 1, 'Dentist', 'Full-Time', 397000);
INSERT INTO Employees VALUES (DEFAULT, 'joster', 1, 'Dentist', 'Part-Time', 180000);

INSERT INTO Avis VALUES (DEFAULT, 1, 'Jim Pooh bites.');

INSERT INTO Frais VALUES (DEFAULT, 5356, 911, 3556);
INSERT INTO Frais VALUES (DEFAULT, 7777, 123, 324);

INSERT INTO Rendezvous VALUES (DEFAULT, '2022-07-08', 1, 2, 13, 14, 'Surgery', 'Pending', 1);
INSERT INTO Rendezvous VALUES (DEFAULT, '2022-03-30', 2, 3, 15, 16, 'Check-up', 'Completed', 1);

INSERT INTO Procedurerdv VALUES (DEFAULT, 911, 'The whole package. Complete denture.', 1, '2022-07-08', 14, 'Toutes les dents donc 32.');
INSERT INTO Procedurerdv VALUES (DEFAULT, 123, 'Buckteeth arrangement.', 2, '2022-03-30', 2, '2 dents de devant');

INSERT INTO Traitement VALUES (DEFAULT, '911', 'Surgery', 'Toutes les dents', '38h open mouth procedure.', 'Mal de bouche.', 'Opiod et morphine');
INSERT INTO Traitement VALUES (DEFAULT, '123', 'Check-up', '2 dents de devant', 'Braces check', 'None', 'None');

INSERT INTO Facture VALUES (DEFAULT, '2022-07-08', 1, 3000, 556, 3556, 2550, 'Intact');
INSERT INTO Facture VALUES (DEFAULT, '2022-03-30', 2, 300, 24, 324, 310, 'Intact');

INSERT INTO Reclamation VALUES (DEFAULT, 2);

INSERT INTO Paiement VALUES (DEFAULT, 2, 1, 'Debit');

INSERT INTO estPatientde VALUES (1,2);
INSERT INTO estPatientde VALUES (2,3);
