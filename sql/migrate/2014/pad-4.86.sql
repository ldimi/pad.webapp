--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  art46.db_versie
where db_versie = '4.86';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

-- schuldvordering.aanvraag_pdf_brief_id  veld verwijderen

alter table art46.schuldvordering
     drop foreing key FK_SV_AANVRAAG_PDF_BRIEF
;

alter table art46.schuldvordering
    drop column aanvraag_pdf_brief_id
;

call sysproc.admin_cmd('reorg table art46.schuldvordering')
;




-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('4.86');

