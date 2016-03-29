

--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

delete from  art46.db_versie
where db_versie = '6.25';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

alter table art46.db_versie
    add column omschrijving varchar(200)
;

-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie, omschrijving) values ('6.25', 'add column  omschrijving in db_versie');
