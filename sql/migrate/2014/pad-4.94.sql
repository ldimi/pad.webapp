--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

delete from  art46.db_versie
where db_versie = '4.94';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;


alter table art46.aanvr_schuldvordering
    alter column bedrag_excl_btw drop not null
    alter column bedrag_incl_btw drop not null
    alter column van_datum drop not null
    alter column tot_datum drop not null
;

call sysproc.admin_cmd('reorg table art46.aanvr_schuldvordering')
;

insert into art46.schuldvordering_status
(key,name,name_web_loker)
values
('IN OPMAAK', 'IN OPMAAK', 'IN OPMAAK')
;


alter table art46.schuldvordering
    alter column vordering_d drop not null
    alter column uiterste_d drop not null
    alter column uiterste_verific_d drop not null
;
call sysproc.admin_cmd('reorg table art46.schuldvordering');

alter table art46.schuldvordering
    add constraint cc_schuldvordering_vordering_d_status
        check ( (status = 'IN OPMAAK' and vordering_d is null) OR
                (status != 'IN OPMAAK' and vordering_d is not null)
              )
    add constraint cc_schuldvordering_uiterste_d_status
        check ( (status = 'IN OPMAAK' and uiterste_d is null) OR
                (status != 'IN OPMAAK' and uiterste_d is not null)
              )
    add constraint cc_schuldvordering_uiterste_verific_d_status
        check ( (status = 'IN OPMAAK' and uiterste_verific_d is null) OR
                (status != 'IN OPMAAK' and uiterste_verific_d is not null)
              )
;

alter table art46.schuldvordering
    drop constraint cc_antwoord_pdf_brief
    drop constraint cc_acceptatie_d
    drop constraint cc_goedkeuring_d
;

alter table art46.schuldvordering
    add constraint cc_antwoord_pdf_brief check ( (antwoord_pdf_brief_id is null and  status not in ('ONDERTEKEND', 'VERZONDEN') ) OR
                                                 status in ('ONDERTEKEND', 'VERZONDEN')
                                               )
    add constraint cc_acceptatie_d CHECK( (acceptatie_d is null and  status not in ('BEOORDEELD', 'ONDERTEKEND', 'VERZONDEN') ) OR
                                          (acceptatie_d is not null and status in ('BEOORDEELD', 'ONDERTEKEND', 'VERZONDEN') )
                                        )
    add constraint cc_goedkeuring_d CHECK( (goedkeuring_d is null and  status not in ('ONDERTEKEND', 'VERZONDEN') ) OR
                                          (goedkeuring_d is not null and status in ('ONDERTEKEND', 'VERZONDEN') )
                                        )
;


alter table art46.aanvr_schuldvordering_regel
    drop constraint fk_asvr_aanvraag_schuldvordering
;

alter table art46.aanvr_schuldvordering_regel
    add constraint fk_asvr_aanvraag_schuldvordering
    foreign key (aanvr_schuldvordering_id)
    references art46.aanvr_schuldvordering(aanvr_schuldvordering_id)
      on delete cascade on update restrict
;


alter table art46.aanvr_schuldvordering_regel
    add constraint fk_asvr_vdoregel foreign key (voorstel_deelopdracht_regel_id) references art46.voorstel_deelopdracht_regel(id)
;

alter table art46.aanvr_schuldvordering_regel
    alter column afname set not null
    alter column afnamebedrag set not null
;
call sysproc.admin_cmd('reorg table art46.aanvr_schuldvordering_regel')
;

alter table art46.aanvr_schuldvordering_regel
    alter column afname set not null
    alter column afnamebedrag set not null
;
call sysproc.admin_cmd('reorg table art46.aanvr_schuldvordering_regel')
;

alter table art46.voorstel_deelopdracht_regel
    alter column afname set not null
    alter column afnamebedrag set not null
;
call sysproc.admin_cmd('reorg table art46.voorstel_deelopdracht_regel')
;


-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('4.94');


