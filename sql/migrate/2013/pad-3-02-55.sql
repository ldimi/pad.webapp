
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


drop VIEW ART46.V_SMEG_DOSSIER_ONDERZOEK;

CREATE VIEW ART46.V_SMEG_DOSSIER_ONDERZOEK AS
SELECT
    d.dossier_id AS DOSSIER_ID,
    o.id AS ONDERZOEK_ID
from SMEG.DOSSIER d
INNER JOIN SMEG.BUNDEL_BUNDEL bb ON d.id = bb.parent_bundel_id
inner join SMEG.ONDERZOEK o on bb.child_bundel_id = o.id
;


delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.55';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

drop VIEW ART46.V_SMEG_DOSSIER_ONDERZOEK;

CREATE VIEW ART46.V_SMEG_DOSSIER_ONDERZOEK AS
SELECT
    d.dossier_id AS DOSSIER_ID,
    o.id AS ONDERZOEK_ID,
    ka.uid AS DOSS_HDR_ID,
    op.straat || coalesce(' ' || op.huis_nr, '')
        || (case when (op.bus_nr is not null and op.bus_nr <> '')  then (' bus ' || op.bus_nr) else '' end)
        || ' ' || op.POSTCODE || ' ' || op.GEMEENTE
        AS ADRES
from SMEG.DOSSIER d
        INNER JOIN SMEG.BUNDEL_BUNDEL bb ON d.id = bb.parent_bundel_id
       INNER JOIN SMEG.BUNDEL b ON bb.child_bundel_id = b.id
        inner join SMEG.OPDRACHT op on b.id = op.id
        inner join SMEG.ONDERZOEK o on op.id = o.id
       INNER JOIN SMEG.KLANT_AMBTENAAR ka ON b.klant_ambtenaar_id = ka.id
;


-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.55');
