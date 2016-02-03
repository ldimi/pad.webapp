--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.39';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

ALTER TABLE ART46.DEELOPDRACHT    
    ADD CONSTRAINT FK_DO_BESTEK FOREIGN KEY (BESTEK_ID)
    REFERENCES ART46.BESTEK(BESTEK_ID)
;


ALTER TABLE ART46.DEELOPDRACHT
    ALTER COLUMN bedrag drop not null
;


call sysproc.admin_cmd('reorg table ART46.DEELOPDRACHT')
;

ALTER TABLE ART46.DEELOPDRACHT    
    add CONSTRAINT CC_GOEDK_BEDRAG CHECK(
        (goedkeuring_bedrag is null and goedkeuring_d is null) 
        OR
        (goedkeuring_bedrag is not null and (goedkeuring_d is null or
                                             bedrag <= 1.501 * goedkeuring_bedrag))
    )
;




-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.39');
