--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

drop view art46.v_bestek;

create view art46.v_bestek as
select
    be.bestek_id,
    be.bestek_nr,
    be.dossier_id,
    be.dossier_type,
    dos.dossier_nr,
    dos.dossier_b,
    dos.dossier_b_l,
    dos.dossier_id_boa,
    dos.raamcontract_jn,
    be.type_id,
    bt.type_b,
    be.procedure_id,
    bp.procedure_b,
    be.fase_id,
    bf.fase_b,
    be.dienst_id,
    be.omschrijving,
    be.commentaar,
    be.afsluit_d,
    be.raamcontract_s,
    be.btw_tarief,
    be.start_d,
    be.stop_d,
    be.verlenging_s,
    be.wbs_nr,
    be.creatie_ts,
    be.wijzig_ts,
    be.meetstaat_pdf_brief_id,
    be.meetstaat_excel_brief_id
from art46.bestek be
        inner join art46.v_dossier dos on be.dossier_id = dos.id
        inner join art46.bestek_type bt on be.type_id = bt.type_id
        inner join art46.bestek_procedure bp on be.procedure_id = bp.procedure_id
        inner join art46.bestek_fase bf on be.fase_id = bf.fase_id
;


delete from  art46.db_versie
where db_versie = '4.87';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;


drop view art46.v_bestek

create view art46.v_bestek as
select
    be.bestek_id,
    be.bestek_nr,
    be.dossier_id,
    dos.dossier_nr,
    be.dossier_type,
    dos.dossier_b,
    dos.dossier_b_l,
    dos.dossier_id_boa,
    dos.raamcontract_jn,
    dos.doss_hdr_id,
    be.type_id,
    bt.type_b,
    be.procedure_id,
    bp.procedure_b,
    be.fase_id,
    bf.fase_b,
    be.dienst_id,
    be.omschrijving,
    be.commentaar,
    be.afsluit_d,
    be.raamcontract_s,
    be.btw_tarief,
    be.start_d,
    be.stop_d,
    be.verlenging_s,
    be.wbs_nr,
    be.creatie_ts,
    be.wijzig_ts,
    be.meetstaat_pdf_brief_id,
    be.meetstaat_excel_brief_id
from art46.bestek be
        inner join art46.v_dossier dos on be.dossier_id = dos.id
        inner join art46.bestek_type bt on be.type_id = bt.type_id
        inner join art46.bestek_procedure bp on be.procedure_id = bp.procedure_id
        inner join art46.bestek_fase bf on be.fase_id = bf.fase_id
;


drop view art46.v_deelopdracht;

create view art46.v_deelopdracht as
select
    do.deelopdracht_id,
    art46.deelopdracht_nr(do.deelopdracht_id) as deelopdracht_nr,
    do.dossier_id,
    dos.dossier_nr,
    dos.dossier_type,
    dos.dossier_b,
    dos.dossier_b_l,
    dos.dossier_id_boa,
    dos.raamcontract_jn,
    dos.doss_hdr_id,
    do.bestek_id,
    be.bestek_nr,
    be.dossier_id as ander_dossier_id,
    be.dossier_type as ander_dossier_type,
    be.dossier_id as ander_dossier_id_ivs,
    be.doss_hdr_id as ander_doss_hdr_id,
    be.raamcontract_jn as ander_raamcontract_jn,
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
    coalesce(art46.pdr_fct_sap(do.deelopdracht_id),0) as totaal_facturen
from art46.deelopdracht do
    left join art46.v_dossier dos on do.dossier_id = dos.id
    left outer join art46.v_bestek be on do.bestek_id = be.bestek_id
;

drop view art46.v_schuldvordering;

create view art46.v_schuldvordering as
select
    sv.vordering_id,
    art46.schuldvordering_nr(sv.vordering_id) as schuldvordering_nr,
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
    sv.vordering_d,
    sv.goedkeuring_d,
    sv.uiterste_d,
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
    sv.aanvraag_schuldvordering_id,
    sv.brief_of_aanvraag_id,
    sv.acceptatie_d,
    sv.antwoord_pdf_brief_id,
    sv.print_d,
    sv.status,
    sv.vordering_correct_bedrag,
    sv.herziening_correct_bedrag,
    sv.brief_categorie_id,
    sv.gebruiker_id
from art46.schuldvordering sv
        inner join art46.v_bestek be on sv.bestek_id = be.bestek_id
        left join art46.brief br on sv.brief_id = br.brief_id
        left join art46.v_deelopdracht do on sv.deelopdracht_id = do.deelopdracht_id
;


select 'drop view ' || rtrim(v.VIEWSCHEMA) || '.' || rtrim(v.VIEWNAME) || ';' ||
       replace(cast(v.text as varchar(32000)), chr(10) , ' ') || ';'
from syscat.VIEWS v
where v.viewschema = 'ART46'
     and v.valid = 'N'
;


-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('4.87');

