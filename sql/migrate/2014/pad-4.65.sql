--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

DROP FUNCTION ART46.DEELOPDRACHT_NR;

delete from  ART46.DB_VERSIE
where DB_VERSIE = '4.65';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;


CREATE FUNCTION ART46.DEELOPDRACHT_NR(V_DEELOPDRACHT_ID INT)
   RETURNS  VARCHAR(8)
   DETERMINISTIC NO EXTERNAL ACTION
   RETURN 'DO-' || lpad(rtrim(cast(V_DEELOPDRACHT_ID as char(5))), 5 , '0')
;


-- -- select op functie definitie op te halen
-- -- funcdef functions
--
-- select  cast(r.TEXT as varchar(32000)),
--     r.*
-- from syscat.ROUTINES r
-- where 1 = 1
--     and r.routineschema = 'ART46'



-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('4.65');





























