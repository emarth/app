/* 
idk if we keep this part in
insert into Users(username, pass_word)
values('myaccount', 'password123');

insert into Persons(personne_id, nom, prenom)
values (1, 'Doe', 'John');

update Users
set personne_id = 1
where (username = 'myaccount');
*/

INSERT INTO Succursales VALUES (DEFAULT, );

SET search_path = Dentist;

INSERT INTO Adresses VALUES (DEFAULT, '45', 'Rue du Sommelier', 'Ottawa', 'Ontario');
INSERT INTO Adresses VALUES (DEFAULT, '47', 'Rue du Sommelier', 'Ottawa', 'Ontario');
INSERT INTO Adresses VALUES (DEFAULT, '49', 'Rue du Sommelier', 'Ottawa', 'Ontario');
INSERT INTO Adresses VALUES (DEFAULT, '1067', 'Boulevard du Dentist', 'Ottawa', 'Ontario');

INSERT INTO Persons VALUES (DEFAULT, 'Samir', 'Pooh', '123456789', 'M', '2000-06-06', 'Intact', 's.p@gmail.com', '6138439221', 1);
INSERT INTO Persons VALUES (DEFAULT, 'Jimmy', 'Pooh', '987654321', 'M', '2015-12-28', 'Intact', 'j.p@gmail.com', '6135556666', 1);
INSERT INTO Persons VALUES (DEFAULT, 'Julie', 'Smite', '786543012', 'F', '1945-01-01', 'Desjardins', 'j.smite@hotmail.com', '6130009911', 2);
INSERT INTO Persons VALUES (DEFAULT, 'Mike', 'Eagle', '010203040', 'M', '1969-06-09', 'Sunlife', 'm.e@gmail.com', '4167776666', 3);

INSERT INTO Users VALUES (DEFAULT, 'sampoo', '1234', 1);
INSERT INTO Users VALUES (DEFAULT, 'jimpoo', '4321', 2);
INSERT INTO Users VALUES (DEFAULT, 'julsmi', '1243', 3);
INSERT INTO Users VALUES (DEFAULT, 'mikeag', '2134', 4);

INSERT INTO Patients VALUES (DEFAULT, 1, 'sampoo');
INSERT INTO Patients VALUES (DEFAULT, 2, 'jimpoo');

INSERT INTO Succursales VALUES (DEFAULT, 'Ottawa', 4);

INSERT INTO Employees VALUES (DEFAULT, 'julsmi', 1, 'Secretaire', 'Full-time', '55000');
INSERT INTO Employees VALUES (DEFAULT, 'mikeag', 1, 'Dentist', 'Full-Time', '397000');

INSERT INTO Avis VALUES (DEFAULT, 1, 'Jim Pooh bites.');

INSERT INTO Frais VALUES (DEFAULT, '5356', '911', '3556');
INSERT INTO Frais VALUES (DEFAULT, '7777', '123', '324');

INSERT INTO Rendezvous VALUES (DEFAULT, '2022-07-08', 1, 4, '13', '14', 'Surgery', 'Pending', 1);
INSERT INTO Rendezvous VALUES (DEFAULT, '2022-03-30', 2, 4, '15', '16', 'Check-up', 'Completed', 1);

INSERT INTO Procedurerdv VALUES (DEFAULT, '911', 'The whole package. Complete denture.', 1, '2022-07-08', 14, 'Toutes les dents donc 32.');
INSERT INTO Procedurerdv VALUES (DEFAULT, '123', 'Buckteeth arrangement.', 2, '2022-03-30', 2, '2 dents de devant');

INSERT INTO Traitement VALUES (DEFAULT, '911', 'Surgery', 'Toutes les dents', '38h open mouth procedure.', 'Mal de bouche.', 'Opiod et morphine');
INSERT INTO Traitement VALUES (DEFAULT, '123', 'Check-up', '2 dents de devant', 'Braces check', 'None', 'None');

INSERT INTO Facture VALUES (DEFAULT, '2022-07-08', 1, '3000', '556', '3556', '2550', 'Intact');
INSERT INTO Facture VALUES (DEFAULT, '2022-03-30', 2, '300', '24', '324', '310', 'Intact');

INSERT INTO Reclamation VALUES (DEFAULT, 2);

INSERT INTO Paiement VALUES (DEFAULT, 2, 2, 'Debit');
