
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


DROP TRIGGER ART46.BI_PLANNING_ACTIE_TS
;


DROP TRIGGER ART46.BU_PLANNING_ACTIE_TS
;

delete from art46.PLANNING_ACTIE;

delete from  ART46.DB_VERSIE 
where DB_VERSIE = '3.02.44';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

CREATE TRIGGER ART46.BI_PLANNING_ACTIE_TS
NO CASCADE BEFORE INSERT ON ART46.PLANNING_ACTIE
REFERENCING
    NEW AS post
FOR EACH ROW MODE DB2SQL
SET
        post.creatie_ts = CURRENT TIMESTAMP,
        post.wijzig_ts = CURRENT TIMESTAMP
;


CREATE TRIGGER ART46.BU_PLANNING_ACTIE_TS
NO CASCADE BEFORE UPDATE ON ART46.PLANNING_ACTIE
REFERENCING
    OLD AS pre
    NEW AS post
FOR EACH ROW MODE DB2SQL
SET
        post.wijzig_ts = CURRENT TIMESTAMP
;



INSERT INTO "ART46"."PLANNING_ACTIE" (ACTIE_CODE,ACTIE_CODE_B) VALUES ('GGO','Gegroep. Opdracht');
INSERT INTO "ART46"."PLANNING_ACTIE" (ACTIE_CODE,ACTIE_CODE_B) VALUES ('H_B','Herhaling Bestek');
INSERT INTO "ART46"."PLANNING_ACTIE" (ACTIE_CODE,ACTIE_CODE_B) VALUES ('N_B','Nieuw Bestek');
INSERT INTO "ART46"."PLANNING_ACTIE" (ACTIE_CODE,ACTIE_CODE_B) VALUES ('RC','Raamcontract');

	
-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.44');
