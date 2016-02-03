--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

drop view art46.v_planning_lijn_details;

drop view art46.v_planning_lijn_details_versie;

delete from  art46.db_versie
where db_versie = '5.09';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

create view art46.v_planning_lijn_details_versie as
        select
            d.doss_hdr_id,
            pl.lijn_id,
            pl.planning_dossier_versie,
            pl.dossier_id,
            d.dossier_nr,
            d.gemeente_b as dossier_gemeente_b,
            d.dossier_b,
            pl.dossier_type,
            d.raamcontract_jn as dossier_is_raamcontract_jn,
            pl.fase_code,
            pf.heeft_details_jn as fase_code_heeft_details_jn,
            pl.fase_detail_code,
            pl.actie_code,
            pl.contract_id,
            pl.contract_type,
            case when pl.CONTRACT_ID is null then null
                else contr.dossier_b
            end as contract_b,
            contr.dossier_nr as contract_nr,
            contr.raamcontract_jn as contract_is_raamcontract_jn,
            pl.bestek_id,
            b.bestek_nr,
            b.omschrijving as bestek_omschrijving,
            pl.igb_d,
            pl.ig_bedrag,
            do.deelopdracht_id,
            av.id as aanvraagvastlegging_id,
            case when do.goedkeuring_d is not null then do.bedrag
                  when sp.project_id is not null then sp.totaal_vastgelegd
                  else null
            end as ib_bedrag,
            case when do.goedkeuring_d is not null then do.goedkeuring_d
                  when sp.project_id is not null then date(sp.creatie_ts)
                  else null
            end as ibb_d,
            sp.project_id as sap_project_id,
            case when do.bestek_id is not null then do.bestek_id
                  when av.bestekid is not null then av.bestekid
                  else null
            end as benut_bestek_id,
            case when do.bestek_id is not null then dob.bestek_nr
                  when av.bestekid is not null then avb.bestek_nr
                  else null
            end as benut_bestek_nr,
            case when do.bestek_id is not null then dob.omschrijving
                  when av.bestekid is not null then avb.omschrijving
                  else null
            end as benut_bestek_omschrijving,
            pl.commentaar,
            pl.deleted_jn,
            'R' as status_crud
        from art46.PLANNING_LIJN_VERSIE pl
                left join art46.V_PLANNING_FASE pf
                on pl.fase_code = pf.fase_code
                    inner join art46.v_dossier d
                    on pl.dossier_id = d.id
                left join art46.v_dossier contr
                on pl.contract_id = contr.id
                    left join art46.BESTEK b
                    on pl.BESTEK_ID = b.BESTEK_ID
                left join art46.deelopdracht do
                on pl.lijn_id = do.planning_lijn_id
                      left join art46.bestek dob
                      on do.bestek_id = dob.bestek_id
                left join art46.AANVRAAGVASTLEGGING av
                on pl.LIJN_ID = av.PLANNINGSITEM
                        left join (select p.project_id,
                                          p.aanvraagid,
                                          p.creatie_ts,
                                          sum(pv.bedrag) as totaal_vastgelegd
                                   from sap.project p
                                            inner join sap.project_vastlegging pv
                                            on p.PROJECT_ID = pv.PROJECT_ID
                                   where p.ACTIEF_S = 'J'
                                   group by p.project_id,
                                          p.aanvraagid,
                                          p.creatie_ts
                                   ) as sp
                        on av.AANVRAAGID = sp.AANVRAAGID
                left join art46.bestek avb
                on av.bestekid = avb.bestek_id
;

create view art46.v_planning_lijn_details as
select pl.*
from art46.v_planning_lijn_details_versie pl
where pl.DELETED_JN = 'N'
    and pl.PLANNING_DOSSIER_VERSIE = (select max(plv2.planning_dossier_versie)
                              from art46.PLANNING_LIJN_VERSIE plv2
                              where pl.LIJN_ID = plv2.LIJN_ID
                              )


-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('5.09');



