
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

DROP TABLE ART46.MEETSTAATREGEL
;

delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.28';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;


CREATE TABLE ART46.MEETSTAATREGEL
(
  id int not null,
  bestek_id int NOT NULL,
  postnr VARCHAR(24),
  taak VARCHAR(1024),
  details VARCHAR(1024),
  type VARCHAR(24),
  eenheid VARCHAR(24),
  aantal DOUBLE,
  eenheidsprijs DOUBLE,
  regel_totaal DOUBLE,
  PRIMARY KEY (id)
);

ALTER TABLE ART46.MEETSTAATREGEL    
    ADD CONSTRAINT FK_MR_BESTEK FOREIGN KEY (bestek_id)
    REFERENCES ART46.BESTEK(bestek_id);



-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.28');
