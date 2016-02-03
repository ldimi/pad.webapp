

--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  art46.db_versie
where db_versie = '6.03';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;


alter table art46.DOSSIER_OVERDRACHT_hist
  add column dossier_id_tmp_boa integer
;
call sysproc.admin_cmd('reorg table ART46.DOSSIER_OVERDRACHT_HIST')
;

update art46.DOSSIER_OVERDRACHT_HIST
set dossier_id_tmp_boa = dossier_id_boa
where 1 = 1

alter table art46.DOSSIER_OVERDRACHT_hist
  rename column dossier_id_boa to dossier_id
;
call sysproc.admin_cmd('reorg table ART46.DOSSIER_OVERDRACHT_HIST')
;

alter table art46.DOSSIER_OVERDRACHT_hist
  rename column dossier_id_tmp_boa to dossier_id_boa
;
call sysproc.admin_cmd('reorg table ART46.DOSSIER_OVERDRACHT_HIST')
;

update art46.DOSSIER_OVERDRACHT_HIST doh
set dossier_id = (select id from art46.dossier where dossier_id_boa = doh.dossier_id_boa)
where 1 = 1
    and doh.dossier_type = 'B'
;







drop trigger art46.bu_dossier_overdracht_versie_nr;

drop trigger  art46.au_dossier_overdr_hist;




alter table art46.DOSSIER_OVERDRACHT
  add column dossier_id_tmp_boa integer
;
call sysproc.admin_cmd('reorg table ART46.DOSSIER_OVERDRACHT')
;

update art46.DOSSIER_OVERDRACHT
set dossier_id_tmp_boa = dossier_id_boa
where 1 = 1

alter table art46.DOSSIER_OVERDRACHT
  rename column dossier_id_boa to dossier_id
;
call sysproc.admin_cmd('reorg table ART46.DOSSIER_OVERDRACHT')
;

alter table art46.DOSSIER_OVERDRACHT
  rename column dossier_id_tmp_boa to dossier_id_boa
;
call sysproc.admin_cmd('reorg table ART46.DOSSIER_OVERDRACHT')
;

update art46.DOSSIER_OVERDRACHT doso
set dossier_id = (select id from art46.dossier where dossier_id_boa = doso.dossier_id_boa)
where 1 = 1
    and doso.dossier_type = 'B'
;





create trigger art46.au_dossier_overdr_hist
after update on art46.dossier_overdracht
referencing
    old as pre
    new as post
for each row mode db2sql
insert into art46.dossier_overdracht_hist (
       overdracht_id,
       versie_nr,
       dossier_type,
       dossier_id,
       dossier_id_boa,
       dossier_b,
       rechtsgrond_code,
       commentaar,
       screener,
       screen_bestek_nr,
       screen_bestek_id,
       fiche_dms_id,
       fiche_dms_filename,
       fiche_dms_folder,
       fase_id,
       bbo_prijs,
       bbo_looptijd,
       bsp_jn,
       bsp_prijs,
       bsp_looptijd,
       bsw_prijs,
       bsw_looptijd,
       actueel_risico_id,
       beleidsmatig_risico_id,
       integratie_risico_id,
       potentieel_risico_id,
       prioriteits_index,
       timing_jaar,
       timing_maand,
       status_start_d,
       status,
       deleted_jn,
       wijzig_user
    )
values (
       pre.overdracht_id,
       pre.versie_nr,
       pre.dossier_type,
       pre.dossier_id,
       pre.dossier_id_boa,
       pre.dossier_b,
       pre.rechtsgrond_code,
       pre.commentaar,
       pre.screener,
       pre.screen_bestek_nr,
       pre.screen_bestek_id,
       pre.fiche_dms_id,
       pre.fiche_dms_filename,
       pre.fiche_dms_folder,
       pre.fase_id,
       pre.bbo_prijs,
       pre.bbo_looptijd,
       pre.bsp_jn,
       pre.bsp_prijs,
       pre.bsp_looptijd,
       pre.bsw_prijs,
       pre.bsw_looptijd,
       pre.actueel_risico_id,
       pre.beleidsmatig_risico_id,
       pre.integratie_risico_id,
       pre.potentieel_risico_id,
       pre.prioriteits_index,
       pre.timing_jaar,
       pre.timing_maand,
       pre.status_start_d,
       pre.status,
       pre.deleted_jn,
       pre.wijzig_user
)
;

create trigger art46.bu_dossier_overdracht_versie_nr no cascade before
update on art46.dossier_overdracht referencing old as o new as n for each row mode db2sql when
(
   n.versie_nr != o.versie_nr + 1
)
signal sqlstate '75000' set message_text='ongeldig versie_nr. (stale data)'
;



drop view art46.v_dossier_overdracht_historiek;

create view art46.v_dossier_overdracht_historiek as with doh as
(
   select
   *
   from art46.dossier_overdracht_hist
   union
   select
   *
   from art46.dossier_overdracht
)
select
doh.*
from doh
left join doh as doh_next on doh.overdracht_id = doh_next.overdracht_id
and doh.versie_nr + 1 = doh_next.versie_nr
where 1 = 1
and (doh.status != doh_next.status or doh_next.status is null)
;


-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('6.03');
