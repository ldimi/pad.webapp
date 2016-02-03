
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


ALTER TABLE ART46.DOSSIER    
    drop CONSTRAINT CC_BOA_ID
;


DROP TRIGGER ART46.BU_DOSSIER_ID_BOA_ONWIJZIGBAAR;

DROP TRIGGER ART46.BI_DOSSIER_ID_BOA_UNIEK;


delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.40';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;


ALTER TABLE ART46.DOSSIER    
    add CONSTRAINT CC_BOA_ID CHECK(
        (dossier_type = 'B' and (dossier_id_boa is not null and dossier_id_boa != 0) )
        OR
        (dossier_type != 'B' and (dossier_id_boa is null or dossier_id_boa = 0) )
    )
;


CREATE TRIGGER ART46.BU_DOSSIER_ID_BOA_ONWIJZIGBAAR
BEFORE UPDATE ON ART46.DOSSIER
REFERENCING NEW AS N OLD AS O
FOR EACH ROW
WHEN (N.DOSSIER_TYPE = 'B' AND N.DOSSIER_ID_BOA != O.DOSSIER_ID_BOA )
       SIGNAL SQLSTATE '75000' SET MESSAGE_TEXT='dossier_id_boa is niet wijzigbaar'
;

CREATE TRIGGER ART46.BI_DOSSIER_ID_BOA_UNIEK
BEFORE INSERT ON ART46.DOSSIER
REFERENCING NEW AS N
FOR EACH ROW
WHEN (N.DOSSIER_TYPE = 'B' and N.DOSSIER_ID_BOA in (select DOSSIER_ID_BOA from ART46.DOSSIER))
       SIGNAL SQLSTATE '75000' SET MESSAGE_TEXT='DOSSIER_ID_BOA is al gekoppeld aan een ART46.DOSSIER'
;


-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.40');
