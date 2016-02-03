
alter table ART46.DOSSIER
	add column NIS_ID_INT INTEGER
;

update ART46.DOSSIER
set NIS_ID_INT = int(NIS_ID)
;


CREATE TRIGGER ART46.BI_DOSSIER_NIS_ID 
NO CASCADE BEFORE INSERT ON ART46.DOSSIER
REFERENCING
    NEW AS post
FOR EACH ROW MODE DB2SQL
SET
        post.NIS_ID_INT = int(post.NIS_ID)
;


CREATE TRIGGER ART46.BU_DOSSIER_NIS_ID   
NO CASCADE BEFORE UPDATE ON ART46.DOSSIER
REFERENCING
    OLD AS pre
    NEW AS post
FOR EACH ROW MODE DB2SQL
SET
        post.NIS_ID_INT = int(post.NIS_ID)
;

alter table ART46.DOSSIER
	add constraint cc_nis_id_int CHECK(NIS_ID_INT = int(NIS_ID))
;



-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('2.08.6');
