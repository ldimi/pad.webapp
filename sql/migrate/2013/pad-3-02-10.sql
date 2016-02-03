
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

alter table ART46.DEELOPDRACHT
   drop constraint FK_DO_PLANNING_LIJN
;

alter table ART46.DEELOPDRACHT
   drop column planning_lijn_id
;

drop TABLE ART46.PLANNING_LIJN_VERSIE;

drop TABLE ART46.PLANNING_LIJN;

drop TABLE ART46.PLANNING_VERSIE;

drop table ART46.PLANNING;

drop table ART46.PLANNING_FASE_DETAIL;

drop table ART46.PLANNING_FASE;


delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.10';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;


CREATE TABLE ART46.PLANNING_FASE
(
	DOSSIER_TYPE CHAR(1) not null,
	FASE_CODE VARCHAR(5) not null,
	FASE_CODE_B VARCHAR(40),
	PRIMARY KEY (DOSSIER_TYPE, FASE_CODE)
);


create table art46.PLANNING_FASE_DETAIL
(
	DOSSIER_TYPE CHAR(1) not null,
	FASE_CODE VARCHAR(5) not null,
	FASE_DETAIL_CODE VARCHAR(20) not null,
	FASE_DETAIL_CODE_B VARCHAR(40),
	PRIMARY KEY (DOSSIER_TYPE, FASE_CODE, FASE_DETAIL_CODE),
     FOREIGN KEY FK_PFD_PFASE(DOSSIER_TYPE, FASE_CODE)
	       REFERENCES ART46.PLANNING_FASE (DOSSIER_TYPE, FASE_CODE) ON DELETE RESTRICT
);


CREATE TABLE ART46.PLANNING
(
   DOSS_HDR_ID VARCHAR(8) not null,
   wijzig_ts timestamp not null default current timestamp,
   wijzig_user varchar(25) not null,
   PRIMARY KEY (DOSS_HDR_ID),
   FOREIGN KEY FK_PL_DOSSIER_HOUDER(DOSS_HDR_ID)
	       REFERENCES ART46.DOSSIER_HOUDER (DOSS_HDR_ID) ON DELETE RESTRICT
)
;

CREATE TABLE ART46.PLANNING_VERSIE
(
   DOSS_HDR_ID VARCHAR(8) not null,
   PLANNING_VERSIE smallint not null,
   wijzig_ts timestamp not null default current timestamp,
   wijzig_user varchar(25) not null,
   PRIMARY KEY (DOSS_HDR_ID, PLANNING_VERSIE),
   FOREIGN KEY FK_PLV_PLANNING(DOSS_HDR_ID)
	       REFERENCES ART46.PLANNING (DOSS_HDR_ID) ON DELETE RESTRICT
)
;



CREATE TABLE ART46.PLANNING_LIJN
(
   LIJN_ID smallint not null,
   DOSS_HDR_ID VARCHAR(8) not null,
   wijzig_ts timestamp not null default current timestamp,
   wijzig_user varchar(25) not null default 'user',
   PRIMARY KEY (LIJN_ID),
   CONSTRAINT DOSSIER_ID_UNIEK UNIQUE(LIJN_ID, DOSS_HDR_ID),
   FOREIGN KEY FK_PLL_PLANNING(DOSS_HDR_ID)
	       REFERENCES ART46.PLANNING (DOSS_HDR_ID) ON DELETE RESTRICT
)
;

CREATE TABLE ART46.PLANNING_LIJN_VERSIE
(
   LIJN_ID smallint not null,
   PLANNING_VERSIE smallint not null, 
   DOSS_HDR_ID VARCHAR(8) not null,
   DOSSIER_ID integer not null,
   DOSSIER_TYPE char(1) not null,
   FASE_CODE VARCHAR(5) not null,
   FASE_DETAIL_CODE VARCHAR(20),
   CONTRACT_ID integer,
   CONTRACT_TYPE char(1),
   IGB_D date,
   IG_BEDRAG integer not null,
   BESTEK_ID integer,
   DELETED_JN CHAR(1) not null default 'N',
   wijzig_ts timestamp not null default current timestamp,
   wijzig_user varchar(25) not null default 'user',
   PRIMARY KEY (LIJN_ID, PLANNING_VERSIE),
   FOREIGN KEY FK_PLLV_PLANNING_VERSIE (DOSS_HDR_ID, PLANNING_VERSIE)
	       REFERENCES ART46.PLANNING_VERSIE (DOSS_HDR_ID, PLANNING_VERSIE) ON DELETE RESTRICT,
   FOREIGN KEY FK_PLLV_PLANNING_LIJN(DOSS_HDR_ID, LIJN_ID)
	       REFERENCES ART46.PLANNING_LIJN (DOSS_HDR_ID, LIJN_ID) ON DELETE RESTRICT,
   FOREIGN KEY FK_PLLV_DOSSIER (DOSSIER_ID, DOSSIER_TYPE)
	       REFERENCES ART46.DOSSIER (ID, DOSSIER_TYPE) ON DELETE RESTRICT,
   FOREIGN KEY FK_PLLV_CONTRACT (CONTRACT_ID, CONTRACT_TYPE)
	       REFERENCES ART46.DOSSIER (ID, DOSSIER_TYPE) ON DELETE RESTRICT,
   FOREIGN KEY FK_PLLV_BESTEK (BESTEK_ID)
	       REFERENCES ART46.BESTEK (BESTEK_ID) ON DELETE RESTRICT,
   FOREIGN KEY FK_PLLV_FASE (DOSSIER_TYPE, FASE_CODE)
	       REFERENCES ART46.PLANNING_FASE (DOSSIER_TYPE, FASE_CODE) ON DELETE RESTRICT,
   FOREIGN KEY FK_PLLV_FASE_DETAIL (DOSSIER_TYPE, FASE_CODE, FASE_DETAIL_CODE)
	       REFERENCES ART46.PLANNING_FASE_DETAIL (DOSSIER_TYPE, FASE_CODE, FASE_DETAIL_CODE) ON DELETE RESTRICT,
   CONSTRAINT CC_DELETED_JN CHECK (DELETED_JN = 'J' OR DELETED_JN = 'N')
)
;


-----------------------------------------------------------------------------------------;



alter table ART46.DEELOPDRACHT
	add column planning_lijn_id integer
;

alter table ART46.DEELOPDRACHT
   add constraint FK_DO_PLANNING_LIJN foreign key (PLANNING_LIJN_ID)
      references ART46.PLANNING_LIJN (LIJN_ID)
      on delete restrict on update restrict
;


-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.10');
