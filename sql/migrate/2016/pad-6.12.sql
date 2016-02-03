

--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


drop view art46.v_voorstel_deelopdracht;

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
left join art46.v_dossier dos on vd.DOSSIER_ID = dos.id
inner join art46.VOORSTEL_DEELOPDRACHT_STATUS vds on vd.status = vds.key
;



delete from  art46.db_versie
where db_versie = '6.12';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

drop view art46.v_voorstel_deelopdracht;

create view art46.v_voorstel_deelopdracht as
select
    vd.id as voorstel_deelopdracht_id,
    art46.voorstel_deelopdracht_nr(vd.ID) voorstel_deelopdracht_nr,
    vd.dossier_id,
    dos.dossier_b_l,
    dos.dossier_nr,
    dos.dossier_id_boa,
    dos.dossier_type,
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
    vd.offerte_id,
    off.bestek_id,
    off.bestek_nr,
    off.bestek_omschrijving,
    off.dossier_id as ander_dossier_id,
    off.dossier_nr as ander_dossier_nr,
    off.dossier_type as ander_dossier_type,
    off.dossier_b as ander_dossier_b,
    off.doss_hdr_id as ander_doss_hdr_id,
    off.raamcontract_jn as ander_raamcontract_jn,
    off.offerte_brief_id,
    off.brief_nr as offerte_brief_nr,
    off.inzender_naam_voornaam as inzender_naam_voornaam
from art46.voorstel_deelopdracht vd
        inner join art46.v_dossier dos on vd.DOSSIER_ID = dos.id
    inner join art46.VOORSTEL_DEELOPDRACHT_STATUS vds on vd.status = vds.key
        inner join art46.v_offerte off on vd.offerte_id = off.ID
;


select 'drop view ' || rtrim(v.VIEWSCHEMA) || '.' || rtrim(v.VIEWNAME) || ';' ||
       replace(cast(v.text as varchar(32000)), chr(10) , ' ') || ';'
from syscat.VIEWS v
where v.viewschema = 'ART46'
     and v.valid = 'N'
;


-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('6.12');
