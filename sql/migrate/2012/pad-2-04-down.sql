


-- foreign keys ;
----------------------------------------------------------------;

alter table ART46.DOSSIER 
   drop constraint FK_FASE
;

ALTER TABLE ART46.DOSSIER
   drop CONSTRAINT C_DOSSIER_TYPE
;

ALTER TABLE ART46.DOSSIER
   drop CONSTRAINT C_DOSSIER_B
;


--alter table ART46.DEELOPDRACHT
--   drop constraint FK_BESTEK
--;

alter table ART46.DEELOPDRACHT
   drop constraint FK_DOSSIER
;

alter table ART46.BESTEK
   drop constraint FK_DOSSIER
;


alter table ART46.SCHULDVORDERING
   drop constraint FK_BESTEK
;

-- eigenlijk kan Vastlegging_id de primary key zijn 
--;
--ALTER TABLE SAP.PROJECT_VASTLEGGING
--      ADD CONSTRAINT NEWID UNIQUE(VASTLEGGING_ID)
--;
--
--ALTER TABLE SAP.PROJECT_VASTLEGGING
--   add constraint FK_PROJECT foreign key (PROJECT_ID)
--      references SAP.PROJECT (PROJECT_ID)
--      on delete restrict on update restrict
--;
--
--ALTER TABLE SAP.PROJECT_FACTUUR
--   add constraint FK_PROJECT foreign key (PROJECT_ID)
--      references SAP.PROJECT (PROJECT_ID)
--      on delete restrict on update restrict
--;



-- schuldvordering - factuur;

ALTER TABLE ART46.SCHULDVORDERING_SAP_PROJECT_FACTUUR
   drop constraint FK_FACTUUR
;

ALTER TABLE ART46.SCHULDVORDERING_SAP_PROJECT_FACTUUR
   drop constraint FK_SCHULDV
;


-- dossier - factuur;

ALTER TABLE ART46.DOSSIER_SAP_PROJECT_FACTUUR
   drop constraint FK_FACTUUR
;

ALTER TABLE ART46.DOSSIER_SAP_PROJECT_FACTUUR
   drop constraint FK_DOSSIER
;


-- bestek - sap_project;

ALTER TABLE ART46.BESTEK_SAP_PROJECT
   drop constraint FK_PROJECT
;

ALTER TABLE ART46.BESTEK_SAP_PROJECT
   drop constraint FK_BESTEK
;



--  OVAM_AMBTENAAR_VIEW;
-----------------------------------------------------------------------------------------------------------


drop view ART46.OVAM_AMBTENAAR_VIEW;

drop view ART46.V_DOSSIERHOUDERS_BOA;

drop view ART46.V_DOSSIERHOUDERS;

DROP VIEW ART46.RS_KADASTER_AFD_VIEW;

DROP VIEW ART46.RS_KADASTER_VIEW;

DROP VIEW ART46.V_DOSSIER_OPDRACHT_BOA;

DROP view ART46.RS_KADASTER_ONDERZOEK_VIEW;

drop VIEW ART46.RS_KADASTER_PROJECT_VIEW;

drop view ART46.V_DOSSIER_KADASTER;

drop VIEW ART46.V_SMEG_DOSSIER_ONDERZOEK;

drop VIEW ART46.V_SMEG_DOSSIER_PROJECT;

