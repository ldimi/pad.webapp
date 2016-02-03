--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

delete from  ART46.DB_VERSIE
where DB_VERSIE = '4.43';



--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;


-- voor digitaal traject : copieren van aanvraag_pdf_brief_id naar brief_id
update art46.schuldvordering sv
set brief_id = (
                 select sv2.aanvraag_pdf_brief_id
                 from art46.schuldvordering sv2
                 where sv2.vordering_id = sv.vordering_id
                    and sv2.aanvraag_pdf_brief_id is not null
               )
where 1 = 1
    and sv.brief_id is null


-- dms informatie naar brief verplaatsen
update art46.brief as b
    set (b.dms_id, b.dms_filename, b.dms_folder) = (
                    select pdf.dms_id, pdf.dms_filename, pdf.dms_folder
                    from art46.schuldvordering sv
                             inner join art46.brief pdf
                             on sv.aanvraag_pdf_brief_id = pdf.brief_id
                    where sv.brief_id = b.brief_id
                    )
where b.brief_id in (
                      select brief_id
                      from art46.schuldvordering
                      where aanvraag_pdf_brief_id is not null
                    )
;



--delete
--from art46.brief
--where brief_id in (
--                      select aanvraag_pdf_brief_id
--                      from art46.schuldvordering
--                      where aanvraag_pdf_brief_id is not null
--                            and brief_id != aanvraag_pdf_brief_id
--                    )
--;

--alter table art46.schuldvordering
--    drop column AANVRAAG_PDF_BRIEF_ID
--;
--CALL SYSPROC.ADMIN_CMD('REORG TABLE ART46.schuldvordering');



CREATE TABLE ART46.MAIL (
  id int PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY,
  to VARCHAR (120) not null,
  subject VARCHAR(8) not null,
  from timestamp not null,
  message VARCHAR(2028) not null
);



ALTER TABLE ART46.VOORSTEL_DEELOPDRACHT ADD MAIL_ID int;
CALL SYSPROC.ADMIN_CMD('REORG TABLE ART46.VOORSTEL_DEELOPDRACHT');


alter table ART46.VOORSTEL_DEELOPDRACHT
   add constraint FK_vdo_mail foreign key (mail_id)
      references art46.mail (id)
      on delete restrict on update restrict
;




-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('4.43');