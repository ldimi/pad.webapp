
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  ART46.DB_VERSIE
where DB_VERSIE = '4.05';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

drop table art46.BESTEK_MEETSTAAT_SCHULDVORDERING;

drop table art46.BESTEK_MEETSTAAT_ITEM_SCHULDVORDERING;

drop table art46.BESTEK_MEETSTAAT_ITEM;

drop table art46.BESTEK_MEETSTAAT;

-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('4.05');
