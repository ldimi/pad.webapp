
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;



delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.30';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

alter table art46.DEELOPDRACHT
    drop column DOSS_HDR_OK_S
    drop column DNST_HFD_OK_S
    drop column AFD_HFD_OK_S    
;

call sysproc.admin_cmd('reorg table ART46.DEELOPDRACHT')
;

-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.30');
