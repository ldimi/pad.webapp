

--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

CREATE VIEW JURIDISCH.V_BODEMDOSSIER
(
   ID,
   SAP_PROJECT_NR,
   WBS_IVS_NR,
   IVS_NUMMER,
   IVS_DOSSIERNAAM,
   IVS_DOSSIERHOUDER,
   BODEM_NUMMER,
   BODEM_DOSSIERNAAM,
   BODEM_DOSSIERHOUDER,
   DOSSIER_TYPE
)
AS
select
    d.ID,
    d.SAP_PROJECT_NR,
    d.WBS_IVS_NR,
    trim(d.DOSSIER_nr) as IVS_NUMMER,
    trim(d.dossier_b) as IVS_DOSSIERNAAM ,
    trim(ika.UID) as IVS_DOSSIERHOUDER,
    trim(char(smegd.dossier_id)) as BODEM_NUMMER,
    trim(smegd.naam) as BODEM_DOSSIERNAAM,
    trim(bka.uid) as BODEM_DOSSIERHOUDER,
    d.dossier_type
from ART46.DOSSIER d
        left JOIN SMEG.KLANT_AMBTENAAR ika on d.DOSS_HDR_ID = ika.UID
            left join SMEG.DOSSIER smegd on d.DOSSIER_ID_BOA = smegd.dossier_id
            and d.DOSSIER_ID_BOA <> 0
                left join
                (
                   select
                   v.dossier_id,
                   v.ambtenaar_id,
                   ROW_NUMBER() OVER(PARTITION BY v.dossier_id) as number
                   from art46.V_DOSSIERHOUDERS_BOA v
                   inner join art46.dossier d on v.dossier_id = d.DOSSIER_ID_BOA
                )
                as smegdh on smegdh.dossier_id = d.DOSSIER_ID_BOA
                and smegdh.number = 1
        left JOIN SMEG.KLANT_AMBTENAAR bka on smegdh.ambtenaar_id = bka.UID
;

delete from  art46.db_versie
where db_versie = '6.22';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

drop view JURIDISCH.V_BODEMDOSSIER;


-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('6.22');
