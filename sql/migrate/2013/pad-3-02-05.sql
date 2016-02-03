
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

DROP TABLE ART46.DOSSIER_TYPE_BODEM;

delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.5';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

CREATE TABLE ART46.DOSSIER_TYPE_BODEM
(
   TYPE_ID int NOT NULL,
   TYPE_B varchar(20) NOT NULL,
   PRIMARY KEY (TYPE_ID)
)
;


insert into ART46.DOSSIER_TYPE_BODEM
select * from ART46.DOSSIER_TYPE
;

ALTER TABLE ART46.DOSSIER
   drop constraint FK_DO_TYPE_BODEM
;

alter table ART46.DOSSIER 
   add constraint FK_DO_TYPE_BODEM foreign key (type_id_bodem)
      references ART46.DOSSIER_TYPE_BODEM (type_ID)
      on delete restrict on update restrict
;




-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.5');
