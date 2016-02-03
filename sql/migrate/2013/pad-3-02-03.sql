
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

alter table ART46.DOSSIER 
   drop constraint FK_DO_DOSS_HDR
;

alter table ART46.DOSSIER_HOUDER 
   drop constraint FK_DOHO_KLANT_AMBTENAAR
;

DROP TABLE ART46.DOSSIER_HOUDER;

delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.3';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;


CREATE TABLE ART46.DOSSIER_HOUDER
(
   DOSS_HDR_ID VARCHAR(8) NOT NULL,
   ACTIEF_JN CHAR(1) not null default 'J',
   CONSTRAINT C_ACTIEF_JN  CHECK (ACTIEF_JN in ('J', 'N')),
   PRIMARY KEY (DOSS_HDR_ID)
)
;

insert into ART46.DOSSIER_HOUDER(DOSS_HDR_ID)
	select distinct AMBTENAAR_ID
	from ART46.OVAM_AMBTENAAR_VIEW
	where UPPER(AFDELING_ID) = 'IVS'
;

insert into ART46.DOSSIER_HOUDER(DOSS_HDR_ID, ACTIEF_JN)
	select distinct d.DOSS_HDR_ID, 'N' 
	from ART46.DOSSIER d
	where d.DOSS_HDR_ID not in (select DOSS_HDR_ID from art46.dossier_houder)
;



alter table ART46.DOSSIER 
   add constraint FK_DO_DOSS_HDR foreign key (DOSS_HDR_ID)
      references ART46.DOSSIER_HOUDER (DOSS_HDR_ID)
      on delete restrict on update restrict
;

alter table ART46.DOSSIER_HOUDER 
   add constraint FK_DOHO_KLANT_AMBTENAAR foreign key (DOSS_HDR_ID)
      references SMEG.KLANT_AMBTENAAR (uid)
      on delete restrict on update restrict
;

-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.3');
