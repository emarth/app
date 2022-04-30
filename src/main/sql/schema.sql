create table Adresses (
    adresse_id serial,
    numero integer,
    rue varchar(20),
    ville varchar(20),
    province varchar(20),
    primary key (adresse_id)
);

create table Persons (
    personne_id serial,
    nom varchar(20),
    prenom varchar(20),
    ssn integer,
    sexe varchar(20),
    dateNaissance date,
    assurance varchar(20),
    email varchar(20),
    numeroTel integer,
    adresse_id integer,
    primary key(personne_id),
    foreign key(adresse_id) references Adresses(adresse_id)
);

create table Users (
    username varchar(20),
    pass_word varchar(20),
    personne_id integer,
    primary key(username),
    foreign key(personne_id) references Persons(personne_id)
);

create table Patients (
    patient_id serial,
    personne_id integer,
    username varchar(20),
    primary key (patient_id),
    foreign key (personne_id) references Persons(personne_id),
    foreign key (username) references Users(username)
);

create table Succursales (
    succursale_id serial,
    ville varchar(20),
    adresse_id integer,
    primary key (succursale_id),
    foreign key (adresse_id) references Adresses(adresse_id)
);

create table Employees (
    employee_id serial,
    username varchar(20),
    succursale_id integer,
    emploi_role varchar(20),
    emploi_type varchar(20),
    salaire integer,
    primary key (employee_id),
    foreign key (username) references Users(username),
    foreign key (succursale_id) references Succursales(succursale_id)
);

