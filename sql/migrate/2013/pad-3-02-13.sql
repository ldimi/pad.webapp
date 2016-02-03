
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


alter table art46.PLANNING_LIJN_VERSIE
	drop constraint cc_bestek_omschrijving
	drop column bestek_omschrijving
;


call sysproc.admin_cmd('reorg table ART46.PLANNING_LIJN_VERSIE')
;

delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.13';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

alter table art46.PLANNING_LIJN_VERSIE
	add column bestek_omschrijving varchar(150)
	add constraint cc_bestek_omschrijving check (bestek_id is null or bestek_omschrijving is null)
;

call sysproc.admin_cmd('reorg table ART46.PLANNING_LIJN_VERSIE')
;

-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.13');
