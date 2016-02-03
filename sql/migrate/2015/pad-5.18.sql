--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  art46.db_versie
where db_versie = '5.18';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;
CREATE TABLE art46.DOSSIER_ONDERNEMING_ROL
(
    KEY VARCHAR(35) NOT NULL,
    NAAM VARCHAR(35) NOT NULL,
    ALFRESCOUSER VARCHAR(35) NOT NULL
);


-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('5.18');
