--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

DROP TABLE "ART46"."WEBLOKET_GEBRUIKER_OFFERTE";


delete from  art46.db_versie
where db_versie = '4.56';



--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

CREATE TABLE "ART46"."WEBLOKET_GEBRUIKER_OFFERTE"
(
   GEBRUIKER_ID int NOT NULL,
   OFFERTE_ID int NOT NULL
)
;

ALTER TABLE "ART46"."WEBLOKET_GEBRUIKER_OFFERTE"
ADD CONSTRAINT FK_4RUD0L4207JCHPCCAEC3Y0P3
FOREIGN KEY (OFFERTE_ID)
REFERENCES "ART46"."OFFERTE"(ID)
;

ALTER TABLE "ART46"."WEBLOKET_GEBRUIKER_OFFERTE"
ADD CONSTRAINT FK_6RDNRUFVDTWATUBWMGR9H8DDR
FOREIGN KEY (GEBRUIKER_ID)
REFERENCES "ART46"."WEBLOKET_GEBRUIKER"(ID)
;


-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('4.56');