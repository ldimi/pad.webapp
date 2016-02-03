--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

ALTER TABLE ART46.MEETSTAAT_SCHULDVORDERING
    DROP CONSTRAINT FK_MS_DEELOPDRACHT
;


delete from  art46.db_versie
where db_versie = '4.59';



--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

ALTER TABLE ART46.MEETSTAAT_SCHULDVORDERING
    ADD CONSTRAINT FK_MS_DEELOPDRACHT FOREIGN KEY (deelopdracht_id)
    REFERENCES ART46.DEELOPDRACHT(deelopdracht_id)
;

-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('4.59');