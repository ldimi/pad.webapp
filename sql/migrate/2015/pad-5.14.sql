--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

drop view art46.v_ander_dossier_bestek_saldos;

delete from  art46.db_versie
where db_versie = '5.14';

--------------------------------------------------------------------;
-- UP
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
from (
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
            (select sum(coalesce(spv.bedrag,0))
                from art46.bestek_sap_project absp
                      left outer join sap.project_vastlegging spv
                      on absp.project_id = spv.project_id
                      and spv.volgnummer = 0
                where absp.bestek_id = be.bestek_id)
            as initiele_vastlegging,
            (select sum(coalesce(spv.bedrag,0))
                from art46.bestek_sap_project absp
                       left outer join sap.project_vastlegging spv
                       on absp.project_id = spv.project_id
                where absp.bestek_id = be.bestek_id
            )
            as vastlegging,
            (select sum(coalesce(bedrag, 0))
                from art46.deelopdracht absp
                where bestek_id = be.bestek_id
            )
            as geraamd,
            (select sum(coalesce(spv.bedrag, 0))
                from art46.bestek_sap_project absp
                        left outer join sap.project_factuur spv
                        on (absp.project_id = spv.project_id)
                where absp.bestek_id = be.bestek_id
            )
            as gefactureerd,
            (select sum(coalesce(pl.ig_bedrag, 0))
                from art46.v_planning_lijn pl
                             left join art46.v_dossier d
                             on pl.dossier_id = d.id
                         left join art46.deelopdracht do
                         on pl.lijn_id = do.planning_lijn_id
                where pl.bestek_id = be.bestek_id
                         and pl.contract_id is not null  -- alleen planningslijnen van deelopdrachten
                         and do.deelopdracht_id is null -- niet gekoppeld aan deelopdracht
            )
            as openstaand_gepland
        from art46.bestek be
                inner join art46.v_dossier dos
                on be.dossier_id = dos.id
        where
            be.dossier_type = 'X'
    ) as T
;


select 'drop view ' || rtrim(v.VIEWSCHEMA) || '.' || rtrim(v.VIEWNAME) || ';' ||
       replace(cast(v.text as varchar(32000)), chr(10) , ' ') || ';'
from syscat.VIEWS v
where v.viewschema = 'ART46'
     and v.valid = 'N'
;




-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('5.14');



