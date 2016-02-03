--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;
drop TRIGGER ART46.BU_DO_PLANNING_LIJN_UNIEK;

drop TRIGGER ART46.BI_DO_PLANNING_LIJN_UNIEK;


ALTER TABLE ART46.DEELOPDRACHT
    DROP goedkeuring_bedrag;

call sysproc.admin_cmd('reorg table ART46.DEELOPDRACHT')
;

DROP TABLE art46.DEELOPDRACHT_HIST;
  

delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.21';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

ALTER TABLE ART46.DEELOPDRACHT
    ADD goedkeuring_bedrag DOUBLE;

call sysproc.admin_cmd('reorg table ART46.DEELOPDRACHT')
;


update art46.DEELOPDRACHT do
set do.goedkeuring_bedrag = do.bedrag
where do.goedkeuring_D is not null
;


CREATE TRIGGER ART46.BU_DO_PLANNING_LIJN_UNIEK
BEFORE UPDATE ON ART46.DEELOPDRACHT
REFERENCING NEW AS N OLD AS O
FOR EACH ROW
WHEN ((N.PLANNING_LIJN_ID is not null AND (N.PLANNING_LIJN_ID != O.PLANNING_LIJN_ID OR O.PLANNING_LIJN_ID is null) ) and
     (N.PLANNING_LIJN_ID in (select distinct do.PLANNING_LIJN_ID from art46.DEELOPDRACHT as do)))
       SIGNAL SQLSTATE '75000' SET MESSAGE_TEXT='PLANNING_LIJN_ID is al gekoppeld aan deelopdracht'
;

CREATE TRIGGER ART46.BI_DO_PLANNING_LIJN_UNIEK
BEFORE INSERT ON ART46.DEELOPDRACHT
REFERENCING NEW AS N
FOR EACH ROW
WHEN (N.PLANNING_LIJN_ID is not null and N.PLANNING_LIJN_ID in (select distinct do.PLANNING_LIJN_ID from art46.DEELOPDRACHT as do))
       SIGNAL SQLSTATE '75000' SET MESSAGE_TEXT='PLANNING_LIJN_ID is al gekoppeld aan deelopdracht'
;


CREATE TABLE art46.DEELOPDRACHT_HIST
(
   DEELOPDRACHT_ID int NOT NULL,
   CREATIE_TS timestamp NOT NULL,
   BESTEK_ID int NOT NULL,
   DOSSIER_ID int NOT NULL,
   BEDRAG double,
   GOEDKEURING_D date,
   goedkeuring_bedrag DOUBLE,
   STOP_S char(1) DEFAULT '0',
   VOORSTEL_D date,
   AFSLUIT_D date,
   WBS_NR varchar(24),
   PLANNING_LIJN_ID int,
   primary key (DEELOPDRACHT_ID, CREATIE_TS),
   foreign key FK_DH_DEELOPDRACHT (DEELOPDRACHT_ID)
         references ART46.DEELOPDRACHT (DEELOPDRACHT_ID)
         on delete cascade
)
;


CREATE TRIGGER ART46.AU_DEELOPDRACHT_HIST  
AFTER UPDATE ON ART46.DEELOPDRACHT
REFERENCING
    OLD AS pre
    NEW AS post
FOR EACH ROW MODE DB2SQL
INSERT INTO ART46.DEELOPDRACHT_HIST (
    DEELOPDRACHT_ID,
    CREATIE_TS,
    BESTEK_ID,
    DOSSIER_ID,
    BEDRAG,
    GOEDKEURING_D,
    goedkeuring_bedrag,
    STOP_S,
    VOORSTEL_D,
    AFSLUIT_D,
    WBS_NR,
    PLANNING_LIJN_ID
    ) 
VALUES (
    pre.DEELOPDRACHT_ID,
    pre.WIJZIG_TS,
    pre.BESTEK_ID,
    pre.DOSSIER_ID,
    pre.BEDRAG,
    pre.GOEDKEURING_D,
    pre.goedkeuring_bedrag,
    pre.STOP_S,
    pre.VOORSTEL_D,
    pre.AFSLUIT_D,
    pre.WBS_NR,
    pre.PLANNING_LIJN_ID
)
;


-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.21');
