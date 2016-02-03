--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

alter table art46.brief
   drop column check_afd_hfd_d
   drop column opmerking_afd_hfd
   drop column check_auteur_d
;

call sysproc.admin_cmd('reorg table art46.brief');


delete from  art46.db_versie
where db_versie = '4.48';



--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

alter table art46.brief
   add column check_afd_hfd_d date
   add column opmerking_afd_hfd varchar(500)
   add column check_auteur_d date
;

call sysproc.admin_cmd('reorg table art46.brief');

drop trigger ART46.BU_BRIEF_LTST_WZG_D;

update ART46.BRIEF
set check_afd_hfd_d = '1900-01-01',
    check_auteur_d  = '1900-01-01'
;

CREATE TRIGGER ART46.BU_BRIEF_LTST_WZG_D NO CASCADE BEFORE
UPDATE ON ART46.BRIEF REFERENCING OLD AS pre NEW AS post FOR EACH ROW MODE DB2SQL SET post.LTST_WZG_D = CURRENT TIMESTAMP
;



-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('4.48');