--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

ALTER table art46.schuldvordering
    ALTER COLUMN commentaar SET DATA TYPE VARCHAR(255)
;

CALL SYSPROC.ADMIN_CMD('REORG TABLE ART46.SCHULDVORDERING');



delete from  ART46.DB_VERSIE
where DB_VERSIE = '4.32';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

ALTER table art46.schuldvordering
    ALTER COLUMN commentaar SET DATA TYPE VARCHAR(1000)
;

CALL SYSPROC.ADMIN_CMD('REORG TABLE ART46.SCHULDVORDERING');


-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('4.32');




























