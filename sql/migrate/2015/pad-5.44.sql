

--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  art46.db_versie
where db_versie = '5.44';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

alter table art46.voorstel_deelopdracht_history
    add column webloket_gebruiker_email varchar(120)
;

update art46.voorstel_deelopdracht_history vdh
set webloket_gebruiker_email =
    (select wg.email_address from art46.webloket_gebruiker wg
     where vdh.GEBRUIKER_ID = wg.ID)
where 1 = 1
    and GEBRUIKER_ID is not null
;


-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('5.44');
