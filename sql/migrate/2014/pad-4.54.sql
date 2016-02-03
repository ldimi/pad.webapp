--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  art46.db_versie
where db_versie = '4.54';



--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

drop trigger ART46.BU_BRIEF_LTST_WZG_D;

update ART46.BRIEF
set uit_d = inschrijf_d
where 1 = 1
    and br.UIT_AARD_ID is not null
    and br.UIT_d is null
;

CREATE TRIGGER ART46.BU_BRIEF_LTST_WZG_D NO CASCADE BEFORE
UPDATE ON ART46.BRIEF REFERENCING OLD AS pre NEW AS post FOR EACH ROW MODE DB2SQL SET post.LTST_WZG_D = CURRENT TIMESTAMP
;


call sysproc.admin_cmd('reorg table art46.brief');


create trigger art46.bi_brief_uit_d
no cascade
before insert on art46.brief
referencing new as n
for each row mode db2sql
     when (n.uit_aard_id is not null and  n.uit_d is null) set n.uit_d = current date
;


-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('4.54');