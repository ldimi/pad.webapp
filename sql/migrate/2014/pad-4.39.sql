--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

alter table art46.VOORSTEL_DEELOPDRACHT
    alter column offerte_id set null
;
CALL SYSPROC.ADMIN_CMD('REORG TABLE art46.VOORSTEL_DEELOPDRACHT');

alter table art46.VOORSTEL_DEELOPDRACHT
    alter column dossier_id set null
;
CALL SYSPROC.ADMIN_CMD('REORG TABLE art46.VOORSTEL_DEELOPDRACHT');

alter table art46.VOORSTEL_DEELOPDRACHT
    alter column status set null
;
CALL SYSPROC.ADMIN_CMD('REORG TABLE art46.VOORSTEL_DEELOPDRACHT');

delete from  ART46.DB_VERSIE
where DB_VERSIE = '4.39';


--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

alter table art46.VOORSTEL_DEELOPDRACHT
    alter column offerte_id set not null
;
CALL SYSPROC.ADMIN_CMD('REORG TABLE art46.VOORSTEL_DEELOPDRACHT');


alter table art46.VOORSTEL_DEELOPDRACHT
    alter column dossier_id set not null
;
CALL SYSPROC.ADMIN_CMD('REORG TABLE art46.VOORSTEL_DEELOPDRACHT');

alter table art46.VOORSTEL_DEELOPDRACHT
    alter column status set not null
;
CALL SYSPROC.ADMIN_CMD('REORG TABLE art46.VOORSTEL_DEELOPDRACHT');

-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('4.39');




























