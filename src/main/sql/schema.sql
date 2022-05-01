DROP SCHEMA IF EXISTS Dentist CASCADE;
CREATE SCHEMA Dentist;

SET search_path = Dentist;

create table Adresses (
    adresse_id serial,
    numero integer NOT NULL,
    rue varchar(20) NOT NULL,
    ville varchar(20) NOT NULL,
    province varchar(20) NOT NULL,
    primary key (adresse_id)
);

create table Persons (
    personne_id serial,
    nom varchar(20) NOT NULL,
    prenom varchar(20) NOT NULL,
    ssn integer NOT NULL,
    sexe varchar(20) NOT NULL,
    dateNaissance date NOT NULL,
    assurance varchar(20) NOT NULL,
    email varchar(20) NOT NULL,
    numeroTel integer NOT NULL,
    adresse_id integer NOT NULL,
    CONSTRAINT checkNumeroTel CHECK(numeroTel>2000000000 AND numeroTel<9000000000),
    primary key(personne_id),
    foreign key(adresse_id) references Adresses(adresse_id)
);

create table Users (
    username varchar(20) NOT NULL,
    pass_word varchar(20) NOT NULL,
    personne_id integer NOT NULL,
    primary key(username),
    foreign key(personne_id) references Persons(personne_id)
);

create table Patients (
    patient_id serial,
    personne_id integer NOT NULL,
    username varchar(20) NOT NULL,
    primary key (patient_id),
    foreign key (personne_id) references Persons(personne_id),
    foreign key (username) references Users(username)
);

create table Succursales (
    succursale_id serial,
    ville varchar(20) NOT NULL,
    adresse_id integer NOT NULL,
    primary key (succursale_id),
    foreign key (adresse_id) references Adresses(adresse_id)
);

create table Employees (
    employee_id serial,
    username varchar(20) NOT NULL,
    succursale_id integer NOT NULL,
    emploi_role varchar(20) NOT NULL,
    emploi_type varchar(20) NOT NULL,
    salaire integer NOT NULL,
    primary key (employee_id),
    foreign key (username) references Users(username),
    foreign key (succursale_id) references Succursales(succursale_id)
);

create table Avis (
    avis_id serial,
    employee_id integer NOT NULL,
    commentaire varchar(100) NOT NULL,
    primary key (avis_id),
    foreign key (employee_id) references Employees(employee_id)
);

create table Frais (
    frais_id serial,
    fraisCode integer NOT NULL,
    procedureCode integer NOT NULL,
    frais integer NOT NULL,
    primary key (frais_id),
);

/* this one might need modifications*/
create table Procedurerdv (
    procedure_id serial,
    procedureCode integer NOT NULL,
    description_ varchar(50) NOT NULL,
    patient_id integer NOT NULL,
    dateRDV date NOT NULL,
    quantiteProcedure integer NOT NULL,
    dentImplique varchar(100) NOT NULL,
    primary key(procedure_id),
    foreign key (patient_id) references Patients(patient_id)
);

create table Traitement (
    traitement_id serial,
    typeRDV varchar(20) NOT NULL,
    typeTraitements varchar(20) NOT NULL,
    dents varchar(20) NOT NULL,
    commentaire varchar(100) NOT NULL,
    symptomes varchar(50) NOT NULL,
    medicaments varchar(20) NOT NULL,
    primary key (traitement_id)
);


create table Reclamation (
    reclamation_id serial,
    facture_id integer NOT NULL,
    primary key (reclamation_id),
    foreign key (facture_id) references Facture(facture_id)   
);

create table Facture (
    facture_id serial,
    dateEmission date NOT NULL,
    patient_id integer NOT NULL,
    fraisPatient integer NOT NULL,
    fraisAssurance integer NOT NULL,
    fraisTotaux integer NOT NULL,
    reduction integer NOT NULL,
    assurancePatient varchar(20) NOT NULL,
    CONSTRAINT checkFrais CHECK(fraisTotaux = fraisPatient + fraisAssurance),
    primary key (facture_id),
    foreign key (patient_id) references Patients(patient_id)
);

create table Paiement (
    paiement_id serial,
    facture_id integer NOT NULL,
    reclamation_id integer NOT NULL,
    paiementType varchar(20) NOT NULL,
    primary key (paiement_id),
    foreign key (facture_id) references Facture(facture_id),
    foreign key (reclamation_id) references Reclamation(reclamation_id)
);

create table Rendezvous (
    rdv_id serial,
    daterdv date NOT NULL,
    patient_id integer NOT NULL,
    employee_id integer NOT NULL,
    heureDebut integer NOT NULL,
    heureFin integer NOT NULL,
    typeRDV varchar(20) NOT NULL,
    statut varchar(20) NOT NULL,
    chambreAttribue integer NOT NULL,
    CONSTRAINT checkHeure CHECK(heureDebut<heureFin),
    CONSTRAINT checkHeureNum CHECK(heureDebut>0 AND heureDebut<24 AND heureFin>0 AND heureFin<24)
    primary key (rdv_id),
    foreign key (patient_id) references Patients(patient_id),
    foreign key (employee_id) references Employees(employee_id)
);


