

--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

alter table art46.schuldvordering_status_history
    drop column webloket_gebruiker_login
;


delete from  art46.db_versie
where db_versie = '5.38';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

alter table art46.schuldvordering_status_history
    add column webloket_gebruiker_login varchar(250)
;

-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('5.38');