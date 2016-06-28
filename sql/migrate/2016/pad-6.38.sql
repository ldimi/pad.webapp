

--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;



delete from  art46.db_versie
where db_versie = '6.38';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

create table art46.webloket_medewerkersrol (
    login_id int not null,
    organisatie_id int not null,
    code varchar(75) not null,
    email varchar(250) not null,
    organisatietype_id int not null,
    actief_jn char(1) not null,
    primary key (login_id, organisatie_id, code)
);


-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie, omschrijving) values ('6.38', 'create art46.webloket_medewerkers');
