
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

ALTER TABLE ART46.BESTEK    
    DROP CONSTRAINT FK_BE_TYPE
;


delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.26';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

ALTER TABLE ART46.BESTEK    
    ADD CONSTRAINT FK_BE_TYPE FOREIGN KEY (type_id)
    REFERENCES ART46.BESTEK_TYPE(type_id);


-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.26');
