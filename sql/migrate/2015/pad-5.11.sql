--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  art46.db_versie
where db_versie = '5.11';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

CREATE TABLE ART46.WEBLOKET_GEBRUIKER_DOSSIER
(
  GEBRUIKER_ID INT NOT NULL,
  DOSSIER_ID INT NOT NULL,
  PRIMARY KEY (GEBRUIKER_ID, DOSSIER_ID),
  FOREIGN KEY (DOSSIER_ID) REFERENCES ART46.DOSSIER (ID),
  FOREIGN KEY (GEBRUIKER_ID) REFERENCES ART46.WEBLOKET_GEBRUIKER (ID)
);





-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('5.11');



