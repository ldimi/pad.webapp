

--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;



delete from  art46.db_versie
where db_versie = '6.29';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

alter table art46.bestek
    add column bestek_hdr_id varchar(8);
;

call sysproc.admin_cmd('reorg table art46.bestek')
;


update art46.bestek be
set be.bestek_hdr_id =  ( select doss_hdr_id from art46.dossier dos where dos.id = be.dossier_id)

alter table art46.bestek
    add foreign key FK_BE_DOS_HDR (bestek_hdr_id)
         references art46.dossier_houder (doss_hdr_id)
         on delete restrict
;

drop view art46.v_bestek;

create view art46.v_bestek as
select
    be.bestek_id,
    be.bestek_nr,
    case when dos.raamcontract_jn = 'J' then be.bestek_hdr_id
         else dos.doss_hdr_id
    end as bestek_hdr_id,
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
    be.btw_tarief,
    be.start_d,
    be.stop_d,
    be.verlenging_s,
    be.wbs_nr,
    be.screening_jn,
    be.screening_organisatie_id,
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

select 'drop view ' || rtrim(v.VIEWSCHEMA) || '.' || rtrim(v.VIEWNAME) || ';' ||
       replace(cast(v.text as varchar(32000)), chr(10) , ' ') || ';'
from syscat.VIEWS v
where v.viewschema = 'ART46'
     and v.valid = 'N'
;

drop view ART46.V_OFFERTE;create view art46.v_offerte as     select off.id,             off.id as offerte_id,             off.bestek_id,             be.bestek_nr,             be.omschrijving bestek_omschrijving,             be.dossier_id,             be.dossier_nr,             be.dossier_type,             be.dossier_b,             be.dossier_b_l,             be.doss_hdr_id,             be.raamcontract_jn,             off.offerte_brief_id,             br.brief_nr,             br.naam_voornaam as inzender_naam_voornaam,             off.inzender,             off.status,             off.btw_tarief,             off.totaal,             off.offerte_origineel_id,             off.opdrachtgever_id,             case when ba.contact_id is not null then  coalesce(ac.naam, '') || ' ' || coalesce(ac.voornaam, '')                   when ba.adres_id is not null then coalesce(a.naam, '') || ' ' || coalesce(a.voornaam, '')                  else ''             end as opdrachtgever_naam_voornaam     from  art46.offerte off              inner join art46.v_bestek be on off.bestek_id = be.bestek_id              left join art46.bestek_adres ba on off.opdrachtgever_id = ba.bestek_adres_id              left join art46.adres a on ba.adres_id = a.adres_id               left join art46.adres_contact ac on ba.contact_id = ac.contact_id              inner join art46.v_brief br on off.offerte_brief_id = br.brief_id;
drop view ART46.V_DEELOPDRACHT;create view art46.v_deelopdracht as select do.deelopdracht_id, art46.deelopdracht_nr(do.deelopdracht_id) as deelopdracht_nr, do.dossier_id, dos.dossier_nr, dos.dossier_type, dos.dossier_b, dos.dossier_b_l, dos.dossier_id_boa, dos.raamcontract_jn, dos.doss_hdr_id, do.bestek_id, be.bestek_nr, be.dossier_id as ander_dossier_id, be.dossier_type as ander_dossier_type, be.dossier_nr as ander_dossier_nr, be.dossier_b as ander_dossier_b, be.doss_hdr_id as ander_doss_hdr_id, be.raamcontract_jn as ander_raamcontract_jn, do.bedrag, do.goedkeuring_d, do.stop_s, do.voorstel_d, do.afsluit_d, do.wbs_nr, do.creatie_ts, do.wijzig_ts, do.planning_lijn_id, do.goedkeuring_bedrag, do.offerte_id, off.inzender as offerte_inzender, off.status as offerte_status, off.btw_tarief as offerte_btw_tarief, off.totaal as offerte_totaal, off.totaal_incl_btw as offerte_totaal_incl_btw, do.voorstel_deelopdracht_id, art46.voorstel_deelopdracht_nr(do.voorstel_deelopdracht_id) as voorstel_deelopdracht_nr, coalesce(art46.pdrcht_schld(do.deelopdracht_id),0) as totaal_schuldvorderingen, coalesce(art46.pdr_fct_sap(do.deelopdracht_id),0) as totaal_facturen from art46.deelopdracht do left join art46.v_dossier dos on do.dossier_id = dos.id left join art46.v_bestek be on do.bestek_id = be.bestek_id left join art46.offerte off on do.offerte_id = off.ID;
drop view ART46.V_SCHULDVORDERING;create view art46.v_schuldvordering as select sv.vordering_id, art46.schuldvordering_nr(sv.vordering_id) as schuldvordering_nr, coalesce(do.doss_hdr_id, be.doss_hdr_id) as contact_doss_hdr_id, coalesce(ambt_do.email, ambt_be.email) as contact_doss_hdr_email, sv.bestek_id, be.bestek_nr, be.dossier_id, be.dossier_nr, sv.dossier_type, be.dossier_b, be.dossier_b_l, be.dossier_id_boa, be.raamcontract_jn, be.doss_hdr_id, sv.brief_id, br.brief_nr, br.dms_id, br.dms_folder, br.dms_filename, sv.vordering_d, sv.goedkeuring_d, sv.uiterste_d, sv.excl_btw_bedrag, sv.vordering_bedrag, sv.goedkeuring_bedrag, sv.herziening_bedrag, sv.boete_bedrag, sv.commentaar, sv.van_d, sv.tot_d, sv.vordering_nr, sv.deelopdracht_id, do.deelopdracht_nr, do.dossier_id as deelopdracht_dossier_id, do.dossier_nr as deelopdracht_dossier_nr, do.dossier_type as deelopdracht_dossier_type, do.dossier_b as deelopdracht_dossier_b, do.dossier_b_l as deelopdracht_dossier_b_l, do.dossier_id_boa as deelopdracht_dossier_id_boa, do.raamcontract_jn as deelopdracht_raamcontract_jn, do.doss_hdr_id as deelopdracht_doss_hdr_id, sv.betaal_d, sv.creatie_ts, sv.wijzig_ts, sv.uiterste_verific_d, sv.afgekeurd_jn, sv.aanvr_schuldvordering_id, asv.offerte_id, sv.brief_of_aanvraag_id, sv.acceptatie_d, sv.antwoord_pdf_brief_id, sv.print_d, sv.status, sv.vordering_correct_bedrag, sv.herziening_correct_bedrag, sv.schuldvordering_fase_id, svf.schuldvordering_fase_b, sv.webloket_gebruiker_email from art46.schuldvordering sv inner join art46.v_bestek be on sv.bestek_id = be.bestek_id left join art46.brief br on sv.brief_id = br.brief_id left join art46.v_deelopdracht do on sv.deelopdracht_id = do.deelopdracht_id left join art46.v_ambtenaar ambt_be on be.doss_hdr_id = ambt_be.ambtenaar_id left join art46.v_ambtenaar ambt_do on do.doss_hdr_id = ambt_do.ambtenaar_id left join art46.aanvr_schuldvordering asv on sv.aanvr_schuldvordering_id = asv.aanvr_schuldvordering_id left join art46.schuldvordering_fase svf on sv.schuldvordering_fase_id = svf.schuldvordering_fase_id;
drop view ART46.V_AANVR_SCHULDVORDERING;create view art46.v_aanvr_schuldvordering as     select         asv.aanvr_schuldvordering_id,         asv.offerte_id,         off.bestek_id,         off.bestek_nr,         off.bestek_omschrijving,         off.dossier_id,         off.dossier_nr,         off.dossier_type,         off.dossier_b,         off.dossier_b_l,         off.doss_hdr_id,         off.raamcontract_jn,         off.opdrachtgever_id,         off.opdrachtgever_naam_voornaam,         off.btw_tarief,         sv.vordering_id,         art46.schuldvordering_nr(sv.vordering_id) as schuldvordering_nr,         sv.deelopdracht_id,         do.deelopdracht_nr,         do.dossier_id as deelopdracht_dossier_id,         do.dossier_nr as deelopdracht_dossier_nr,         do.dossier_type as deelopdracht_dossier_type,         do.dossier_b as deelopdracht_dossier_b,         do.dossier_b_l as deelopdracht_dossier_b_l,         do.dossier_id_boa as deelopdracht_dossier_id_boa,         do.doss_hdr_id as deelopdracht_doss_hdr_id,         do.voorstel_deelopdracht_id,         do.voorstel_deelopdracht_nr,         sv.excl_btw_bedrag,         sv.vordering_bedrag,         sv.herziening_bedrag,         sv.boete_bedrag,         sv.goedkeuring_bedrag,         sv.vordering_correct_bedrag,         sv.herziening_correct_bedrag,         sv.van_d,         sv.tot_d,         sv.vordering_d,         sv.uiterste_verific_d,         sv.uiterste_d,         sv.goedkeuring_d,         sv.acceptatie_d,         sv.antwoord_pdf_brief_id,         antw_pdf_br.dms_id as antwoord_pdf_dms_id,         antw_pdf_br.dms_folder as antwoord_pdf_dms_folder,         antw_pdf_br.dms_filename as antwoord_pdf_dms_filename,         asv.beschrijving,         ( coalesce(sv.vordering_correct_bedrag, sv.vordering_bedrag, 0) + coalesce(sv.herziening_correct_bedrag, sv.herziening_bedrag, 0)) as totaal_incl_prijsherziening,         coalesce(sv.afgekeurd_jn,'N') as afgekeurd_jn,         asv.ingezonden_ts,         sv.schuldvordering_fase_id,         svf.schuldvordering_fase_b,         sv.commentaar,         sv.webloket_gebruiker_email,         sv.status,         st.name as status_pad,         st.name_web_loker as status_webloket,         asv.wijzig_ts     from art46.aanvr_schuldvordering as asv         inner join art46.v_offerte off on asv.offerte_id = off.offerte_id         left join art46.schuldvordering sv on asv.aanvr_schuldvordering_id = sv.aanvr_schuldvordering_id         left join art46.schuldvordering_fase svf on sv.schuldvordering_fase_id = svf.schuldvordering_fase_id         left join art46.schuldvordering_status st on sv.status = st.key         left join art46.v_deelopdracht do on sv.deelopdracht_id = do.deelopdracht_id         left join art46.brief antw_pdf_br on sv.antwoord_pdf_brief_id = antw_pdf_br.brief_id         left join art46.brief brief on sv.brief_id = antw_pdf_br.brief_id;
drop view ART46.V_VOORSTEL_DEELOPDRACHT;create view art46.v_voorstel_deelopdracht as select     vd.id as voorstel_deelopdracht_id,     art46.voorstel_deelopdracht_nr(vd.ID) voorstel_deelopdracht_nr,     vd.dossier_id,     dos.dossier_b_l,     dos.dossier_nr,     dos.dossier_id_boa,     dos.dossier_type,     vd.bedrag_excl_btw,     vd.bedrag_incl_btw,     vd.omschrijving,     vd.status,     vds.name as status_pad,     vds.name_web_loker as status_webloket,     vd.aanvraag_datum,     vd.antwoord_datum,     vd.beslissings_datum,     vd.mail_id,     vd.offerte_id,     off.bestek_id,     off.bestek_nr,     off.bestek_omschrijving,     off.dossier_id as ander_dossier_id,     off.dossier_nr as ander_dossier_nr,     off.dossier_type as ander_dossier_type,     off.dossier_b as ander_dossier_b,     off.doss_hdr_id as ander_doss_hdr_id,     off.raamcontract_jn as ander_raamcontract_jn,     off.offerte_brief_id,     off.brief_nr as offerte_brief_nr,     off.inzender_naam_voornaam as inzender_naam_voornaam from art46.voorstel_deelopdracht vd         inner join art46.v_dossier dos on vd.DOSSIER_ID = dos.id     inner join art46.VOORSTEL_DEELOPDRACHT_STATUS vds on vd.status = vds.key         inner join art46.v_offerte off on vd.offerte_id = off.ID;



-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie, omschrijving) values ('6.29', 'toevoegen bestek.bestek_hdr_id');
