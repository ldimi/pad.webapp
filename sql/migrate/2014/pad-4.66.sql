--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

drop view art46.v_deelopdracht;

delete from  ART46.DB_VERSIE
where DB_VERSIE = '4.66';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

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
        dos.dossier_id as dossier_id_ivs,
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


-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('4.66');





























