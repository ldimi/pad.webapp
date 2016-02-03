--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

delete from  ART46.DB_VERSIE
where DB_VERSIE = '4.45';



--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

ALTER TABLE ART46.ADRES_TYPE
    add primary key(ADRES_TYPE_ID)
;

ALTER TABLE ART46.ADRES
    ADD CONSTRAINT FK_ADRES_TYPE FOREIGN KEY (type_id)
    REFERENCES ART46.ADRES_TYPE(ADRES_TYPE_ID)
;


-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('4.45');