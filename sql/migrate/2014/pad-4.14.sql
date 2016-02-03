--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

delete from  ART46.DB_VERSIE
where DB_VERSIE = '4.14';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;


ALTER TABLE ART46.brief
    alter column dienst_id set default 1
;

call sysproc.admin_cmd('reorg table ART46.brief')
;

-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('4.14');



