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

create table Avis (
    avis_id serial,
    employee_id integer,
    commentaire varchar(100),
    primary key (avis_id),
    foreign key (employee_id) references Employees(employee_id)
);

create table Frais (
    frais_id serial,
    fraisCode integer,
    procedureCode integer,
    frais integer,
    primary key (frais_id),
);

/* this one might need modifications*/
create table Procedurerdv (
    procedure_id serial,
    procedureCode integer,
    description_ varchar(50),
    patient_id integer,
    dateRDV date,
    quantiteProcedure integer,
    dentImplique varchar(100),
    primary key(procedure_id),
    foreign key (patient_id) references Patients(patient_id)
);

create table Traitement (
    traitement_id serial,
    typeRDV varchar(20),
    typeTraitements varchar(20),
    dents varchar(20),
    commentaire varchar(100),
    symptomes varchar(50),
    medicaments varchar(20),
    primary key (traitement_id)
);


create table Reclamation (
    reclamation_id serial,
    facture_id integer
    primary key (reclamation_id),
    foreign key (facture_id) references Facture(facture_id)   
);

create table Facture (
    facture_id serial,
    dateEmission date,
    patient_id integer,
    fraisPatient integer,
    fraisAssurance integer,
    fraisTotaux integer,
    reduction integer,
    assurancePatient varchar(20),
    primary key (facture_id),
    foreign key (patient_id) references Patients(patient_id)
);

create table Paiement (
    paiement_id serial,
    facture_id integer,
    reclamation_id integer,
    paiementType varchar(20),
    primary key (paiement_id),
    foreign key (facture_id) references Facture(facture_id),
    foreign key (reclamation_id) references Reclamation(reclamation_id)
);

create table Rendezvous (
    rdv_id serial,
    daterdv date,
    patient_id integer,
    employee_id integer,
    heureDebut integer,
    heureFin integer,
    typeRDV varchar(20),
    statut varchar(20),
    chambreAttribue integer,
    primary key (rdv_id),
    foreign key (patient_id) references Patients(patient_id),
    foreign key (employee_id) references Employees(employee_id)
);


