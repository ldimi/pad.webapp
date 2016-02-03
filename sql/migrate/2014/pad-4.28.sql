--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

alter table art46.SCHULDVORDERING
    drop constraint cc_acceptatie_d_goedkeuring_d
;

delete from  ART46.DB_VERSIE
where DB_VERSIE = '4.28';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

-- acceptatie datum moet ingevuld zijn indien goedkeuring_d ingevuld is.;


update art46.SCHULDVORDERING sv
     set acceptatie_d = goedkeuring_d
WHERE 1 = 1
    and sv.ACCEPTATIE_D is null
    and sv.GOEDKEURING_D is not null
;

alter table art46.SCHULDVORDERING
    add constraint cc_acceptatie_d_goedkeuring_d CHECK((acceptatie_d is not null and goedkeuring_d is not null) OR
                                          goedkeuring_d is null)
;



-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('4.28');




























