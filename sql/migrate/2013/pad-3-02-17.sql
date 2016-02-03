
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


drop table ART46.JAARBUDGET_MIJLPAAL;


delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.17';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;



CREATE TABLE ART46.JAARBUDGET_MIJLPAAL
(
	JAAR INTEGER not null,
	MIJLPAAL_D DATE not null,
	PERCENTAGE INTEGER not null,
   	creatie_ts timestamp default '1990-01-01' not null,
    wijzig_ts timestamp default '1990-01-01' not null,
	PRIMARY KEY (JAAR, MIJLPAAL_D),
	CONSTRAINT CC_PERCENTAGE CHECK( 0 < PERCENTAGE and PERCENTAGE <= 100)
);


CREATE TRIGGER ART46.BI_JAARBUDGET_MIJLPAAL_TS  
NO CASCADE BEFORE INSERT ON ART46.JAARBUDGET_MIJLPAAL
REFERENCING
    NEW AS post
FOR EACH ROW MODE DB2SQL
SET
        post.creatie_ts = CURRENT TIMESTAMP,
        post.wijzig_ts = CURRENT TIMESTAMP
;


CREATE TRIGGER ART46.BU_JAARBUDGET_MIJLPAAL_TS   
NO CASCADE BEFORE UPDATE ON ART46.JAARBUDGET_MIJLPAAL
REFERENCING
    OLD AS pre
    NEW AS post
FOR EACH ROW MODE DB2SQL
SET
        post.wijzig_ts = CURRENT TIMESTAMP
;


-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.17');
