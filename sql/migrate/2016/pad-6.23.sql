

--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

drop table art46.dossier_taak;

delete from  art46.db_versie
where db_versie = '6.23';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

create table art46.dossier_taak (
	taak_id int not null generated by default as identity (START WITH 100),
	dossier_id int not null,
	taak_type char(25) not null,
	behandeld_d date,
    primary key (taak_id),
    foreign key fk_dossier (dossier_id)
         references art46.dossier (id)
         on delete restrict
);




-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('6.23');
