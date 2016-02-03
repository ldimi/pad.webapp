

-- Een voorstel_deelopdracht is onterecht 'TOEGEKEND',
--    wordt teruggezet in de vorige toestand : 'BEOORDELEN'
--  20150211 : sv-10243

select *
from art46.voorstel_deelopdracht vdo
where 1 = 1
    and vdo.id = 21

select *
from art46.DEELOPDRACHT do
where 1 = 1
    and do.VOORSTEL_DEELOPDRACHT_ID = 21


select *
from art46.VOORSTEL_DEELOPDRACHT_HISTORY vdoh
where 1 = 1
    and vdoh.VOORSTEL_DEELOPDRACHT_ID = 21

delete
from art46.VOORSTEL_DEELOPDRACHT_HISTORY vdoh
where 1 = 1
    and vdoh.VOORSTEL_DEELOPDRACHT_ID = 21
    and vdoh.status = 'TOEGEKEND'

    

update art46.voorstel_deelopdracht vdo
set status = 'BEOORDELEN',
    beslissings_datum = null
where 1 = 1
    and vdo.id = 21


update art46.DEELOPDRACHT do
set do.VOORSTEL_DEELOPDRACHT_ID = null
where 1 = 1
    and do.VOORSTEL_DEELOPDRACHT_ID = 21