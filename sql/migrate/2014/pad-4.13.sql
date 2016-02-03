--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

delete from  ART46.DB_VERSIE
where DB_VERSIE = '4.13';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;


ALTER TABLE ART46.brief
    alter column volgnummer set not null
;

call sysproc.admin_cmd('reorg table ART46.brief')
;


-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('4.13');

