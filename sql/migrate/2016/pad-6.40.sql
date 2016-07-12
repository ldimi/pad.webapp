

--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

create view art46.v_ander_dossier_bestek_saldos as
select
bestek_id,
bestek_nr,
dossier_id,
afsluit_d,
dossier_nr,
dossier_b_l,
dossier_type,
programma_code,
doss_hdr_id,
raamcontract_jn,
dossier_afsluit_d,
ART46.ROUND_TO_DEC_31_2(initiele_vastlegging) as initiele_vastlegging,
ART46.ROUND_TO_DEC_31_2(vastlegging) as vastlegging,
ART46.ROUND_TO_DEC_31_2(geraamd) as geraamd,
ART46.ROUND_TO_DEC_31_2(gefactureerd) as gefactureerd,
ART46.ROUND_TO_DEC_31_2(openstaand_gepland) as openstaand_gepland
from
(
   select
   be.bestek_id,
   be.bestek_nr,
   be.dossier_id,
   be.afsluit_d,
   dos.dossier_nr,
   dos.dossier_b_l,
   dos.dossier_type,
   dos.programma_code,
   dos.doss_hdr_id,
   dos.raamcontract_jn,
   dos.afsluit_d as dossier_afsluit_d,
   (select sum(coalesce(spv.bedrag,0)) from art46.bestek_sap_project absp left outer join sap.project_vastlegging spv on absp.project_id = spv.project_id and spv.volgnummer = 0 where absp.bestek_id = be.bestek_id) as initiele_vastlegging,
   (select sum(coalesce(spv.bedrag,0)) from art46.bestek_sap_project absp left outer join sap.project_vastlegging spv on absp.project_id = spv.project_id where absp.bestek_id = be.bestek_id ) as vastlegging,
   (select sum(coalesce(bedrag, 0)) from art46.deelopdracht absp where bestek_id = be.bestek_id ) as geraamd,
   (select sum(coalesce(spv.bedrag, 0)) from art46.bestek_sap_project absp left outer join sap.project_factuur spv on (absp.project_id = spv.project_id) where absp.bestek_id = be.bestek_id ) as gefactureerd,
   (
      select
      sum(coalesce(pl.ig_bedrag, 0))
      from art46.v_planning_lijn pl
      left join art46.v_dossier d on pl.dossier_id = d.id
      left join art46.deelopdracht do on pl.lijn_id = do.planning_lijn_id
      where pl.bestek_id = be.bestek_id
      and pl.contract_id is not null
      and do.deelopdracht_id is null
   )
   as openstaand_gepland
   from art46.bestek be
   inner join art46.v_dossier dos on be.dossier_id = dos.id
   where be.dossier_type = 'X'
)
as T
;

delete from  art46.db_versie
where db_versie = '6.40';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

create view art46.v_bestek_vastlegging as
select absp .bestek_id,
       sum(case when spv.volgnummer = 0 then coalesce(spv.bedrag,0) else 0 end) as initiele_vastlegging,
       sum(coalesce(spv.bedrag,0)) as vastlegging
from art46.bestek_sap_project absp 
	    left outer join sap.project_vastlegging spv
	    on absp.project_id = spv.project_id
group by bestek_id



create view art46.v_bestek_geraamd as
select do.bestek_id,
       sum(coalesce(bedrag, 0)) as geraamd
from art46.deelopdracht do
group by do.bestek_id 



create view art46.v_bestek_gepland as
select pl.bestek_id,
       sum(coalesce(pl.ig_bedrag, 0)) as totaal_gepland,
       sum( case when do.deelopdracht_id is null then coalesce(pl.ig_bedrag, 0) else 0 end) as openstaand_gepland
from art46.v_planning_lijn pl
      left join art46.deelopdracht do on pl.lijn_id = do.planning_lijn_id
where pl.bestek_id is not null
group by pl.BESTEK_ID


create view art46.v_bestek_gefactureerd as
select absp.bestek_id,
       sum(coalesce(spv.bedrag, 0)) as gefactureerd
from art46.bestek_sap_project absp
        left outer join sap.project_factuur spv
        on absp.project_id = spv.project_id
group by absp.bestek_id 


create view art46.v_bestek_saldos as
select
   be.bestek_id,
   be_vl.initiele_vastlegging,
   be_vl.vastlegging,
   be_geraamd.geraamd,
   be_gefactureerd.gefactureerd,
   be_gepland.openstaand_gepland
from art46.bestek be
	 left join art46.V_BESTEK_VASTLEGGING be_vl
	 on be.bestek_id = be_vl.BESTEK_ID
	     left join art46.V_BESTEK_GERAAMD be_geraamd
	     on be.bestek_id = be_geraamd.bestek_id
	 left join art46.V_BESTEK_GEFACTUREERD be_gefactureerd
	 on be.bestek_id = be_gefactureerd.bestek_id
	     left join art46.V_BESTEK_GEPLAND be_gepland
	     on be.BESTEK_ID = be_gepland.bestek_id




drop view art46.v_ander_dossier_bestek_saldos;


-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie, omschrijving) values ('6.40', 'v_bestek_saldo');
