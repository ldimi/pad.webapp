
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


drop table ART46.JAARMIJLPAAL;


delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.35';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

-- rename van tabel JAARBUDGET_MIJLPAAL --> JAARMIJLPAAL
drop table JAARBUDGET_MIJLPAAL;

CREATE TABLE ART46.JAARMIJLPAAL
(
	JAAR INTEGER not null,
	MIJLPAAL_D DATE not null,
	PERCENTAGE INTEGER not null,
   	creatie_ts timestamp default '1990-01-01' not null,
    wijzig_ts timestamp default '1990-01-01' not null,
	PRIMARY KEY (JAAR, MIJLPAAL_D),
	CONSTRAINT CC_PERCENTAGE CHECK( 0 < PERCENTAGE and PERCENTAGE <= 100),
    constraint CC_MIJLPAAL_D  check( year(mijlpaal_d) = jaar)
);


CREATE TRIGGER ART46.BI_JAARMIJLPAAL_TS  
NO CASCADE BEFORE INSERT ON ART46.JAARMIJLPAAL
REFERENCING
    NEW AS post
FOR EACH ROW MODE DB2SQL
SET
        post.creatie_ts = CURRENT TIMESTAMP,
        post.wijzig_ts = CURRENT TIMESTAMP
;


CREATE TRIGGER ART46.BU_JAARMIJLPAAL_TS   
NO CASCADE BEFORE UPDATE ON ART46.JAARMIJLPAAL
REFERENCING
    OLD AS pre
    NEW AS post
FOR EACH ROW MODE DB2SQL
SET
        post.wijzig_ts = CURRENT TIMESTAMP
;


-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.35');
