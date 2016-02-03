--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

delete from  ART46.DB_VERSIE
where DB_VERSIE = '4.37';



--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

UPDATE Art46.Brief SET CATEGORIE_ID =  5
where BRIEF_ID in (
                   SELECT AANVRAAG_PDF_BRIEF_ID
                   from Art46.SCHULDVORDERING
                   where AANVRAAG_PDF_BRIEF_ID IS NOT NULL
                   )
    and CATEGORIE_ID=18;

-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('4.37 ');




























