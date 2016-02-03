--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  art46.db_versie
where db_versie = '5.17';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;
CREATE TABLE art46.DOSSIER_ONDERNEMING
(
    ID INT PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1 INCREMENT BY 1),
    DOSSIER_ID INT NOT NULL,
    ROL VARCHAR(35) NOT NULL,
    ONDERNEMINGSNUMMER VARCHAR(12) NOT NULL
);

-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('5.17');
