
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

ALTER TABLE ART46.SCHULDVORDERING
    DROP uiterste_verific_d;

call sysproc.admin_cmd('reorg table ART46.SCHULDVORDERING')
;



delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.24';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

ALTER TABLE ART46.SCHULDVORDERING
    ADD uiterste_verific_d date;

call sysproc.admin_cmd('reorg table ART46.SCHULDVORDERING')
;



-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.24');
