--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

DROP FUNCTION ART46.SCHULDVORDERING_NR;

delete from  ART46.DB_VERSIE
where DB_VERSIE = '4.23';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;


CREATE FUNCTION ART46.SCHULDVORDERING_NR(V_VORDERING_ID INT)
   RETURNS  VARCHAR(8)
   DETERMINISTIC NO EXTERNAL ACTION
   RETURN 'SV-' || lpad(rtrim(cast(V_VORDERING_ID as char(5))), 5 , '0')
;


-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('4.23');





























