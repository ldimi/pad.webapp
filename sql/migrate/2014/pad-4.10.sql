--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

delete from  ART46.DB_VERSIE
where DB_VERSIE = '4.10';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;
ALTER TABLE ART46.OFFERTE ADD OFFERTE_ORGINEEL_ID INT;
CALL SYSPROC.ADMIN_CMD('REORG TABLE ART46.OFFERTE');


-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('4.10');

