--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

ALTER TABLE ART46.SCHULDVORDERING ALTER VORDERING_NR SET DATA TYPE VARCHAR(10);
ALTER TABLE ART46.SCHULDVORDERING DROP GECORRIGEERD_HERZIENING_BEDRAG
CALL SYSPROC.ADMIN_CMD('REORG TABLE ART46.SCHULDVORDERING');


delete from  ART46.DB_VERSIE
where DB_VERSIE = '4.19';



--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

ALTER TABLE ART46.SCHULDVORDERING ALTER VORDERING_NR SET DATA TYPE VARCHAR(25);
ALTER TABLE ART46.SCHULDVORDERING ADD GECORRIGEERD_HERZIENING_BEDRAG double;
CALL SYSPROC.ADMIN_CMD('REORG TABLE ART46.SCHULDVORDERING');

-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('4.19');



