
-- De schuldvordering SV-11542 werd voor een verkeerd bedrag goedgekeurd en door Ann ondertekend en zou opnieuw in status ingediend moeten komen.
--     -> schulvordering aanpasssen
--     -> antwoord_pdf_brief verwijderen in pad en alfresco
--     -> schuldvordering_status_history herstellen
--
--     -> wbs nog te verwijderen (coordinatie christine todts en boekhouding.     
--
-- SV-11724 (mail ctodts 04 jun)
-- SV-12021 (mail ctodts 08 jul)
-- SV-13825 (mail ctodts 08 dec)
-- SV-12012 (vraag dirk 16 dec)

select
    sv.*
from art46.schuldvordering sv
where 1 = 1
     and sv.VORDERING_ID = 12012  -- 13825 -- 12021 -- 11724 -- 11542
;


-- laatste statussen verwijderen : manueel de statussen 'VERZONDEN', 'ONDERTEKEND', 'BEOORDEELD' verwijderd in squirrel
select
    sv.*
from art46.schuldvordering_status_history sv
where 1 = 1
     and sv.SCHULDVORDERING_ID = 12012  -- 13825 -- 12021 -- 11724 -- 11542
;



--   antwoord_pdf_brief_id noteren om nadien manueel te verwijderen in toepassing Pad (wordt dan ook in alfresco verwijderd)
-- 123354 -- 129732 -- 123651 -- 122225 -- 121796

update art46.schuldvordering
set (afgekeurd_jn, acceptatie_d, goedkeuring_d, antwoord_pdf_brief_id, print_d, status) = ('N', null, null, null, null, 'INGEDIEND')
where 1 = 1
  and VORDERING_ID = 12012  -- 13825 -- 12021 -- 11724 -- 11542

--wbs moet nog verwijderd worden.