
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

ALTER TABLE ART46.AANVRAAGVASTLEGGING   
    drop CONSTRAINT FK_AV_GUNNINGSVERSLAG,
    drop CONSTRAINT FK_AV_GUNNINGSBESLISSING
;

ALTER TABLE ART46.AANVRAAGVASTLEGGINGBRIEVEN
    drop CONSTRAINT FK_AVB_BRIEF,
;



delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.50';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;






ALTER TABLE ART46.AANVRAAGVASTLEGGING   
    ADD CONSTRAINT FK_AV_GUNNINGSVERSLAG FOREIGN KEY (GUNNINGSVERSLAG)
    REFERENCES ART46.BRIEF(BRIEF_ID)
;

ALTER TABLE ART46.AANVRAAGVASTLEGGING   
    ADD CONSTRAINT FK_AV_GUNNINGSBESLISSING FOREIGN KEY (GUNNINGSBESLISSING)
    REFERENCES ART46.BRIEF(BRIEF_ID)
;


ALTER TABLE ART46.AANVRAAGVASTLEGGINGBRIEVEN   
    ADD CONSTRAINT FK_AVB_BRIEF FOREIGN KEY (BRIEFID)
    REFERENCES ART46.BRIEF(BRIEF_ID)
;



-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.50');
