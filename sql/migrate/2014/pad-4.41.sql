--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

alter TABLE art46.DEELOPDRACHT_HIST
    drop column offerte_id
    drop column voorstel_deelopdracht_id
;

CALL SYSPROC.ADMIN_CMD('REORG TABLE art46.DEELOPDRACHT_HIST');

DROP TRIGGER ART46.AU_DEELOPDRACHT_HIST;

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

delete from  ART46.DB_VERSIE
where DB_VERSIE = '4.41';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;


alter TABLE art46.DEELOPDRACHT_HIST
    add column offerte_id integer,
    add column voorstel_deelopdracht_id integer
;

CALL SYSPROC.ADMIN_CMD('REORG TABLE art46.DEELOPDRACHT_HIST');

DROP TRIGGER ART46.AU_DEELOPDRACHT_HIST;

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
    PLANNING_LIJN_ID,
    offerte_id,
    voorstel_deelopdracht_id
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
    pre.PLANNING_LIJN_ID,
    pre.offerte_id,
    pre.voorstel_deelopdracht_id
)
;


-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('4.41');





























