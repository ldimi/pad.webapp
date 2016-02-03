

--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  art46.db_versie
where db_versie = '5.47';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

drop table art46.dossier_onderneming_email;

drop table art46.dossier_onderneming;

alter table art46.offerte
    drop column ondernemingsnummer
;

alter table ART46.OFFERTE
    drop constraint CC_KEY_STATUS;

call sysproc.admin_cmd('reorg table art46.offerte');


-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('5.47');
