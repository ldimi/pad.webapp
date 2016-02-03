

--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

alter table art46.OFFERTE
    add constraint cc_key_opdrachtgever check(
            (key is null and opdrachtgever_id is null)
            OR
            (key is not null and opdrachtgever_id is not null)
        )
;


delete from  art46.db_versie
where db_versie = '5.41';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

alter table art46.OFFERTE
    drop constraint cc_key_opdrachtgever
;


-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('5.41');