

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
    drop column fiche_dms_id
    drop column fiche_dms_filename
    drop column fiche_dms_folder
    drop column timing_jaar
    drop column timing_maand
    drop column bbo_prijs
    drop column bbo_looptijd
    drop column bsp_jn
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
    drop column fiche_dms_id
    drop column fiche_dms_filename
    drop column fiche_dms_folder
    drop column timing_jaar
    drop column timing_maand
    drop column bbo_prijs
    drop column bbo_looptijd
    drop column bsp_jn
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
(overdracht_id    ,versie_nr    ,dossier_type    ,dossier_id    ,dossier_b    ,commentaar    ,screener    ,screen_bestek_nr    ,screen_bestek_id    ,status_start_d    ,status    ,deleted_jn    ,wijzig_user    ) VALUES
(pre.overdracht_id,pre.versie_nr,pre.dossier_type,pre.dossier_id,pre.dossier_b,pre.commentaar,pre.screener,pre.screen_bestek_nr,pre.screen_bestek_id,pre.status_start_d,pre.status,pre.deleted_jn,pre.wijzig_user)
;

select 'drop view ' || rtrim(v.VIEWSCHEMA) || '.' || rtrim(v.VIEWNAME) || ';' ||
       replace(cast(v.text as varchar(32000)), chr(10) , ' ') || ';'
from syscat.VIEWS v
where v.viewschema = 'ART46'
     and v.valid = 'N'
;

drop view ART46.V_DOSSIER_OVERDRACHT_HISTORIEK;create view art46.v_dossier_overdracht_historiek as with doh as (    select    *    from art46.dossier_overdracht_hist    union    select    *    from art46.dossier_overdracht ) select doh.* from doh left join doh as doh_next on doh.overdracht_id = doh_next.overdracht_id and doh.versie_nr + 1 = doh_next.versie_nr where 1 = 1 and (doh.status != doh_next.status or doh_next.status is null);





-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('6.16');
