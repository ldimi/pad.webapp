

-- OM oude schuldvorderingen uit takenlijst te verwijderen
--    toestand wordt op verzonden gezet, (en afgekeurd zoals gevraagd door CT)
--    bijhorende datums moeten ook met een zinvolle waarde ingevuld worden.

select
    sv.*
from art46.schuldvordering sv
where 1 = 1
     and sv.VORDERING_ID in (7285, 7290)

select
    sv.*
from art46.schuldvordering sv
where 1 = 1
    and sv.AFGEKEURD_JN = 'J'


update art46.schuldvordering sv
set (goedkeuring_d, acceptatie_d, print_d, status, afgekeurd_jn) = (uiterste_d, uiterste_d, uiterste_d, 'VERZONDEN', 'J')
where 1 = 1
     and sv.VORDERING_ID in (7285, 7290)

