
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;




delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.57';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

// bugfix van SMEG.RS_DERDE_ROL_VIEW

CREATE VIEW ART46.RS_DERDE_ROL_VIEW AS
    SELECT
        k.id AS DERDE_ID, r.code AS ROL_ID
    FROM SMEG.KLANT_ROL kr
        INNER JOIN SMEG.KLANT_REGULIER k ON k.id = kr.klant_id
        INNER JOIN SMEG_REF.ROL r ON r.id = kr.rol_id       
UNION
    SELECT
        k.id AS DERDE_ID, r.code AS ROL_ID
    FROM SMEG.OPDRACHT_KLANT_ROL okr
        INNER JOIN SMEG.KLANT_REGULIER k ON k.id = okr.klant_id
        INNER JOIN SMEG_REF.ROL r ON r.id = okr.rol_id
;

-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.57');
