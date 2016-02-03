--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

drop view art46.v_aanvraag_schuldvordering;

delete from  ART46.DB_VERSIE
where DB_VERSIE = '4.68';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

create view art46.v_aanvraag_schuldvordering as
    select
        asv.id as aanvraag_schuldvordering_id,
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
    from art46.meetstaat_schuldvordering as asv
            left join art46.schuldvordering sv
            on asv.id = sv.aanvraag_schuldvordering_id
         left join art46.schuldvordering_status st
         on sv.STATUS = st.key
;


-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('4.68');

