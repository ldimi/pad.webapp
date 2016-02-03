--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

drop TRIGGER ART46.BI_WEBLOKET_GEBRUIKER_MAIL;

delete from  ART46.DB_VERSIE
where DB_VERSIE = '4.69';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

update ART46.WEBLOKET_GEBRUIKER
set EMAIL_ADDRESS = lower(EMAIL_ADDRESS)
;

CREATE TRIGGER ART46.BI_WEBLOKET_GEBRUIKER_MAIL
NO CASCADE BEFORE INSERT ON ART46.WEBLOKET_GEBRUIKER
REFERENCING
    NEW AS new
FOR EACH ROW MODE DB2SQL
SET
        new.EMAIL_ADDRESS = lower(new.EMAIL_ADDRESS)
;


-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('4.69');

