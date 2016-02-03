

--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  art46.db_versie
where db_versie = '5.43';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

alter table art46.schuldvordering
    add column webloket_gebruiker_email varchar(120)
;

drop view art46.v_schuldvordering;
create view art46.v_schuldvordering as
select
sv.vordering_id,
art46.schuldvordering_nr(sv.vordering_id) as schuldvordering_nr,
coalesce(do.doss_hdr_id, be.doss_hdr_id) as contact_doss_hdr_id,
coalesce(ambt_do.email, ambt_be.email) as contact_doss_hdr_email,
sv.bestek_id,
be.bestek_nr,
be.dossier_id,
be.dossier_nr,
sv.dossier_type,
be.dossier_b,
be.dossier_b_l,
be.dossier_id_boa,
be.raamcontract_jn,
be.doss_hdr_id,
sv.brief_id,
br.brief_nr,
br.dms_id,
br.dms_folder,
br.dms_filename,
sv.vordering_d,
sv.goedkeuring_d,
sv.uiterste_d,
sv.excl_btw_bedrag,
sv.vordering_bedrag,
sv.goedkeuring_bedrag,
sv.herziening_bedrag,
sv.boete_bedrag,
sv.commentaar,
sv.van_d,
sv.tot_d,
sv.vordering_nr,
sv.deelopdracht_id,
do.deelopdracht_nr,
do.dossier_id as deelopdracht_dossier_id,
do.dossier_nr as deelopdracht_dossier_nr,
do.dossier_type as deelopdracht_dossier_type,
do.dossier_b as deelopdracht_dossier_b,
do.dossier_b_l as deelopdracht_dossier_b_l,
do.dossier_id_boa as deelopdracht_dossier_id_boa,
do.raamcontract_jn as deelopdracht_raamcontract_jn,
do.doss_hdr_id as deelopdracht_doss_hdr_id,
sv.betaal_d,
sv.creatie_ts,
sv.wijzig_ts,
sv.uiterste_verific_d,
sv.afgekeurd_jn,
sv.aanvr_schuldvordering_id,
asv.offerte_id,
sv.brief_of_aanvraag_id,
sv.acceptatie_d,
sv.antwoord_pdf_brief_id,
sv.print_d,
sv.status,
sv.vordering_correct_bedrag,
sv.herziening_correct_bedrag,
sv.schuldvordering_fase_id,
svf.schuldvordering_fase_b,
sv.webloket_gebruiker_email
from art46.schuldvordering sv
inner join art46.v_bestek be on sv.bestek_id = be.bestek_id
left join art46.brief br on sv.brief_id = br.brief_id
left join art46.v_deelopdracht do on sv.deelopdracht_id = do.deelopdracht_id
left join art46.v_ambtenaar ambt_be on be.doss_hdr_id = ambt_be.ambtenaar_id
left join art46.v_ambtenaar ambt_do on do.doss_hdr_id = ambt_do.ambtenaar_id
left join art46.aanvr_schuldvordering asv on sv.aanvr_schuldvordering_id = asv.aanvr_schuldvordering_id
left join art46.schuldvordering_fase svf on sv.schuldvordering_fase_id = svf.schuldvordering_fase_id
;

update art46.schuldvordering sv
set webloket_gebruiker_email = (select email_address
                                from art46.WEBLOKET_GEBRUIKER wg
                                where wg.id = sv.WEBLOKET_GEBRUIKER_ID)
where 1 = 1
	and sv.WEBLOKET_GEBRUIKER_ID is not null


-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('5.43');