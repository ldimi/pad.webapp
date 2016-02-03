

--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  art46.db_versie
where db_versie = '5.31';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

alter table art46.dossier_overdracht
    rename column afval_dossier_naam to dossier_b;

alter table art46.dossier_overdracht
   alter column dossier_b set data type varchar(80)
;

call sysproc.admin_cmd('reorg table art46.dossier_overdracht')
;

alter table art46.dossier_overdracht_hist
    rename column afval_dossier_naam to dossier_b;

alter table art46.dossier_overdracht_hist
   alter column dossier_b set data type varchar(80)
;


call sysproc.admin_cmd('reorg table art46.dossier_overdracht_hist')
;





alter table art46.dossier_overdracht
    add column fase_id       int
    add column bbo_prijs     int
    add column bbo_looptijd  int
    add column bsp_jn        char(1)
    add column bsp_prijs     int
    add column bsp_looptijd  int
    add column bsw_prijs     int
    add column bsw_looptijd  int
    add column actueel_risico_id       int
    add column beleidsmatig_risico_id  int
    add column integratie_risico_id   int
    add column potentieel_risico_id    int
    add column prioriteits_index       double
    add column timing_jaar int
    add column timing_maand int
    add constraint cc_timing_jaar check (timing_jaar > 1999 and timing_jaar < 2101)
    add constraint cc_timing_maand check (timing_maand > 0 and timing_maand < 13)
;


alter table art46.dossier_overdracht_hist
    add column fase_id       int
    add column bbo_prijs     int
    add column bbo_looptijd  int
    add column bsp_jn        char(1)
    add column bsp_prijs     int
    add column bsp_looptijd  int
    add column bsw_prijs     int
    add column bsw_looptijd  int
    add column actueel_risico_id       int
    add column beleidsmatig_risico_id  int
    add column integratie_risico_id   int
    add column potentieel_risico_id    int
    add column prioriteits_index       double
    add column timing_jaar int
    add column timing_maand int
;



call sysproc.admin_cmd('reorg table art46.dossier_overdracht')
;

call sysproc.admin_cmd('reorg table art46.dossier_overdracht_hist')
;





drop trigger  art46.au_dossier_overdr_hist;

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



-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('5.31');