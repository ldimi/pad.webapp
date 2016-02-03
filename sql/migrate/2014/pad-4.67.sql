--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

drop view art46.v_voorstel_deelopdracht;

delete from  ART46.DB_VERSIE
where DB_VERSIE = '4.67';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

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
            dos.dossier_id as dossier_id_ivs,
            dos.dossier_id_boa,
            dos.dossier_type
    from art46.voorstel_deelopdracht vd
                left join art46.v_dossier dos
                on vd.DOSSIER_ID = dos.id
            inner join art46.VOORSTEL_DEELOPDRACHT_STATUS vds
            on vd.status = vds.key
;


-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('4.67');

