--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

alter table art46.brief
   drop FOREIGN KEY FK_BR_bestek
;


delete from  art46.db_versie
where db_versie = '4.74';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

select *
from art46.brief br
where 1 = 1
    and br.BESTEK_ID is not null
    and not exists (select * from art46.bestek be where  br.bestek_id = be.bestek_id)

update art46.brief br
set br.bestek_id = null
where 1 = 1
    and br.BESTEK_ID is not null
    and not exists (select * from art46.bestek be where  br.bestek_id = be.bestek_id)
;


alter table art46.brief
   add FOREIGN KEY FK_BR_bestek (bestek_id)
           REFERENCES ART46.bestek (bestek_id) ON DELETE RESTRICT
;



-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('4.74');

