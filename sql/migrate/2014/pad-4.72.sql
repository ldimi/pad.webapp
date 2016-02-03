--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

drop view art46.v_voorstel_deelopdracht;

drop view art46.v_deelopdracht;

drop view art46.v_dossier;

delete from  art46.db_versie
where db_versie = '4.72';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

-- dossier_id wordt dossier_nr (ipv. soms dossier_id_ivs)

create view art46.v_dossier as
select
    d.id,
    d.dossier_id,
    d.dossier_id as dossier_nr,
    d.dossier_id_boa,
    d.dossier_b,
    d.dossier_type,
    d.dossier_fase_id,
    d.doss_hdr_id,
    d.nis_id,
    d.afsluit_d,
    d.commentaar,
    d.aanpak_s,
    d.aanpak_onderzocht_s,
    d.aanpak_l,
    d.aanpak_onderzocht_l,
    d.art46_gesloten_s,
    d.art46_gesloten_d,
    d.financiele_info,
    d.onderzoek_id,
    d.conform_bbo_d,
    d.conform_bsp_d,
    d.eindverklaring_d,
    d.commentaar_bodem,
    d.type_id_bodem,
    d.sap_project_nr,
    d.wbs_ivs_nr,
    d.wbs_migratie,
    d.creatie_ts,
    d.wijzig_ts,
    d.programma_code,
    d.nis_id_int,
    d.rechtsgrond_code,
    g.postcode,
    g.naam as gemeente_b,
    LTRIM(coalesce(g.naam, '') || ' ' || coalesce(d.dossier_b, '')) as dossier_b_l,
    case when d.dossier_type != 'X' then null when d.dossier_fase_id = 2 then 'J' else 'N' end raamcontract_jn
from art46.dossier d
        left join smeg_ref.crab_gemeente g on d.nis_id_int = g.nis_id
;

create view art46.v_deelopdracht as
    select
        do.deelopdracht_id,
        art46.deelopdracht_nr(do.deelopdracht_id) as deelopdracht_nr,
        do.bestek_id,
        do.dossier_id,
        do.bedrag,
        do.goedkeuring_d,
        do.stop_s,
        do.voorstel_d,
        do.afsluit_d,
        do.wbs_nr,
        do.creatie_ts,
        do.wijzig_ts,
        do.planning_lijn_id,
        do.goedkeuring_bedrag,
        do.offerte_id,
        do.voorstel_deelopdracht_id,
        art46.voorstel_deelopdracht_nr(do.voorstel_deelopdracht_id) as voorstel_deelopdracht_nr,
        coalesce(art46.pdrcht_schld(do.deelopdracht_id),0) as totaal_schuldvorderingen,
        coalesce(art46.pdr_fct_sap(do.deelopdracht_id),0) as totaal_facturen,
        dos.dossier_nr,
        dos.dossier_id_boa,
        dos.dossier_type,
        dos.doss_hdr_id,
        dos.dossier_b_l,
        b.bestek_nr,
        b.dossier_id as ander_dossier_id,
        ados.dossier_type as ander_dossier_type,
        ados.dossier_id as ander_dossier_id_ivs,
        ados.doss_hdr_id as ander_doss_hdr_id,
        ados.raamcontract_jn as ander_raamcontract_jn
    from
        art46.deelopdracht do
            left join art46.v_dossier dos
            on do.dossier_id = dos.id
        left outer join smeg.ovam_gemeente_view og
        on dos.nis_id = og.nis_id
            left outer join art46.bestek b
            on do.bestek_id = b.bestek_id
        left outer join art46.v_dossier ados
        on b.dossier_id = ados.id
;

create view art46.v_voorstel_deelopdracht as
    select
            vd.id as voorstel_deelopdracht_id,
            art46.voorstel_deelopdracht_nr(vd.ID) voorstel_deelopdracht_nr,
            vd.offerte_id,
            vd.dossier_id,
            vd.bedrag_excl_btw,
            vd.bedrag_incl_btw,
            vd.omschrijving,
            vd.status,
            vds.name as status_pad,
            vds.name_web_loker as status_webloket,
            vd.aanvraag_datum,
            vd.antwoord_datum,
            vd.beslissings_datum,
            vd.mail_id,
            dos.dossier_b_l,
            dos.dossier_nr,
            dos.dossier_id_boa,
            dos.dossier_type
    from art46.voorstel_deelopdracht vd
                left join art46.v_dossier dos
                on vd.DOSSIER_ID = dos.id
            inner join art46.VOORSTEL_DEELOPDRACHT_STATUS vds
            on vd.status = vds.key
;



-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('4.72');

