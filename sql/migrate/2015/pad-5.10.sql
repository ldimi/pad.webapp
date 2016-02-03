--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  art46.db_versie
where db_versie = '5.10';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

alter table  art46.bestek
    add column controle_dms_id varchar(100)
    add column controle_dms_filename varchar(100)
    add column controle_dms_folder varchar(255)
;

call sysproc.admin_cmd('reorg table art46.bestek')
;

alter table  art46.bestek
    add constraint cc_controle_dms check ( controle_dms_id is null or
            (controle_dms_id is not null and controle_dms_filename is not null or controle_dms_folder is not null) )
;


-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('5.10');



