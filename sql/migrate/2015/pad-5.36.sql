

--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

CREATE FUNCTION ART46.BESTEK_HDR(F_BESKTEK_ID INTEGER)
RETURNS  VARCHAR(500)
DETERMINISTIC NO EXTERNAL ACTION READS SQL DATA
RETURN
    select   coalesce(aa.naam, '') || ' ' || coalesce(aa.voornaam, '')
    from  ART46.ADRES aa
    where
          aa.adres_id = (select min(adres_id) from ART46.BESTEK_ADRES where bestek_id = F_BESKTEK_ID)
;


delete from  art46.db_versie
where db_versie = '5.36';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;


DROP FUNCTION ART46.BESTEK_HDR ;

-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('5.36');