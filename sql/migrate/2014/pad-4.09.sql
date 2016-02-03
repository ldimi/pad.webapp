--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

delete from  ART46.DB_VERSIE
where DB_VERSIE = '4.09';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

ALTER TABLE ART46.OFFERTE_REGEL ADD extra_regel_taak varchar(256);
CALL SYSPROC.ADMIN_CMD('REORG TABLE ART46.OFFERTE_REGEL');

ALTER TABLE ART46.OFFERTE_REGEL ADD extra_regel_eenheid varchar(25);
CALL SYSPROC.ADMIN_CMD('REORG TABLE ART46.OFFERTE_REGEL');

ALTER TABLE ART46.OFFERTE_REGEL ADD extra_regel_type varchar(25);
CALL SYSPROC.ADMIN_CMD('REORG TABLE ART46.OFFERTE_REGEL');

ALTER TABLE ART46.OFFERTE_REGEL ADD extra_regel_details varchar(256);
CALL SYSPROC.ADMIN_CMD('REORG TABLE ART46.OFFERTE_REGEL');

ALTER TABLE ART46.OFFERTE_REGEL ADD EXTRA_REGEL_POSTNR varchar(25);
CALL SYSPROC.ADMIN_CMD('REORG TABLE ART46.OFFERTE_REGEL');

-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('4.09');
