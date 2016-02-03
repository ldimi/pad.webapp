--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

drop view art46.v_bestek;

delete from  art46.db_versie
where db_versie = '4.80';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

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
           inner join art46.v_dossier dos
           on be.dossier_id = dos.id
     inner join art46.bestek_type bt
     on be.type_id = bt.type_id
        inner join art46.bestek_procedure bp
        on be.procedure_id = bp.procedure_id
     inner join art46.bestek_fase bf
     on be.fase_id = bf.fase_id
;

alter table art46.schuldvordering
    rename column inzender to gebruiker_id
;

call sysproc.admin_cmd('reorg table art46.schuldvordering')
;


alter table art46.SCHULDVORDERING
   add constraint fk_sv_gebruiker foreign key (gebruiker_id)
      references art46.webloket_gebruiker (id)
      on delete restrict on update restrict
;


create view art46.v_schuldvordering as
select
       sv.vordering_id,
       art46.schuldvordering_nr(sv.vordering_id) as schuldvordering_nr,
       sv.bestek_id,
       be.bestek_nr,
       be.dossier_id,
       be.dossier_nr,
       sv.dossier_type,
       be.raamcontract_jn,
       be.dossier_b,
       be.dossier_b_l,
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
       do.doss_hdr_id as deelopdracht_doss_hdr_id,
       do.dossier_b_l as deelopdracht_dossier_b_l,
       sv.betaal_d,
       sv.creatie_ts,
       sv.wijzig_ts,
       sv.uiterste_verific_d,
       sv.afgekeurd_jn,
       sv.aanvraag_schuldvordering_id,
       sv.brief_of_aanvraag_id,
       sv.acceptatie_d,
       sv.antwoord_pdf_brief_id,
       sv.aanvraag_pdf_brief_id,
       sv.print_d,
       sv.status,
       sv.vordering_correct_bedrag,
       sv.herziening_correct_bedrag,
       sv.brief_categorie_id,
       sv.gebruiker_id
from art46.schuldvordering sv
            inner join art46.v_bestek be
            on sv.bestek_id = be.bestek_id
     left join art46.brief br
     on sv.brief_id = br.brief_id
        left join art46.v_deelopdracht do
        on sv.deelopdracht_id = do.deelopdracht_id
;






-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('4.80');

