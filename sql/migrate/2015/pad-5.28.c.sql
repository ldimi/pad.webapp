
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

delete from  art46.db_versie
where db_versie = '5.28.c';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;


alter table art46.dossier
    drop foreign key fk_dos_doelgroep
;



rename table art46.dossier_doelgroep_type to doelgroep_type;
call sysproc.admin_cmd('reorg table art46.doelgroep_type');

alter table art46.dossier
    add foreign key fk_dos_doelgroep (doelgroep_type_id)
         references art46.doelgroep_type (doelgroep_type_id)
         on delete restrict;




drop view art46.v_dossier;

create view art46.v_dossier as
select
    dos.id,
    dos.dossier_id,
    dos.dossier_id as dossier_nr,
    dos.dossier_id_boa,
    dos.dossier_b,
    dos.dossier_type,
    dos.dossier_fase_id,
    dos.doss_hdr_id,
    ambt.ambtenaar_b as doss_hdr_b,
    ambt.tel as doss_hdr_tel,
    ambt.email as doss_hdr_email,
    ambt.functie as doss_hdr_functie,
    dos.nis_id,
    dos.afsluit_d,
    dos.commentaar,
    dos.aanpak_s,
    dos.aanpak_onderzocht_s,
    dos.aanpak_l,
    dos.aanpak_onderzocht_l,
    dos.art46_gesloten_s,
    dos.art46_gesloten_d,
    dos.financiele_info,
    dos.onderzoek_id,
    dos.conform_bbo_d,
    dos.conform_bsp_d,
    dos.eindverklaring_d,
    dos.commentaar_bodem,
    dos.type_id_bodem,
    dos.sap_project_nr,
    dos.wbs_ivs_nr,
    dos.wbs_migratie,
    dos.creatie_ts,
    dos.wijzig_ts,
    dos.programma_code,
    dos.nis_id_int,
    dos.rechtsgrond_code,
    dos.doelgroep_type_id,
    doelgr.doelgroep_type_b,
    g.postcode,
    g.naam as gemeente_b,
    ltrim(coalesce(g.naam, '') || ' ' || coalesce(dos.dossier_b, '')) as dossier_b_l,
    case when dos.dossier_type != 'X' then null when dos.dossier_fase_id = 2 then 'J' else 'N' end raamcontract_jn
from art46.dossier dos
        left join smeg_ref.crab_gemeente g on dos.nis_id_int = g.nis_id
            left join art46.v_ambtenaar ambt on dos.DOSS_HDR_ID = ambt.ambtenaar_id
        left join art46.doelgroep_type doelgr on dos.doelgroep_type_id = doelgr.doelgroep_type_id
;

--select 'drop view ' || rtrim(v.VIEWSCHEMA) || '.' || rtrim(v.VIEWNAME) || ';' ||
--       replace(cast(v.text as varchar(32000)), chr(10) , ' ') || ';'
--from syscat.VIEWS v
--where v.viewschema = 'ART46'
--     and v.valid = 'N'
--;



drop view ART46.V_ANDER_DOSSIER_BESTEK_SALDOS;create view art46.v_ander_dossier_bestek_saldos as select     bestek_id,     bestek_nr,     dossier_id,     afsluit_d,     dossier_nr,     dossier_b_l,     dossier_type,     programma_code,     doss_hdr_id,     raamcontract_jn,     dossier_afsluit_d,     ART46.ROUND_TO_DEC_31_2(initiele_vastlegging) as initiele_vastlegging,     ART46.ROUND_TO_DEC_31_2(vastlegging) as vastlegging,     ART46.ROUND_TO_DEC_31_2(geraamd) as geraamd,     ART46.ROUND_TO_DEC_31_2(gefactureerd) as gefactureerd,     ART46.ROUND_TO_DEC_31_2(openstaand_gepland) as openstaand_gepland from (         select             be.bestek_id,             be.bestek_nr,             be.dossier_id,             be.afsluit_d,             dos.dossier_nr,             dos.dossier_b_l,             dos.dossier_type,             dos.programma_code,             dos.doss_hdr_id,             dos.raamcontract_jn,             dos.afsluit_d as dossier_afsluit_d,             (select sum(coalesce(spv.bedrag,0))                 from art46.bestek_sap_project absp                       left outer join sap.project_vastlegging spv                       on absp.project_id = spv.project_id                       and spv.volgnummer = 0                 where absp.bestek_id = be.bestek_id)             as initiele_vastlegging,             (select sum(coalesce(spv.bedrag,0))                 from art46.bestek_sap_project absp                        left outer join sap.project_vastlegging spv                        on absp.project_id = spv.project_id                 where absp.bestek_id = be.bestek_id             )             as vastlegging,             (select sum(coalesce(bedrag, 0))                 from art46.deelopdracht absp                 where bestek_id = be.bestek_id             )             as geraamd,             (select sum(coalesce(spv.bedrag, 0))                 from art46.bestek_sap_project absp                         left outer join sap.project_factuur spv                         on (absp.project_id = spv.project_id)                 where absp.bestek_id = be.bestek_id             )             as gefactureerd,             (select sum(coalesce(pl.ig_bedrag, 0))                 from art46.v_planning_lijn pl                              left join art46.v_dossier d                              on pl.dossier_id = d.id                          left join art46.deelopdracht do                          on pl.lijn_id = do.planning_lijn_id                 where pl.bestek_id = be.bestek_id                          and pl.contract_id is not null                           and do.deelopdracht_id is null             )             as openstaand_gepland         from art46.bestek be                 inner join art46.v_dossier dos                 on be.dossier_id = dos.id         where             be.dossier_type = 'X'     ) as T;
drop view ART46.V_PLANNING_LIJN_DETAILS_VERSIE;create view art46.v_planning_lijn_details_versie as         select             d.doss_hdr_id,             pl.lijn_id,             pl.planning_dossier_versie,             pl.dossier_id,             d.dossier_nr,             d.gemeente_b as dossier_gemeente_b,             d.dossier_b,             pl.dossier_type,             d.raamcontract_jn as dossier_is_raamcontract_jn,             pl.fase_code,             pf.heeft_details_jn as fase_code_heeft_details_jn,             pl.fase_detail_code,             pl.actie_code,             pl.contract_id,             pl.contract_type,             case when pl.CONTRACT_ID is null then null                 else contr.dossier_b             end as contract_b,             contr.dossier_nr as contract_nr,             contr.raamcontract_jn as contract_is_raamcontract_jn,             pl.bestek_id,             b.bestek_nr,             b.omschrijving as bestek_omschrijving,             pl.igb_d,             pl.ig_bedrag,             do.deelopdracht_id,             av.id as aanvraagvastlegging_id,             case when do.goedkeuring_d is not null then do.bedrag                   when sp.project_id is not null then sp.totaal_vastgelegd                   else null             end as ib_bedrag,             case when do.goedkeuring_d is not null then do.goedkeuring_d                   when sp.project_id is not null then date(sp.creatie_ts)                   else null             end as ibb_d,             sp.project_id as sap_project_id,             case when do.bestek_id is not null then do.bestek_id                   when av.bestekid is not null then av.bestekid                   else null             end as benut_bestek_id,             case when do.bestek_id is not null then dob.bestek_nr                   when av.bestekid is not null then avb.bestek_nr                   else null             end as benut_bestek_nr,             case when do.bestek_id is not null then dob.omschrijving                   when av.bestekid is not null then avb.omschrijving                   else null             end as benut_bestek_omschrijving,             pl.commentaar,             pl.deleted_jn,             'R' as status_crud         from art46.PLANNING_LIJN_VERSIE pl                 left join art46.V_PLANNING_FASE pf                 on pl.fase_code = pf.fase_code                     inner join art46.v_dossier d                     on pl.dossier_id = d.id                 left join art46.v_dossier contr                 on pl.contract_id = contr.id                     left join art46.BESTEK b                     on pl.BESTEK_ID = b.BESTEK_ID                 left join art46.deelopdracht do                 on pl.lijn_id = do.planning_lijn_id                       left join art46.bestek dob                       on do.bestek_id = dob.bestek_id                 left join art46.AANVRAAGVASTLEGGING av                 on pl.LIJN_ID = av.PLANNINGSITEM                         left join (select p.project_id,                                           p.aanvraagid,                                           p.creatie_ts,                                           sum(pv.bedrag) as totaal_vastgelegd                                    from sap.project p                                             inner join sap.project_vastlegging pv                                             on p.PROJECT_ID = pv.PROJECT_ID                                    where p.ACTIEF_S = 'J'                                    group by p.project_id,                                           p.aanvraagid,                                           p.creatie_ts                                    ) as sp                         on av.AANVRAAGID = sp.AANVRAAGID                 left join art46.bestek avb                 on av.bestekid = avb.bestek_id;
drop view ART46.V_PLANNING_LIJN_DETAILS;create view art46.v_planning_lijn_details as select pl.* from art46.v_planning_lijn_details_versie pl where pl.DELETED_JN = 'N'     and pl.PLANNING_DOSSIER_VERSIE = (select max(plv2.planning_dossier_versie)                               from art46.PLANNING_LIJN_VERSIE plv2                               where pl.LIJN_ID = plv2.LIJN_ID                               );
drop view ART46.V_BESTEK;create view art46.v_bestek as select     be.bestek_id,     be.bestek_nr,     be.dossier_id,     dos.dossier_nr,     be.dossier_type,     dos.dossier_b,     dos.dossier_b_l,     dos.dossier_id_boa,     dos.raamcontract_jn,     dos.doss_hdr_id,     be.type_id,     bt.type_b,     be.procedure_id,     bp.procedure_b,     be.fase_id,     bf.fase_b,     be.dienst_id,     be.omschrijving,     be.commentaar,     be.afsluit_d,     be.btw_tarief,     be.start_d,     be.stop_d,     be.verlenging_s,     be.wbs_nr,     be.screening_jn,     be.creatie_ts,     be.wijzig_ts,     be.meetstaat_pdf_brief_id,     be.meetstaat_excel_brief_id from art46.bestek be     inner join art46.v_dossier dos on be.dossier_id = dos.id     inner join art46.bestek_type bt on be.type_id = bt.type_id     inner join art46.bestek_procedure bp on be.procedure_id = bp.procedure_id     inner join art46.bestek_fase bf on be.fase_id = bf.fase_id;
drop view ART46.V_DEELOPDRACHT;create view art46.v_deelopdracht as select     do.deelopdracht_id,     art46.deelopdracht_nr(do.deelopdracht_id) as deelopdracht_nr,     do.dossier_id,     dos.dossier_nr,     dos.dossier_type,     dos.dossier_b,     dos.dossier_b_l,     dos.dossier_id_boa,     dos.raamcontract_jn,     dos.doss_hdr_id,     do.bestek_id,     be.bestek_nr,     be.dossier_id as ander_dossier_id,     be.dossier_type as ander_dossier_type,     be.dossier_id as ander_dossier_id_ivs,     be.doss_hdr_id as ander_doss_hdr_id,     be.raamcontract_jn as ander_raamcontract_jn,     do.bedrag,     do.goedkeuring_d,     do.stop_s,     do.voorstel_d,     do.afsluit_d,     do.wbs_nr,     do.creatie_ts,     do.wijzig_ts,     do.planning_lijn_id,     do.goedkeuring_bedrag,     do.offerte_id,     off.inzender as offerte_inzender,     off.status as offerte_status,     off.btw_tarief as offerte_btw_tarief,     off.totaal as offerte_totaal,     off.totaal_incl_btw as offerte_totaal_incl_btw,     do.voorstel_deelopdracht_id,     art46.voorstel_deelopdracht_nr(do.voorstel_deelopdracht_id) as voorstel_deelopdracht_nr,     coalesce(art46.pdrcht_schld(do.deelopdracht_id),0) as totaal_schuldvorderingen,     coalesce(art46.pdr_fct_sap(do.deelopdracht_id),0) as totaal_facturen from art46.deelopdracht do     left join art46.v_dossier dos on do.dossier_id = dos.id     left join art46.v_bestek be on do.bestek_id = be.bestek_id     left join art46.offerte off on do.offerte_id = off.ID;
drop view ART46.V_SCHULDVORDERING;create view art46.v_schuldvordering as select     sv.vordering_id,     art46.schuldvordering_nr(sv.vordering_id) as schuldvordering_nr,     coalesce(do.doss_hdr_id, be.doss_hdr_id) as contact_doss_hdr_id,     coalesce(ambt_do.email, ambt_be.email) as contact_doss_hdr_email,     sv.bestek_id,     be.bestek_nr,     be.dossier_id,     be.dossier_nr,     sv.dossier_type,     be.dossier_b,     be.dossier_b_l,     be.dossier_id_boa,     be.raamcontract_jn,     be.doss_hdr_id,     sv.brief_id,     br.brief_nr,     br.dms_id,     br.dms_folder,     br.dms_filename,     sv.vordering_d,     sv.goedkeuring_d,     sv.uiterste_d,     sv.excl_btw_bedrag,     sv.vordering_bedrag,     sv.goedkeuring_bedrag,     sv.herziening_bedrag,     sv.boete_bedrag,     sv.commentaar,     sv.van_d,     sv.tot_d,     sv.vordering_nr,     sv.deelopdracht_id,     do.deelopdracht_nr,     do.dossier_id as deelopdracht_dossier_id,     do.dossier_nr as deelopdracht_dossier_nr,     do.dossier_type as deelopdracht_dossier_type,     do.dossier_b as deelopdracht_dossier_b,     do.dossier_b_l as deelopdracht_dossier_b_l,     do.dossier_id_boa as deelopdracht_dossier_id_boa,     do.raamcontract_jn as deelopdracht_raamcontract_jn,     do.doss_hdr_id as deelopdracht_doss_hdr_id,     sv.betaal_d,     sv.creatie_ts,     sv.wijzig_ts,     sv.uiterste_verific_d,     sv.afgekeurd_jn,     sv.aanvr_schuldvordering_id,     asv.offerte_id,     sv.brief_of_aanvraag_id,     sv.acceptatie_d,     sv.antwoord_pdf_brief_id,     sv.print_d,     sv.status,     sv.vordering_correct_bedrag,     sv.herziening_correct_bedrag,     sv.schuldvordering_fase_id,     svf.schuldvordering_fase_b,     sv.webloket_gebruiker_id,     wg.email_address as webloket_gebruiker_email from art46.schuldvordering sv     inner join art46.v_bestek be on sv.bestek_id = be.bestek_id     left join art46.brief br on sv.brief_id = br.brief_id     left join art46.v_deelopdracht do on sv.deelopdracht_id = do.deelopdracht_id     left join art46.webloket_gebruiker wg on sv.webloket_gebruiker_id = wg.id     left join art46.v_ambtenaar ambt_be on be.doss_hdr_id = ambt_be.ambtenaar_id     left join art46.v_ambtenaar ambt_do on do.doss_hdr_id = ambt_do.ambtenaar_id     left join art46.aanvr_schuldvordering asv on sv.aanvr_schuldvordering_id = asv.aanvr_schuldvordering_id     left join art46.schuldvordering_fase svf on sv.schuldvordering_fase_id = svf.schuldvordering_fase_id;
drop view ART46.V_BRIEF;create view art46.v_brief as select     br.brief_id,     br.brief_nr,     br.adres_id,     a.naam as adres_naam,     a.voornaam as adres_voornaam,     br.contact_id,     ac.naam as contact_naam,     ac.voornaam as contact_voornaam,     case when br.contact_id is null then coalesce(a.naam, '') || ' ' || coalesce(a.voornaam, '') else coalesce(ac.naam, '') || ' ' || coalesce(ac.voornaam, '') end as naam_voornaam,     br.dossier_id,     d.dossier_nr,     d.dossier_b,     d.dossier_id_boa,     d.doss_hdr_id,     d.dossier_fase_id,     d.nis_id,     d.raamcontract_jn,     d.gemeente_b,     br.dienst_id,     br.inschrijf_d,     br.betreft,     br.commentaar,     br.ltst_wzg_user_id,     br.ltst_wzg_d,     br.in_aard_id,     bai.brief_aard_b as in_aard_b,     br.in_type_id,     bti.brief_type_b as in_type_b,     br.in_d,     br.in_stuk_d,     br.in_referte,     br.in_bijlage,     br.uit_aard_id,     bau.brief_aard_b as uit_aard_b,     br.uit_type_id,     btu.brief_type_b as uit_type_b,     br.uit_type_id_vos,     typ.type_b as uit_type_vos_b,     br.uit_d,     br.uit_referte,     br.uit_bijlage,     br.volgnummer,     br.auteur_id,     br.dms_id,     br.dms_filename,     br.dms_folder,     br.categorie_id,     bc.brief_categorie_b,     br.reactie_d,     br.reactie_voor_d,     br.opnemen_export_s,     br.parent_brief_id,     br.bestek_id,     be.bestek_nr,     br.check_afd_hfd_d,     br.opmerking_afd_hfd,     br.check_auteur_d,     br.qr_code,     br.teprinten_jn,     br.print_d,     br.gegenereerd_jn from art46.brief br     inner join art46.v_dossier d on br.dossier_id = d.id     inner join art46.adres a on br.adres_id = a.adres_id     left join art46.adres_contact ac on br.contact_id = ac.contact_id     left join art46.brief_type_vos typ on typ.type_id = br.uit_type_id_vos     left join art46.brief_aard bai on bai.brief_aard_id = br.in_aard_id     left join art46.brief_aard bau on bau.brief_aard_id = br.uit_aard_id     left join art46.brief_categorie bc on br.categorie_id = bc.brief_categorie_id     left join art46.bestek be on br.bestek_id = be.bestek_id     left join art46.brief_type bti on br.in_type_id = bti.brief_type_id     left join art46.brief_type btu on br.uit_type_id = btu.brief_type_id;
drop view ART46.V_VOORSTEL_DEELOPDRACHT;create view art46.v_voorstel_deelopdracht as     select             vd.id as voorstel_deelopdracht_id,             art46.voorstel_deelopdracht_nr(vd.ID) voorstel_deelopdracht_nr,             vd.offerte_id,             vd.dossier_id,             vd.bedrag_excl_btw,             vd.bedrag_incl_btw,             vd.omschrijving,             vd.status,             vds.name as status_pad,             vds.name_web_loker as status_webloket,             vd.aanvraag_datum,             vd.antwoord_datum,             vd.beslissings_datum,             vd.mail_id,             dos.dossier_b_l,             dos.dossier_nr,             dos.dossier_id_boa,             dos.dossier_type     from art46.voorstel_deelopdracht vd                 left join art46.v_dossier dos                 on vd.DOSSIER_ID = dos.id             inner join art46.VOORSTEL_DEELOPDRACHT_STATUS vds             on vd.status = vds.key;
drop view ART46.V_OFFERTE;create view art46.v_offerte as select     off.id,     off.id as offerte_id,     off.bestek_id,     be.bestek_nr,     be.omschrijving bestek_omschrijving,     be.dossier_id,     be.dossier_nr,     be.dossier_type,     be.dossier_b,     be.dossier_b_l,     be.doss_hdr_id,     be.raamcontract_jn,     off.offerte_brief_id,     br.brief_nr,     br.naam_voornaam as inzender_naam_voornaam,     off.inzender,     off.status,     off.btw_tarief,     off.totaal,     off.key,     off.offerte_origineel_id,     off.opdrachtgever_id,     case when ba.contact_id is not null then  coalesce(ac.naam, '') || ' ' || coalesce(ac.voornaam, '')          when ba.adres_id is not null then coalesce(a.naam, '') || ' ' || coalesce(a.voornaam, '')          else ''     end as opdrachtgever_naam_voornaam from  art46.offerte off         inner join art46.v_bestek be         on off.bestek_id = be.bestek_id     left join art46.bestek_adres ba     on off.opdrachtgever_id = ba.bestek_adres_id         left join art46.adres a         on ba.adres_id = a.adres_id             left join art46.adres_contact ac             on ba.contact_id = ac.contact_id     inner join art46.v_brief br     on off.offerte_brief_id = br.brief_id;
drop view ART46.V_AANVR_SCHULDVORDERING;create view art46.v_aanvr_schuldvordering as select     asv.aanvr_schuldvordering_id,     asv.offerte_id,     off.bestek_id,     off.bestek_nr,     off.bestek_omschrijving,     off.dossier_id,     off.dossier_nr,     off.dossier_type,     off.dossier_b,     off.dossier_b_l,     off.doss_hdr_id,     off.raamcontract_jn,     off.opdrachtgever_id,     off.opdrachtgever_naam_voornaam,     off.btw_tarief,     sv.vordering_id,     art46.schuldvordering_nr(sv.vordering_id) as schuldvordering_nr,     sv.deelopdracht_id,     do.deelopdracht_nr,     do.dossier_id as deelopdracht_dossier_id,     do.dossier_nr as deelopdracht_dossier_nr,     do.dossier_type as deelopdracht_dossier_type,     do.dossier_b as deelopdracht_dossier_b,     do.dossier_b_l as deelopdracht_dossier_b_l,     do.dossier_id_boa as deelopdracht_dossier_id_boa,     do.doss_hdr_id as deelopdracht_doss_hdr_id,     do.voorstel_deelopdracht_id,     do.voorstel_deelopdracht_nr,     sv.excl_btw_bedrag,     sv.vordering_bedrag,     sv.herziening_bedrag,     sv.boete_bedrag,     sv.goedkeuring_bedrag,     sv.vordering_correct_bedrag,     sv.herziening_correct_bedrag,     sv.van_d,     sv.tot_d,     sv.vordering_d,     sv.uiterste_verific_d,     sv.uiterste_d,     sv.goedkeuring_d,     sv.acceptatie_d,     sv.antwoord_pdf_brief_id,     antw_pdf_br.dms_id as antwoord_pdf_dms_id,     antw_pdf_br.dms_folder as antwoord_pdf_dms_folder,     antw_pdf_br.dms_filename as antwoord_pdf_dms_filename,     asv.beschrijving,     (  coalesce(sv.vordering_correct_bedrag, sv.vordering_bedrag, 0)  +        coalesce(sv.herziening_correct_bedrag, sv.herziening_bedrag, 0)) as totaal_incl_prijsherziening,     coalesce(sv.afgekeurd_jn,'N') as afgekeurd_jn,     asv.ingezonden_ts,     sv.schuldvordering_fase_id,     svf.schuldvordering_fase_b,     sv.commentaar,     sv.webloket_gebruiker_id,     sv.status,     st.name as status_pad,     st.name_web_loker as status_webloket,     asv.wijzig_ts from art46.aanvr_schuldvordering as asv        inner join art46.v_offerte off on asv.offerte_id = off.offerte_id     left join art46.schuldvordering sv on asv.aanvr_schuldvordering_id = sv.aanvr_schuldvordering_id     left join art46.schuldvordering_fase svf on sv.schuldvordering_fase_id = svf.schuldvordering_fase_id     left join art46.schuldvordering_status st on sv.status = st.key     left join art46.v_deelopdracht do on sv.deelopdracht_id = do.deelopdracht_id     left join art46.brief antw_pdf_br on sv.antwoord_pdf_brief_id = antw_pdf_br.brief_id     left join art46.brief brief on sv.brief_id = antw_pdf_br.brief_id;




-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('5.28.c');


