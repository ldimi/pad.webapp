
CREATE TRIGGER art46_brief_id NO CASCADE BEFORE
INSERT ON ART46.BRIEF REFERENCING NEW AS n FOR EACH ROW MODE DB2SQL set n.brief_id =
(
   select
   max(brief_id) + 1
   from ART46.BRIEF
)
;

alter table art46.BRIEF alter column BRIEF_ID set generated by default as identity (
START WITH 73000);