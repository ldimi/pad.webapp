

--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;



delete from  art46.db_versie
where db_versie = '6.28';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

alter table art46.voorstel_deelopdracht
    alter column omschrijving set data type varchar(1024)
;

call sysproc.admin_cmd('reorg table art46.voorstel_deelopdracht')
;

-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie, omschrijving) values ('6.28', 'voorstel_deelopdracht.omschrijving : varchar(1024) ipv 60');
