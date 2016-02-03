
CREATE TABLE ART46.PROGRAMMA_TYPE
(
   CODE varchar(5) NOT NULL,
   PROGRAMMA_TYPE_B varchar(40) NOT NULL,
   creatie_ts timestamp default '1990-01-01' not null,
   wijzig_ts timestamp default '1990-01-01' not null,
   primary key(CODE)
)
;

CREATE TRIGGER ART46.BI_PROGRAMMA_TYPE_TS  
NO CASCADE BEFORE INSERT ON ART46.PROGRAMMA_TYPE
REFERENCING
    NEW AS post
FOR EACH ROW MODE DB2SQL
SET
        post.creatie_ts = CURRENT TIMESTAMP,
        post.wijzig_ts = CURRENT TIMESTAMP
;


CREATE TRIGGER ART46.BU_PROGRAMMA_TYPE_TS   
NO CASCADE BEFORE UPDATE ON ART46.PROGRAMMA_TYPE
REFERENCING
    OLD AS pre
    NEW AS post
FOR EACH ROW MODE DB2SQL
SET
        post.wijzig_ts = CURRENT TIMESTAMP
;



--insert into ART46.PROGRAMMA_TYPE(CODE, PROGRAMMA_TYPE_B)
--values ('prog1', 'Programma 1') , ('prog2', 'Programma 2');


alter table ART46.DOSSIER
	add column PROGRAMMA_CODE varchar(5)
	add constraint FK_DO_PROGRAMMA foreign key (PROGRAMMA_CODE)
         references ART46.PROGRAMMA_TYPE (CODE)
         on delete restrict on update restrict
;



-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('2.08.4');
