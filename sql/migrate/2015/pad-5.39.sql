

--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

alter table art46.schuldvordering_status_history
    drop column webloket_gebruiker_login
;


delete from  art46.db_versie
where db_versie = '5.39';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

drop table art46.dossier_onderneming;

create table art46.dossier_onderneming
(
    dossier_id int not null,
    ondernemingsnummer varchar(12) not null,
    primary key (dossier_id, ondernemingsnummer),
    foreign key fk_dosond_dossier (dossier_id)
        references art46.dossier (id)
        on delete restrict
);

-- voorlopig : om bestaande hibernate mapping te supporteren
--        moet later verwijderd worden.
alter table art46.dossier_onderneming
   add column id int
   add column rol varchar(35) default 'aannemer'
;



alter table art46.offerte
    add column ondernemingsnummer varchar(12)
;

drop table art46.offerte_onderneming;


-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('5.39');