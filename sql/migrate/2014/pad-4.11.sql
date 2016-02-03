--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

delete from  ART46.DB_VERSIE
where DB_VERSIE = '4.11';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

DROP TABLE ART46.PLANNING_VERSIE_MARKERING;

DROP TABLE ART46.PLANNING_VERSIE;

-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('4.11');

