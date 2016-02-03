
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

alter table art46.AANVRAAGVASTLEGGINGSPREIDING
   alter column bedrag set data type int
;

call sysproc.admin_cmd('reorg table art46.AANVRAAGVASTLEGGINGSPREIDING')
;


delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.48';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

alter table art46.AANVRAAGVASTLEGGINGSPREIDING
   alter column bedrag set data type double
;

call sysproc.admin_cmd('reorg table art46.AANVRAAGVASTLEGGINGSPREIDING')
;


-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.48');
