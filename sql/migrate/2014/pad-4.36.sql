--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

delete from  ART46.DB_VERSIE
where DB_VERSIE = '4.36';



--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

alter table art46.BRIEF add column inkomende_qrcode VARCHAR(15) ;
CALL SYSPROC.ADMIN_CMD('REORG TABLE art46.BRIEF');

-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('4.36');




























