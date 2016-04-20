

--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;



delete from  art46.db_versie
where db_versie = '6.31';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

alter table art46.deelopdracht
    drop column stop_s;
;

call sysproc.admin_cmd('reorg table art46.deelopdracht')
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
be.dossier_nr as ander_dossier_nr,
be.dossier_b as ander_dossier_b,
be.doss_hdr_id as ander_doss_hdr_id,
be.raamcontract_jn as ander_raamcontract_jn,
do.bedrag,
do.goedkeuring_d,
do.voorstel_d,
do.afsluit_d,
do.wbs_nr,
do.creatie_ts,
do.wijzig_ts,
do.planning_lijn_id,
do.goedkeuring_bedrag,
do.offerte_id,
off.inzender as offerte_inzender,
off.status as offerte_status,
off.btw_tarief as offerte_btw_tarief,
off.totaal as offerte_totaal,
off.totaal_incl_btw as offerte_totaal_incl_btw,
do.voorstel_deelopdracht_id,
art46.voorstel_deelopdracht_nr(do.voorstel_deelopdracht_id) as voorstel_deelopdracht_nr,
coalesce(art46.pdrcht_schld(do.deelopdracht_id),0) as totaal_schuldvorderingen,
coalesce(art46.pdr_fct_sap(do.deelopdracht_id),0) as totaal_facturen
from art46.deelopdracht do
left join art46.v_dossier dos on do.dossier_id = dos.id
left join art46.v_bestek be on do.bestek_id = be.bestek_id
left join art46.offerte off on do.offerte_id = off.ID
;


select 'drop view ' || rtrim(v.VIEWSCHEMA) || '.' || rtrim(v.VIEWNAME) || ';' ||
       replace(cast(v.text as varchar(32000)), chr(10) , ' ') || ';'
from syscat.VIEWS v
where v.viewschema = 'ART46'
     and v.valid = 'N'
;





drop trigger art46.au_deelopdracht_hist;

create trigger art46.au_deelopdracht_hist
after update on art46.deelopdracht
referencing
    old as pre
    new as post
for each row mode db2sql
insert into art46.deelopdracht_hist (
    deelopdracht_id,
    creatie_ts,
    bestek_id,
    dossier_id,
    bedrag,
    goedkeuring_d,
    goedkeuring_bedrag,
    voorstel_d,
    afsluit_d,
    wbs_nr,
    planning_lijn_id,
    offerte_id,
    voorstel_deelopdracht_id
    )
values (
    pre.deelopdracht_id,
    pre.wijzig_ts,
    pre.bestek_id,
    pre.dossier_id,
    pre.bedrag,
    pre.goedkeuring_d,
    pre.goedkeuring_bedrag,
    pre.voorstel_d,
    pre.afsluit_d,
    pre.wbs_nr,
    pre.planning_lijn_id,
    pre.offerte_id,
    pre.voorstel_deelopdracht_id
)
;


-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie, omschrijving) values ('6.31', 'drop deelopdracht.stop_s');
