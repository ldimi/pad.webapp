--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

alter table art46.schuldvordering
    add constraint cc_aanvraag_pdf_brief
;

alter table art46.schuldvordering
    add constraint cc_vordering_nr
;

alter table art46.schuldvordering
    drop constraint cc_print_d
;

alter table art46.schuldvordering
    drop constraint cc_acceptatie_d
;

alter table art46.schuldvordering
    drop constraint cc_goedkeuring_d
;

alter table art46.schuldvordering
    alter column status set null
;
CALL SYSPROC.ADMIN_CMD('REORG TABLE ART46.SCHULDVORDERING');


alter table art46.schuldvordering
    add constraint CC_UITERSTE_VERIFIC_D CHECK( UITERSTE_VERIFIC_D is not null or CREATIE_TS < '2013-07-03 11:00:00')
;

alter table art46.schuldvordering
    alter column uiterste_verificatie_d set null
;
CALL SYSPROC.ADMIN_CMD('REORG TABLE ART46.SCHULDVORDERING');



delete from  ART46.DB_VERSIE
where DB_VERSIE = '4.34';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

alter table art46.schuldvordering
    alter column uiterste_verific_d set not null
;
CALL SYSPROC.ADMIN_CMD('REORG TABLE ART46.SCHULDVORDERING');


alter table art46.schuldvordering
    drop constraint CC_UITERSTE_VERIFIC_D
;



alter table art46.schuldvordering
    alter column status set not null
;
CALL SYSPROC.ADMIN_CMD('REORG TABLE ART46.SCHULDVORDERING');



alter table art46.schuldvordering
    add constraint cc_acceptatie_d CHECK( (acceptatie_d is null and  status in ('INGEDIEND') ) OR
                                          (acceptatie_d is not null and status in ('BEOORDEELD', 'ONDERTEKEND', 'VERZONDEN') )
                                        )
;

alter table art46.schuldvordering
    add constraint cc_goedkeuring_d CHECK( (goedkeuring_d is null and  status in ('INGEDIEND', 'BEOORDEELD') ) OR
                                          (goedkeuring_d is not null and status in ('ONDERTEKEND', 'VERZONDEN') )
                                        )
;

-- print_d wordt alleen ingevuld bij niet digitaal traject;

update art46.SCHULDVORDERING
set print_d = goedkeuring_d
WHERE 1 = 1
    and brief_id is not null
    and (print_d is null and status in ('VERZONDEN'))

alter table art46.schuldvordering
    add constraint cc_print_d CHECK( (brief_id is null and print_d is null) OR
                                     (brief_id is not null and ( (print_d is null and status not in ('VERZONDEN') ) OR
                                                                 (print_d is not null and status in ('VERZONDEN') )
                                                               )
                                     )
                                   )
;



alter table art46.schuldvordering
    add constraint cc_vordering_nr check ( (brief_id is null and vordering_nr is null) OR
                                           (brief_id is not null)
                                         )
;


-- alleen in niet-digitaal traject wordt aanvraag_pdf_brief_id ingevuld (= scannen )

alter table art46.schuldvordering
    add constraint cc_aanvraag_pdf_brief check ( (brief_id is null and aanvraag_pdf_brief_id is null) OR
                                                 (brief_id is not null)
                                               )
;


alter table art46.schuldvordering
    add constraint cc_antwoord_pdf_brief check ( (antwoord_pdf_brief_id is null and  status in ('INGEDIEND', 'BEOORDEELD') ) OR
                                                 status in ('ONDERTEKEND', 'VERZONDEN')
                                               )
;



-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('4.34');




























