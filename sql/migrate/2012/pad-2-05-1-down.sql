
-- rename foreign keys ;
----------------------------------------------------------------;

alter table ART46.DOSSIER 
   drop constraint FK_DO_FASE
;

alter table ART46.DOSSIER 
   add constraint FK_FASE  foreign key (DOSSIER_FASE_ID)
      references ART46.DOSSIER_FASE (FASE_ID)
      on delete restrict on update restrict
;

alter table ART46.DEELOPDRACHT
   drop constraint FK_DEOP_DOSSIER
;

alter table ART46.DEELOPDRACHT
   add constraint FK_DOSSIER foreign key (DOSSIER_ID)
      references ART46.DOSSIER (ID)
      on delete restrict on update restrict
;

alter table ART46.BESTEK
   drop constraint FK_BE_DOSSIER
;

alter table ART46.BESTEK
   add constraint FK_DOSSIER foreign key (DOSSIER_ID)
      references ART46.DOSSIER (ID)
      on delete restrict on update restrict
;


alter table ART46.SCHULDVORDERING
   drop constraint FK_SV_BESTEK
;

alter table ART46.SCHULDVORDERING
   add constraint FK_BESTEK foreign key (BESTEK_ID)
      references ART46.BESTEK (BESTEK_ID)
      on delete restrict on update restrict
;


-- schuldvordering - factuur;

ALTER TABLE ART46.SCHULDVORDERING_SAP_PROJECT_FACTUUR
   drop constraint FK_SSPF_FACTUUR
;

ALTER TABLE ART46.SCHULDVORDERING_SAP_PROJECT_FACTUUR
   add constraint FK_FACTUUR foreign key (PROJECT_ID, FACTUUR_ID)
      references SAP.PROJECT_FACTUUR (PROJECT_ID, FACTUUR_ID)
      on delete restrict on update restrict
;

ALTER TABLE ART46.SCHULDVORDERING_SAP_PROJECT_FACTUUR
   drop constraint FK_SSPF_SCHULDV
;

ALTER TABLE ART46.SCHULDVORDERING_SAP_PROJECT_FACTUUR
   add constraint FK_SCHULDV foreign key (VORDERING_ID)
      references ART46.SCHULDVORDERING (VORDERING_ID)
      on delete restrict on update restrict
;


-- dossier - factuur;

ALTER TABLE ART46.DOSSIER_SAP_PROJECT_FACTUUR
   drop constraint FK_DSPF_FACTUUR
;

ALTER TABLE ART46.DOSSIER_SAP_PROJECT_FACTUUR
   add constraint FK_FACTUUR foreign key (PROJECT_ID, FACTUUR_ID)
      references SAP.PROJECT_FACTUUR (PROJECT_ID, FACTUUR_ID)
      on delete restrict on update restrict
;

ALTER TABLE ART46.DOSSIER_SAP_PROJECT_FACTUUR
   drop constraint FK_DSPF_DOSSIER
;

ALTER TABLE ART46.DOSSIER_SAP_PROJECT_FACTUUR
   add constraint FK_DOSSIER foreign key (DOSSIER_ID)
      references ART46.DOSSIER (ID)
      on delete restrict on update restrict
;


-- bestek - sap_project;

ALTER TABLE ART46.BESTEK_SAP_PROJECT
   drop constraint FK_BSP_PROJECT
;

ALTER TABLE ART46.BESTEK_SAP_PROJECT
   add constraint FK_PROJECT foreign key (PROJECT_ID)
      references SAP.PROJECT (PROJECT_ID)
      on delete restrict on update restrict
;

ALTER TABLE ART46.BESTEK_SAP_PROJECT
   drop constraint FK_BSP_BESTEK
;

ALTER TABLE ART46.BESTEK_SAP_PROJECT
   add constraint FK_BESTEK foreign key (BESTEK_ID)
      references ART46.BESTEK (BESTEK_ID)
      on delete restrict on update restrict
;


---------------------------------------------------------------------;

alter table ART46.DOSSIER 
   drop constraint FK_DO_TYPE_BODEM
;

alter table ART46.ARCHIEF
   drop constraint FK_AR_DOSSIER
;

alter table ART46.BRIEF
   drop constraint FK_BR_DOSSIER
;

alter table ART46.CHECKLIST_HISTORIEK
   drop constraint FK_CH_HI_DOSSIER
;

alter table ART46.DOSSIER_ACTIE
   drop constraint FK_DO_AC_DOSSIER
;

alter table ART46.DOSSIER_ADRES
   drop constraint FK_DO_AD_DOSSIER
;

alter table ART46.DOSSIER_AFSPRAAK
   drop constraint FK_DO_AF_DOSSIER
;

alter table ART46.DOSSIER_DERDE
   drop constraint FK_DO_DE_DOSSIER
;

alter table ART46.DOSSIER_FOTO
   drop constraint FK_FO_DOSSIER
;

alter table ART46.DOSSIER_RAMING
   drop constraint FK_DO_RA_DOSSIER
;

alter table ART46.DOSSIER_RAMING_VOORSTEL
   drop constraint FK_DO_RA_VO_DOSSIER
;

alter table ART46.DOSSIER_RAMING_VOORSTEL_HISTORIEK
   drop constraint FK_DO_RA_VO_HI_DOSSIER
;

alter table ART46.DOS_KAD_TYPE
   drop constraint FK_DO_KA_TY_DOSSIER
;

alter table ART46.DOS_KAD_TYPE_HIST
   drop constraint FK_DO_KA_TY_HI_DOSSIER
;

alter table ART46.MELDING
   drop constraint FK_ME_DOSSIER
;

alter table ART46.NA_GOEDK
   drop constraint FK_NA_GO_DOSSIER
;

alter table ART46.VOOR_GOEDK
   drop constraint FK_VO_GO_DOSSIER
;



drop view ART46.OVAM_AMBTENAAR_VIEW;

create view ART46.OVAM_AMBTENAAR_VIEW as
(
   select
--	   id,
	   ambtenaar_id,
	   ambtenaar_b,
	   tel1,
	   functie,
	   email,
	   uid,
	   max(afdeling_id) as afdeling_id ,
	   max(dienst_id) as dienst_id
   from
   (
      SELECT
	      k.id,
	      ka.uid AS ambtenaar_id,
	      k.naam_1 as ambtenaar_b,
	      k.tel as tel1,
	      ka.functie,
	      k.email,
	      ka.uid,
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


drop view ART46.V_DOSSIERHOUDERS_BOA;

create view ART46.V_DOSSIERHOUDERS_BOA as
(
select
   distinct o.DOSSIER_ID,
		   a.AMBTENAAR_ID,
		   a.AMBTENAAR_B,
		   'BOA' as AFDELING,
		   'O' as TYPE
from SMEG.RS_ONDERZOEK_VIEW o 
   		inner join ART46.OVAM_AMBTENAAR_VIEW a
   		on o.DOSS_HDR_ID = a.UID
   union
select
   distinct p.DOSSIER_ID,
   a.AMBTENAAR_ID,
   a.AMBTENAAR_B,
   'BOA' as AFDELING,
   'P' as TYPE
from SMEG.RS_PROJECT_VIEW p
		inner join ART46.OVAM_AMBTENAAR_VIEW a
		on p.DOSS_HDR_ID = a.UID
)
;

