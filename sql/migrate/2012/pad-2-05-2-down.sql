

alter table SAP.PROJECT
	drop column credit_TOTAAL
	drop column debet_TOTAAL
	drop column initieel_bedrag
	drop column initieel_acht_nr
	drop column boekjaar
	drop column wbs_nr
	drop column creatie_ts
	drop column wijzig_ts
;


alter table ART46.SCHULDVORDERING
	drop column project_id
	drop column wbs_nr
	drop column creatie_ts
	drop column wijzig_ts
;


alter table ART46.BESTEK
	drop column wbs_nr
	drop column creatie_ts
	drop column wijzig_ts
;

alter table ART46.DEELOPDRACHT
	drop column wbs_nr
	drop column creatie_ts
	drop column wijzig_ts
;


alter table ART46.DOSSIER
    drop column sap_project_nr
	drop column wbs_ivs_nr
	drop column creatie_ts
	drop column wijzig_ts
;




alter table sap.PROJECT_FACTUUR
	drop column boekjaar 
	alter column saldo set default 0
	alter column saldo set not null
;
alter table sap.PROJECT_FACTUUR
	drop primary key
;
alter table sap.PROJECT_FACTUUR
	add primary key (PROJECT_ID, FACTUUR_ID)
;





alter table sap.PROJECT_VASTLEGGING
	drop column boekjaar
;
alter table sap.PROJECT_VASTLEGGING
	drop primary key
;
alter table sap.PROJECT_VASTLEGGING
	add primary key (PROJECT_ID, VASTLEGGING_ID)
;


alter table art46.BRIEF alter column BRIEF_ID DROP IDENTITY; 

CREATE TRIGGER art46_brief_id NO CASCADE BEFORE
INSERT ON ART46.BRIEF REFERENCING NEW AS n FOR EACH ROW MODE DB2SQL set n.brief_id =
(
   select
   max(brief_id) + 1
   from ART46.BRIEF
)

alter table sap.PROJECT_FACTUUR
	alter column saldo set not null
;


drop table ART46.SCHULDVORDERING_SAP_PROJECT;

