

--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  art46.db_versie
where db_versie = '5.32';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

alter table art46.dossier_rechtsgrond
    add column screening_jn char(1) not null default 'N';


call sysproc.admin_cmd('reorg table art46.dossier_rechtsgrond')
;

alter table art46.dossier_rechtsgrond
    add foreign key fk_dosrg_screening_jn (screening_jn)
         references art46.ja_nee_code (code)
         on delete restrict;


-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('5.32');