--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  art46.db_versie
where db_versie = '4.73';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

alter table art46.meetstaat_schuldvordering
    drop column status
;

call sysproc.admin_cmd('reorg table art46.meetstaat_schuldvordering');

alter table art46.meetstaat_schuldvordering
    alter column bedrag_excl_btw set not null
    alter column bedrag_incl_btw set not null
    alter column van_datum set not null
    alter column tot_datum set not null
;

call sysproc.admin_cmd('reorg table art46.meetstaat_schuldvordering');


-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('4.73');

