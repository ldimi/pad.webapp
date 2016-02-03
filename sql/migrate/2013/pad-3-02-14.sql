
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;



delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.14';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

alter table ART46.DEELOPDRACHT
   drop constraint FK_DO_PLANNING_LIJN
;


drop TABLE ART46.PLANNING_LIJN_VERSIE;

drop TABLE ART46.PLANNING_LIJN;

drop TABLE ART46.PLANNING_VERSIE;

drop table ART46.PLANNING;

drop table ART46.PLANNING_FASE_DETAIL;

drop table ART46.PLANNING_FASE;


CREATE TABLE ART46.PLANNING_FASE
(
	FASE_CODE VARCHAR(5) not null,
	DOSSIER_TYPE CHAR(1) not null,
	BUDGET_CODE varchar(15) NOT NULL,
	FASE_CODE_B VARCHAR(40),
   	creatie_ts timestamp default '1990-01-01' not null,
     wijzig_ts timestamp default '1990-01-01' not null,
	PRIMARY KEY (FASE_CODE),
	foreign key FK_PLF_BUDGET_CODE (BUDGET_CODE)
         references ART46.BUDGET_CODE (BUDGET_CODE)
         on delete restrict
);


CREATE TRIGGER ART46.BI_PLANNING_FASE_TS  
NO CASCADE BEFORE INSERT ON ART46.PLANNING_FASE
REFERENCING
    NEW AS post
FOR EACH ROW MODE DB2SQL
SET
        post.creatie_ts = CURRENT TIMESTAMP,
        post.wijzig_ts = CURRENT TIMESTAMP
;


CREATE TRIGGER ART46.BU_PLANNING_FASE_TS   
NO CASCADE BEFORE UPDATE ON ART46.PLANNING_FASE
REFERENCING
    OLD AS pre
    NEW AS post
FOR EACH ROW MODE DB2SQL
SET
        post.wijzig_ts = CURRENT TIMESTAMP
;

create table art46.PLANNING_FASE_DETAIL
(
	FASE_CODE VARCHAR(5) not null,
	FASE_DETAIL_CODE VARCHAR(20) not null,
	FASE_DETAIL_CODE_B VARCHAR(40),
   	creatie_ts timestamp default '1990-01-01' not null,
     wijzig_ts timestamp default '1990-01-01' not null,
	PRIMARY KEY (FASE_CODE, FASE_DETAIL_CODE),
    FOREIGN KEY FK_PFD_PFASE(FASE_CODE)
	       REFERENCES ART46.PLANNING_FASE (FASE_CODE) ON DELETE RESTRICT
);

CREATE TRIGGER ART46.BI_PLANNING_FASE_DETAIL_TS  
NO CASCADE BEFORE INSERT ON ART46.PLANNING_FASE_DETAIL
REFERENCING
    NEW AS post
FOR EACH ROW MODE DB2SQL
SET
        post.creatie_ts = CURRENT TIMESTAMP,
        post.wijzig_ts = CURRENT TIMESTAMP
;


CREATE TRIGGER ART46.BU_PLANNING_FASE_DETAIL_TS   
NO CASCADE BEFORE UPDATE ON ART46.PLANNING_FASE_DETAIL
REFERENCING
    OLD AS pre
    NEW AS post
FOR EACH ROW MODE DB2SQL
SET
        post.wijzig_ts = CURRENT TIMESTAMP
;



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
   bestek_omschrijving varchar(150),
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
   FOREIGN KEY FK_PLLV_FASE (FASE_CODE)
	       REFERENCES ART46.PLANNING_FASE (FASE_CODE) ON DELETE RESTRICT,
   FOREIGN KEY FK_PLLV_FASE_DETAIL (FASE_CODE, FASE_DETAIL_CODE)
	       REFERENCES ART46.PLANNING_FASE_DETAIL (FASE_CODE, FASE_DETAIL_CODE) ON DELETE RESTRICT,
   CONSTRAINT CC_DELETED_JN CHECK (DELETED_JN = 'J' OR DELETED_JN = 'N'),
   constraint cc_bestek_omschrijving check (bestek_id is null or bestek_omschrijving is null)
)
;

-----------------------------------------------------------------------------------------;



alter table ART46.DEELOPDRACHT
   add constraint FK_DO_PLANNING_LIJN foreign key (PLANNING_LIJN_ID)
      references ART46.PLANNING_LIJN (LIJN_ID)
      on delete restrict on update restrict
;





-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.14');
