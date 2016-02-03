--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

ALTER TABLE ART46.AANVRAAGVASTLEGGING
    DROP CONSTRAINT FK_AV_OVEREENKOMST FOREIGN KEY (OVEREENKOMST)
    REFERENCES ART46.BRIEF(BRIEF_ID)
;


delete from  art46.db_versie
where db_versie = '4.51';



--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

ALTER TABLE ART46.AANVRAAGVASTLEGGING
    ADD CONSTRAINT FK_AV_OVEREENKOMST FOREIGN KEY (OVEREENKOMST)
    REFERENCES ART46.BRIEF(BRIEF_ID)
;



-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('4.51');