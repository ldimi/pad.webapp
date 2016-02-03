--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

alter table art46.offerte
   drop foreign key fk_off_opdrachtgever
;

alter table art46.offerte
   drop column opdrachtgever_id
;

call sysproc.admin_cmd('reorg table art46.offerte');


alter table art46.offerte
   add column opdrachtgever varchar(24)
;

call sysproc.admin_cmd('reorg table art46.offerte');


delete from  art46.db_versie
where db_versie = '4.63';



--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

alter table art46.offerte
   add column opdrachtgever_id integer
   foreign key fk_off_opdrachtgever(opdrachtgever_id)
           references art46.bestek_adres (bestek_adres_id) on delete restrict
;

call sysproc.admin_cmd('reorg table art46.offerte');

alter table art46.offerte
   drop column opdrachtgever
;

call sysproc.admin_cmd('reorg table art46.offerte');

-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('4.63');