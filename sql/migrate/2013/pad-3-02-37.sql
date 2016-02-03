
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.37';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;


CREATE TABLE ART46.OFFERTE
(
  id INT NOT NULL,
  bestek_id int NOT NULL,
  opdrachtgever VARCHAR(24),
  status  VARCHAR(24),
  inzender VARCHAR(120),
  offerte_brief_id int NOT NULL,
  btw_tarief int NOT NULL,
  totaal DOUBLE,
  totaal_incl_btw DOUBLE,
  PRIMARY KEY (id)
);

CREATE TABLE ART46.OFFERTE_REGEL
(
  id INT NOT NULL,
  offerte_id int ,
  meetstaat_regel_id int ,
  aantal DOUBLE,
  eenheidsprijs DOUBLE,
  regelTotaal DOUBLE,
  btw_tarief int NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE ART46.MEETSTAATREGEL
	ADD COLUMN template_id INTEGER;
	
call sysproc.admin_cmd('reorg table ART46.MEETSTAATREGEL')
;

ALTER TABLE ART46.MEETSTAATREGEL
	ALTER COLUMN bestek_id DROP NOT NULL;
	
call sysproc.admin_cmd('reorg table ART46.MEETSTAATREGEL')
;

ALTER TABLE ART46.KOSTENPLAATS
	ALTER COLUMN KOSTENPLAATS_B drop not null
	ALTER COLUMN KOSTENPLAATS_B SET DATA TYPE VARCHAR(150)
;

call sysproc.admin_cmd('reorg table ART46.KOSTENPLAATS')
;

CREATE TABLE ART46.MEETSTAAT_TEMPLATE
(
  id INT NOT NULL,
  naam VARCHAR(24),
  PRIMARY KEY (id)
);

-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.37');
