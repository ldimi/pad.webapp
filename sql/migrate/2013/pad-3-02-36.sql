
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

ALTER TABLE ART46.Bestek
    DROP CONSTRAINT FK_BE_PDF_BRIEF
;


ALTER TABLE ART46.Bestek
    DROP CONSTRAINT FK_BE_EXCEL_BRIEF
;


ALTER TABLE ART46.Bestek
	DROP COLUMN meetstaat_pdf_brief_id
	DROM COLUMN meetstaat_excel_brief_id
;

call sysproc.admin_cmd('reorg table ART46.Bestek')
;


delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.36';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

ALTER TABLE ART46.Bestek
	ADD COLUMN meetstaat_pdf_brief_id INTEGER
	ADD COLUMN meetstaat_excel_brief_id INTEGER
;

call sysproc.admin_cmd('reorg table ART46.Bestek')
;

ALTER TABLE ART46.Bestek
    ADD CONSTRAINT FK_BE_PDF_BRIEF FOREIGN KEY (meetstaat_pdf_brief_id)
    REFERENCES ART46.BRIEF(brief_id)
    ADD CONSTRAINT FK_BE_EXCEL_BRIEF FOREIGN KEY (meetstaat_excel_brief_id)
    REFERENCES ART46.BRIEF(brief_id)
;

-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.36');
