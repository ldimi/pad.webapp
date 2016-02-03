--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  art46.db_versie
where db_versie = '4.83';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

--select 'drop view ' || rtrim(v.VIEWSCHEMA) || '.' || rtrim(v.VIEWNAME) || ';' ||
--       replace(cast(v.text as varchar(32000)), chr(10) , ' ') || ';'
--from syscat.VIEWS v
--where v.viewschema = 'ART46'
--     and v.valid = 'N'
--;

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
    g.postcode,
    g.naam as gemeente_b,
    ltrim(coalesce(g.naam, '') || ' ' || coalesce(dos.dossier_b, '')) as dossier_b_l,
    case when dos.dossier_type != 'X' then null
         when dos.dossier_fase_id = 2 then 'J'
         else 'N'
    end raamcontract_jn
from art46.dossier dos
        left join smeg_ref.crab_gemeente g
        on dos.nis_id_int = g.nis_id
     left join art46.v_ambtenaar ambt
     on dos.DOSS_HDR_ID = ambt.ambtenaar_id
;

drop view ART46.V_BRIEF;
create view art46.v_brief as select     br.brief_id,     br.brief_nr,     br.adres_id,     br.contact_id,     br.dossier_id,     d.dossier_nr,     d.dossier_b,     d.dossier_id_boa,     d.doss_hdr_id,     d.dossier_fase_id,     d.nis_id,     d.raamcontract_jn,     d.gemeente_b,     br.dienst_id,     br.inschrijf_d,     br.betreft,     br.commentaar,     br.ltst_wzg_user_id,     br.ltst_wzg_d,     br.in_aard_id,     bai.brief_aard_b as in_aard_b,     br.in_type_id,     bti.brief_type_b as in_type_b,     br.in_d,     br.in_stuk_d,     br.in_referte,     br.in_bijlage,     br.uit_aard_id,     bau.brief_aard_b as uit_aard_b,     br.uit_type_id,     btu.brief_type_b as uit_type_b,     br.uit_type_id_vos,     typ.type_b as uit_type_vos_b,     br.uit_d,     br.uit_referte,     br.uit_bijlage,     br.volgnummer,     br.auteur_id,     br.dms_id,     br.dms_filename,     br.dms_folder,     br.categorie_id,     bc.brief_categorie_b,     br.reactie_d,     br.reactie_voor_d,     br.opnemen_export_s,     br.parent_brief_id,     br.bestek_id,     be.bestek_nr,     br.check_afd_hfd_d,     br.opmerking_afd_hfd,     br.check_auteur_d,     br.qr_code,     br.teprinten_jn,     br.print_d,     a.naam as adres_naam,     a.voornaam as adres_voornaam from art46.brief br             inner join art46.v_dossier d             on br.dossier_id = d.id         inner join art46.adres a         on br.adres_id = a.adres_id             left outer join art46.brief_type_vos typ             on typ.type_id = br.uit_type_id_vos         left outer join art46.brief_aard bai         on bai.brief_aard_id = br.in_aard_id             left outer join art46.brief_aard bau             on bau.brief_aard_id = br.uit_aard_id         left join art46.brief_categorie bc         on br.categorie_id = bc.brief_categorie_id             left outer join art46.bestek be             on br.bestek_id = be.bestek_id         left outer join art46.brief_type bti         on br.in_type_id  = bti.brief_type_id             left outer join art46.brief_type btu             on br.uit_type_id  = btu.brief_type_id;

drop view ART46.V_BESTEK;
create view art46.v_bestek as select         be.bestek_id,         be.bestek_nr,         be.dossier_id,         be.dossier_type,         dos.dossier_nr,         dos.dossier_b,         dos.dossier_b_l,         dos.dossier_id_boa,         dos.raamcontract_jn,         be.type_id,         bt.type_b,         be.procedure_id,         bp.procedure_b,         be.fase_id,         bf.fase_b,         be.dienst_id,         be.omschrijving,         be.commentaar,         be.afsluit_d,         be.raamcontract_s,         be.btw_tarief,         be.start_d,         be.stop_d,         be.verlenging_s,         be.wbs_nr,         be.creatie_ts,         be.wijzig_ts,         be.meetstaat_pdf_brief_id,         be.meetstaat_excel_brief_id from art46.bestek be            inner join art46.v_dossier dos            on be.dossier_id = dos.id      inner join art46.bestek_type bt      on be.type_id = bt.type_id         inner join art46.bestek_procedure bp         on be.procedure_id = bp.procedure_id      inner join art46.bestek_fase bf      on be.fase_id = bf.fase_id;

drop view ART46.V_DEELOPDRACHT;
create view art46.v_deelopdracht as     select         do.deelopdracht_id,         art46.deelopdracht_nr(do.deelopdracht_id) as deelopdracht_nr,         do.bestek_id,         do.dossier_id,         do.bedrag,         do.goedkeuring_d,         do.stop_s,         do.voorstel_d,         do.afsluit_d,         do.wbs_nr,         do.creatie_ts,         do.wijzig_ts,         do.planning_lijn_id,         do.goedkeuring_bedrag,         do.offerte_id,         do.voorstel_deelopdracht_id,         art46.voorstel_deelopdracht_nr(do.voorstel_deelopdracht_id) as voorstel_deelopdracht_nr,         coalesce(art46.pdrcht_schld(do.deelopdracht_id),0) as totaal_schuldvorderingen,         coalesce(art46.pdr_fct_sap(do.deelopdracht_id),0) as totaal_facturen,         dos.dossier_nr,         dos.dossier_id_boa,         dos.dossier_type,         dos.doss_hdr_id,         dos.dossier_b_l,         b.bestek_nr,         b.dossier_id as ander_dossier_id,         ados.dossier_type as ander_dossier_type,         ados.dossier_id as ander_dossier_id_ivs,         ados.doss_hdr_id as ander_doss_hdr_id,         ados.raamcontract_jn as ander_raamcontract_jn     from         art46.deelopdracht do             left join art46.v_dossier dos             on do.dossier_id = dos.id         left outer join smeg.ovam_gemeente_view og         on dos.nis_id = og.nis_id             left outer join art46.bestek b             on do.bestek_id = b.bestek_id         left outer join art46.v_dossier ados         on b.dossier_id = ados.id;

drop view ART46.V_VOORSTEL_DEELOPDRACHT;
create view art46.v_voorstel_deelopdracht as     select             vd.id as voorstel_deelopdracht_id,             art46.voorstel_deelopdracht_nr(vd.ID) voorstel_deelopdracht_nr,             vd.offerte_id,             vd.dossier_id,             vd.bedrag_excl_btw,             vd.bedrag_incl_btw,             vd.omschrijving,             vd.status,             vds.name as status_pad,             vds.name_web_loker as status_webloket,             vd.aanvraag_datum,             vd.antwoord_datum,             vd.beslissings_datum,             vd.mail_id,             dos.dossier_b_l,             dos.dossier_nr,             dos.dossier_id_boa,             dos.dossier_type     from art46.voorstel_deelopdracht vd                 left join art46.v_dossier dos                 on vd.DOSSIER_ID = dos.id             inner join art46.VOORSTEL_DEELOPDRACHT_STATUS vds             on vd.status = vds.key;

drop view ART46.V_SCHULDVORDERING;
create view art46.v_schuldvordering as select        sv.vordering_id,        art46.schuldvordering_nr(sv.vordering_id) as schuldvordering_nr,        sv.bestek_id,        be.bestek_nr,        be.dossier_id,        be.dossier_nr,        sv.dossier_type,        be.raamcontract_jn,        be.dossier_b,        be.dossier_b_l,        sv.brief_id,        br.brief_nr,        sv.vordering_d,        sv.goedkeuring_d,        sv.uiterste_d,        sv.vordering_bedrag,        sv.goedkeuring_bedrag,        sv.herziening_bedrag,        sv.boete_bedrag,        sv.commentaar,        sv.van_d,        sv.tot_d,        sv.vordering_nr,        sv.deelopdracht_id,        do.deelopdracht_nr,        do.dossier_id as deelopdracht_dossier_id,        do.dossier_nr as deelopdracht_dossier_nr,        do.dossier_type as deelopdracht_dossier_type,        do.doss_hdr_id as deelopdracht_doss_hdr_id,        do.dossier_b_l as deelopdracht_dossier_b_l,        sv.betaal_d,        sv.creatie_ts,        sv.wijzig_ts,        sv.uiterste_verific_d,        sv.afgekeurd_jn,        sv.aanvraag_schuldvordering_id,        sv.brief_of_aanvraag_id,        sv.acceptatie_d,        sv.antwoord_pdf_brief_id,        sv.aanvraag_pdf_brief_id,        sv.print_d,        sv.status,        sv.vordering_correct_bedrag,        sv.herziening_correct_bedrag,        sv.brief_categorie_id,        sv.gebruiker_id from art46.schuldvordering sv             inner join art46.v_bestek be             on sv.bestek_id = be.bestek_id      left join art46.brief br      on sv.brief_id = br.brief_id         left join art46.v_deelopdracht do         on sv.deelopdracht_id = do.deelopdracht_id;

-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('4.83');

