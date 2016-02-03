

-- Een schulvordering is onterecht afgekeurd, (nog niet beoordeeld door afdelingshoofd)
--  20150211 : sv-10243

select
    sv.*
from art46.schuldvordering sv
where 1 = 1
     and sv.VORDERING_ID = ?
;

-- TODO : de laatste status moet hier verwijderd worden in schuldvordering_status_history
--     Dit heb ik manueel in squirrel uitgevoerd.

select
    ssh.*
from art46.schuldvordering_status_history ssh
where 1 = 1
     and ssh.SCHULDVORDERING_ID = ?
;


-- update naar vorige status
--   antwoord_pdf_brief_id noteren om nadien manueel te verwijderen

update art46.schuldvordering
set (afgekeurd_jn, acceptatie_d, goedkeuring_d, antwoord_pdf_brief_id, print_d, status) = ('N', null, null, null, null, 'INGEDIEND')
where 1 = 1
  and VORDERING_ID = ?


-- TODO antwoord_pdf_brief_id manueel verwijderen, via pad brief detail scherm (verwijdert tegelijk in alfresco)