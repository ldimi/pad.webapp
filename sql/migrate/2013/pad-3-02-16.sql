
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


drop table ART46.JAARBUDGET;


delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.16';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;



CREATE TABLE ART46.JAARBUDGET
(
	JAAR INTEGER not null,
	BUDGET_CODE VARCHAR(15) not null,
	BUDGET INTEGER not null,
   	creatie_ts timestamp default '1990-01-01' not null,
    wijzig_ts timestamp default '1990-01-01' not null,
	PRIMARY KEY (JAAR, BUDGET_CODE),
	foreign key FK_JB_BUDGET_CODE (BUDGET_CODE)
         references ART46.BUDGET_CODE (BUDGET_CODE)
         on delete restrict
);


CREATE TRIGGER ART46.BI_JAARBUDGET_TS  
NO CASCADE BEFORE INSERT ON ART46.JAARBUDGET
REFERENCING
    NEW AS post
FOR EACH ROW MODE DB2SQL
SET
        post.creatie_ts = CURRENT TIMESTAMP,
        post.wijzig_ts = CURRENT TIMESTAMP
;


CREATE TRIGGER ART46.BU_JAARBUDGET_TS   
NO CASCADE BEFORE UPDATE ON ART46.JAARBUDGET
REFERENCING
    OLD AS pre
    NEW AS post
FOR EACH ROW MODE DB2SQL
SET
        post.wijzig_ts = CURRENT TIMESTAMP
;





-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.16');
