
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;




delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.56';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

update art46.BRIEF br
set br.DMS_FOLDER =  null
where br.DMS_FOLDER = ''
;

update art46.BRIEF br
set br.DMS_FILENAME =  null
where br.DMS_FILENAME = ''
;

update art46.BRIEF br
set br.DMS_ID =  null
where br.DMS_ID = ''
;

-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.56');
