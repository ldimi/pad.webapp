--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;



delete from  art46.db_versie
where db_versie = '5.06';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

-- toevoegen veld gegenereerd_jn aan art46.brief
--------------------------------------------------------------------;


alter table art46.brief
    add column gegenereerd_jn char(1) not null default 'N'
;

call sysproc.admin_cmd('reorg table ART46.brief')
;


alter table art46.brief
   foreign key fk_br_gegenereerd_jn(gegenereerd_jn)
           references art46.ja_nee_code (code) on delete restrict
;


-- toevoegen veld gegenereerd_jn aan V_BRIEF

drop view art46.v_brief;

create view art46.v_brief as
select
    br.brief_id,
    br.brief_nr,
    br.adres_id,
    a.naam as adres_naam,
    a.voornaam as adres_voornaam,
    br.contact_id,
    ac.naam as contact_naam,
    ac.voornaam as contact_voornaam,
    case when br.contact_id is null then coalesce(a.naam, '') || ' ' || coalesce(a.voornaam, '') else coalesce(ac.naam, '') || ' ' || coalesce(ac.voornaam, '') end as naam_voornaam,
    br.dossier_id,
    d.dossier_nr,
    d.dossier_b,
    d.dossier_id_boa,
    d.doss_hdr_id,
    d.dossier_fase_id,
    d.nis_id,
    d.raamcontract_jn,
    d.gemeente_b,
    br.dienst_id,
    br.inschrijf_d,
    br.betreft,
    br.commentaar,
    br.ltst_wzg_user_id,
    br.ltst_wzg_d,
    br.in_aard_id,
    bai.brief_aard_b as in_aard_b,
    br.in_type_id,
    bti.brief_type_b as in_type_b,
    br.in_d,
    br.in_stuk_d,
    br.in_referte,
    br.in_bijlage,
    br.uit_aard_id,
    bau.brief_aard_b as uit_aard_b,
    br.uit_type_id,
    btu.brief_type_b as uit_type_b,
    br.uit_type_id_vos,
    typ.type_b as uit_type_vos_b,
    br.uit_d,
    br.uit_referte,
    br.uit_bijlage,
    br.volgnummer,
    br.auteur_id,
    br.dms_id,
    br.dms_filename,
    br.dms_folder,
    br.categorie_id,
    bc.brief_categorie_b,
    br.reactie_d,
    br.reactie_voor_d,
    br.opnemen_export_s,
    br.parent_brief_id,
    br.bestek_id,
    be.bestek_nr,
    br.check_afd_hfd_d,
    br.opmerking_afd_hfd,
    br.check_auteur_d,
    br.qr_code,
    br.teprinten_jn,
    br.print_d,
    br.gegenereerd_jn
from art46.brief br
    inner join art46.v_dossier d on br.dossier_id = d.id
    inner join art46.adres a on br.adres_id = a.adres_id
    left join art46.adres_contact ac on br.contact_id = ac.contact_id
    left join art46.brief_type_vos typ on typ.type_id = br.uit_type_id_vos
    left join art46.brief_aard bai on bai.brief_aard_id = br.in_aard_id
    left join art46.brief_aard bau on bau.brief_aard_id = br.uit_aard_id
    left join art46.brief_categorie bc on br.categorie_id = bc.brief_categorie_id
    left join art46.bestek be on br.bestek_id = be.bestek_id
    left join art46.brief_type bti on br.in_type_id = bti.brief_type_id
    left join art46.brief_type btu on br.uit_type_id = btu.brief_type_id
;


-- herdefinitie van dependend views


drop view art46.v_offerte;

create view art46.v_offerte as
select
    off.id,
    off.id as offerte_id,
    off.bestek_id,
    be.bestek_nr,
    be.omschrijving bestek_omschrijving,
    be.dossier_id,
    be.dossier_nr,
    be.dossier_type,
    be.dossier_b,
    be.dossier_b_l,
    be.doss_hdr_id,
    be.raamcontract_jn,
    off.offerte_brief_id,
    br.brief_nr,
    br.naam_voornaam as inzender_naam_voornaam,
    off.inzender,
    off.status,
    off.btw_tarief,
    off.totaal,
    off.key,
    off.offerte_origineel_id,
    off.opdrachtgever_id,
    case when ba.contact_id is not null then  coalesce(ac.naam, '') || ' ' || coalesce(ac.voornaam, '')
         when ba.adres_id is not null then coalesce(a.naam, '') || ' ' || coalesce(a.voornaam, '')
         else ''
    end as opdrachtgever_naam_voornaam
from  art46.offerte off
        inner join art46.v_bestek be
        on off.bestek_id = be.bestek_id
    left join art46.bestek_adres ba
    on off.opdrachtgever_id = ba.bestek_adres_id
        left join art46.adres a
        on ba.adres_id = a.adres_id
            left join art46.adres_contact ac
            on ba.contact_id = ac.contact_id
    inner join art46.v_brief br
    on off.offerte_brief_id = br.brief_id
;



drop view art46.v_aanvr_schuldvordering;

create view art46.v_aanvr_schuldvordering as
select
    asv.aanvr_schuldvordering_id,
    asv.offerte_id,
    off.bestek_id,
    off.bestek_nr,
    off.bestek_omschrijving,
    off.dossier_id,
    off.dossier_nr,
    off.dossier_type,
    off.dossier_b,
    off.dossier_b_l,
    off.doss_hdr_id,
    off.raamcontract_jn,
    off.opdrachtgever_id,
    off.opdrachtgever_naam_voornaam,
    off.btw_tarief,
    sv.vordering_id,
    art46.schuldvordering_nr(sv.vordering_id) as schuldvordering_nr,
    sv.deelopdracht_id,
    do.deelopdracht_nr,
    do.dossier_id as deelopdracht_dossier_id,
    do.dossier_nr as deelopdracht_dossier_nr,
    do.dossier_type as deelopdracht_dossier_type,
    do.dossier_b as deelopdracht_dossier_b,
    do.dossier_b_l as deelopdracht_dossier_b_l,
    do.dossier_id_boa as deelopdracht_dossier_id_boa,
    do.doss_hdr_id as deelopdracht_doss_hdr_id,
    do.voorstel_deelopdracht_id,
    do.voorstel_deelopdracht_nr,
    sv.excl_btw_bedrag,
    sv.vordering_bedrag,
    sv.herziening_bedrag,
    sv.boete_bedrag,
    sv.goedkeuring_bedrag,
    sv.vordering_correct_bedrag,
    sv.herziening_correct_bedrag,
    sv.van_d,
    sv.tot_d,
    sv.vordering_d,
    sv.uiterste_verific_d,
    sv.uiterste_d,
    sv.goedkeuring_d,
    sv.acceptatie_d,
    sv.antwoord_pdf_brief_id,
    antw_pdf_br.dms_id as antwoord_pdf_dms_id,
    antw_pdf_br.dms_folder as antwoord_pdf_dms_folder,
    antw_pdf_br.dms_filename as antwoord_pdf_dms_filename,
    asv.beschrijving,
    asv.opmerking,
    (  coalesce(sv.vordering_correct_bedrag, sv.vordering_bedrag, 0)  +
       coalesce(sv.herziening_correct_bedrag, sv.herziening_bedrag, 0)) as totaal_incl_prijsherziening,
    coalesce(sv.afgekeurd_jn,'N') as afgekeurd_jn,
    asv.ingezonden_ts,
    sv.schuldvordering_fase_id,
    svf.schuldvordering_fase_b,
    sv.commentaar,
    sv.status,
    st.name as status_pad,
    st.name_web_loker as status_webloket,
    asv.wijzig_ts
from art46.aanvr_schuldvordering as asv
       inner join art46.v_offerte off on asv.offerte_id = off.offerte_id
    left join art46.schuldvordering sv on asv.aanvr_schuldvordering_id = sv.aanvr_schuldvordering_id
    left join art46.schuldvordering_fase svf on sv.schuldvordering_fase_id = svf.schuldvordering_fase_id
    left join art46.schuldvordering_status st on sv.status = st.key
    left join art46.v_deelopdracht do on sv.deelopdracht_id = do.deelopdracht_id
    left join art46.brief antw_pdf_br on sv.antwoord_pdf_brief_id = antw_pdf_br.brief_id
    left join art46.brief brief on sv.brief_id = antw_pdf_br.brief_id
;


select 'drop view ' || rtrim(v.VIEWSCHEMA) || '.' || rtrim(v.VIEWNAME) || ';' ||
       replace(cast(v.text as varchar(32000)), chr(10) , ' ') || ';'
from syscat.VIEWS v
where v.viewschema = 'ART46'
     and v.valid = 'N'
;




-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('5.06');



