--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

CREATE TRIGGER art46_brief_id NO CASCADE BEFORE
INSERT ON ART46.BRIEF REFERENCING NEW AS n FOR EACH ROW MODE DB2SQL set n.brief_id =
(
   select
   max(brief_id) + 1
   from ART46.BRIEF
)
;

delete from  ART46.DB_VERSIE
where DB_VERSIE = '4.17';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;


drop TRIGGER smeg.art46_brief_id;

-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('4.17');
