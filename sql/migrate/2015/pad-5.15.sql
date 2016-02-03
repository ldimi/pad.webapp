--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  art46.db_versie
where db_versie = '5.15';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

create table art46.adres_verwijderd like art46.adres
;

insert into art46.adres_verwijderd
select
	a.*
from art46.adres a
       left join art46.brief br
       on a.ADRES_ID = br.ADRES_ID
     left join art46.BESTEK_ADRES ba
     on ba.ADRES_ID = a.adres_id
          left join art46.DOSSIER_ADRES da
          on da.ADRES_ID = a.adres_id
     left join art46.ADRES_CONTACT ac
     on a.ADRES_ID = ac.ADRES_ID
where 1 = 1
    and br.ADRES_ID is null
    and ba.ADRES_ID is null
    and da.ADRES_ID is null
    and ac.ADRES_ID is null
    --and a.STOP_S = 1
;


delete
from art46.adres
where adres_id in (select adres_id from art46.ADRES_VERWIJDERD
                   -- where adres_id between 25000 and 30000
                   )
      --and adres_id between 25000 and 30000
;


select *
from art46.adres
where adres_id in (select adres_id from art46.ADRES_VERWIJDERD)
;



-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('5.15');



