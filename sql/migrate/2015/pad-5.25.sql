--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

delete from  art46.db_versie
where db_versie = '5.25';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

update art46.adres a
set a.land = 'BE'
where 1 = 1
    and (trim(a.land)  = '' or a.land is null)

alter table art46.adres
    add constraint cc_land check ( not (trim(land)  = '' or land is null) )
;

alter table art46.adres
    add constraint cc_gemeente check ( not trim(gemeente)  = '' )
;


-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('5.25');
