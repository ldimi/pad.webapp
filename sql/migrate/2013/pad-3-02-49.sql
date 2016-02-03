
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;



delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.49';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;



ALTER TABLE ART46.MEETSTAAT_EENHEID_MAPPING
    drop column EENHEIDCODE
;


call sysproc.admin_cmd('reorg table ART46.MEETSTAAT_EENHEID_MAPPING')
;



ALTER TABLE ART46.MEETSTAAT_EENHEID
    drop column code
;

call sysproc.admin_cmd('reorg table ART46.MEETSTAAT_EENHEID')
;




-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.49');
