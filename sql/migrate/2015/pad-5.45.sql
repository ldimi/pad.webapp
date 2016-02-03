

--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  art46.db_versie
where db_versie = '5.45';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

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
    ( coalesce(sv.vordering_correct_bedrag, sv.vordering_bedrag, 0) + coalesce(sv.herziening_correct_bedrag, sv.herziening_bedrag, 0)) as totaal_incl_prijsherziening,
    coalesce(sv.afgekeurd_jn,'N') as afgekeurd_jn,
    asv.ingezonden_ts,
    sv.schuldvordering_fase_id,
    svf.schuldvordering_fase_b,
    sv.commentaar,
    sv.webloket_gebruiker_email,
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


alter table art46.schuldvordering_status_history
    rename column webloket_gebruiker_login to webloket_gebruiker_email
;

call sysproc.admin_cmd('reorg table art46.schuldvordering_status_history')
;


drop view art46.v_schuldvordering_status_history;
create view art46.v_schuldvordering_status_history as
select
    ssh.id,
    ssh.schuldvordering_id,
    ssh.webloket_gebruiker_email,
    ssh.dossierhouder_id,
    ssh.datum,
    ssh.motivatie,
    ssh.status,
    ss.name as status_pad,
    ss.name_web_loker as status_webloket
from art46.schuldvordering_status_history ssh
    left join art46.schuldvordering_status ss on ssh.status = ss.key
;


drop view art46.v_schuldvordering_status_laatste;
create view art46.v_schuldvordering_status_laatste as
select
ssh.id,
ssh.schuldvordering_id,
ssh.webloket_gebruiker_email,
ssh.dossierhouder_id,
ssh.datum,
ssh.motivatie,
ssh.status
from art46.schuldvordering_status_history ssh
where ssh.id =
(
   select
   max(ssh2.id)
   from art46.schuldvordering_status_history ssh2
   where ssh2.schuldvordering_id = ssh.schuldvordering_id
)
;






alter table art46.dossier_abonnee
    drop primary key
;

alter table art46.dossier_abonnee
    rename column email_address to email
;

alter table art46.dossier_abonnee
    add primary key (email, dossier_id)
;

call sysproc.admin_cmd('reorg table art46.dossier_abonnee')
;


drop table art46.webloket_gebruiker_dossier;

drop table art46.dossier_rol;

drop table art46.webloket_gebruiker_offerte;

alter table art46.schuldvordering
    drop column webloket_gebruiker_id
;

alter table art46.schuldvordering
    add constraint cc_aanvr_email check(
           (aanvr_schuldvordering_id is null and webloket_gebruiker_email is null) or
           (aanvr_schuldvordering_id is not null and webloket_gebruiker_email is not null)
    )
;

call sysproc.admin_cmd('reorg table art46.schuldvordering')
;



alter table art46.voorstel_deelopdracht_history
    drop column gebruiker_id
;

call sysproc.admin_cmd('reorg table art46.voorstel_deelopdracht_history')
;


alter table art46.schuldvordering_status_history
    drop column gebruiker_id
;

call sysproc.admin_cmd('reorg table art46.schuldvordering_status_history')
;


drop table art46.webloket_gebruiker;

-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('5.45');
