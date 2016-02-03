--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;



delete from  art46.db_versie
where db_versie = '5.08';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;



alter table art46.schuldvordering
    add column goedk_buiten_sap_jn char(1) not null default 'N'
;


call sysproc.admin_cmd('reorg table ART46.schuldvordering')
;

alter table art46.schuldvordering
   foreign key fk_sv_goedk_buiten_sap_jn(goedk_buiten_sap_jn)
           references art46.ja_nee_code (code) on delete restrict
;

-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('5.08');



