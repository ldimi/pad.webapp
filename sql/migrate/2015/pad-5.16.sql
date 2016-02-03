--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  art46.db_versie
where db_versie = '5.16';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;


alter table art46.dossier_overdracht
   drop foreign key fk_dov_status
;

drop trigger art46.au_dossier_overdr_hist;



update art46.dossier_overdracht
set status = 'registrati'
where status= 'voorstel'
;

update art46.dossier_overdracht
set status = 'instroom'
where status= 'acceptatie'
;

update art46.dossier_overdracht
set status = 'na_screen'
where status= 'nascreen'
;

update art46.dossier_overdracht
set status = 'naar_ivs'
where status= 'toegewezen'
;


update art46.dossier_overdracht
set status = 'geklassrd'
where status= 'afgewezen'
;




update art46.dossier_overdracht_hist
set status = 'registrati'
where status= 'voorstel'
;

update art46.dossier_overdracht_hist
set status = 'instroom'
where status= 'acceptatie'
;

update art46.dossier_overdracht_hist
set status = 'na_screen'
where status= 'nascreen'
;

update art46.dossier_overdracht_hist
set status = 'naar_ivs'
where status= 'toegewezen'
;


update art46.dossier_overdracht_hist
set status = 'geklassrd'
where status= 'afgewezen'
;



delete from art46.dossier_overdracht_status;

insert into art46.dossier_overdracht_status (code, omschrijving, volg_nr)
values
    ('registrati','registratie (voorstel)', 1),
    ('instroom','instroom (acceptatie)', 2),
    ('screening','in screening', 3),
    ('na_screen','screening afgerond (nascreen)', 4),
    ('bib','bibliotheek', 5),
    ('uitstroom','uitstroom', 6),
    ('naar_ivs','naar ivs (toegewezen)', 7),
    ('geklassrd','geklasseerd (afgewezen)', 8)
;





alter table art46.dossier_overdracht
   foreign key fk_dov_status(status)
           references art46.dossier_overdracht_status (code) on delete restrict
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
       pre.overdracht_d,
       pre.status_start_d,
       pre.status,
       pre.deleted_jn,
       pre.wijzig_user
)
;



-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('5.16');



