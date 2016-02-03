--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

ALTER TABLE ART46.BRIEF
    DROP CONSTRAINT FK_BR_PARENT
;


delete from  art46.db_versie
where db_versie = '4.55';



--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

ALTER TABLE ART46.BRIEF
    ADD CONSTRAINT FK_BR_PARENT FOREIGN KEY (parent_brief_id)
    REFERENCES ART46.BRIEF(BRIEF_ID)
;



-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('4.55');