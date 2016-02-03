--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  art46.db_versie
where db_versie = '4.90';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

alter table art46.meetstaat_schuldvordering_regel
    drop constraint fk_msvr_offerte_regel
;

alter table art46.meetstaat_schuldvordering_regel
    drop constraint fk_msvr_meetstaatregel_type
;

alter table art46.meetstaat_schuldvordering_regel
    drop constraint fk_d9jvfv92kqbvgr7oic5yvgeyb
;

alter table art46.meetstaat_schuldvordering_regel
    drop constraint fk_msvr_meetstaat_eenheid
;


rename table art46.meetstaat_schuldvordering_regel to aanvr_schuldvordering_regel;

call sysproc.admin_cmd('reorg table art46.aanvr_schuldvordering_regel');

alter table art46.aanvr_schuldvordering_regel
    rename column SCHULDVORDERINGID to aanvr_schuldvordering_id
;

call sysproc.admin_cmd('reorg table art46.aanvr_schuldvordering_regel');


alter table art46.aanvr_schuldvordering_regel
    add constraint fk_asvr_offerte_regel
    foreign key (offerte_regel_id)
    references art46.offerte_regel(id)
;

alter table art46.aanvr_schuldvordering_regel
    add constraint fk_asvr_meetstaatregel_type
    foreign key (meerwerken_type)
    references art46.meetstaatregel_type(type)
;

alter table art46.aanvr_schuldvordering_regel
    add constraint fk_asvr_meetstaat_eenheid foreign key (meerwerken_eenheid)
        references art46.meetstaat_eenheid (naam)
        on delete restrict on update restrict
;

alter table art46.aanvr_schuldvordering_regel
    add constraint fk_asvr_aanvraag_schuldvordering
    foreign key (aanvr_schuldvordering_id)
    references art46.meetstaat_schuldvordering(id)
;







alter table art46.webloket_bijlage
   drop constraint fk_wlb_aanvr_schuldvordering
;

alter table art46.schuldvordering
   drop constraint FK_sv_aanvraag_id
;

alter table art46.aanvr_schuldvordering_regel
    drop constraint fk_asvr_aanvraag_schuldvordering
;




alter table art46.meetstaat_schuldvordering
    drop constraint fk_ms_deelopdracht
;

alter table art46.meetstaat_schuldvordering
   drop constraint fk_msv_offerte
;


drop  view art46.v_aanvraag_schuldvordering;




rename table art46.meetstaat_schuldvordering to aanvr_schuldvordering;

call sysproc.admin_cmd('reorg table art46.aanvr_schuldvordering');

alter table art46.aanvr_schuldvordering  drop primary key;

alter table art46.aanvr_schuldvordering
    rename column id to aanvr_schuldvordering_id
;

alter table art46.aanvr_schuldvordering add primary key(aanvr_schuldvordering_id);

call sysproc.admin_cmd('reorg table art46.aanvr_schuldvordering');



alter table art46.webloket_bijlage drop constraint cc_aanvraag_voorstel;

alter table art46.webloket_bijlage
    rename column schuldvordering_id to aanvr_schuldvordering_id
;
call sysproc.admin_cmd('reorg table art46.webloket_bijlage');

alter table art46.webloket_bijlage
    add constraint cc_aanvraag_voorstel check (
                                                (aanvr_schuldvordering_id is null and voorstel_id is not null) or
                                                (aanvr_schuldvordering_id is not null and voorstel_id is null)
                                              )
;


alter table art46.webloket_bijlage
   add constraint fk_wlb_aanvr_schuldvordering foreign key (aanvr_schuldvordering_id)
      references art46.aanvr_schuldvordering (aanvr_schuldvordering_id)
      on delete restrict on update restrict
;


ALTER TABLE ART46.SCHULDVORDERING
    drop constraint cc_brief_of_aanvraag
;

alter table art46.schuldvordering
    rename column aanvraag_schuldvordering_id to aanvr_schuldvordering_id
;

ALTER TABLE ART46.SCHULDVORDERING
    add constraint cc_brief_of_aanvraag check ( (brief_id is null and aanvr_schuldvordering_id is not null) or
                                                (brief_id is not null and aanvr_schuldvordering_id is null)  )
;

call sysproc.admin_cmd('reorg table art46.schuldvordering');


drop trigger art46.bi_schuldv_brief_of_aanvraag;
create trigger art46.bi_schuldv_brief_of_aanvraag
no cascade before insert on art46.schuldvordering
referencing
    new as post
for each row mode db2sql
set
        post.brief_of_aanvraag_id = case when post.brief_id is not null then 'B' || rtrim(cast(post.brief_id as char(10)))
                                         else 'A' || rtrim(cast(post.aanvr_schuldvordering_id as char(10)))
                                    end
;

drop trigger art46.bu_schuldv_brief_of_aanvraag;
create trigger art46.bu_schuldv_brief_of_aanvraag
no cascade before update on art46.schuldvordering
referencing
    old as pre
    new as post
for each row mode db2sql
set
        post.brief_of_aanvraag_id = case when post.brief_id is not null then 'B' || rtrim(cast(post.brief_id as char(10)))
                                         else 'A' || rtrim(cast(post.aanvr_schuldvordering_id as char(10)))
                                    end
;



alter table art46.schuldvordering
   add constraint FK_sv_aanvraag_id foreign key (aanvr_schuldvordering_id)
      references art46.aanvr_schuldvordering (aanvr_schuldvordering_id)
      on delete restrict on update restrict
;

alter table art46.aanvr_schuldvordering_regel
    add constraint fk_asvr_aanvraag_schuldvordering
    foreign key (aanvr_schuldvordering_id)
    references art46.aanvr_schuldvordering(aanvr_schuldvordering_id)
;




alter table art46.aanvr_schuldvordering
    add constraint fk_asv_deelopdracht foreign key (deelopdracht_id)
    references art46.deelopdracht(deelopdracht_id)
;

alter table art46.aanvr_schuldvordering
   add constraint fk_asv_offerte foreign key (offerte_id)
      references art46.offerte (id)
      on delete restrict on update restrict
;


create view art46.v_aanvr_schuldvordering as
    select
        asv.aanvr_schuldvordering_id as aanvr_schuldvordering_id,
        asv.offerte_id,
        asv.beschrijving,
        asv.ingezonden,
        asv.bedrag_excl_btw,
        asv.bedrag_incl_btw,
        asv.bedrag_prijsherziening,
        asv.gecorrigeerd_herziening_bedrag,
        asv.opmerking,
        asv.van_datum,
        asv.tot_datum,
        asv.updated,
        asv.deelopdracht_id,
        (asv.bedrag_incl_btw + coalesce(asv.gecorrigeerd_herziening_bedrag, asv.bedrag_prijsherziening, 0)) as totaal_incl_prijsherziening,
        sv.vordering_id as schuldvordering_id,
        art46.schuldvordering_nr(sv.vordering_id) as schuldvordering_nr,
        sv.goedkeuring_bedrag as schuldvordering_goedkeuring_bedrag,
        coalesce(sv.afgekeurd_jn,'N') as afgekeurd_jn,
        coalesce(sv.status, 'IN OPMAAK') as status,
        coalesce(st.NAME, 'IN OPMAAK') as status_pad,
        coalesce(st.NAME_WEB_LOKER, 'IN OPMAAK') as status_webloket
    from art46.aanvr_schuldvordering as asv
            left join art46.schuldvordering sv
            on asv.aanvr_schuldvordering_id = sv.aanvr_schuldvordering_id
         left join art46.schuldvordering_status st
         on sv.STATUS = st.key
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
    sv.aanvr_schuldvordering_id,
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












-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('4.90');





























