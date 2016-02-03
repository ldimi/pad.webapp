--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

ALTER TABLE ART46.MEETSTAAT_SCHULDVORDERING DROP GECORRIGEERD_HERZIENING_BEDRAG;
CALL SYSPROC.ADMIN_CMD('REORG TABLE ART46.MEETSTAAT_SCHULDVORDERING');

ALTER TABLE ART46.SCHULDVORDERING ADD GECORRIGEERD_HERZIENING_BEDRAG FLOAT (53);
CALL SYSPROC.ADMIN_CMD('REORG TABLE ART46.SCHULDVORDERING');

delete from  ART46.DB_VERSIE
where DB_VERSIE = '4.20';



--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

ALTER TABLE ART46.SCHULDVORDERING DROP GECORRIGEERD_HERZIENING_BEDRAG;
CALL SYSPROC.ADMIN_CMD('REORG TABLE ART46.SCHULDVORDERING');

ALTER TABLE ART46.MEETSTAAT_SCHULDVORDERING ADD GECORRIGEERD_HERZIENING_BEDRAG FLOAT (53);
CALL SYSPROC.ADMIN_CMD('REORG TABLE ART46.MEETSTAAT_SCHULDVORDERING');

-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('4.20');



