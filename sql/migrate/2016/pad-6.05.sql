

--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  art46.db_versie
where db_versie = '6.05';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

update art46.DOSSIER_OVERDRACHT_STATUS
set volg_nr = volg_nr + 1
where volg_nr > 4
;

INSERT INTO ART46.DOSSIER_OVERDRACHT_STATUS 
(CODE,OMSCHRIJVING,VOLG_NR) 
VALUES ('na_scr_afg','screening afgerond en afgekeurd',5);


-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('6.05');
