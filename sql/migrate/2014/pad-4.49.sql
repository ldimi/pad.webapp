--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

drop trigger art46.bu_brief_contact_id;


delete from  art46.db_versie
where db_versie = '4.49';



--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

create trigger art46.bu_brief_contact_id
before update on art46.brief
referencing old as o new as n
for each row
when (n.contact_id = 0)
    set n.contact_id = null
;


drop trigger ART46.BU_BRIEF_LTST_WZG_D;

update ART46.BRIEF
set contact_id = null
where contact_id = 0
;

CREATE TRIGGER ART46.BU_BRIEF_LTST_WZG_D NO CASCADE BEFORE
UPDATE ON ART46.BRIEF REFERENCING OLD AS pre NEW AS post FOR EACH ROW MODE DB2SQL SET post.LTST_WZG_D = CURRENT TIMESTAMP
;


-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('4.49');