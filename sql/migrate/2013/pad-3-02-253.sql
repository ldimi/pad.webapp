
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.25';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;
CREATE TABLE ART46.KOSTENPLAATS
(
  kostenplaats_B varchar(24) NOT NULL,
  kostenplaats_code varchar(24)
);
ALTER TABLE "ART46.kostenplaats" ALTER COLUMN kostenplaats_code drop not null;
CALL SYSPROC.ADMIN_CMD('REORG TABLE "ART46.kostenplaats"');
ALTER TABLE "ART46.kostenplaats"
ADD CONSTRAINT unique_kostenplaats_code UNIQUE (kostenplaats_code);
CALL SYSPROC.ADMIN_CMD('REORG TABLE "ART46.kostenplaats"');


CREATE TABLE ART46.AANVRAAGVASTLEGGING
(
  id int not null,
  bestekid int NOT NULL,
  aanvraagid int,
  planningsItem int,
  budgetairartikel int,
  kostenplaats VARCHAR(24),
  inspectievanfinancien date,
  voogdijminister date,
  ministerVanBegroting date,
  vlaamseregering date,
  vast_bedrag DOUBLE,
  gunningsverslag int,
  gunningsbeslissing int,
  opdrachthouder VARCHAR(24),
  commentaarweigering VARCHAR(500),
  geweigerd VARCHAR(3),
  PRIMARY KEY (id)
);

CREATE TABLE ART46.AANVRAAGVASTLEGGINGSPREIDING (
  aanvraagid int NOT NULL,
  jaar int NOT NULL,
  bedrag int NOT NULL
);

ALTER TABLE ART46.bestek_sap_project bp ADD aanvraagid int;
CALL SYSPROC.ADMIN_CMD('REORG TABLE ART46.SCHULDVORDERING_SAP_PROJECT');

-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.25');
