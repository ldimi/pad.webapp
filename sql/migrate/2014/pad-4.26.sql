--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

DROP TRIGGER SAP.BU_PROJECT_AANVRAAGID_UNIEK;
DROP TRIGGER SAP.BI_PROJECT_AANVRAAGID_UNIEK;

delete from  ART46.DB_VERSIE
where DB_VERSIE = '4.26';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;


CREATE TRIGGER SAP.BU_PROJECT_AANVRAAGID_UNIEK
BEFORE UPDATE ON SAP.PROJECT
REFERENCING NEW AS N OLD AS O
FOR EACH ROW
WHEN ((N.AANVRAAGID is not null AND (N.AANVRAAGID != O.AANVRAAGID OR O.AANVRAAGID is null) ) and
      (N.AANVRAAGID in (select distinct AANVRAAGID from SAP.PROJECT where actief_s = 'J')))
       SIGNAL SQLSTATE '75000' SET MESSAGE_TEXT='AANVRAAGID is al gekoppeld aan een SAP.PROJECT'
;

CREATE TRIGGER SAP.BI_PROJECT_AANVRAAGID_UNIEK
BEFORE INSERT ON SAP.PROJECT
REFERENCING NEW AS N
FOR EACH ROW
WHEN (N.AANVRAAGID is not null and N.AANVRAAGID in (select distinct AANVRAAGID from SAP.PROJECT where actief_s = 'J'))
       SIGNAL SQLSTATE '75000' SET MESSAGE_TEXT='AANVRAAGID is al gekoppeld aan een SAP.PROJECT'
;


-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('4.26');




























