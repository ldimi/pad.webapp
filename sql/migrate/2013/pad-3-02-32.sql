
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


drop table ART46.JAARBUDGET_PER_PROGRAMMA;


delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.32';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;



CREATE TABLE ART46.JAARBUDGET_PER_PROGRAMMA
(
	JAAR INTEGER not null,
	PROGRAMMA_CODE VARCHAR(5) not null,
	BUDGET INTEGER not null,
   	creatie_ts timestamp default '1990-01-01' not null,
    wijzig_ts timestamp default '1990-01-01' not null,
	PRIMARY KEY (JAAR, PROGRAMMA_CODE),
	foreign key FK_JB_PROGRAMMA_CODE (PROGRAMMA_CODE)
         references ART46.PROGRAMMA_TYPE (CODE)
         on delete restrict
);


CREATE TRIGGER ART46.BI_JAARBUDGET_PROGR_TS  
NO CASCADE BEFORE INSERT ON ART46.JAARBUDGET_PER_PROGRAMMA
REFERENCING
    NEW AS post
FOR EACH ROW MODE DB2SQL
SET
        post.creatie_ts = CURRENT TIMESTAMP,
        post.wijzig_ts = CURRENT TIMESTAMP
;


CREATE TRIGGER ART46.BU_JAARBUDGET_PROGR_TS   
NO CASCADE BEFORE UPDATE ON ART46.JAARBUDGET_PER_PROGRAMMA
REFERENCING
    OLD AS pre
    NEW AS post
FOR EACH ROW MODE DB2SQL
SET
        post.wijzig_ts = CURRENT TIMESTAMP
;





-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.32');
