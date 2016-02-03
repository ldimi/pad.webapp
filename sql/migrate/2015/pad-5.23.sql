--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

delete from  art46.db_versie
where db_versie = '5.23';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;


alter table art46.dossier_overdracht
    add column screen_bestek_id int
    add column fiche_dms_id varchar(100)
    add column fiche_dms_filename varchar(100)
    add column fiche_dms_folder varchar(255)
;

call sysproc.admin_cmd('reorg table art46.dossier_overdracht')
;

alter table art46.dossier_overdracht
   add foreign key fk_dov_screen_bestek(screen_bestek_id)
           references art46.bestek(bestek_id) on delete restrict
;


alter table art46.dossier_overdracht_hist
    add column screen_bestek_id int
    add column fiche_dms_id varchar(100)
    add column fiche_dms_filename varchar(100)
    add column fiche_dms_folder varchar(255)
;

call sysproc.admin_cmd('reorg table art46.dossier_overdracht_hist')
;

drop trigger art46.au_dossier_overdr_hist



update art46.DOSSIER_OVERDRACHT doso
set screen_bestek_id = (select b.bestek_id from art46.bestek b where b.bestek_nr = doso.screen_bestek_nr)
where 1 = 1
    and doso.SCREEN_BESTEK_NR is not null
    and doso.screen_bestek_id is null
;

update art46.DOSSIER_OVERDRACHT_hist dosoh
set screen_bestek_id = (select b.bestek_id from art46.bestek b where b.bestek_nr = dosoh.screen_bestek_nr)
where 1 = 1
    and dosoh.SCREEN_BESTEK_NR is not null
    and dosoh.screen_bestek_id is null
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
       dossier_id_boa,
       dossier_nr_afval,
       rechtsgrond_code,
       commentaar,
       screener,
       screen_bestek_nr,
       screen_bestek_id,
       fiche_dms_id,
       fiche_dms_filename,
       fiche_dms_folder,
       overdracht_d,
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
       pre.dossier_nr_afval,
       pre.rechtsgrond_code,
       pre.commentaar,
       pre.screener,
       pre.screen_bestek_nr,
       pre.screen_bestek_id,
       pre.fiche_dms_id,
       pre.fiche_dms_filename,
       pre.fiche_dms_folder,
       pre.overdracht_d,
       pre.status_start_d,
       pre.status,
       pre.deleted_jn,
       pre.wijzig_user
)
;

drop view art46.v_dossier_overdracht_historiek

create view art46.v_dossier_overdracht_historiek as
    with doh as (
        select *
        from art46.dossier_overdracht_hist
        union
        select *
        from art46.dossier_overdracht
    )
    select
        doh.*
    from  doh
            left join doh as doh_next
            on doh.overdracht_id = doh_next.overdracht_id
            and doh.versie_nr + 1 = doh_next.versie_nr
    where 1 = 1
        and (doh.status != doh_next.status or doh_next.status is null)
;



-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('5.23');
