
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

drop  TABLE ART46.DOSSIER_HOUDER_VERVANG;

CREATE TABLE ART46.DOSSIERHOUDER
(
   AMBTENAAR_ID varchar(8) PRIMARY KEY NOT NULL,
   AMBTENAAR_ID_VERVANG varchar(8) NOT NULL,
   VAN_D date,
   TOT_D date
)
;

delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.4';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

DROP TABLE ART46.DOSSIERHOUDER
;


CREATE TABLE ART46.DOSSIER_HOUDER_VERVANG
(
   DOSS_HDR_ID varchar(8) NOT NULL,
   DOSS_HDR_VERVANG_ID varchar(8) NOT NULL,
   VAN_D date NOT NULL default current date,
   TOT_D date,
   PRIMARY KEY (DOSS_HDR_ID, VAN_D)
)
;

alter table ART46.DOSSIER_HOUDER_VERVANG
   add constraint FK_DOHV_DOSS_HDR foreign key (DOSS_HDR_ID)
      references ART46.DOSSIER_HOUDER (DOSS_HDR_ID)
      on delete restrict on update restrict
;

alter table ART46.DOSSIER_HOUDER_VERVANG
   add constraint FK_DOHVV_DOSS_HDR foreign key (DOSS_HDR_VERVANG_ID)
      references ART46.DOSSIER_HOUDER (DOSS_HDR_ID)
      on delete restrict on update restrict
;


-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.4');
