

--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  art46.db_versie
where db_versie = '6.20';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

alter table art46.dossier
    drop constraint cc_dossier_raming
;

DROP TRIGGER ART46.bu_dossier_ts;

update art46.dossier
set bsp_jn = 'N'
where bsp_jn is null
;


CREATE TRIGGER ART46.bu_dossier_ts NO CASCADE BEFORE
UPDATE ON art46.dossier REFERENCING OLD AS pre NEW AS post FOR EACH ROW MODE DB2SQL SET post.wijzig_ts = CURRENT TIMESTAMP
;

alter table art46.dossier
    add constraint cc_dossier_raming check
        ( dossier_type = 'B' OR
          (bbo_prijs is null and bbo_looptijd is null and
           bsp_jn = 'N' and
           bsp_prijs is null and bsp_looptijd is null and
           bsw_prijs is null and bsw_looptijd is null
          )
        )
;



alter table art46.dossier alter column bsp_jn
    set default 'N'
;

alter table art46.dossier alter column bsp_jn
    set not null
;


call sysproc.admin_cmd('reorg table art46.dossier')
;


-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('6.20');
