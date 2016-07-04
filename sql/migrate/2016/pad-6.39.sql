

--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

drop table art46.webloket_medewerkersrol;

delete from  art46.db_versie
where db_versie = '6.39';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

alter table art46.dossier_overdracht 
    add column screening_jn char(1) not null default 'J'
    add column aanmaak_pad_jn char(1) not null default 'J'
    add constraint cc_aanmaak_pad check 
        ( (aanmaak_pad_jn = 'N' and screening_jn = 'N') or aanmaak_pad_jn = 'J' )
    foreign key fk_dov_screening_jn (screening_jn)
         references art46.ja_nee_code (code)
         on delete restrict
    foreign key fk_dov_aanmaak_pad_jn (aanmaak_pad_jn)
         references art46.ja_nee_code (code)
         on delete restrict
;


alter table art46.dossier_overdracht_hist 
    add column screening_jn char(1) not null default 'J'
    add column aanmaak_pad_jn char(1) not null default 'J'
;


drop trigger art46.au_dossier_overdr_hist;

create trigger art46.au_dossier_overdr_hist after
update on art46.dossier_overdracht referencing old as pre new as post for each row mode db2sql
insert into art46.dossier_overdracht_hist 
(overdracht_id    ,versie_nr    ,dossier_type    ,dossier_id    ,dossier_b    ,commentaar    ,screener    ,screen_bestek_nr    ,screen_bestek_id    ,status_start_d    ,status    ,deleted_jn    , screening_jn   , aanmaak_pad_jn   , wijzig_user   ) VALUES
(pre.overdracht_id,pre.versie_nr,pre.dossier_type,pre.dossier_id,pre.dossier_b,pre.commentaar,pre.screener,pre.screen_bestek_nr,pre.screen_bestek_id,pre.status_start_d,pre.status,pre.deleted_jn,pre.screening_jn,pre.aanmaak_pad_jn,pre.wijzig_user)
;

-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie, omschrijving) values ('6.39', 'dossier_overdracht:  toevoegen screening_jn   en   aanmaak_pad_jn');
