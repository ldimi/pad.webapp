
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.38';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;


CREATE TABLE ART46.MEETSTAAT_EENHEID
(
  code VARCHAR(24),
  naam VARCHAR(24)
);

CREATE TABLE ART46.MEETSTAAT_EENHEID_MAPPING
(
  eenheidCode VARCHAR(24),
  naam VARCHAR(24)
);


INSERT INTO ART46.KOSTENPLAATS (KOSTENPLAATS_CODE,KOSTENPLAATS_B) VALUES ('0221000602', '22.100.0602 (ambtshalve bodemdossiers)');
INSERT INTO ART46.KOSTENPLAATS (KOSTENPLAATS_CODE,KOSTENPLAATS_B) VALUES ('0221000601', '22.100.0601 (veiligheid en voorzorg)');
INSERT INTO ART46.KOSTENPLAATS (KOSTENPLAATS_CODE,KOSTENPLAATS_B) VALUES ('0134000201', '13.400.0201 (ambtshalve verw. consumentenafval)');
INSERT INTO ART46.KOSTENPLAATS (KOSTENPLAATS_CODE,KOSTENPLAATS_B) VALUES ('0144000201', '14.400.0201 (ambtshalve verw. producentenafval)');
INSERT INTO ART46.KOSTENPLAATS (KOSTENPLAATS_CODE,KOSTENPLAATS_B) VALUES ('1234000', '12.340.00 (werkingsk. saneringen en attesteringen)');

-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.38');
