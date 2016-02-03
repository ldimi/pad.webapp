--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

delete from  ART46.DB_VERSIE
where DB_VERSIE = '4.27';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;


update art46.SCHULDVORDERING sv
set sv.status = 'VERZONDEN'
WHERE 1 = 1
    and  sv.VORDERING_ID in(
                        select sv2.vordering_id
                        from art46.schuldvordering sv2
                                 left join art46.SCHULDVORDERING_SAP_PROJECT ssp
                                 on sv2.VORDERING_ID = ssp.VORDERING_ID
                                 and ssp.VOLG_NR = 0
                        WHERE 1 = 1
                            and ssp.vordering_id is not null
                       )
    and sv.GOEDKEURING_D is not null
;


update art46.SCHULDVORDERING sv
set sv.status = 'GOEDGEKEURD'
WHERE 1 = 1
    and  sv.status is null
    and sv.GOEDKEURING_D is not null
;


update art46.SCHULDVORDERING sv
set sv.status = 'GEACCEPTEERD'
WHERE 1 = 1
    and sv.status is null
    and sv.AFGEKEURD_JN = 'J'
;
update art46.SCHULDVORDERING sv
set sv.ACCEPTATIE_D = sv.UITERSTE_VERIFIC_D,
    sv.goedkeuring_d = sv.UITERSTE_VERIFIC_D,
    sv.status = 'GOEDGEKEURD'
WHERE 1 = 1
    and sv.status like 'GEACCEPTEERD'
    and sv.ACCEPTATIE_D is null
    and sv.goedkeuring_d is null




update art46.SCHULDVORDERING sv
set sv.status = 'INGEDIEND'
WHERE 1 = 1
    and sv.status is null
;


-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('4.27');




























