--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

drop view art46.v_ambtenaar;

delete from  art46.db_versie
where db_versie = '4.82';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

create view art46.v_ambtenaar as
select
      ka.uid as ambtenaar_id,
      kl.naam_1 as ambtenaar_b,
      kl.tel as tel,
      kl.email,
      ka.functie
from smeg.klant_ambtenaar ka
        inner join smeg.klant kl
        on ka.id = kl.id
;


-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('4.82');

