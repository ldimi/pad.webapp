--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;




delete from  ART46.DB_VERSIE
where DB_VERSIE = '4.33';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

ALTER table art46.schuldvordering
    add column inzender integer
;

CALL SYSPROC.ADMIN_CMD('REORG TABLE ART46.SCHULDVORDERING');


-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('4.33');




























