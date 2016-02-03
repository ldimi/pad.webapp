--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

delete from  ART46.DB_VERSIE
where DB_VERSIE = '4.42';



--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

alter table art46.BRIEF drop column inkomende_qrcode;
CALL SYSPROC.ADMIN_CMD('REORG TABLE art46.BRIEF');

alter table art46.BRIEF add column inkomende_qrcode INTEGER ;
CALL SYSPROC.ADMIN_CMD('REORG TABLE art46.BRIEF');

-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('4.42');