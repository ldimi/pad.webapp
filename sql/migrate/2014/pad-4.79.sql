--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  art46.db_versie
where db_versie = '4.79';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

alter table art46.webloket_bijlage
    alter column id set generated always as identity (start with 200)
;


call sysproc.admin_cmd('reorg table art46.webloket_bijlage')
;


alter table art46.webloket_bijlage
    add constraint cc_false check (id < 0)
;

alter table art46.webloket_bijlage
    add constraint cc_aanvraag_voorstel check (
                                                (schuldvordering_id is null and voorstel_id is not null) or
                                                (schuldvordering_id is not null and voorstel_id is null)
                                              )
;


-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('4.79');

