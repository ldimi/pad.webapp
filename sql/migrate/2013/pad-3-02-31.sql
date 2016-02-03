
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

ALTER TABLE ART46.SCHULDVORDERING    
    DROP CONSTRAINT FK_BE_TYPE
;


delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.31';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

ALTER TABLE ART46.SCHULDVORDERING    
    ADD CONSTRAINT FK_SV_BRIEF FOREIGN KEY (brief_id)
    REFERENCES ART46.BRIEF(BRIEF_ID);


-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.31');
