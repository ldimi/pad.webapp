--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

delete from  art46.db_versie
where db_versie = '5.24';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

alter table art46.dossier_overdracht
    rename column dossier_nr_afval to afval_dossier_naam;

alter table art46.dossier_overdracht_hist
    rename column dossier_nr_afval to afval_dossier_naam;



alter table art46.dossier_overdracht
    drop column overdracht_d;

call sysproc.admin_cmd('reorg table art46.dossier_overdracht');

alter table art46.dossier_overdracht_hist
     drop column overdracht_d;

call sysproc.admin_cmd('reorg table art46.dossier_overdracht_hist');


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
       afval_dossier_naam,
       rechtsgrond_code,
       commentaar,
       screener,
       screen_bestek_nr,
       screen_bestek_id,
       fiche_dms_id,
       fiche_dms_filename,
       fiche_dms_folder,
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
       pre.afval_dossier_naam,
       pre.rechtsgrond_code,
       pre.commentaar,
       pre.screener,
       pre.screen_bestek_nr,
       pre.screen_bestek_id,
       pre.fiche_dms_id,
       pre.fiche_dms_filename,
       pre.fiche_dms_folder,
       pre.status_start_d,
       pre.status,
       pre.deleted_jn,
       pre.wijzig_user
)
;


-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('5.24');
