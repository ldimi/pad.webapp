--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


drop view art46.v_schuldvordering_status_history;



delete from  art46.db_versie
where db_versie = '5.01';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;


create view art46.v_schuldvordering_status_history as
select
    ssh.id,
    ssh.schuldvordering_id,
    ssh.gebruiker_id,
    wg.email_address,
    ssh.dossierhouder_id,
    ssh.datum,
    ssh.motivatie,
    ssh.status,
    ss.name as status_pad,
    ss.name_web_loker as status_webloket
from  art46.schuldvordering_status_history ssh
        left join  art46.schuldvordering_status ss
        on ssh.status = ss.key
      left join art46.webloket_gebruiker wg
      on ssh.gebruiker_id = wg.id
;


-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('5.01');


