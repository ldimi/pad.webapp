
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  ART46.DB_VERSIE
where DB_VERSIE = '4.04';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

select count(*)
from art46.ADRES a
WHERE 1 = 1
    and gemeente != upper(gemeente)
;


update art46.ADRES a
set gemeente = upper(gemeente)
WHERE 1 = 1
    and gemeente != upper(gemeente)
;


ALTER TABLE ART46.adres
      ADD CONSTRAINT C_ADRES_GEMEENTE  CHECK (gemeente = upper(gemeente))
;


-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('4.04');
