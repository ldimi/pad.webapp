

--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;



delete from  art46.db_versie
where db_versie = '6.16';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

drop trigger art46.au_dossier_overdr_hist;

alter table art46.dossier_overdracht
    drop column rechtsgrond_code
    drop column fase_id
    drop column timing_jaar
    drop column timing_maand
    drop column bbo_prijs
    drop column bbo_looptijd
    drop column bsp_jn char(1)
    drop column bsp_prijs
    drop column bsp_looptijd
    drop column bsw_prijs
    drop column bsw_looptijd
    drop column actueel_risico_id
    drop column beleidsmatig_risico_id
    drop column integratie_risico_id
    drop column potentieel_risico_id
    drop column prioriteits_index
    drop column dossier_id_boa
;
call sysproc.admin_cmd('reorg table art46.dossier_overdracht');

alter table art46.dossier_overdracht_hist
    drop column rechtsgrond_code
    drop column fase_id
    drop column timing_jaar
    drop column timing_maand
    drop column bbo_prijs
    drop column bbo_looptijd
    drop column bsp_jn char(1)
    drop column bsp_prijs
    drop column bsp_looptijd
    drop column bsw_prijs
    drop column bsw_looptijd
    drop column actueel_risico_id
    drop column beleidsmatig_risico_id
    drop column integratie_risico_id
    drop column potentieel_risico_id
    drop column prioriteits_index
    drop column dossier_id_boa
;
call sysproc.admin_cmd('reorg table art46.dossier_overdracht_hist');


create trigger art46.au_dossier_overdr_hist after
update on art46.dossier_overdracht referencing old as pre new as post for each row mode db2sql
insert into art46.dossier_overdracht_hist 
(overdracht_id    ,versie_nr    ,dossier_type    ,dossier_id    ,dossier_b    ,commentaar    ,screener    ,screen_bestek_nr    ,screen_bestek_id    ,fiche_dms_id    ,fiche_dms_filename    ,fiche_dms_folder    ,status_start_d    ,status    ,deleted_jn    ,wijzig_user    ) VALUES
(pre.overdracht_id,pre.versie_nr,pre.dossier_type,pre.dossier_id,pre.dossier_b,pre.commentaar,pre.screener,pre.screen_bestek_nr,pre.screen_bestek_id,pre.fiche_dms_id,pre.fiche_dms_filename,pre.fiche_dms_folder,pre.status_start_d,pre.status,pre.deleted_jn,pre.wijzig_user)
;

select 'drop view ' || rtrim(v.VIEWSCHEMA) || '.' || rtrim(v.VIEWNAME) || ';' ||
       replace(cast(v.text as varchar(32000)), chr(10) , ' ') || ';'
from syscat.VIEWS v
where v.viewschema = 'ART46'
     and v.valid = 'N'
;







-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('6.15');
