--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  art46.db_versie
where db_versie = '4.91';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

create table art46.schuldvordering_fase
(
    schuldvordering_fase_id integer not null,
    schuldvordering_fase_b  varchar(50) not null,
    brief_categorie_id integer not null,
    primary key (schuldvordering_fase_id),
    foreign key fk_svf_brief_categorie (brief_categorie_id)
         references art46.brief_categorie (brief_categorie_id)
         on delete restrict
);

insert into art46.schuldvordering_fase
(schuldvordering_fase_id, schuldvordering_fase_b, brief_categorie_id)
values
(12, 'ABBO', 12),
(13, 'ABSP', 13),
(14, 'ABSW-ANZ', 14),
(15, 'VEIM-VZM', 15)
;


alter table art46.schuldvordering
   drop constraint FK_sv_brief_categorie
;

alter table art46.schuldvordering
    drop constraint cc_brief_categorie
;

alter table art46.schuldvordering
    rename column brief_categorie_id to schuldvordering_fase_id
;

call sysproc.admin_cmd('reorg table art46.schuldvordering')
;

alter table art46.SCHULDVORDERING
   add constraint fk_sv_fase foreign key (schuldvordering_fase_id)
      references art46.schuldvordering_fase (schuldvordering_fase_id)
      on delete restrict on update restrict
;




alter table art46.SCHULDVORDERING
   drop constraint fk_sv_gebruiker;

alter table art46.schuldvordering
    rename column gebruiker_id to webloket_gebruiker_id
;

call sysproc.admin_cmd('reorg table art46.schuldvordering')
;


alter table art46.SCHULDVORDERING
   add constraint fk_sv_webloket_gebruiker foreign key (webloket_gebruiker_id)
      references art46.webloket_gebruiker (id)
      on delete restrict on update restrict
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
    sv.webloket_gebruiker_id,
    wg.email_address as webloket_gebruiker_email
from art46.schuldvordering sv
    inner join art46.v_bestek be on sv.bestek_id = be.bestek_id
    left join art46.brief br on sv.brief_id = br.brief_id
    left join art46.v_deelopdracht do on sv.deelopdracht_id = do.deelopdracht_id
    left join art46.webloket_gebruiker wg on sv.webloket_gebruiker_id = wg.id
    left join art46.v_ambtenaar ambt_be on be.doss_hdr_id = ambt_be.ambtenaar_id
    left join art46.v_ambtenaar ambt_do on do.doss_hdr_id = ambt_do.ambtenaar_id
    left join art46.aanvr_schuldvordering asv on sv.aanvr_schuldvordering_id = asv.aanvr_schuldvordering_id
    left join art46.schuldvordering_fase svf on sv.schuldvordering_fase_id = svf.schuldvordering_fase_id
;


-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('4.91');





























