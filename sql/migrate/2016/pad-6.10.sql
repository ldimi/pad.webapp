

--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

delete from  art46.db_versie
where db_versie = '6.10';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

alter table art46.schuldvordering
   alter column commentaar set data type varchar(3000)
;

call sysproc.admin_cmd('reorg table art46.schuldvordering')
;


-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('6.10');
