--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  art46.db_versie
where db_versie = '4.81';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

alter table art46.offerte
    rename column offerte_orgineel_id to offerte_origineel_id
;

call sysproc.admin_cmd('reorg table art46.offerte')
;


alter table art46.offerte
   add constraint fk_off_origineel foreign key (offerte_origineel_id)
      references art46.offerte (id)
      on delete restrict on update restrict
;




-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('4.81');

