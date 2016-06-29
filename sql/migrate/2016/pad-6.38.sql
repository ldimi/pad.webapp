

--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

drop table art46.webloket_medewerkersrol;

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
    wijzig_ts timestamp not null,
    primary key (login_id, organisatie_id, code)
);


create trigger art46.bi_webloket_medewerkersrol_ts  
no cascade before insert on art46.webloket_medewerkersrol
referencing
    new as n
for each row mode db2sql
set
        n.wijzig_ts = current timestamp
;


create trigger art46.bu_webloket_medewerkersrol_ts   
no cascade before update on art46.webloket_medewerkersrol
referencing
    old as o
    new as n
for each row mode db2sql
set
        n.wijzig_ts = current timestamp
;



-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie, omschrijving) values ('6.38', 'create art46.webloket_medewerkers');
