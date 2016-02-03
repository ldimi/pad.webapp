
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

alter table ART46.SCHULDVORDERING
    alter column uiterste_d set null
    alter column vordering_d set null
;

alter table ART46.SCHULDVORDERING
     drop constraint CC_UITERSTE_VERIFIC_D
;

call sysproc.admin_cmd('reorg table ART46.SCHULDVORDERING')
;


update art46.SCHULDVORDERING
set sv.UITERSTE_D = null
where sv.VORDERING_ID in
(20,572,1040,1136,1194,1197,1258,1281,2186,4805,5850,108,1155,2442,7184,3901,4250,4488,5599,6338,6339,
7097)


update art46.SCHULDVORDERING
set sv.VORDERING_D = null
where sv.VORDERING_ID in
(7097,6999,6339,6338,5850,5599,4805,4488,4310,4250,3901,2442,2186,1953,1794,1787,1281,1258,1197,1194,
1155,1139,1136,1091,1040,976,572,357,206,108,20)


delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.33';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;


-- invullen ontbrekende vordering_d
update art46.SCHULDVORDERING sv1
set sv1.vordering_d = ( select coalesce(br.in_d, br.IN_STUK_D, br.INSCHRIJF_D)
                    from art46.SCHULDVORDERING sv2
                            inner join art46.BRIEF br
                            on sv2.BRIEF_ID = br.BRIEF_ID
                    where sv2.vordering_id = sv1.vordering_id
                   )
where sv1.vordering_d is null
;



-- invullen ontbrekende uiterste_d
update art46.SCHULDVORDERING sv
set sv.UITERSTE_D = (sv.VORDERING_D + 2 month)
where sv.UITERSTE_D is null
;


-- toevoegen constraints

alter table ART46.SCHULDVORDERING
    alter column uiterste_d drop not null
    alter column vordering_d drop not null
;

alter table ART46.SCHULDVORDERING
     add constraint CC_UITERSTE_VERIFIC_D  check( UITERSTE_VERIFIC_D is not null or CREATIE_TS < '2013-07-03 11:00:00')
;

call sysproc.admin_cmd('reorg table ART46.SCHULDVORDERING')
;



-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.33');
