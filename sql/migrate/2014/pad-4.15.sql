--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

ALTER TABLE ART46.MEETSTAAT_SCHULDVORDERING_REGEL  drop column MEERWERKEN_EENHEID;

call sysproc.admin_cmd('reorg table ART46.MEETSTAAT_SCHULDVORDERING_REGEL')
;

delete from  ART46.DB_VERSIE
where DB_VERSIE = '4.15';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;


ALTER TABLE ART46.MEETSTAAT_SCHULDVORDERING_REGEL  ADD MEERWERKEN_EENHEID CHAR(10);

call sysproc.admin_cmd('reorg table ART46.MEETSTAAT_SCHULDVORDERING_REGEL')
;

-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('4.15');



