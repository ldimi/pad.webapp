

--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

alter table art46.brief
    drop constraint cc_dms_folder
;


delete from  art46.db_versie
where db_versie = '6.27';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

alter table art46.brief
    add constraint cc_dms_folder check (
        dms_folder not like '/%'
    )
;

-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie, omschrijving) values ('6.27', 'brief.cc_dms_folder');
