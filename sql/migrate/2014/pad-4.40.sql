--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

DROP FUNCTION ART46.VOORSTEL_DEELOPDRACHT_NR;

delete from  ART46.DB_VERSIE
where DB_VERSIE = '4.40';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;


CREATE FUNCTION ART46.VOORSTEL_DEELOPDRACHT_NR(V_VOORSTEL_DEELOPDRACHT_ID INT)
   RETURNS  VARCHAR(8)
   DETERMINISTIC NO EXTERNAL ACTION
   RETURN 'VD-' || lpad(rtrim(cast(V_VOORSTEL_DEELOPDRACHT_ID as char(5))), 5 , '0')
;


-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('4.40');





























