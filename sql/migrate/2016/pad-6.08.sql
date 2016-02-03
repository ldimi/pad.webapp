

--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


drop trigger art46.ai_dossier_zip;


delete from  art46.db_versie
where db_versie = '6.08';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

create table art46.dossier_zip (
	dossier_id int not null,
	dossier_type char(1) not null,
	zip_d date not null,
    primary key (dossier_id),
    foreign key fk_dossier (dossier_id, dossier_type)
         references art46.dossier (id, dossier_type)
         on delete restrict,
    constraint cc_dossier_type check (dossier_type = 'B')
);

create trigger art46.ai_dossier_zip
after insert on art46.dossier_zip referencing new as nrow for each row mode db2sql
insert into smeg.dossier_zip (dossier_id)
values
( select dossier_id_boa from art46.dossier where id = nrow.dossier_id)
;



-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('6.08');
