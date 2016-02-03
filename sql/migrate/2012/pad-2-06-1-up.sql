--select count(*)
--from art46.SCHULDVORDERING sv
--where sv.DEELOPDRACHT_ID = 0
--;
--
--select *
--FROM ART46.DEELOPDRACHT
--WHERE DEELOPDRACHT_ID = 0
--;


update art46.SCHULDVORDERING
set commentaar = null
where commentaar = ''
;

update art46.SCHULDVORDERING
set vordering_nr = null
where vordering_nr = ''
;



update art46.SCHULDVORDERING
set DEELOPDRACHT_ID = null
where DEELOPDRACHT_ID = 0
;


insert into art46.SCHULDVORDERING_SAP_PROJECT
   (VORDERING_ID, VOLG_NR, PROJECT_ID
   , BEDRAG, CREATIE_TS, WIJZIG_TS
   )
(
select DISTINCT sv.VORDERING_ID, 0, spf.PROJECT_ID , sv.GOEDKEURING_BEDRAG, current timestamp, current timestamp
 from art46.SCHULDVORDERING sv
        inner join ART46.SCHULDVORDERING_SAP_PROJECT_FACTUUR spf
        on sv.VORDERING_ID = spf.VORDERING_ID
);



update art46.SCHULDVORDERING
set van_d = van_d + 2000 years
where year(van_d)  < 20
;

update art46.SCHULDVORDERING
set tot_d = tot_d + 2000 years
where year(tot_d)  < 20
;

update art46.SCHULDVORDERING
set GOEDKEURING_D = GOEDKEURING_D + 2000 years
where year(GOEDKEURING_D)  < 20
;

update art46.SCHULDVORDERING
set VORDERING_D = VORDERING_D + 2000 years
where year(VORDERING_D)  < 20
;



