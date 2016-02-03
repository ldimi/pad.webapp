--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;




delete from  ART46.DB_VERSIE
where DB_VERSIE = '4.31';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

update art46.SCHULDVORDERING sv
set sv.VORDERING_CORRECT_BEDRAG = sv.GOEDKEURING_BEDRAG - coalesce(sv.HERZIENING_BEDRAG, 0) + coalesce(sv.BOETE_BEDRAG, 0)
WHERE 1 = 1
    and sv.goedkeuring_bedrag is not null
    and  sv.GOEDKEURING_BEDRAG != coalesce(sv.VORDERING_BEDRAG, 0) + coalesce(sv.HERZIENING_BEDRAG, 0) - coalesce(sv.BOETE_BEDRAG, 0)
;


-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('4.31');




























