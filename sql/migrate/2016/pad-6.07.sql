

--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  art46.db_versie
where db_versie = '6.07';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;


drop table art46.verontreinig_activiteit;

-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('6.07');
