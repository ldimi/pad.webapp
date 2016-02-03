--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

drop trigger art46.bi_brief_inschrijf_d

alter table ART46.BRIEF
    alter column INSCHRIJF_D set null
;

call sysproc.admin_cmd('reorg table art46.brief');




delete from  art46.db_versie
where db_versie = '4.52';



--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

drop trigger ART46.BU_BRIEF_LTST_WZG_D;

update ART46.BRIEF
set inschrijf_d = coalesce(IN_STUK_D, in_d, uit_d, reactie_d, reactie_voor_d)
WHERE 1 = 1
    and INSCHRIJF_D is null
;

update ART46.BRIEF
set inschrijf_d = '1900-01-01'
WHERE 1 = 1
    and inschrijf_d is null
;


CREATE TRIGGER ART46.BU_BRIEF_LTST_WZG_D NO CASCADE BEFORE
UPDATE ON ART46.BRIEF REFERENCING OLD AS pre NEW AS post FOR EACH ROW MODE DB2SQL SET post.LTST_WZG_D = CURRENT TIMESTAMP
;


alter table ART46.BRIEF
    alter column INSCHRIJF_D set not null
;

call sysproc.admin_cmd('reorg table art46.brief');


create trigger art46.bi_brief_inschrijf_d
no cascade
before insert on art46.brief
referencing new as n
for each row mode db2sql
     when (n.inschrijf_d is null) set n.inschrijf_d = current date
;

create trigger art46.bu_brief_inschrijf_d
no cascade
before update on art46.brief
referencing new as n old as o
for each row mode db2sql
     when (n.inschrijf_d is null) set n.inschrijf_d = o.inschrijf_d
;

-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('4.52');