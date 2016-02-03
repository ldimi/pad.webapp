
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

drop view ART46.V_PLANNING_FASE;

alter table art46.PLANNING_FASE
	drop column BUDGET_CODE
   	add column creatie_ts timestamp
     add column wijzig_ts timestamp
	drop constraint FK_PLF_BUDGET_CODE
;


DROP TABLE ART46.BUDGET_CODE;

delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.12';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;





CREATE TABLE ART46.BUDGET_CODE
(
   BUDGET_CODE varchar(15) NOT NULL,
   BUDGET_CODE_B varchar(40) NOT NULL,
   creatie_ts timestamp default '1990-01-01' not null,
   wijzig_ts timestamp default '1990-01-01' not null,
   primary key(BUDGET_CODE)
)
;

CREATE TRIGGER ART46.BI_BUDGET_CODE_TS  
NO CASCADE BEFORE INSERT ON ART46.BUDGET_CODE
REFERENCING
    NEW AS post
FOR EACH ROW MODE DB2SQL
SET
        post.creatie_ts = CURRENT TIMESTAMP,
        post.wijzig_ts = CURRENT TIMESTAMP
;


CREATE TRIGGER ART46.BU_BUDGET_CODE_TS   
NO CASCADE BEFORE UPDATE ON ART46.BUDGET_CODE
REFERENCING
    OLD AS pre
    NEW AS post
FOR EACH ROW MODE DB2SQL
SET
        post.wijzig_ts = CURRENT TIMESTAMP
;


INSERT INTO "ART46"."BUDGET_CODE" (BUDGET_CODE,BUDGET_CODE_B)
VALUES 
('onderzoek','onderzoek'),
('werken','werken'),
('afval','afval'),
('Umicore werken','Umicore werken'),
('feu werken','feu werken')
;



alter table art46.PLANNING_FASE
	add column BUDGET_CODE varchar(15)
   	add column creatie_ts timestamp default '1990-01-01' not null
     add column wijzig_ts timestamp default '1990-01-01' not null
	add constraint FK_PLF_BUDGET_CODE foreign key (BUDGET_CODE)
         references ART46.BUDGET_CODE (BUDGET_CODE)
         on delete restrict on update restrict
;

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


call sysproc.admin_cmd('reorg table ART46.PLANNING_FASE')
;









create view ART46.V_PLANNING_FASE as
(
	select pf.DOSSIER_TYPE,
		  pf.FASE_CODE,
		  pf.FASE_CODE_B,
		  pf.BUDGET_CODE,
		  case when exists (select *
		  				from art46.PLANNING_FASE_DETAIL pfd
		  				where pfd.FASE_CODE = pf.fase_code)
		  	 then 'J'
		  	 else 'N'
		 end as heeft_details_JN
	from art46.PLANNING_FASE pf
);


-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.12');
