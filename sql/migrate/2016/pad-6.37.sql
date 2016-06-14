

--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;



delete from  art46.db_versie
where db_versie = '6.37';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

drop table art46.dossier_organisatie_email;


-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie, omschrijving) values ('6.37', 'drop art46.dossier_organisatie_email');
