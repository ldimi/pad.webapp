

-- rename foreign keys ;
----------------------------------------------------------------;

alter table ART46.DOSSIER 
   drop constraint FK_FASE
;

alter table ART46.DOSSIER 
   add constraint FK_DO_FASE foreign key (DOSSIER_FASE_ID)
      references ART46.DOSSIER_FASE (FASE_ID)
      on delete restrict on update restrict
;

alter table ART46.DEELOPDRACHT
   drop constraint FK_DOSSIER
;

alter table ART46.DEELOPDRACHT
   add constraint FK_DEOP_DOSSIER foreign key (DOSSIER_ID)
      references ART46.DOSSIER (ID)
      on delete restrict on update restrict
;

alter table ART46.BESTEK
   drop constraint FK_DOSSIER
;

alter table ART46.BESTEK
   add constraint FK_BE_DOSSIER foreign key (DOSSIER_ID)
      references ART46.DOSSIER (ID)
      on delete restrict on update restrict
;


alter table ART46.SCHULDVORDERING
   drop constraint FK_BESTEK
;

alter table ART46.SCHULDVORDERING
   add constraint FK_SV_BESTEK foreign key (BESTEK_ID)
      references ART46.BESTEK (BESTEK_ID)
      on delete restrict on update restrict
;


-- schuldvordering - factuur;

ALTER TABLE ART46.SCHULDVORDERING_SAP_PROJECT_FACTUUR
   drop constraint FK_FACTUUR
;

ALTER TABLE ART46.SCHULDVORDERING_SAP_PROJECT_FACTUUR
   add constraint FK_SSPF_FACTUUR foreign key (PROJECT_ID, FACTUUR_ID)
      references SAP.PROJECT_FACTUUR (PROJECT_ID, FACTUUR_ID)
      on delete restrict on update restrict
;

ALTER TABLE ART46.SCHULDVORDERING_SAP_PROJECT_FACTUUR
   drop constraint FK_SCHULDV
;

ALTER TABLE ART46.SCHULDVORDERING_SAP_PROJECT_FACTUUR
   add constraint FK_SSPF_SCHULDV foreign key (VORDERING_ID)
      references ART46.SCHULDVORDERING (VORDERING_ID)
      on delete restrict on update restrict
;


-- dossier - factuur;

ALTER TABLE ART46.DOSSIER_SAP_PROJECT_FACTUUR
   drop constraint FK_FACTUUR
;

ALTER TABLE ART46.DOSSIER_SAP_PROJECT_FACTUUR
   add constraint FK_DSPF_FACTUUR foreign key (PROJECT_ID, FACTUUR_ID)
      references SAP.PROJECT_FACTUUR (PROJECT_ID, FACTUUR_ID)
      on delete restrict on update restrict
;

ALTER TABLE ART46.DOSSIER_SAP_PROJECT_FACTUUR
   drop constraint FK_DOSSIER
;

ALTER TABLE ART46.DOSSIER_SAP_PROJECT_FACTUUR
   add constraint FK_DSPF_DOSSIER foreign key (DOSSIER_ID)
      references ART46.DOSSIER (ID)
      on delete restrict on update restrict
;


-- bestek - sap_project;

ALTER TABLE ART46.BESTEK_SAP_PROJECT
   drop constraint FK_PROJECT
;

ALTER TABLE ART46.BESTEK_SAP_PROJECT
   add constraint FK_BSP_PROJECT foreign key (PROJECT_ID)
      references SAP.PROJECT (PROJECT_ID)
      on delete restrict on update restrict
;

ALTER TABLE ART46.BESTEK_SAP_PROJECT
   drop constraint FK_BESTEK
;

ALTER TABLE ART46.BESTEK_SAP_PROJECT
   add constraint FK_BSP_BESTEK foreign key (BESTEK_ID)
      references ART46.BESTEK (BESTEK_ID)
      on delete restrict on update restrict
;


-- bijkomende foreign keys
-----------------------------------------------------------------------------------------------------------



alter table ART46.DOSSIER 
   add constraint FK_DO_TYPE_BODEM foreign key (type_id_bodem)
      references ART46.DOSSIER_TYPE (type_ID)
      on delete restrict on update restrict
;


alter table ART46.ARCHIEF
   add constraint FK_AR_DOSSIER foreign key (dossier_id)
      references ART46.DOSSIER (ID)
      on delete restrict on update restrict
;


alter table ART46.BRIEF
   add constraint FK_BR_DOSSIER foreign key (dossier_id)
      references ART46.DOSSIER (ID)
      on delete restrict on update restrict
;

alter table ART46.CHECKLIST_HISTORIEK
   add constraint FK_CH_HI_DOSSIER foreign key (dossier_id)
      references ART46.DOSSIER (ID)
      on delete restrict on update restrict
;

alter table ART46.DOSSIER_ACTIE
   add constraint FK_DO_AC_DOSSIER foreign key (dossier_id)
      references ART46.DOSSIER (ID)
      on delete restrict on update restrict
;

alter table ART46.DOSSIER_ADRES
   add constraint FK_DO_AD_DOSSIER foreign key (dossier_id)
      references ART46.DOSSIER (ID)
      on delete restrict on update restrict
;

alter table ART46.DOSSIER_AFSPRAAK
   add constraint FK_DO_AF_DOSSIER foreign key (dossier_id)
      references ART46.DOSSIER (ID)
      on delete restrict on update restrict
;

alter table ART46.DOSSIER_DERDE
   add constraint FK_DO_DE_DOSSIER foreign key (dossier_id)
      references ART46.DOSSIER (ID)
      on delete restrict on update restrict
;

alter table ART46.DOSSIER_FOTO
   add constraint FK_FO_DOSSIER foreign key (dossier_id)
      references ART46.DOSSIER (ID)
      on delete restrict on update restrict
;

alter table ART46.DOSSIER_RAMING
   add constraint FK_DO_RA_DOSSIER foreign key (dossier_id)
      references ART46.DOSSIER (ID)
      on delete restrict on update restrict
;

alter table ART46.DOSSIER_RAMING_VOORSTEL
   add constraint FK_DO_RA_VO_DOSSIER foreign key (dossier_id)
      references ART46.DOSSIER (ID)
      on delete restrict on update restrict
;

alter table ART46.DOSSIER_RAMING_VOORSTEL_HISTORIEK
   add constraint FK_DO_RA_VO_HI_DOSSIER foreign key (dossier_id)
      references ART46.DOSSIER (ID)
      on delete restrict on update restrict
;

alter table ART46.DOS_KAD_TYPE
   add constraint FK_DO_KA_TY_DOSSIER foreign key (dossier_id)
      references ART46.DOSSIER (ID)
      on delete restrict on update restrict
;

alter table ART46.DOS_KAD_TYPE_HIST
   add constraint FK_DO_KA_TY_HI_DOSSIER foreign key (dossier_id)
      references ART46.DOSSIER (ID)
      on delete restrict on update restrict
;

alter table ART46.MELDING
   add constraint FK_ME_DOSSIER foreign key (dossier_id)
      references ART46.DOSSIER (ID)
      on delete restrict on update restrict
;

alter table ART46.NA_GOEDK
   add constraint FK_NA_GO_DOSSIER foreign key (dossier_id)
      references ART46.DOSSIER (ID)
      on delete restrict on update restrict
;

alter table ART46.VOOR_GOEDK
   add constraint FK_VO_GO_DOSSIER foreign key (dossier_id)
      references ART46.DOSSIER (ID)
      on delete restrict on update restrict
;

-- id toegevoegd aan ART46.OVAM_AMBTENAAR_VIEW;

drop view ART46.OVAM_AMBTENAAR_VIEW;

create view ART46.OVAM_AMBTENAAR_VIEW as
(
   select
--   id,
   uid,
   ambtenaar_id,
   ambtenaar_b,
   tel1,
   functie,
   email,
   max(afdeling_id) as afdeling_id ,
   max(dienst_id) as dienst_id
   from
   (
      SELECT
      k.id,
      ka.uid,
      ka.uid AS ambtenaar_id,
      k.naam_1 as ambtenaar_b,
      k.tel as tel1,
      ka.functie,
      k.email,
      coalesce(a1.code , a2.code, a3.code) as afdeling_id,
      coalesce(d2.code , d3.code) AS dienst_id
      FROM SMEG.KLANT k
      INNER JOIN SMEG.KLANT_AMBTENAAR ka ON ka.id = k.id
      LEFT OUTER JOIN SMEG.KLANT_AMBTENAAR_AFDELING kaa ON kaa.klant_ambtenaar_id = ka.id
      LEFT OUTER JOIN SMEG_REF.AFDELING a1 ON a1.id = kaa.afdeling_id
      left JOIN SMEG.KLANT_AMBTENAAR_DIENST kad ON kad.klant_ambtenaar_id = ka.id
      LEFT OUTER JOIN SMEG_REF.DIENST d2 ON d2.id = kad.dienst_id
      LEFT OUTER JOIN SMEG_REF.AFDELING a2 ON a2.id = d2.afdeling_id
      left JOIN SMEG.KLANT_AMBTENAAR_CEL kac ON kac.klant_ambtenaar_id = ka.id
      left JOIN SMEG_REF.CEL c ON c.id = kac.cel_id
      LEFT OUTER JOIN SMEG_REF.DIENST d3 ON d3.id = c.dienst_id
      LEFT OUTER JOIN SMEG_REF.AFDELING a3 ON a3.id = d3.afdeling_id
   )
   GROUP BY id, ambtenaar_id, ambtenaar_b, tel1, functie, email, uid
)
;

-- alleen dossierhouder is nodig 
--   (niet meer contacten voor opdrachten en projecten)

drop view ART46.V_DOSSIERHOUDERS_BOA;

create view ART46.V_DOSSIERHOUDERS_BOA as
(
select distinct 
	d.DOSSIER_ID,
	dka.uid as AMBTENAAR_ID,
	dk.NAAM_1 as AMBTENAAR_B,
	'BOA' as AFDELING,
	'DHR' as TYPE
from SMEG.DOSSIER d 
        inner join SMEG.BUNDEL db
        on d.id = db.id
     INNER JOIN SMEG.KLANT dk
     ON dk.id = db.klant_ambtenaar_id
	     INNER JOIN SMEG.KLANT_AMBTENAAR dka
	     ON dka.id = dk.id
);



