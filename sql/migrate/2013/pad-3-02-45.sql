
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

delete from art46.PLANNING_LIJN_VERSIE;
delete from art46.PLANNING_LIJN;
delete from art46.PLANNING_VERSIE;
delete from art46.PLANNING;

delete from  ART46.DB_VERSIE 
where DB_VERSIE = '3.02.45';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

-- Migratie oude planning (vanaf 2014) naar nieuwe planning

create view art46.V_TEMP_PLANNING_MIGR (
      DOSS_HDR_ID,
      dossier_id,
      dossier_type,
      FASE_CODE,
      actie_code,
      igb_d,
      ig_bedrag,
      COMMENTAAR
)
as (
select d.DOSS_HDR_ID,
      dr.dossier_id,
      dr.dossier_type,
      pf.FASE_CODE,
      'N_B' as actie_code,
      date(trim(CHAR(dr.jaartal)) || '-' || trim(CHAR(dr.maand)) || '-01') as igb_d,
      dr.raming as ig_bedrag,
      dr.COMMENTAAR
from art46.DOSSIER_RAMING dr
        inner join art46.RAMING_FASE rf
        on dr.DOSSIER_TYPE = rf.DOSSIER_TYPE
        and dr.FASE_ID = rf.FASE_ID
    left join art46.PLANNING_FASE pf 
    on rf.DOSSIER_TYPE = pf.DOSSIER_TYPE
    and rf.FASE_B = pf.FASE_CODE
        inner join art46.dossier d
        on dr.DOSSIER_ID = d.id
        and dr.DOSSIER_TYPE  = d.DOSSIER_TYPE
    --left join art46.PLANNING_LIJN_VERSIE plv
    left join art46.V_PLANNING_LIJN plv
    on d.DOSS_HDR_ID = plv.DOSS_HDR_ID
    and d.ID = plv.DOSSIER_ID
    and pf.fase_code = plv.fase_code
where dr.JAARTAL > 2013
    and d.DOSSIER_B is not null
    and plv.LIJN_ID is null     
)
;



INSERT INTO "ART46"."PLANNING" (DOSS_HDR_ID, WIJZIG_USER)
( 
    select distinct vpm.DOSS_HDR_ID, 'dvdveken'
    from art46.V_TEMP_PLANNING_MIGR vpm
    where vpm.DOSS_HDR_ID not in (select distinct doss_hdr_id from art46.planning)
        and vpm.DOSS_HDR_ID is not null
)
;


INSERT INTO "ART46"."PLANNING_VERSIE" (DOSS_HDR_ID, PLANNING_VERSIE, WIJZIG_USER)
( 
    select distinct vpm.DOSS_HDR_ID, 1, 'dvdveken'
    from art46.V_TEMP_PLANNING_MIGR vpm
    where vpm.DOSS_HDR_ID not in (select distinct doss_hdr_id from art46.planning_versie)   
        and vpm.DOSS_HDR_ID is not null
)
;


ALTER TABLE ART46.PLANNING_LIJN_VERSIE
   DROP FOREIGN KEY FK_PLLV_PLANNING_LIJN
;


INSERT INTO "ART46"."PLANNING_LIJN_VERSIE" (
    LIJN_ID,
    PLANNING_VERSIE,
    DOSS_HDR_ID,
    DOSSIER_ID,
    DOSSIER_TYPE,
    FASE_CODE,
    ACTIE_CODE,
    IGB_D,
    IG_BEDRAG,
    COMMENTAAR)
(
    select (ml.id +ROW_NUMBER() OVER()) as lijn_id,
        1 as planning_versie,
         vpm.DOSS_HDR_ID,
         vpm.DOSSIER_ID,
         vpm.DOSSIER_TYPE,
         vpm.FASE_CODE,
         'N_B' as ACTIE_CODE,
         vpm.IGB_D,
         vpm.IG_BEDRAG,
         vpm.COMMENTAAR
    from art46.V_TEMP_PLANNING_MIGR vpm,
        (select coalesce(max(lijn_id), 0) as id from art46.PLANNING_LIJN) as ml              
    where vpm.DOSS_HDR_ID is not null              
)
;

insert into art46.PLANNING_LIJN (lijn_id, doss_hdr_id)
( 
  select distinct plv.lijn_id, plv.doss_hdr_id
  from art46.planning_lijn_versie plv
  where not exists (select lijn_id from art46.PLANNING_LIJN pl
                    where pl.lijn_id = plv.lijn_id
                    and pl.doss_hdr_id = plv.doss_hdr_id
                   )
)



ALTER TABLE ART46.PLANNING_LIJN_VERSIE
   ADD FOREIGN KEY FK_PLLV_PLANNING_LIJN(DOSS_HDR_ID, LIJN_ID)
           REFERENCES ART46.PLANNING_LIJN (DOSS_HDR_ID, LIJN_ID) ON DELETE RESTRICT
;


drop view art46.V_TEMP_PLANNING_MIGR;

	
-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.45');
