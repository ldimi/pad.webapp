--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;



delete from  art46.db_versie
where db_versie = '5.02';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

alter table art46.voorstel_deelopdracht_regel
   alter column meerwerken_type set data type varchar(25)
;

call sysproc.admin_cmd('reorg table art46.voorstel_deelopdracht_regel')
;

alter table art46.voorstel_deelopdracht_regel
   alter column meerwerken_eenheid set data type varchar(25)
;

call sysproc.admin_cmd('reorg table art46.voorstel_deelopdracht_regel')
;


-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('5.02');


