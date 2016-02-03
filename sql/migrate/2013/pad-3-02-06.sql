
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

CREATE TABLE ART46.DOSSIER_TYPE_BODEM
(
   TYPE_ID int NOT NULL,
   TYPE_B varchar(20) NOT NULL,
   PRIMARY KEY (TYPE_ID)
)
;


insert into ART46.DOSSIER_TYPE
select * from ART46.DOSSIER_TYPE_BODEM
;

delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.6';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;


DROP TABLE ART46.DOSSIER_TYPE;


-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.6');
