--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  ART46.DB_VERSIE
where DB_VERSIE = '4.35';


--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

alter table art46.AANVRAAGVASTLEGGING add column OVEREENKOMST integer;
CALL SYSPROC.ADMIN_CMD('REORG TABLE ART46.AANVRAAGVASTLEGGING');

-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('4.35');




























