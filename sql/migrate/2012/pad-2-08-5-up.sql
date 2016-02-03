
select *
from art46.BRIEF br
where  
	ltst_wzg_d is null
;


update art46.BRIEF
set ltst_wzg_d = current timestamp
where ltst_wzg_d is null
;


CREATE TRIGGER ART46.BI_BRIEF_LTST_WZG_D  
NO CASCADE BEFORE INSERT ON ART46.BRIEF
REFERENCING
    NEW AS post
FOR EACH ROW MODE DB2SQL
SET
        post.LTST_WZG_D = CURRENT TIMESTAMP
;


CREATE TRIGGER ART46.BU_BRIEF_LTST_WZG_D  
NO CASCADE BEFORE UPDATE ON ART46.BRIEF
REFERENCING
    OLD AS pre
    NEW AS post
FOR EACH ROW MODE DB2SQL
SET
        post.LTST_WZG_D = CURRENT TIMESTAMP
;



-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('2.08.5');
