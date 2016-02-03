
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

ALTER TABLE ART46.DOSSIER
      DROP CONSTRAINT DOSSIER_ID_TYPE_UNIEK
;

delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.1';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

ALTER TABLE ART46.DOSSIER
      ADD CONSTRAINT DOSSIER_ID_TYPE_UNIEK UNIQUE(ID, DOSSIER_TYPE)
;

-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.1');
