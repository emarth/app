insert into Users(username, pass_word)
values('myaccount', 'password123');

insert into Persons(personne_id, nom, prenom)
values (1, 'Doe', 'John');

update Users
set personne_id = 1
where (username = 'myaccount');

-- Il faut en ajouter davantage de données