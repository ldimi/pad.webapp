

alter table SAP.PROJECT
	add column credit_TOTAAL decimal(13,2)
	add column debet_TOTAAL decimal(13,2)
	add column initieel_bedrag decimal(13,2)
	add column initieel_acht_nr varchar(20)
	add column boekjaar integer default 0 not null 
	add column wbs_nr   varchar(24) 
	add column creatie_ts timestamp default '1990-01-01' not null
	add column wijzig_ts timestamp default '1990-01-01' not null
;

alter table ART46.SCHULDVORDERING
	add column creatie_ts timestamp default '1990-01-01' not null
	add column wijzig_ts timestamp default '1990-01-01' not null
;

alter table ART46.BESTEK
	add column wbs_nr   varchar(24)
	add column creatie_ts timestamp default '1990-01-01' not null
	add column wijzig_ts timestamp default '1990-01-01' not null
;


alter table ART46.DEELOPDRACHT
	add column wbs_nr   varchar(24)
	add column creatie_ts timestamp default '1990-01-01' not null
	add column wijzig_ts timestamp default '1990-01-01' not null
;


alter table ART46.DOSSIER
	add column sap_project_nr   varchar(24)
	add column wbs_ivs_nr   varchar(24)
	add column wbs_migratie   varchar(24)
	add column creatie_ts timestamp default '1990-01-01' not null
	add column wijzig_ts timestamp default '1990-01-01' not null
	add constraint cc_project_id_wbs CHECK((sap_project_nr is null and wbs_ivs_nr is null) OR
								    (sap_project_nr is not null and wbs_ivs_nr is not null and (DOSSIER_TYPE = 'A' or DOSSIER_TYPE = 'B')) OR
								    (sap_project_nr is not null and wbs_ivs_nr is null and (DOSSIER_TYPE = 'X'))
                                           )
;




alter table sap.PROJECT_FACTUUR
	add column boekjaar integer default 0 not null 
	alter column saldo drop not null
;

alter table sap.PROJECT_FACTUUR
	drop primary key
;


alter table sap.PROJECT_FACTUUR
	add primary key (PROJECT_ID, FACTUUR_ID, BOEKJAAR)
;






alter table sap.PROJECT_VASTLEGGING
	add column boekjaar integer default 0 not null 
;
alter table sap.PROJECT_VASTLEGGING
	drop primary key
;

alter table sap.PROJECT_VASTLEGGING
	add primary key (PROJECT_ID, VASTLEGGING_ID, BOEKJAAR)
;




alter table ART46.SCHULDVORDERING_SAP_PROJECT_FACTUUR
	add column boekjaar integer default 0 not null 
;


alter table ART46.SCHULDVORDERING_SAP_PROJECT_FACTUUR
	drop primary key
;

alter table ART46.SCHULDVORDERING_SAP_PROJECT_FACTUUR
	add primary key (VORDERING_ID,PROJECT_ID, FACTUUR_ID, BOEKJAAR)
;





-- ART46.SCHULDVORDERING_SAP_PROJECT ;

CREATE TABLE ART46.SCHULDVORDERING_SAP_PROJECT
(
   VORDERING_ID int NOT NULL,
   VOLG_NR int NOT NULL default 0,
   PROJECT_ID varchar(20)  NOT NULL,
   BEDRAG DEC(9,2),
   WBS_NR varchar(24),   
   CREATIE_TS timestamp DEFAULT '1990-01-01-00.00.00.000000' NOT NULL,
   WIJZIG_TS timestamp DEFAULT '1990-01-01-00.00.00.000000' NOT NULL,
   constraint PK_SCHULDVORDERING_SAP_PROJECT primary key (VORDERING_ID,VOLG_NR, PROJECT_ID),
   constraint cc_project_id_wbs CHECK( wbs_nr is null OR
								(project_id is not null and wbs_nr is not null)),
   constraint cc_volg_nr CHECK( volg_nr = 0 OR volg_nr = 1)
)
;


alter table ART46.SCHULDVORDERING_SAP_PROJECT
   add constraint FK_SVSP_SV foreign key (VORDERING_ID)
      references ART46.SCHULDVORDERING (VORDERING_ID)
      on delete restrict on update restrict
;

alter table ART46.SCHULDVORDERING_SAP_PROJECT
   add constraint FK_SVSP_PRO foreign key (PROJECT_ID)
      references SAP.PROJECT (PROJECT_ID)
      on delete restrict on update restrict
;

