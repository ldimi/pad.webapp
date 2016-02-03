

--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  art46.db_versie
where db_versie = '5.30';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

alter table art46.webloket_bijlage
    drop documenturl;

call sysproc.admin_cmd('reorg table art46.webloket_bijlage')
;
alter table art46.webloket_bijlage
    drop editurl;

call sysproc.admin_cmd('reorg table art46.webloket_bijlage')
;


-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('5.30');