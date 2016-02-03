
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

drop view art46.V_DOSSIER;
CREATE VIEW ART46.V_DOSSIER
(
   ID,
   DOSSIER_ID,
   DOSSIER_ID_BOA,
   DOSSIER_B,
   DOSSIER_TYPE,
   DOSSIER_FASE_ID,
   DOSS_HDR_ID,
   NIS_ID,
   AFSLUIT_D,
   COMMENTAAR,
   AANPAK_S,
   AANPAK_ONDERZOCHT_S,
   AANPAK_L,
   AANPAK_ONDERZOCHT_L,
   ART46_GESLOTEN_S,
   ART46_GESLOTEN_D,
   FINANCIELE_INFO,
   ONDERZOEK_ID,
   CONFORM_BBO_D,
   CONFORM_BSP_D,
   EINDVERKLARING_D,
   COMMENTAAR_BODEM,
   TYPE_ID_BODEM,
   SAP_PROJECT_NR,
   WBS_IVS_NR,
   WBS_MIGRATIE,
   CREATIE_TS,
   WIJZIG_TS,
   PROGRAMMA_CODE,
   NIS_ID_INT,
   POSTCODE,
   GEMEENTE_B,
   DOSSIER_B_L
)
AS
select
d.ID,
d.DOSSIER_ID,
d.DOSSIER_ID_BOA,
d.DOSSIER_B,
d.DOSSIER_TYPE,
d.DOSSIER_FASE_ID,
d.DOSS_HDR_ID,
d.NIS_ID,
d.AFSLUIT_D,
d.COMMENTAAR,
d.AANPAK_S,
d.AANPAK_ONDERZOCHT_S,
d.AANPAK_L,
d.AANPAK_ONDERZOCHT_L,
d.ART46_GESLOTEN_S,
d.ART46_GESLOTEN_D,
d.FINANCIELE_INFO,
d.ONDERZOEK_ID,
d.CONFORM_BBO_D,
d.CONFORM_BSP_D,
d.EINDVERKLARING_D,
d.COMMENTAAR_BODEM,
d.TYPE_ID_BODEM,
d.SAP_PROJECT_NR,
d.WBS_IVS_NR,
d.WBS_MIGRATIE,
d.CREATIE_TS,
d.WIJZIG_TS,
d.PROGRAMMA_CODE,
d.NIS_ID_INT,
g.postcode,
g.naam as gemeente_b,
LTRIM(coalesce(g.naam, '') || ' ' || coalesce(d.dossier_b, '')) as dossier_b_l
from art46.dossier d
left join SMEG_REF.CRAB_GEMEENTE g on d.NIS_ID_INT = g.NIS_ID
;


delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.15';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

drop view art46.V_DOSSIER;
CREATE VIEW ART46.V_DOSSIER
(
   ID,
   DOSSIER_ID,
   DOSSIER_ID_BOA,
   DOSSIER_B,
   DOSSIER_TYPE,
   DOSSIER_FASE_ID,
   DOSS_HDR_ID,
   NIS_ID,
   AFSLUIT_D,
   COMMENTAAR,
   AANPAK_S,
   AANPAK_ONDERZOCHT_S,
   AANPAK_L,
   AANPAK_ONDERZOCHT_L,
   ART46_GESLOTEN_S,
   ART46_GESLOTEN_D,
   FINANCIELE_INFO,
   ONDERZOEK_ID,
   CONFORM_BBO_D,
   CONFORM_BSP_D,
   EINDVERKLARING_D,
   COMMENTAAR_BODEM,
   TYPE_ID_BODEM,
   SAP_PROJECT_NR,
   WBS_IVS_NR,
   WBS_MIGRATIE,
   CREATIE_TS,
   WIJZIG_TS,
   PROGRAMMA_CODE,
   NIS_ID_INT,
   POSTCODE,
   GEMEENTE_B,
   DOSSIER_B_L,
   RAAMCONTRACT_JN   
)
AS
select
	d.ID,
	d.DOSSIER_ID,
	d.DOSSIER_ID_BOA,
	d.DOSSIER_B,
	d.DOSSIER_TYPE,
	d.DOSSIER_FASE_ID,
	d.DOSS_HDR_ID,
	d.NIS_ID,
	d.AFSLUIT_D,
	d.COMMENTAAR,
	d.AANPAK_S,
	d.AANPAK_ONDERZOCHT_S,
	d.AANPAK_L,
	d.AANPAK_ONDERZOCHT_L,
	d.ART46_GESLOTEN_S,
	d.ART46_GESLOTEN_D,
	d.FINANCIELE_INFO,
	d.ONDERZOEK_ID,
	d.CONFORM_BBO_D,
	d.CONFORM_BSP_D,
	d.EINDVERKLARING_D,
	d.COMMENTAAR_BODEM,
	d.TYPE_ID_BODEM,
	d.SAP_PROJECT_NR,
	d.WBS_IVS_NR,
	d.WBS_MIGRATIE,
	d.CREATIE_TS,
	d.WIJZIG_TS,
	d.PROGRAMMA_CODE,
	d.NIS_ID_INT,
	g.postcode,
	g.naam as gemeente_b,
	LTRIM(coalesce(g.naam, '') || ' ' || coalesce(d.dossier_b, '')) as dossier_b_l,
  	case
  		when d.DOSSIER_TYPE != 'X' then null
  		when d.dossier_fase_id = 2 then 'J'
  		else 'N'
    end raamcontract_jn
from art46.dossier d
left join SMEG_REF.CRAB_GEMEENTE g on d.NIS_ID_INT = g.NIS_ID
;


-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.15');
