--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

ALTER TABLE ART46.DEELOPDRACHT    
    drop CONSTRAINT CC_GOEDK_BEDRAG
;

ALTER TABLE ART46.DEELOPDRACHT    
    add CONSTRAINT CC_GOEDK_BEDRAG CHECK(
        (goedkeuring_bedrag is null and goedkeuring_d is null) 
        OR
        (goedkeuring_bedrag is not null and (goedkeuring_d is null or
                                             bedrag <= 1.501 * goedkeuring_bedrag))
    )
;



delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.51';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

ALTER TABLE ART46.DEELOPDRACHT    
    drop CONSTRAINT CC_GOEDK_BEDRAG
;

ALTER TABLE ART46.DEELOPDRACHT    
    add CONSTRAINT CC_GOEDK_BEDRAG CHECK(
        (goedkeuring_bedrag is null and goedkeuring_d is null) 
        OR
        (goedkeuring_bedrag is not null and (goedkeuring_d is null or
                                             bedrag <= 1.501 * goedkeuring_bedrag))
        OR afsluit_d is not null
    )
;




-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.51');
