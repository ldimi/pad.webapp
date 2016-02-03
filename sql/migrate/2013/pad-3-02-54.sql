
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


drop table ART46.JAARMIJLPAAL_PROGRAMMA;


delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.54';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

CREATE TABLE ART46.JAARMIJLPAAL_PROGRAMMA
(
	JAAR INTEGER not null,
	MIJLPAAL_D DATE not null,
	PROGRAMMA_CODE VARCHAR(5) not null,
	PERCENTAGE INTEGER not null,
   	creatie_ts timestamp default '1990-01-01' not null,
    wijzig_ts timestamp default '1990-01-01' not null,
	PRIMARY KEY (JAAR, MIJLPAAL_D, PROGRAMMA_CODE),
	CONSTRAINT CC_PERCENTAGE CHECK( 0 < PERCENTAGE and PERCENTAGE <= 100),
    FOREIGN KEY FK_JMPP_JAARMIJLPAAL(JAAR, MIJLPAAL_D)
           REFERENCES ART46.JAARMIJLPAAL (JAAR, MIJLPAAL_D) ON DELETE RESTRICT,
    FOREIGN KEY FK_JMPP_PROGRAMMA(PROGRAMMA_CODE)
           REFERENCES ART46.PROGRAMMA_TYPE (CODE) ON DELETE RESTRICT
);


CREATE TRIGGER ART46.BI_JAARMIJLPAAL_PROGRAMMA_TS  
NO CASCADE BEFORE INSERT ON ART46.JAARMIJLPAAL_PROGRAMMA
REFERENCING
    NEW AS post
FOR EACH ROW MODE DB2SQL
SET
        post.creatie_ts = CURRENT TIMESTAMP,
        post.wijzig_ts = CURRENT TIMESTAMP
;


CREATE TRIGGER ART46.BU_JAARMIJLPAAL_PROGRAMMA_TS   
NO CASCADE BEFORE UPDATE ON ART46.JAARMIJLPAAL_PROGRAMMA
REFERENCING
    OLD AS pre
    NEW AS post
FOR EACH ROW MODE DB2SQL
SET
        post.wijzig_ts = CURRENT TIMESTAMP
;


-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.54');
