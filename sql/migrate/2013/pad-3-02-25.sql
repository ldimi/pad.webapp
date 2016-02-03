
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

DROP TABLE ART46.AANVRAAGVASTLEGGINGBRIEVEN;

DROP TABLE ART46.AANVRAAGVASTLEGGINGSPREIDING;

DROP TRIGGER ART46.BI_AV_PLANNING_LIJN_UNIEK;
DROP TRIGGER ART46.BU_AV_PLANNING_LIJN_UNIEK;

DROP TABLE ART46.AANVRAAGVASTLEGGING;

DROP TABLE ART46.KOSTENPLAATS

delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.25';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;
CREATE TABLE ART46.KOSTENPLAATS
(
  kostenplaats_code varchar(24) NOT NULL,
  kostenplaats_B varchar(24) NOT NULL,
  PRIMARY KEY (kostenplaats_code)
);


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


ALTER TABLE ART46.AANVRAAGVASTLEGGING    
    ADD CONSTRAINT FK_AV_BESTEK FOREIGN KEY (bestekid)
    REFERENCES ART46.BESTEK(bestek_id)
    ADD CONSTRAINT FK_AV_PLANNING_LIJN FOREIGN KEY (planningsItem)
    REFERENCES ART46.PLANNING_LIJN(lijn_id)
;


CREATE TRIGGER ART46.BU_AV_PLANNING_LIJN_UNIEK
BEFORE UPDATE ON ART46.AANVRAAGVASTLEGGING
REFERENCING NEW AS N OLD AS O
FOR EACH ROW
WHEN ((N.PLANNINGSITEM is not null AND (N.PLANNINGSITEM != O.PLANNINGSITEM OR O.PLANNINGSITEM is null) ) and
     (N.PLANNINGSITEM in (select distinct PLANNINGSITEM from art46.AANVRAAGVASTLEGGING)))
       SIGNAL SQLSTATE '75000' SET MESSAGE_TEXT='PLANNINGSITEM is al gekoppeld aan aanvraagVastlegging'
;

CREATE TRIGGER ART46.BI_AV_PLANNING_LIJN_UNIEK
BEFORE INSERT ON ART46.AANVRAAGVASTLEGGING
REFERENCING NEW AS N
FOR EACH ROW
WHEN (N.PLANNINGSITEM is not null and N.PLANNINGSITEM in (select distinct PLANNINGSITEM from art46.AANVRAAGVASTLEGGING))
       SIGNAL SQLSTATE '75000' SET MESSAGE_TEXT='PLANNINGSITEM is al gekoppeld aan aanvraagVastlegging'
;






CREATE TABLE ART46.AANVRAAGVASTLEGGINGSPREIDING (
  aanvraagid int NOT NULL,
  jaar int NOT NULL,
  bedrag int NOT NULL,
  PRIMARY KEY (aanvraagid, jaar),
  foreign key FK_AVS_AANVRAAGVASTLEGGING (aanvraagid)
         references ART46.AANVRAAGVASTLEGGING (id)
         on delete restrict
);

CREATE TABLE ART46.AANVRAAGVASTLEGGINGBRIEVEN (
  aanvraagid int NOT NULL,
  briefid int NOT NULL,
  PRIMARY KEY (aanvraagid, briefid),
  foreign key FK_AVB_AANVRAAGVASTLEGGING (aanvraagid)
         references ART46.AANVRAAGVASTLEGGING (id)
         on delete restrict,
  foreign key FK_AVB_BRIEF (briefid)
         references ART46.brief (brief_id)
         on delete restrict
);


-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.25');
